package com.lab.jpa.SistemaBiblioteca.repository;

import com.lab.jpa.SistemaBiblioteca.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByNomeContainingIgnoreCase(String nome);

    Optional<Categoria>findByNomeIgnoreCase(String nomeCategoria);
}
