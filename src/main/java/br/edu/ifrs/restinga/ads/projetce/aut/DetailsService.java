/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.ads.projetce.aut;

import br.edu.ifrs.restinga.ads.projetce.dao.PessoaDAO;
import br.edu.ifrs.restinga.ads.projetce.modelo.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class DetailsService implements UserDetailsService {
    @Autowired
    PessoaDAO pessoaDAO;
    @Override
    public UserDetails loadUserByUsername(String email) 
            throws UsernameNotFoundException {
        Pessoa pessoa = pessoaDAO.findByEmail(email);
        if (pessoa == null) {
            throw new UsernameNotFoundException(email + " não existe!");
        }
        return new PessoaAut(pessoa);
    }
}
