package br.edu.ifrs.restinga.ads.projetce.controller;

import br.edu.ifrs.restinga.ads.projetce.dao.EventoDAO;
import br.edu.ifrs.restinga.ads.projetce.modelo.Evento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api")
public class Eventos {

    @Autowired
    EventoDAO eventoDAO;

    @RequestMapping(path = "/eventos/pesquisar/nome", method = RequestMethod.GET)
    public Iterable<Evento> pesquisaPorNome(
            @RequestParam(required = false) String igual,
            @RequestParam(required = false) String contem,
            @RequestParam(required = false, defaultValue = "0") int pagina) {
        PageRequest pageRequest = new PageRequest(pagina, 5);
        if (igual != null) {
            return eventoDAO.findByNome(igual, pageRequest);
        } else {
            return eventoDAO.findByNomeContainingOrderByNome(contem, pageRequest);
        }
    }

    @RequestMapping(path = "/eventos", method = RequestMethod.GET)
    public Iterable<Evento> listar() {
        return eventoDAO.findAll();
    }

    @RequestMapping(path = "/eventos/{id}", method = RequestMethod.GET)
    public Evento recuperar(@PathVariable int id) {
        return eventoDAO.findOne(id);
    }

    @RequestMapping(path = "/eventos", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Evento inserir(@RequestBody Evento evento) throws Exception {
        evento.setId(0);
        return eventoDAO.save(evento);
    }

    @RequestMapping(path = "/eventos/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void apagar(@PathVariable int id) {
        if (eventoDAO.exists(id)) {
            eventoDAO.delete(id);
        }
    }

    @RequestMapping(path = "/eventos/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void atualizar(@PathVariable int id, @RequestBody Evento evento) throws Exception {
        if (eventoDAO.exists(id)) {
            evento.setId(id);
            eventoDAO.save(evento);
        }
    }

}