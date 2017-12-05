package br.edu.ifrs.restinga.ads.projetce.controller;

import br.edu.ifrs.restinga.ads.projetce.dao.ProjetoDAO;
import br.edu.ifrs.restinga.ads.projetce.modelo.Projeto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api")
public class Projetos {

    @Autowired
    ProjetoDAO projetoDAO;

    @RequestMapping(path = "/projetos/pesquisar/nome", method = RequestMethod.GET)
    public Iterable<Projeto> pesquisaPorNome(
            @RequestParam(required = false) String igual,
            @RequestParam(required = false) String contem,
            @RequestParam(required = false, defaultValue = "0") int pagina) {
        PageRequest pageRequest = new PageRequest(pagina, 5);
        if (igual != null) {
            return projetoDAO.findByNome(igual, pageRequest);
        } else {
            return projetoDAO.findByNomeContainingOrderByNome(contem, pageRequest);
        }
    }

    @RequestMapping(path = "/projetos", method = RequestMethod.GET)
    public Iterable<Projeto> listar() {
        return projetoDAO.findAll();
    }

    @RequestMapping(path = "/projetos/{id}", method = RequestMethod.GET)
    public Projeto recuperar(@PathVariable int id) {
        return projetoDAO.findOne(id);
    }

    @RequestMapping(path = "/projetos", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Projeto inserir(@RequestBody Projeto projeto) throws Exception {
        projeto.setId(0);
        return projetoDAO.save(projeto);
    }

    @RequestMapping(path = "/projetos/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void apagar(@PathVariable int id) {
        if (projetoDAO.exists(id)) {
            projetoDAO.delete(id);
        }
    }

    @RequestMapping(path = "/projetos/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void atualizar(@PathVariable int id, @RequestBody Projeto projeto) throws Exception {
        if (projetoDAO.exists(id)) {
            projeto.setId(id);
            projetoDAO.save(projeto);
        }
    }

}