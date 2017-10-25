
package br.edu.ifrs.restinga.ads.projetce.modelo;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class AreaInteresse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @Column(nullable = false, length=80)
    private String nome;



    public void setId(int id) throws Exception {
        if (id < 0)
            throw new Exception("Id deve ser maior que 1.");
        else
            this.id = id;        
    }
    public void setNome(String nome) throws Exception {
        if (nome == null || nome.isEmpty())
            throw new Exception("O campo nome é de preenchimento obrigatório.");
        else if (nome.length() > 80)
            throw new Exception("Excedido o tamanho máximo para o campo nome");
        else        
            this.nome = nome;
    }
   
    
    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    } 
    
    
   
}
