package br.edu.ifrs.restinga.ads.projetce.dao;

import br.edu.ifrs.restinga.ads.projetce.modelo.Projeto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjetoDAO extends PagingAndSortingRepository<Projeto, Integer> {

    Page<Projeto> findByNome(String nome, Pageable pageable);

    Page<Projeto> findByNomeContainingOrderByNome(String nome, Pageable pageable);

}