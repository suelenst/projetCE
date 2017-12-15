/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.ads.projetce.controller;

import br.edu.ifrs.restinga.ads.projetce.aut.ForbiddenException;
import br.edu.ifrs.restinga.ads.projetce.aut.PessoaAut;
import br.edu.ifrs.restinga.ads.projetce.dao.PessoaDAO;
import br.edu.ifrs.restinga.ads.projetce.modelo.Administrador;
import br.edu.ifrs.restinga.ads.projetce.modelo.Pessoa;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping(path = "/api")
public class Pessoas {

    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @RequestMapping(path = "/pessoas", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Pessoa inserir( @RequestBody Pessoa pessoa) throws Exception {
        pessoa.setId(0);
        pessoa.setSenha(PASSWORD_ENCODER.encode(pessoa.getNovaSenha()));


//        if (pessoaAut == null || !pessoaAut.getPessoa().getPermissoes().contains("administrador")) {  
//            ArrayList<String> permissao = new ArrayList<String>();
//            permissao.add("usuario");           
//            pessoa.setPermissoes(permissao);
//        }


        Pessoa pessoaSalvo = pessoaDAO.save(pessoa);
        return pessoaSalvo;
    }

    @Autowired
    PessoaDAO pessoaDAO;

    //    @PreAuthorize("hasAuthority('administrador')")
    
    @RequestMapping(path = "/pessoas", method = RequestMethod.GET)
    public Iterable<Pessoa> listar(@AuthenticationPrincipal PessoaAut pessoaAut, @RequestParam(required = false, defaultValue = "0") int pagina) {
        if ( pessoaAut.getPessoa().getPermissoes().contains("administrador")){
                PageRequest pageRequest = new PageRequest(pagina, 5);
                return pessoaDAO.findAll(pageRequest);
        } else {
            throw new ForbiddenException("Acesso somente de administrador!");
        }

    }

    @RequestMapping(path = "/pessoas/{id}", method = RequestMethod.GET)
    public Pessoa recuperar(@AuthenticationPrincipal PessoaAut pessoaAut, @PathVariable int id) {
        if (pessoaAut.getPessoa().getId() == id
                || pessoaAut.getPessoa().getPermissoes().contains("administrador")) {
            return pessoaDAO.findOne(id);
        } else {
            throw new ForbiddenException("Não é permitido acessar dados de outro usuários");
        }
    }

    @RequestMapping(path = "/pessoas/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void atualizar(@PathVariable int id, @RequestBody Pessoa pessoa) throws Exception {
        if (pessoaDAO.exists(id)) {    
            pessoa.setId(id);
            
            Pessoa pessoaAnterior = pessoaDAO.findOne(id);            
            pessoa.setSenha(pessoaAnterior.getSenha());
            
            pessoaDAO.save(pessoa);
        }
    }

    @RequestMapping(path = "/pessoas/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void apagar(@AuthenticationPrincipal PessoaAut pessoaAut, @PathVariable int id) {
        if ( pessoaAut.getPessoa().getPermissoes().contains("administrador")){
            if (pessoaDAO.exists(id)) {
                pessoaDAO.delete(id);
            }
        } else {
            throw new ForbiddenException("Acesso somente de administrador!");
        }

    }

    public static final String SEGREDO = "string grande para c*, usada como chave para assinatura! Sorvete";
    
    @RequestMapping(path = "/pessoas/login", method = RequestMethod.GET)
//    public Pessoa login(@AuthenticationPrincipal PessoaAut pessoaAut) {
//        return pessoaAut.getPessoa();
//    }
    
    public ResponseEntity<Pessoa> login(@AuthenticationPrincipal PessoaAut pessoaAut) 

        throws IllegalArgumentException, UnsupportedEncodingException {

        Algorithm algorithm = Algorithm.HMAC256(SEGREDO);

        Calendar agora = Calendar.getInstance();

        agora.add(Calendar.MINUTE, 15);

        Date expira = agora.getTime();


        String token = JWT.create()

                .withClaim("id", pessoaAut.getPessoa().getId()).

                withExpiresAt(expira).

                sign(algorithm);

        HttpHeaders respHeaders = new HttpHeaders();

        respHeaders.set("token", token);


        return new ResponseEntity<>(pessoaAut.getPessoa(), respHeaders, HttpStatus.OK);

    }


    @RequestMapping(path = "/pessoas/{id}/foto", method = RequestMethod.POST)
    public void inserirFoto(@AuthenticationPrincipal PessoaAut pessoaAut, @PathVariable int id,
                            @RequestParam("arquivo") MultipartFile uploadfiles) {
        
        if (pessoaAut.getPessoa().getId() == id
            || pessoaAut.getPessoa().getPermissoes().contains("administrador")) {

            Pessoa pessoa = pessoaDAO.findOne(id);

            try {
                pessoa.setTipoFoto(uploadfiles.getContentType());
                pessoa.setFoto(uploadfiles.getBytes());
                pessoaDAO.save(pessoa);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            throw new ForbiddenException("Não é permitido acessar dados de outro usuários");
        }
    }

    @RequestMapping(value = "/pessoas/{id}/foto", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> recuperarFoto(@AuthenticationPrincipal PessoaAut pessoaAut, @PathVariable int id)
            throws IOException {
            
        
            Pessoa pessoa = pessoaDAO.findOne(id);
            if (pessoa.getFoto() == null) {
                    HttpHeaders respHeaders = new HttpHeaders();
                    respHeaders.setContentType(MediaType.valueOf("image/png"));
                    InputStreamResource img =
                    new InputStreamResource(new ByteArrayInputStream(Files.readAllBytes(Paths.get("sem.png"))));
                return new ResponseEntity<InputStreamResource>(img, respHeaders, HttpStatus.OK);
            }
            HttpHeaders respHeaders = new HttpHeaders();
            respHeaders.setContentType(MediaType.valueOf(pessoa.getTipoFoto()));
            InputStreamResource img =
                    new InputStreamResource(new ByteArrayInputStream(pessoa.getFoto()));
            return new ResponseEntity<InputStreamResource>(img, respHeaders, HttpStatus.OK);
            
        
    
    }

}
