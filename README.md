# Especialista Spring REST

Projeto desenvolvido durante a minha progressão no curso **"Especialista Spring REST"**,
o qual é ofertado pela empresa **Algaworks**

Este projeto irá tomar forma conforme eu avanço na progressão pretendida pelo curso.
Também pretendo aplicar novos conhecimentos em outros projetos quando cabíveis.

## Pretexto

Esta é uma API pública que será consumida por um sistema de gerenciamento de uma aplicação de delivery de comida.
Com ela é possível gerenciar restaurantes, seus produtos e pedidos de clientes.

Esta API é desenvolvida em Java com Spring Boot seguindo os princípios do REST.

## Tecnologias utilizadas

- Java 11
- Maven
- Spring Framework
	- Spring Boot v2.5.3
	- Spring Data JPA
- Swagger UI / OpenAPI 3
- PostgreSQL
- Docker
- Amazon AWS
	- S3 (armazenamento de arquivos)
	- SMTP (envio de e-mails comerciais)

## Pré-requisitos

- Java 11
- Docker & Docker Compose v2.4 (caso não possua um banco de dados PostgreSQL)
- Maven
- IDE de sua preferência (caso deseje visualizar o código)
- Conta na AWS para configuração do S3 e SMTP (caso deseje utilizar)

## Como executar o projeto

1. Configure as variáveis de ambiente conforme o perfil de execução desejado.
   _(Consulte o arquivo `application.properties` para saber quais são as variáveis de ambiente necessárias)_;

2. Abra a pasta raiz do projeto que você acabou de clonar no terminal de comandos;

3. Execute o comando `docker-compose -f docker/docker-compose.yaml up -d` na raiz do projeto para subir o banco de dados
   PostgreSQL. _(Isso é para caso você não possua um outro banco de dados PostgreSQL para conectar a aplicação)_;

4. Navegue até a pasta _service_ com o comando `cd service`;

5. Execute o comando `mvn spring-boot:run` para subir a aplicação. Para especificar o perfil de execução, adicione o
   argumento `-Dspring.profiles.active={{nome-do-perfil}}` ao final do comando;

## Documentação da API

Para acessar a documentação da API, basta acessar o link `{{url-da-aplicacao}}/swagger-ui/index.html#/` no seu
navegador (enquanto a aplicação estiver rodando, é claro).
<br>
**Exemplo:** `http://localhost:8080/swagger-ui/index.html#/`

Para acessar a documentação da API em formato JSON, basta enviar uma requisição GET para o link
`{{url-da-aplicacao}}/v3/api-docs`. O retorno será um JSON com a documentação da API.
