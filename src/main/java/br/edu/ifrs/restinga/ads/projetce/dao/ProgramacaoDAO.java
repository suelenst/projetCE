package br.edu.ifrs.restinga.ads.projetce.dao;

import br.edu.ifrs.restinga.ads.projetce.modelo.Programacao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ProgramacaoDAO extends PagingAndSortingRepository<Programacao, Integer> {


}