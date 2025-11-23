# 🚀 Work Connect - Plataforma Colaborativa de Aprendizagem

Este projeto é uma API REST desenvolvida em Java 17 utilizando Spring Boot, criada para servir como backend de uma aplicação mobile para uma Comunidade Colaborativa de Aprendizagem. O objetivo é resolver o problema da falta de redes de apoio entre trabalhadores, permitindo o compartilhamento de dicas, cursos e oportunidades.

## 🏗️ Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Azure SQL
- Bean Validation
- Lombok
- Maven
- Tratamento centralizado de erros com ExceptionHandler

## 🔗 Endpoints Disponíveis

### 👤 Usuários (Trabalhadores/Estudantes)
- `POST /usuarios/cadastro` — Cadastra um novo usuário na plataforma.
- `POST /usuarios/login` — Realiza a autenticação (retorna os dados do usuário).
- `GET /usuarios/{id}` — Obtém o perfil de um usuário específico.

### 💡 Dicas e Conteúdos
- `GET /dicas` — Lista todas as dicas compartilhadas (com paginação).
- `GET /dicas?busca={termo}` — Filtra dicas pelo título.
- `POST /dicas` — Cria uma nova dica/postagem (requer ID do autor).
- `DELETE /dicas/{id}` — Remove uma dica.

## 📦 Entidades

- `Usuario`: nome, email, senha, profissão.
- `Dica`: título, descrição, categoria, data de criação e autor (usuário).

## ⚙️ Como Executar

**Pré-requisitos:**
- Java 17 (JDK)
- Maven

**Passos:**

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/joaovictords11/Java-gs.git
    ```

2.  **Compile e empacote o projeto:**
    ```bash
    mvn clean package
    ```

3.  **Execute a aplicação:**
    ```bash
    java -jar target/work-connect.jar
    ```

4.  A API estará disponível em `http://localhost:8080`.

## 🧪 Exemplos de Uso (cURL)

### Cadastrar Usuário
```bash
curl -X POST http://localhost:8080/usuarios/cadastro \
-H "Content-Type: application/json" \
-d '{
  "nome": "João Silva",
  "email": "joao@email.com",
  "senha": "123",
  "profissao": "Desenvolvedor"
}'