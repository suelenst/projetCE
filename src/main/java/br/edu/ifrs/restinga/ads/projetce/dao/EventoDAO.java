package br.edu.ifrs.restinga.ads.projetce.dao;

import br.edu.ifrs.restinga.ads.projetce.modelo.Evento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoDAO extends PagingAndSortingRepository<Evento, Integer> {

    Page<Evento> findByNome(String nome, Pageable pageable);

    Page<Evento> findByNomeContainingOrderByNome(String nome, Pageable pageable);

}