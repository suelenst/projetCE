/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
