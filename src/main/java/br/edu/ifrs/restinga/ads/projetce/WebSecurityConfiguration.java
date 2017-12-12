/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.ads.projetce;

import br.edu.ifrs.restinga.ads.projetce.aut.TokenBasedAuthorizationFilter;
import br.edu.ifrs.restinga.ads.projetce.dao.PessoaDAO;

import br.edu.ifrs.restinga.ads.projetce.aut.DetailsService;
import br.edu.ifrs.restinga.ads.projetce.controller.Pessoas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.stereotype.Component;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Component
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    DetailsService detailsService;
    
    @Autowired
    AuthenticationManager authenticationManager;
        
    @Autowired
    PessoaDAO pessoaDAO;
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(detailsService)
                .passwordEncoder(Pessoas.PASSWORD_ENCODER);
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
//    web.ignoring().antMatchers(HttpMethod.POST,"/api/pessoas/");
//    web.ignoring().antMatchers(HttpMethod.POST,"/api/pessoas/**");
    //web.ignoring().antMatchers(HttpMethod.POST,"/api/imgs/**");
}
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/pessoas/").permitAll()
                .antMatchers("/api/**").authenticated()
                .and().httpBasic()
                .and().
                addFilterBefore(new TokenBasedAuthorizationFilter(authenticationManager, pessoaDAO)
                        , BasicAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable();
    }
}