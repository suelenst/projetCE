
package br.edu.ifrs.restinga.ads.projetce.modelo;



import br.edu.ifrs.restinga.ads.projetce.util.Utilitarios;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Transient;


@Entity
//@PrimaryKeyJoinColumn(name="id")
public class Administrador extends Pessoa {
    // Para não gravar no banco 
    @Transient
    // Define o campo
    @JsonProperty("tipo")
    private final String tipo = "administrador";

    @Column(unique = true, nullable = false, length=14)
    private String cpf;


    public void setCpf(String cpf) throws Exception {
        if (cpf == null || cpf.isEmpty())
            throw new Exception("O campo cpf é de preenchimento obrigatório.");    
//        else if (new Utilitarios().validaCPF(cpf) == false)
//            throw new Exception("O cpf informado não é válido!");
        else
            this.cpf = cpf;
    }
        
    public String getCpf() {
        return cpf;
    }
   
    

 
}
