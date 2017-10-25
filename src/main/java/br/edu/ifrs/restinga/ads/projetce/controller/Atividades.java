package br.edu.ifrs.restinga.ads.projetce.controller;

import br.edu.ifrs.restinga.ads.projetce.dao.AtividadeDAO;
import br.edu.ifrs.restinga.ads.projetce.modelo.Atividade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/atividades")
public class Atividades {

    @Autowired
    AtividadeDAO atividadeDAO;

    @RequestMapping(path = "/pesquisar/nome", method = RequestMethod.GET)
    public Iterable<Atividade> pesquisaPorNome(
            @RequestParam(required = false) String igual,
            @RequestParam(required = false) String contem,
            @RequestParam(required = false, defaultValue = "0") int pagina) {
        PageRequest pageRequest = new PageRequest(pagina, 5);
        if (igual != null) {
            return atividadeDAO.findByNome(igual, pageRequest);
        } else {
            return atividadeDAO.findByNomeContainingOrderByNome(contem, pageRequest);
        }
    }

    @RequestMapping(path = "/pesquisar", method = RequestMethod.GET)
    public Iterable<Atividade> listar() {
        return atividadeDAO.findAll();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Atividade recuperar(@PathVariable int id) {
        return atividadeDAO.findOne(id);
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Atividade inserir(@RequestBody Atividade atividade) throws Exception {
        atividade.setId(0);
        return atividadeDAO.save(atividade);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void apagar(@PathVariable int id) {
        if (atividadeDAO.exists(id)) {
            atividadeDAO.delete(id);
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void atualizar(@PathVariable int id, @RequestBody Atividade atividade) throws Exception {
        if (atividadeDAO.exists(id)) {
            atividade.setId(id);
            atividadeDAO.save(atividade);
        }
    }

}