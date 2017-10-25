package br.edu.ifrs.restinga.ads.projetce.controller;

import br.edu.ifrs.restinga.ads.projetce.dao.AreaInteresseDAO;
import br.edu.ifrs.restinga.ads.projetce.modelo.AreaInteresse;
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

@RestController
@RequestMapping(path = "/areasinteresse")
public class AreasInteresse {

    @Autowired
    AreaInteresseDAO areaInteresseDAO;

    @RequestMapping(path = "/pesquisar/nome", method = RequestMethod.GET)
    public Iterable<AreaInteresse> pesquisaPorNome(
            @RequestParam(required = false) String igual,
            @RequestParam(required = false) String contem,
            @RequestParam(required = false, defaultValue = "0") int pagina) {
        PageRequest pageRequest = new PageRequest(pagina, 5);
        if (igual != null) {
            return areaInteresseDAO.findByNome(igual, pageRequest);
        } else {
            return areaInteresseDAO.findByNomeContainingOrderByNome(contem, pageRequest);
        }
    }

    @RequestMapping(path = "/pesquisar", method = RequestMethod.GET)
    public Iterable<AreaInteresse> listar(@RequestParam(required = false, defaultValue = "0") int pagina) {
        PageRequest pageRequest = new PageRequest(pagina, 5);
        return areaInteresseDAO.findAll(pageRequest);
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public AreaInteresse inserir(@RequestBody AreaInteresse areaInteresse) throws Exception {
        areaInteresse.setId(0);
        AreaInteresse areaInteresseSalvo = areaInteresseDAO.save(areaInteresse);
        return areaInteresseSalvo;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public AreaInteresse recuperar(@PathVariable int id) {
        return areaInteresseDAO.findOne(id);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void atualizar(@PathVariable int id, @RequestBody AreaInteresse areaInteresse) throws Exception {
        if (areaInteresseDAO.exists(id)) {
            areaInteresse.setId(id);
            areaInteresseDAO.save(areaInteresse);
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void apagar(@PathVariable int id) {
        if (areaInteresseDAO.exists(id)) {
            areaInteresseDAO.delete(id);
        }

    }

}