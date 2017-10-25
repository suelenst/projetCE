
package br.edu.ifrs.restinga.ads.projetce.modelo;



import br.edu.ifrs.restinga.ads.projetce.util.Utilitarios;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;


@Entity
@PrimaryKeyJoinColumn(name="id")
public class Administrador extends Pessoa {

    @Column(unique = true, nullable = false, length=11)
    private String cpf;


    public void setCpf(String cpf) throws Exception {
        if (cpf == null || cpf.isEmpty())
            throw new Exception("O campo cpf é de preenchimento obrigatório.");    
        else if (new Utilitarios().validaCPF(cpf) == false)
            throw new Exception("O cpf informado não é válido!");        
        else
            this.cpf = cpf;
    }
        
    public String getCpf() {
        return cpf;
    }
   
    

 
}
