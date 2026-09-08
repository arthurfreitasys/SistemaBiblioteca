# SistemaBiblioteca — Sistema de Gestão de Biblioteca

Aplicação em Java com Spring Boot, Spring Data JPA e banco de dados H2, 
expandido com novas entidades, metodos, relacionamentos e uma camada de serviço.

## Identificação

- **Nome completo:** [Arthur Silva de Freitas]
- **RA:** [1630482511026]

## Descrição do Projeto

O sistema permite o cadastro e consulta de **Autores**, **Livros**, **Editoras**
e **Categorias**, com relacionamentos @OneToMany/@ManyToOne e @ManyToMany. 
A interação ocorre por um menu interativo no console.

## Tecnologias

- Java 25
- Spring Boot
- Spring Data JPA
- H2 Database (em memória)
- Lombok


## Modelo de Domínio

- **Autor** — possui uma lista de Livros (@OneToMany).
- **Livro** — pertence a um Autor (`ManyToOne, obrigatório), a uma Editora
  (@ManyToOne, opcional) e possui várias Categorias (@ManyToMany).
- **Editora** — possui uma lista de Livros (@OneToMany).
- **Categoria** — possui uma lista de Livros (@ManyToMany),
  permitindo que um livro tenha uma ou mais categorias e que uma categoria
  esteja associada a vários livros.

## Modificações Implementadas

### 1. Entidade Editora

Nova entidade Editora, com relacionamento @ManyToOne (Livro → Editora) e
@OneToMany (Editora → Livros), seguindo o mesmo padrão já usado entre Autor
e Livro.

**Funcionalidades adicionadas ao menu:**
- Cadastrar editora
- Listar editoras
- Seleção de editora (por nome) ao cadastrar um novo livro

### 2. Entidade Categoria

Nova entidade Categoria, com relacionamento @ManyToMany para Livro, usando @JoinTable, 
os dois lados podem ter múltiplas associações: um livro pode pertencer a várias
categorias, e uma categoria pode conter vários livros. livro é o lado dono
da relação, e Categoria é o lado espelho.

Ao cadastrar um livro, é possível informar nenhuma, uma ou várias categorias,
uma por vez, encerrando a digitação com uma linha em branco.

**Funcionalidades adicionadas ao menu:**
- Cadastrar categoria.
- Listar categorias.
- Seleção de uma ou mais categorias (por nome) ao cadastrar um novo livro.
- Exibição das categorias de cada livro na listagem, formatada com Streams.

### 3. Busca de Livro por Título

Opção de busca de livros pelo título, utilizando o método
findByTituloContainingIgnoreCase do LivroRepository, resultado é exibido em lista, 
já que mais de um livro pode conter o termo buscado no título.

### 4. Busca de Livros por Categoria

Opção de busca que lista todos os livros associados a uma determinada
categoria, utilizando o método findByCategorias_NomeContainingIgnoreCase do
LivroRepository. Diferente das buscas anteriores, essa consulta navega por
um relacionamento @ManyToMany (Livro → Categoria) para localizar o campo
Nome dentro da entidade relacionada, em vez de comparar um campo direto da
própria entidade Livro.

### 5. Camada de Serviço (`LivroService`)

A lógica de negócio do cadastro de livro (validação de autor, tratamento de
editora opcional, categorias informadas e persistência) foi
extraída do main para uma classe LivroService:

- O main passou a ser responsável apenas pela interação com o
  usuário (ler dados via Scanner, exibir mensagens).
- O LivroService contém as regras de negócio e lança
  erros quando uma validação falha (por exemplo, autor
  ou categoria não encontrados), permitindo que o chamador decida como tratar
  o erro.
