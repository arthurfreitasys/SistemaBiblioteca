package com.lab.jpa.SistemaBiblioteca.service;

import com.lab.jpa.SistemaBiblioteca.model.Autor;
import com.lab.jpa.SistemaBiblioteca.model.Categoria;
import com.lab.jpa.SistemaBiblioteca.model.Editora;
import com.lab.jpa.SistemaBiblioteca.model.Livro;
import com.lab.jpa.SistemaBiblioteca.repository.AutorRepository;
import com.lab.jpa.SistemaBiblioteca.repository.CategoriaRepository;
import com.lab.jpa.SistemaBiblioteca.repository.EditoraRepository;
import com.lab.jpa.SistemaBiblioteca.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LivroService {
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final EditoraRepository editoraRepository;
    private final CategoriaRepository categoriaRepository;

    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository, EditoraRepository editoraRepository, CategoriaRepository categoriaRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.editoraRepository = editoraRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public Livro cadastrarLivro(Long autorId,String titulo, Integer ano, String editora, List<String> categorias){
        Optional<Autor> autorOpt = autorRepository.findById(autorId);
        if (autorOpt.isEmpty()){
            throw new IllegalArgumentException("Autor não encontrado");
        }

        if (titulo.isBlank()){
            throw new IllegalArgumentException("Titulo invalido");
        }

        Optional<Editora> editoraOpt = editoraRepository.findByNomeIgnoreCase(editora);

        List<Categoria> categorialist = new ArrayList<>();
        for (String nomeCategoria: categorias){
            if (nomeCategoria.isBlank()){
                break;
            }
            Optional<Categoria> x = categoriaRepository.findByNomeIgnoreCase(nomeCategoria);
            if (x.isEmpty()){
                throw new IllegalArgumentException("Categoria não encontrada: "+nomeCategoria);
            }
            categorialist.add(x.get());
        }
        var livro = new Livro(titulo, ano, autorOpt.get(), editoraOpt.orElse(null), categorialist);
        livroRepository.save(livro);
        return livro;
    }


}
