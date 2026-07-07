# Projeto - Library API

# Sobre

<p>Projeto de gerenciamento de bibliotecas desenvolvido em Java com Spring Boot, neste 
sistema é possível cadastrar livros, atualizar, buscar livros, buscar livros alugados, além disso
é possível gerenciar os alugueis de livros, este sistema possui um serviço de envio de emails com 
aviso para o caso de devolução atrasada, este serviço de envio de email e executado via cronjob.</p>

# API Book
-Base URL: api/books

## Methods HTTP
- Criar POST
- Atualizar PUT
- Buscar GET
- Buscar aluguel (loan) por Livro

# API Loans (Aluguel)
-Base URL: api/loans

## Methods HTTP
- Criar POST
- Buscar GET
- Buscar por Id GET


# Tecnologias usadas
- Java 17
- Maven
- Spring Boot
- Spring Admin
- H2 DataBase
- Postman

# Como executar o projeto
- Java 17 instalado
- Baixar o projeto  via git clone por exemplo
- Executar o comando mvn clean package
- Executar o comando java library-api-0.0.1-SNAPSHOT.jar

# Autor

Marcello Joaquim da Silva

linkedin.com/in/marcello-joaquim-da-silva-6814bb69
