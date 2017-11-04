package br.edu.ifrs.restinga.ads.projetce.controller;

import br.edu.ifrs.restinga.ads.projetce.modelo.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import br.edu.ifrs.restinga.ads.projetce.dao.AreaDAO;

@RestController
@RequestMapping(path = "/areas")
public class Areas {

    @Autowired
    AreaDAO areaDAO;

    @RequestMapping(path = "/pesquisar/nome", method = RequestMethod.GET)
    public Iterable<Area> pesquisaPorNome(
            @RequestParam(required = false) String igual,
            @RequestParam(required = false) String contem,
            @RequestParam(required = false, defaultValue = "0") int pagina) {
        PageRequest pageRequest = new PageRequest(pagina, 5);
        if (igual != null) {
            return areaDAO.findByNome(igual, pageRequest);
        } else {
            return areaDAO.findByNomeContainingOrderByNome(contem, pageRequest);
        }
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public Iterable<Area> listar(@RequestParam(required = false, defaultValue = "0") int pagina) {
        PageRequest pageRequest = new PageRequest(pagina, 5);
        return areaDAO.findAll(pageRequest);
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Area inserir(@RequestBody Area area) throws Exception {
        area.setId(0);
        Area areaSalvo = areaDAO.save(area);
        return areaSalvo;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Area recuperar(@PathVariable int id) {
        return areaDAO.findOne(id);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void atualizar(@PathVariable int id, @RequestBody Area area) throws Exception {
        if (areaDAO.exists(id)) {
            area.setId(id);
            areaDAO.save(area);
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void apagar(@PathVariable int id) {
        if (areaDAO.exists(id)) {
            areaDAO.delete(id);
        }

    }

}