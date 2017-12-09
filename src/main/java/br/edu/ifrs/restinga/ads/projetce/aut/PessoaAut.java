package br.edu.ifrs.restinga.ads.projetce.aut;
import br.edu.ifrs.restinga.ads.projetce.modelo.Pessoa;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
public class PessoaAut extends User {
    private Pessoa pessoa;
    public PessoaAut(Pessoa pessoa) {
        super(pessoa.getEmail(),
                pessoa.getSenha(),
                AuthorityUtils.createAuthorityList(
                        pessoa.getPermissoes().toArray(new String[]{})));
        this.pessoa=pessoa;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

}
