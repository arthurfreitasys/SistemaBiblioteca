package com.lab.jpa.SistemaBiblioteca.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categoria")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 50)
    private String nome;

    @ManyToMany(mappedBy = "categorias")
    @ToString.Exclude
    private List<Livro> livros = new ArrayList<>();

    public Categoria(String nome) {
        this.nome = nome;
    }

    public Categoria(String nome, List<Livro> livros) {
        this.nome = nome;
        this.livros = livros;
    }
}
