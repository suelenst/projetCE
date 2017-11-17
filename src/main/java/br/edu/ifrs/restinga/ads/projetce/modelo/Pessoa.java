
package br.edu.ifrs.restinga.ads.projetce.modelo;

import br.edu.ifrs.restinga.ads.projetce.util.Utilitarios;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
// Configurando herança
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "tipo")
// define o tipo raiz
@JsonTypeName("pessoa")
// tem que definir as subclasses conhecidas
@JsonSubTypes({
        @JsonSubTypes.Type(name = "usuario", value = Usuario.class),
        @JsonSubTypes.Type(name = "administrador", value = Administrador.class)})


public abstract class Pessoa implements Serializable {

    // para não gravar no banco 
    @Transient
    // Define o campo
    @JsonProperty("tipo")
    private final String tipo = "pessoa";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dataInsercao = new Date(System.currentTimeMillis());;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dataDelecao;
    
    @Column(unique = true, nullable = false, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String senha;

    @Column(nullable = false, length = 60)
    private String nome;

    @Column(length = 15)
    private String telefone;

    public void setId(int id) throws Exception {
        if (id < 0)
            throw new Exception("Id deve ser maior que 0.");
        else
            this.id = id;
    }

    public void setEmail(String prefixo) throws Exception {
        String email = prefixo;
        
        if (prefixo == null || prefixo.isEmpty())
            throw new Exception("O campo E-mail é de preenchimento obrigatório!");
        else if (!new Utilitarios().validaEmail(email+"@restinga.ifrs.edu.br"))
            throw new Exception("O E-mail digitado não é válido!");
        else
            this.email =prefixo;
    }

    public void setSenha(String senha) throws Exception {
        if (senha == null || senha.isEmpty())
            throw new Exception("O campo senha é de preenchimento obrigatório.");
        else
            this.senha = senha;
    }

    public void setNome(String nome) throws Exception {
        if (nome == null || nome.isEmpty())
            throw new Exception("O campo nome é de preenchimento obrigatório.");
        else if (nome.length() > 60)
            throw new Exception("Excedido o tamanho máximo para o campo nome");
        else
            this.nome = nome;
    }

    public void setTelefone(String telefone) throws Exception {
        if (telefone.length() > 15)
            throw new Exception("Excedido o tamanho máximo para o campo telefone");
        else
            this.telefone = telefone;
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

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getNome() {
        return nome;
    }
    public String getTelefone() {
        return telefone;
    }
    public Date getDataInsercao() {
        return dataInsercao;
    }

    public Date getDataDelecao() {
        return dataDelecao;
    }



}
