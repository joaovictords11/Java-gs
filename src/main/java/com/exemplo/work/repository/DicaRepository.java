package com.exemplo.work.repository;

import com.exemplo.work.model.Dica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DicaRepository extends JpaRepository<Dica, Long> {
    Page<Dica> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);
    Page<Dica> findByCategoria(String categoria, Pageable pageable);
}