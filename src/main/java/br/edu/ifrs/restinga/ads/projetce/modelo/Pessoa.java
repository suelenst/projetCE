
package br.edu.ifrs.restinga.ads.projetce.modelo;

import br.edu.ifrs.restinga.ads.projetce.util.Utilitarios;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
// Configurando herança
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY, property = "tipo")
// define o tipo raiz
@JsonTypeName("pessoa")
// tem que definir as subclasses conhecidas
@JsonSubTypes({
        @JsonSubTypes.Type(name = "usuario", value = Usuario.class),
        @JsonSubTypes.Type(name = "administrador", value = Administrador.class)})

public abstract class Pessoa implements Serializable {



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
    
    // Senha não deve nunca ficar disponível para a api cliente  
    @JsonIgnore  
    private String senha;
    // Campo necessário para cadastrar senha inicial ou atualiza-lá 
    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String novaSenha;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> permissoes;   
    

    @Column(nullable = false, length = 60)
    private String nome;

        
    @Lob()
    @Basic(fetch = FetchType.EAGER)
    @JsonIgnore
    private byte[] foto;
    @JsonIgnore
    private String tipoFoto;
    
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
    
    

    
    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }
    
    public void setPermissoes(List<String> permissoes) {
        this.permissoes = permissoes;
    }
    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
    public void setTipoFoto(String tipoFoto) {
        this.tipoFoto = tipoFoto;
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

    public String getNovaSenha() {
        return novaSenha;
    }

    public List<String> getPermissoes() {
        return permissoes;
    }
    public byte[] getFoto() {
        return foto;
    }
    public String getTipoFoto() {
        return tipoFoto;
    }



}
