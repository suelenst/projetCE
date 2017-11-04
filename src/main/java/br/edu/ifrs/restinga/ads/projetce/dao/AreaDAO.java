package br.edu.ifrs.restinga.ads.projetce.dao;

import br.edu.ifrs.restinga.ads.projetce.modelo.Area;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaDAO extends PagingAndSortingRepository<Area, Integer>{
    
    Page<Area> findByNome(String nome, Pageable pageable);
    
    Page<Area> findByNomeContainingOrderByNome(String nome, Pageable pageable);
    
}