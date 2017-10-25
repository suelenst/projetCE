package br.edu.ifrs.restinga.ads.projetce.dao;

import br.edu.ifrs.restinga.ads.projetce.modelo.Atividade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtividadeDAO extends PagingAndSortingRepository<Atividade, Integer> {

    Page<Atividade> findByNome(String nome, Pageable pageable);

    Page<Atividade> findByNomeContainingOrderByNome(String nome, Pageable pageable);

}