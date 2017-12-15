
package br.edu.ifrs.restinga.ads.projetce.modelo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
 
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Lob;


@Entity
public class Projeto implements Serializable{


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id; 
  
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dataDelecao;
    
    @Column(nullable = false, length=60)
    private String nome;     
    
    @Lob()
    @Basic(fetch = FetchType.EAGER)
    @JsonIgnore
    private byte[] foto;
    
    @JsonIgnore
    private String tipoFoto;
    
    
    @Column(nullable = false, length=400, columnDefinition="TEXT")
    private String resumo;
    
    @Column(nullable = false, columnDefinition="TEXT")
    private String descricao;
    
    @Lob()
    @Basic(fetch = FetchType.EAGER)
    @JsonIgnore
    private byte[] arquivoAnexo;
    
    @JsonIgnore
    private String tipoArquivoAnexo;

    
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dataInicio = new Date(System.currentTimeMillis());;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dataFim;
    
         

    private Area area;    
    
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private Usuario coordenadorProjeto;
        
     
    @ManyToMany
    private List<Usuario> integrantesProjeto;
    
    
    @ManyToMany
    private List<Usuario> solicitantesProjeto;      
    
    
         
    @OneToMany(orphanRemoval=true)
    private List<Atividade> atividades;    
    
    
//    @JsonIgnore
//    @OneToMany(mappedBy = "projeto", orphanRemoval=true)      //se for o caso de remover eventos quando o projeto for removido
//    private List<Evento> eventos;


    
    
    public void setId(int id) throws Exception {
        if (id < 0)
            throw new Exception("Id deve ser maior que 1.");
        else
            this.id = id;        
    }
    public void setNome(String nome) throws Exception {
        if (nome == null || nome.isEmpty())
            throw new Exception("O campo nome é de preenchimento obrigatório.");
        else if (nome.length() > 60)
            throw new Exception("Excedido o tamanho máximo para o campo nome");
        else        
            this.nome = nome;
    }
    
    public void setResumo(String resumo) throws Exception {
        if (resumo == null || resumo.isEmpty())
            throw new Exception("O campo resumo é de preenchimento obrigatório.");
        else if (resumo.length() > 400)
            throw new Exception("Excedido o tamanho máximo para o campo nome");
        else 
            this.resumo = resumo;        
    }  

    public void setDescricao(String descricao) throws Exception {
        if (descricao == null || descricao.isEmpty())
            throw new Exception("O campo descrição é de preenchimento obrigatório.");
        else 
            this.descricao = descricao;
    }

    public void setDataInicio(Date dataInicio) throws Exception {
        if (dataInicio == null)
            throw new Exception("Data de inicio é de preenchimento obrigatório.");
        else 
            this.dataInicio = dataInicio;
    }
    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }
    public void setCoordenadorProjeto(Usuario coordenadorProjeto) throws Exception {
        if (getDataInicio() == null)
            throw new Exception("Coordenador de projeto é de preenchimento obrigatório.");
        else
            this.coordenadorProjeto = coordenadorProjeto;     
        
    }
    public void setIntegrantesProjeto(List<Usuario> integrantesProjeto) {
        this.integrantesProjeto = integrantesProjeto;
    }
    public void setAtividades(List<Atividade> atividades) {
        this.atividades = atividades;
    }
//    public void setEventos(List<Evento> eventos) {
//        this.eventos = eventos;
//    }

    public void setDataDelecao(Date dataDelecao) {
        this.dataDelecao = dataDelecao;
    }
    
    public void setSolicitantesProjeto(List<Usuario> solicitantesProjeto) {
        this.solicitantesProjeto = solicitantesProjeto;
    }
    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
    public void setTipoFoto(String tipoFoto) {
        this.tipoFoto = tipoFoto;
    }
    public void setArquivoAnexo(byte[] arquivoAnexo) {
        this.arquivoAnexo = arquivoAnexo;
    }
    public void setTipoArquivoAnexo(String tipoArquivoAnexo) {
        this.tipoArquivoAnexo = tipoArquivoAnexo;
    }
    public void setArea(Area area) {
        this.area = area;
    }
    
    
 
    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getResumo() {
        return resumo;
    }
    public String getDescricao() {
        return descricao;
    }

    public Date getDataInicio() {
        return dataInicio;
    }
    public Date getDataFim() {
        return dataFim;
    }

    public Usuario getCoordenadorProjeto() {
        return coordenadorProjeto;
    }
    public List<Usuario> getIntegrantesProjeto() {
        return integrantesProjeto;
    }
    public List<Atividade> getAtividades() {
        return atividades;
    }
//    public List<Evento> getEventos() {
//        return eventos;
//    }
    public Date getDataDelecao() {
        return dataDelecao;
    }
    public List<Usuario> getSolicitantesProjeto() {
        return solicitantesProjeto;
    }
    public byte[] getFoto() {
        return foto;
    }
    public String getTipoFoto() {
        return tipoFoto;
    }
    public byte[] getArquivoAnexo() {
        return arquivoAnexo;
    }
    public String getTipoArquivoAnexo() {
        return tipoArquivoAnexo;
    }
    public Area getArea() {
        return area;
    }



   
}
