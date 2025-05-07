
# Cadastros de Clientes

## Descrição
O **Cadastros de Clientes** é um aplicativo para cadastro de clientes, utilizando **Angular** no front-end, **Spring Boot** no back-end e **PostgreSQL** como banco de dados. O projeto também está configurado para ser executado usando **Docker**.

## Tecnologias
- **Angular** versão 19 (front-end)
- **Spring Boot** para Java 17 (back-end)
- **PostgreSQL** (banco de dados)
- **Docker** (orquestração)

## Pré-requisitos
- **Docker**: O projeto utiliza **Docker** para orquestrar os containers. Nenhuma versão específica é necessária, mas certifique-se de ter o Docker e o Docker Compose instalados corretamente em seu sistema.

## Como rodar o projeto

### Passos para executar com Docker

1. Clone o repositório:
   ```bash
   git clone <url-do-repositorio>
   cd <diretorio-do-repositorio>
   ```

2. No diretório raiz do projeto, execute o seguinte comando para subir todos os containers do Docker:
   ```bash
   docker-compose up
   ```

3. O Docker irá construir os containers para o front-end, back-end e banco de dados. O **back-end** será exposto na porta `8080` e o **front-end** será exposto na porta `4200`.

### Página Inicial
- A página inicial do aplicativo estará disponível no **localhost** na seguinte URL:
  ```
  http://localhost:4200
  ```

### Como o Front-End e o Back-End interagem
- Ambos os serviços (front-end e back-end) sobem com o comando `docker-compose up`.
- O **back-end** precisa estar rodando antes de o **front-end** ser iniciado, mas como ambos estão orquestrados com Docker, isso será feito automaticamente.

### Estrutura do Projeto
O projeto está organizado da seguinte forma:
```
/backend          # Contém o código do back-end (Spring Boot)
/frontend         # Contém o código do front-end (Angular)
/docker-compose.yml   # Arquivo Docker Compose para orquestração
```

## Docker Compose

O arquivo `docker-compose.yml` está configurado da seguinte forma:

```yaml
version: '3.8'

services:
  db:
    image: bitnami/postgresql:latest
    container_name: db_cadastros
    ports:
      - '5432:5432'
    environment:
      - POSTGRESQL_USERNAME=root
      - POSTGRESQL_PASSWORD=root
      - POSTGRESQL_DATABASE=db_cadastros
    volumes:
      - db_cadastros_pg_data:/bitnami/postgresql

  backend:
    build:
      context: ./backend/cadastros  
    container_name: spring_boot_backend
    ports:
      - '8080:8080'
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://db_cadastros:5432/db_cadastros
      - SPRING_DATASOURCE_USERNAME=root
      - SPRING_DATASOURCE_PASSWORD=root
    depends_on:
      - db
    volumes:
      - ./backend/cadastros:/app
    working_dir: /app
    command: ["java", "-jar", "target/cadastros-0.0.1-SNAPSHOT.jar"]

  frontend:
    build:
      context: ./frontend/cadastros  
    container_name: angular_frontend
    ports:
      - '4200:4200'
    volumes:
      - ./frontend/cadastros:/app
    working_dir: /app
    command: ["npm", "start"]
    depends_on:
      - backend

volumes:
  db_cadastros_pg_data:
```

## Testes Automatizados
O projeto possui testes automatizados implementados no back-end (Spring Boot). Para executá-los, você pode rodar os testes diretamente através de uma IDE, como o **IntelliJ IDEA** ou **Eclipse**.

## Autenticação
O projeto **não** exige autenticação ou login para o cadastro de clientes.

## Instalação e Execução
1. Para rodar o projeto localmente sem Docker, será necessário configurar o ambiente de desenvolvimento (instalar Java 17, Node.js e PostgreSQL) e executar o back-end e o front-end manualmente. Para isso, siga as instruções da documentação de cada tecnologia:
   - [Instruções de instalação do Spring Boot](https://spring.io/guides/gs/spring-boot/)
   - [Instruções de instalação do Angular](https://angular.io/guide/setup-local)
   - [Configuração do PostgreSQL](https://www.postgresql.org/docs/)

2. Se preferir rodar com Docker, siga os passos mencionados anteriormente para utilizar o `docker-compose`.

