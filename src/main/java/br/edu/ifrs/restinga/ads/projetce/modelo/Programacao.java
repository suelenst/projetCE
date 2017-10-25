
package br.edu.ifrs.restinga.ads.projetce.modelo;


import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Programacao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inicio;
    
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fim;
    
    private String localizacao;

    public void setId(int id) throws Exception {
        if (id < 0)
            throw new Exception("Id deve ser maior que 1.");
        else
            this.id = id;        
    }
    public void setInicio(Date inicio) throws Exception {

            this.inicio = inicio;
    }
    public void setFim(Date fim) throws Exception {

            this.fim = fim;
    }
    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }


    public int getId() {
        return id;
    }
    public Date getInicio() {
        return inicio;
    }
    public Date getFim() {
        return fim;
    }
    public String getLocalizacao() {
        return localizacao;
    }
    
    
    
}
