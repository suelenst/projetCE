package br.edu.ifrs.restinga.ads.projetce.controller;

import br.edu.ifrs.restinga.ads.projetce.dao.UsuarioDAO;
import br.edu.ifrs.restinga.ads.projetce.modelo.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/usuarios")
public class Usuarios {

    @Autowired
    UsuarioDAO usuarioDAO;

    @RequestMapping(path = "/pesquisar/nome", method = RequestMethod.GET)
    public Iterable<Usuario> pesquisaPorNome(
            @RequestParam(required = false) String igual,
            @RequestParam(required = false) String contem,
            @RequestParam(required = false, defaultValue = "0") int pagina) {
        PageRequest pageRequest = new PageRequest(pagina, 5);
        if (igual != null) {
            return usuarioDAO.findByNome(igual, pageRequest);
        } else {
            return usuarioDAO.findByNomeContainingOrderByNome(contem, pageRequest);
        }
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public Iterable<Usuario> listar() {
        return usuarioDAO.findAll();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Usuario recuperar(@PathVariable int id) {
        return usuarioDAO.findOne(id);
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario inserir(@RequestBody Usuario usuario) throws Exception {
        usuario.setId(0);
        return usuarioDAO.save(usuario);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void apagar(@PathVariable int id) {
        if (usuarioDAO.exists(id)) {
            usuarioDAO.delete(id);
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void atualizar(@PathVariable int id, @RequestBody Usuario usuario) throws Exception {
        if (usuarioDAO.exists(id)) {
            usuario.setId(id);
            usuarioDAO.save(usuario);
        }
    }
}