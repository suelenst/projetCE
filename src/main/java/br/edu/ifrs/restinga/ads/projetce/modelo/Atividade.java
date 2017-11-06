
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
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class Atividade implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false, length=60)
    private String nome;
    
    @Column(nullable = false)
    private String descricao;
    
    private byte[] arquivosAnexos;
    
    @Column(nullable = false,  columnDefinition = "boolean default true")
    private boolean permanente;
    
     
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
    public void setDescricao(String descricao) throws Exception {
        if (descricao == null || descricao.isEmpty())
            throw new Exception("O campo descrição é de preenchimento obrigatório.");
        else 
            this.descricao = descricao;
    }
    public void setArquivosAnexos(byte[] arquivosAnexos) {
        this.arquivosAnexos = arquivosAnexos;
    }   
    public void setPermanente(boolean permanente) {
        this.permanente = permanente;
    }
    public void setProgramacoes(List<Programacao> programacoes) {
        this.programacoes = programacoes;
    }


    
    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public byte[] getArquivosAnexos() {
        return arquivosAnexos;
    }
    public boolean isPermanente() {
        return permanente;
    }
    public List<Programacao> getProgramacoes() {
        return programacoes;
    }

    


   
}
