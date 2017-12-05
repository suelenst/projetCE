package br.edu.ifrs.restinga.ads.projetce.controller;


import br.edu.ifrs.restinga.ads.projetce.dao.ProgramacaoDAO;
import br.edu.ifrs.restinga.ads.projetce.modelo.Programacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api")
public class Programacoes {

    @Autowired
    ProgramacaoDAO programacaoDAO;



    @RequestMapping(path = "/programacoes/pesquisar", method = RequestMethod.GET)
    public Iterable<Programacao> listar() {
        return programacaoDAO.findAll();
    }

    @RequestMapping(path = "/programacoes/{id}", method = RequestMethod.GET)
    public Programacao recuperar(@PathVariable int id) {
        return programacaoDAO.findOne(id);
    }

    @RequestMapping(path = "/programacoes", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Programacao inserir(@RequestBody Programacao programacao) throws Exception {
        programacao.setId(0);
        return programacaoDAO.save(programacao);
    }

    @RequestMapping(path = "/programacoes/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void apagar(@PathVariable int id) {
        if (programacaoDAO.exists(id)) {
            programacaoDAO.delete(id);
        }
    }

    @RequestMapping(path = "/programacoes/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void atualizar(@PathVariable int id, @RequestBody Programacao programacao) throws Exception {
        if (programacaoDAO.exists(id)) {
            programacao.setId(id);
            programacaoDAO.save(programacao);
        }
    }

}