
package br.edu.ifrs.restinga.ads.projetce.modelo;



import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
 
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class Evento implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dataInsercao;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dataDelecao;
    
    @Column(nullable = false, length=60)
    private String nome; 
    
    private byte[] imagem;
    
    @Column(nullable = false, length=400, columnDefinition="TEXT")
    private String resumo;
    
    @Column(nullable = false, columnDefinition="TEXT")
    private String descricao;
    
    @Lob
    private byte[] arquivosAnexos;
    
     
    @OneToMany
    private List<Area> areas;
    

    @ManyToOne
    @JoinColumn(nullable = false)
    private Usuario promotorEvento;
    
     
    @ManyToMany
    private List<Usuario> inscritosEvento;
   
    
    @ManyToOne
    private Projeto projeto;
    
    
         
    @OneToMany(orphanRemoval=true)
    private List<Programacao> programacoes;



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
    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }
    public void setResumo(String resumo) throws Exception {
        if (resumo == null || resumo.isEmpty())
            throw new Exception("O campo resumo é de preenchimento obrigatório.");
        else if (resumo.length() > 400)
            throw new Exception("Excedido o tamanho máximo para o campo nome");
        else 
            this.resumo = resumo;        
    }    
    public void setArquivosAnexos(byte[] arquivosAnexos) {
        this.arquivosAnexos = arquivosAnexos;
    }
    public void setDescricao(String descricao) throws Exception {
        if (descricao == null || descricao.isEmpty())
            throw new Exception("O campo descrição é de preenchimento obrigatório.");
        else 
            this.descricao = descricao;
    }
    public void setAreas(List<Area> areas) throws Exception {
        if (areas == null || areas.isEmpty())
            throw new Exception("É necessário cadastrar pelo menos uma área de interesse");
        else 
            this.areas = areas;
    }
    public void setPromotorEvento(Usuario promotorEvento) throws Exception {
        if (promotorEvento == null)
            throw new Exception("Promotor do evento é de preenchimento obrigatório.");
        else 
            this.promotorEvento = promotorEvento;
    }
    public void setInscritosEvento(List<Usuario> inscritosEvento) {
        this.inscritosEvento = inscritosEvento;
    }    
    public void setProjeto(Projeto projeto) throws Exception {
        this.projeto = projeto;
    }
    public void setProgramacoes(List<Programacao> programacoes) throws Exception {
        if (programacoes == null || programacoes.isEmpty())
            throw new Exception("É necessário cadastrar pelo menos uma programação");
        else 
            this.programacoes = programacoes;
    }
    public void setDataInsercao(Date dataInsercao) {
        this.dataInsercao = dataInsercao;
    }
    public void setDataDelecao(Date dataDelecao) {
        this.dataDelecao = dataDelecao;
    
    }
    
    
    
    
    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public byte[] getImagem() {
        return imagem;
    }
    public String getResumo() {
        return resumo;
    }
    public String getDescricao() {
        return descricao;
    }
    public byte[] getArquivosAnexos() {
        return arquivosAnexos;
    }
    public List<Area> getArea() {
        return areas;
    }
    public Usuario getPromotorEvento() {
        return promotorEvento;
    }
    public List<Usuario> getInscritosEvento() {
        return inscritosEvento;
    }
    public Projeto getProjeto() {
        return projeto;
    }
    public List<Programacao> getProgramacoes() {
        return programacoes;
    }
    public Date getDataInsercao() {
        return dataInsercao;
    }

    public Date getDataDelecao() {
        return dataDelecao;
    }


    
    
    

}
