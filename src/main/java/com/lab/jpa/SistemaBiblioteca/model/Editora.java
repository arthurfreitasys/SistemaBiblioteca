package com.lab.jpa.SistemaBiblioteca.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "editora")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Editora {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @OneToMany(mappedBy = "editora", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Livro> livros = new ArrayList<>();

    public Editora(String nome) {
        this.nome = nome;
    }
}
