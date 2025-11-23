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
| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/usuarios/cadastro` | Cadastra um novo usuário na plataforma. |
| `POST` | `/usuarios/login` | Realiza a autenticação (retorna os dados do usuário e ID). |
| `GET` | `/usuarios/{id}` | Retorna os dados do perfil de um usuário específico. |
| `PUT` | `/usuarios/{id}` | **(Novo)** Atualiza os dados do perfil (nome, email, profissão). |
| `DELETE` | `/usuarios/{id}` | **(Novo)** Exclui a conta do usuário. |

### 💡 Dicas (Feed de Conteúdo)
| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/dicas` | Lista todas as dicas (suporta paginação). |
| `GET` | `/dicas?busca={termo}` | Filtra dicas pelo título (barra de pesquisa). |
| `POST` | `/dicas` | Cria uma nova publicação de dica ou curso. |
| `PUT` | `/dicas/{id}` | **(Novo)** Edita o título, descrição ou categoria de uma dica. |
| `DELETE` | `/dicas/{id}` | **(Novo)** Remove uma dica do feed. |

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
```

## 📝 Exemplos de JSON (CRUD)
Abaixo estão os modelos de JSON para interagir com os endpoints da API.

### 👤 Usuários (/usuarios)

1. Cadastrar Usuário (POST)
Endpoint: /usuarios/cadastro
```
{
  "nome": "Maria Souza",
  "email": "maria.souza@email.com",
  "senha": "senhaSegura123",
  "profissao": "Designer Gráfico"
}
```

2. Login (POST)
   Endpoint: /usuarios/login
```
{
  "email": "maria.souza@email.com",
  "senha": "senhaSegura123"
}
```

3. Atualizar Perfil (PUT)
   Endpoint: /usuarios/{id}
```
{
  "nome": "Maria Souza Editada",
  "email": "maria.nova@email.com",
  "senha": "novaSenha456",
  "profissao": "UX Designer"
}
```

4. Resposta de Exemplo (GET)
   Endpoint: /usuarios/{id}
```
{
  "id": 1,
  "nome": "Maria Souza",
  "email": "maria.souza@email.com",
  "senha": "senhaSegura123",
  "profissao": "Designer Gráfico"
}
```

5. Excluir Usuário (DELETE)
   Endpoint: /usuarios/{id} 

Nota: Não requer corpo na requisição (JSON).

### 💡 Dicas (/dicas)

1. Criar Nova Dica (POST)
   Endpoint: /dicas 

Nota: O autorId deve ser o ID de um usuário existente.
```
{
  "titulo": "Melhores ferramentas para Prototipagem",
  "descricao": "Uma lista com as ferramentas mais usadas no mercado, como Figma e Adobe XD.",
  "categoria": "Design",
  "autorId": 1
}
```

2. Atualizar Dica (PUT)
   Endpoint: /dicas/{id}
```
{
  "titulo": "Ferramentas de Prototipagem Atualizadas",
  "descricao": "Inclusão do Sketch na lista de ferramentas.",
  "categoria": "UX/UI",
  "autorId": 1
}
```

3. Resposta de Exemplo (GET)
   Endpoint: /dicas ou /dicas/{id}
```
{
  "id": 10,
  "titulo": "Melhores ferramentas para Prototipagem",
  "descricao": "Uma lista com as ferramentas mais usadas no mercado, como Figma e Adobe XD.",
  "categoria": "Design",
  "dataCriacao": "2024-05-20T14:30:00",
  "autor": {
    "id": 1,
    "nome": "Maria Souza",
    "email": "maria.souza@email.com",
    "profissao": "Designer Gráfico"
  }
}
```

4. Excluir Dica (DELETE)
   Endpoint: /dicas/{id} 

Nota: Não requer corpo na requisição (JSON).