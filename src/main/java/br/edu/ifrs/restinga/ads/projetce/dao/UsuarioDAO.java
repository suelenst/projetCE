package br.edu.ifrs.restinga.ads.projetce.dao;

import br.edu.ifrs.restinga.ads.projetce.modelo.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioDAO extends PagingAndSortingRepository<Usuario, Integer> {

    Page<Usuario> findByNome(String nome, Pageable pageable);

    Page<Usuario> findByNomeContainingOrderByNome(String nome, Pageable pageable);
    
    Page<Usuario> findAllByDataDelecaoIsNull(Pageable pageable);

}