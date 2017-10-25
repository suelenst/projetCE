package br.edu.ifrs.restinga.ads.projetce.dao;

import br.edu.ifrs.restinga.ads.projetce.modelo.AreaInteresse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaInteresseDAO extends PagingAndSortingRepository<AreaInteresse, Integer>{
    
    Page<AreaInteresse> findByNome(String nome, Pageable pageable);
    
    Page<AreaInteresse> findByNomeContainingOrderByNome(String nome, Pageable pageable);
    
}