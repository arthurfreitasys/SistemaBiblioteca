package com.lab.jpa.SistemaBiblioteca.config;
import com.lab.jpa.SistemaBiblioteca.model.Autor;
import com.lab.jpa.SistemaBiblioteca.model.Editora;
import com.lab.jpa.SistemaBiblioteca.model.Livro;
import com.lab.jpa.SistemaBiblioteca.repository.AutorRepository;
import com.lab.jpa.SistemaBiblioteca.repository.EditoraRepository;
import com.lab.jpa.SistemaBiblioteca.repository.LivroRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
@Component
public class DataInitializer implements CommandLineRunner {

    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;
    private final EditoraRepository editoraRepository;
    Scanner scanner = new Scanner(System.in);

    public DataInitializer(AutorRepository autorRepository, LivroRepository livroRepository, EditoraRepository editoraRepository) {
        this.autorRepository = autorRepository;
        this.livroRepository = livroRepository;
        this.editoraRepository = editoraRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        var continuar = true;

        System.out.println("==========================================");
        System.out.println(" SISTEMA DE GESTÃO DE BIBLIOTECA JPA ");
        System.out.println("==========================================");

        while (continuar) {
            System.out.println("\nMENU DE OPÇÕES:");
            System.out.println("1 - Cadastrar Autor");
            System.out.println("2 - Listar Autores");
            System.out.println("3 - Cadastrar Livro");
            System.out.println("4 - Listar Livros");
            System.out.println("5 - Cadastrar editoras");
            System.out.println("6 - Listar editoras");
            System.out.println("7 - Buscar Livros Por Nome");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            var opcao = scanner.nextLine();

            continuar = switch (opcao) {
                case "1" -> {
                    cadastrarAutor(scanner);
                    yield true;
                }
                case "2" -> {
                    listarAutores();
                    yield true;
                }
                case "3" -> {
                    cadastrarLivro(scanner);
                    yield true;
                }
                case "4" -> {
                    listarLivros();
                    yield true;
                }
                case "5" -> {
                    cadastrarEditora(scanner);
                    yield true;
                }
                case "6" -> {
                    listarEditoras();
                    yield true;
                }
                case "7" -> {
                    buscarLivroPorNome();
                    yield true;
                }
                case "0" -> {
                    System.out.println("Encerrando aplicação...");
                    yield false;
                }
                default -> {
                    System.out.println("Opção inválida! Tente novamente.");
                    yield true;
                }
            };
        }
        System.out.println("Aplicação finalizada.");
    }

    private void cadastrarAutor(Scanner scanner) {
        System.out.print("Digite o nome do autor: ");
        var nome = scanner.nextLine();

        if (nome.isBlank()) {
            System.out.println("Nome inválido!");
            return;
        }

        var autor = new Autor(nome);
        autorRepository.save(autor);
        System.out.println(">>> Autor '" + autor.getNome() + "' cadastrado com ID: " + autor.getId());
    }

    private void cadastrarEditora(Scanner scanner){
        System.out.println("Digite o nome da editora: ");
        var nome = scanner.nextLine();

        if (nome.isBlank()){
            System.out.println("Nome invalido");
            return;
        }

        var editora = new Editora(nome);
        editoraRepository.save(editora);
        System.out.println(">>> Editora '" + editora.getNome() + "'Cadastrado com ID: " + editora.getId());
    }

    private void listarAutores() {
        var autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor cadastrado.");
            return;
        }

        System.out.println("\n--- LISTA DE AUTORES ---");
        autores.forEach(a -> System.out.printf("ID: %d | Nome: %s%n", a.getId(), a.getNome()));
        System.out.println("------------------------");
    }

    private void cadastrarLivro(Scanner scanner) {
        listarAutores();
        System.out.print("Informe o ID do autor do livro: ");
        var idStr = scanner.nextLine();

        try {
            var autorId = Long.parseLong(idStr);
            Optional<Autor> autorOpt = autorRepository.findById(autorId);

            if (autorOpt.isEmpty()) {
                System.out.println("Autor não encontrado com o ID informado!");
                return;
            }

            System.out.print("Digite o título do livro: ");
            var titulo = scanner.nextLine();

            if (titulo.isBlank()) {
                System.out.println("Título inválido!");
                return;
            }

            System.out.print("Digite o ano de publicação: ");
            var ano = Integer.parseInt(scanner.nextLine());

            listarEditoras();
            System.out.println("Digite o nome da editora responsavel: ");
            var nomeEditora = scanner.nextLine();
            Optional<Editora> editoraOpt = editoraRepository.findByNomeIgnoreCase(nomeEditora);

            if (editoraOpt.isEmpty()){
                System.out.println("Editora não encontrada");
                return;
            }

            var livro = new Livro(titulo, ano, autorOpt.get(), editoraOpt.get() );
            livroRepository.save(livro);
            System.out.println(">>> Livro '" + livro.getTitulo() + "' cadastrado com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("Valor numérico inválido informado.");
        }
    }

    private void listarLivros() {
        var livros = livroRepository.findAll();

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
            return;
        }

        System.out.println("\n--- LISTA DE LIVROS ---");
        livros.forEach(l -> System.out.printf(" ID: %d | Título: %s | Ano: %d | Autor: %s | Editora: %s%n",
                l.getId(), l.getTitulo(), l.getAnoPublicacao(), l.getAutor().getNome(), l.getEditora().getNome()));
        System.out.println("-----------------------");
    }

    private void listarEditoras(){
        var editoras = editoraRepository.findAll();

        if (editoras.isEmpty()){
            System.out.println("Nenhuma editora cadastrada");
            return;
        }

        System.out.println("\n --- Lista de editoras ---");
        editoras.forEach(e -> System.out.printf(" ID: %d | Nome: %s%n",
                e.getId(), e.getNome()));
        System.out.println("--------------------------");
    }

    private void buscarLivroPorNome(){
        System.out.println("Informe o nome do livro que você deseja: ");
        var nomeLivro = scanner.nextLine();

            List<Livro> livroList = livroRepository.findByTituloContainingIgnoreCase(nomeLivro);
            if (livroList.isEmpty()){
                System.out.println("Não há livros cadastrados com esse nome");
                return;
            }

            livroList.forEach(l -> System.out.printf(" ID: %d | Titulo: %s | Ano: %d | Autor: %s%n",
                    l.getId(), l.getTitulo(), l.getAnoPublicacao(), l.getAutor().getNome()));
            System.out.println("----------------------------");
    }
}