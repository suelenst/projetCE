package br.edu.ifrs.restinga.ads.projetce.controller;

import br.edu.ifrs.restinga.ads.projetce.dao.ProjetoDAO;
import br.edu.ifrs.restinga.ads.projetce.dao.UsuarioDAO;
import br.edu.ifrs.restinga.ads.projetce.modelo.Area;
import br.edu.ifrs.restinga.ads.projetce.modelo.Projeto;
import br.edu.ifrs.restinga.ads.projetce.modelo.Usuario;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api")
public class Projetos {

    @Autowired
    ProjetoDAO projetoDAO;
    
    @Autowired
    UsuarioDAO usuarioDAO;
    

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

    @RequestMapping(path = "/projetos/coordenador/{id}", method = RequestMethod.GET)
    public Iterable<Projeto> pesquisaPorCoordenador(
            @PathVariable int id,
            @RequestParam(required = false, defaultValue = "0") int pagina) {
        PageRequest pageRequest = new PageRequest(pagina, 5);
        return projetoDAO.findByCoordenadorProjeto_Id(id, pageRequest);
    }

    @RequestMapping(path = "/projetos", method = RequestMethod.GET)
    public Iterable<Projeto> listar(@RequestParam(required = false, defaultValue = "0") int pagina) {
        PageRequest pageRequest = new PageRequest(pagina, 5);
        return projetoDAO.findAll(pageRequest);
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
    
    @RequestMapping(path = "/projetos/participa/{id}/{idu}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void solicitaParticipacao(@PathVariable int id, @PathVariable int idu) throws Exception {
        if (projetoDAO.exists(id) && usuarioDAO.exists(idu)) {
            
            Projeto projeto = projetoDAO.findOne(id); 
            Usuario usuario = usuarioDAO.findOne(idu);
            
            if ( projeto.getCoordenadorProjeto() != usuario && 
                    !projeto.getSolicitantesProjeto().contains(usuario) &&
                    !projeto.getIntegrantesProjeto().contains(usuario) ){
            
                List<Usuario> lista = projeto.getSolicitantesProjeto();
                lista.add(usuario);

                projeto.setSolicitantesProjeto(lista);

                projetoDAO.save(projeto);
                
            } else {
                throw new Exception("Não pode solicitar participação");
            }
            
        }
        
    }
    
    @RequestMapping(path = "/projetos/participa/{id}/{ids}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void negarSolicitaParticipacao(@PathVariable int id, @PathVariable int ids) throws Exception {
        if (projetoDAO.exists(id) && usuarioDAO.exists(ids)) {
            
            Projeto projeto = projetoDAO.findOne(id); 
            Usuario usuario = usuarioDAO.findOne(ids);
            
            if ( projeto.getSolicitantesProjeto().contains(usuario) ){
            
                List<Usuario> lista = projeto.getSolicitantesProjeto();
                lista.remove(usuario);

                projeto.setSolicitantesProjeto(lista);

                projetoDAO.save(projeto);
                
            } else {
                throw new Exception("Solicitação de participação não encontrada");
            }
            
        }      
    }
    
    
    @RequestMapping(path = "/projetos/integrante/{id}/{ids}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void incluiIntegrante(@PathVariable int id, @PathVariable int ids) throws Exception {
        if (projetoDAO.exists(id) && usuarioDAO.exists(ids)) {
            
            Projeto projeto = projetoDAO.findOne(id); 
            Usuario usuario = usuarioDAO.findOne(ids);
            
            if ( projeto.getCoordenadorProjeto() != usuario &&                    
                    !projeto.getIntegrantesProjeto().contains(usuario) ){
            
                List<Usuario> lista = projeto.getIntegrantesProjeto();
                lista.add(usuario);

                projeto.setIntegrantesProjeto(lista);

                
                if ( projeto.getSolicitantesProjeto().contains(usuario) ){
            
                    List<Usuario> lista2 = projeto.getSolicitantesProjeto();
                    lista2.remove(usuario);

                    projeto.setSolicitantesProjeto(lista2);
                }
                
                
                projetoDAO.save(projeto);
                
            } else {
                throw new Exception("Não pode integrar projeto");
            }
            
        } else {
                throw new Exception("Não encontrado");
        }
        
    }
    
    
    
    
    
    
    
    
       

}