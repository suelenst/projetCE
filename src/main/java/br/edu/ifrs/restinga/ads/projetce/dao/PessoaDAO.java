package br.edu.ifrs.restinga.ads.projetce.dao;

import br.edu.ifrs.restinga.ads.projetce.modelo.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaDAO extends PagingAndSortingRepository<Pessoa, Integer> {

    Page<Pessoa> findByNome(String nome, Pageable pageable);

    Page<Pessoa> findByNomeContainingOrderByNome(String nome, Pageable pageable);
    
    Page<Pessoa> findAllByDataDelecaoIsNull(Pageable pageable);
    
    public Pessoa findByEmail(String email);

}