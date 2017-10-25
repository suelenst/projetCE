package br.edu.ifrs.restinga.ads.projetce.dao;

import br.edu.ifrs.restinga.ads.projetce.modelo.Administrador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministradorDAO extends PagingAndSortingRepository<Administrador, Integer> {

    Page<Administrador> findByNome(String nome, Pageable pageable);

    Page<Administrador> findByNomeContainingOrderByNome(String nome, Pageable pageable);

}