
package br.edu.ifrs.restinga.ads.projetce.modelo;


import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
 
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@PrimaryKeyJoinColumn(name="id")
public class Usuario extends Pessoa {
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dataDelete;
    
    @Column(length=60)
    private String apelido;
        
    @Lob
    private byte[] imagem;
    
    @Column(nullable = false, length=20)
    private String tipoVinculo;
    
    @Column(nullable = false, columnDefinition = "varchar(80) default 'Não se aplica'", length=80) 
    private String curso;
    
    
    
     
    @OneToMany
    private List<AreaInteresse> areasInteresse;    

     
    @OneToMany(mappedBy = "coordenadorProjeto")
    private List<Projeto> projetosCoordenados;
        
     
    @ManyToMany(mappedBy = "integrantesProjeto")
    private List<Projeto> projetosIntegrados;
    
     
    @OneToMany(mappedBy = "promotorEvento")
    private List<Evento> eventosPromovidos;

     
    @ManyToMany(mappedBy = "inscritosEvento")
    private List<Evento> eventosInscrito;

    
    
    

    public void setApelido(String apelido) throws Exception {
        if (apelido.length() > 20)
            throw new Exception("Excedido o tamanho máximo para o campo apelido");
        else
            this.apelido = apelido;
    }
    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }
    public void setTipoVinculo(String tipoVinculo) throws Exception {
        if (tipoVinculo == null || tipoVinculo.isEmpty())
            throw new Exception("O campo Tipo de Vinculo é de preenchimento obrigatório.");
        else if (tipoVinculo.length() > 20)
            throw new Exception("Excedido o tamanho máximo para o campo Tipo de Vinculo");
        else
            this.tipoVinculo = tipoVinculo;
    }
    public void setCurso(String curso) throws Exception {
        if (curso == null || curso.isEmpty())
            throw new Exception("O campo curso é de preenchimento obrigatório.");
        else if (tipoVinculo.length() > 80)
            throw new Exception("Excedido o tamanho máximo para o campo curso");
        else
            this.curso = curso;
    }

    public void setProjetosCoordenados(List<Projeto> projetosCoordenados) {
        this.projetosCoordenados = projetosCoordenados;
    }

    public void setProjetosIntegrados(List<Projeto> projetosIntegrados) {
        this.projetosIntegrados = projetosIntegrados;
    }
    public void setEventosPromovidos(List<Evento> eventosPromovidos) {
        this.eventosPromovidos = eventosPromovidos;
    }
    public void setEventosInscrito(List<Evento> eventosInscrito) {
        this.eventosInscrito = eventosInscrito;
    }
    


    public String getApelido() {
        return apelido;
    }
    public byte[] getImagem() {
        return imagem;
    }
    public String getTipoVinculo() {
        return tipoVinculo;
    }
    public String getCurso() {
        return curso;
    }

    public List<Projeto> getProjetosCoordenados() {
        return projetosCoordenados;
    }
    public List<Projeto> getProjetosIntegrados() {
        return projetosIntegrados;
    }
    public List<Evento> getEventosPromovidos() {
        return eventosPromovidos;
    }
    public List<Evento> getEventosInscrito() {
        return eventosInscrito;
    }



 
}
