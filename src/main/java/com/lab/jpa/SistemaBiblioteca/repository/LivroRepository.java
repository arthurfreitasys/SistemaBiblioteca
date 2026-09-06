package com.lab.jpa.SistemaBiblioteca.repository;

import com.lab.jpa.SistemaBiblioteca.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByTituloContainingIgnoreCase(String titulo);
    List<Livro> findByAutorId(Long Id);
    List<Livro> findByCategorias_NomeContainingIgnoreCase(String nomeCategoria);
}
