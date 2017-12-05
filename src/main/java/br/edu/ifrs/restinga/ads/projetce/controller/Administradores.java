package br.edu.ifrs.restinga.ads.projetce.controller;

import br.edu.ifrs.restinga.ads.projetce.dao.AdministradorDAO;
import br.edu.ifrs.restinga.ads.projetce.modelo.Administrador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api")
public class Administradores {

    @Autowired
    AdministradorDAO administradorDAO;

    @RequestMapping(path = "/administradores/pesquisar/nome", method = RequestMethod.GET)
    public Iterable<Administrador> pesquisaPorNome(
            @RequestParam(required = false) String igual,
            @RequestParam(required = false) String contem,
            @RequestParam(required = false, defaultValue = "0") int pagina) {
        PageRequest pageRequest = new PageRequest(pagina, 5);
        if (igual != null) {
            return administradorDAO.findByNome(igual, pageRequest);
        } else {
            return administradorDAO.findByNomeContainingOrderByNome(contem, pageRequest);
        }
    }
    
    @RequestMapping(path = "/administradores", method = RequestMethod.GET)
    public Iterable<Administrador> listar(@RequestParam(required = false, defaultValue = "0") int pagina) {
        PageRequest pageRequest = new PageRequest(pagina, 5);
        return administradorDAO.findAll(pageRequest);
    }

    @RequestMapping(path = "/administradores/{id}", method = RequestMethod.GET)
    public Administrador recuperar(@PathVariable int id) {
        return administradorDAO.findOne(id);
    }

    @RequestMapping(path = "/administradores", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Administrador inserir(@RequestBody Administrador administrador) throws Exception {
        administrador.setId(0);
        return administradorDAO.save(administrador);
    }

    @RequestMapping(path = "/administradores/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void apagar(@PathVariable int id) {
        if (administradorDAO.exists(id)) {
            administradorDAO.delete(id);
        }
    }

    @RequestMapping(path = "/administradores/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void atualizar(@PathVariable int id, @RequestBody Administrador administrador) throws Exception {
        if (administradorDAO.exists(id)) {
            administrador.setId(id);
            administradorDAO.save(administrador);
        }
    }
}