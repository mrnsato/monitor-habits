# 🎯 Monitor de Hábitos com Gamificação - Backend

Um backend robusto em Spring Boot para gerenciar hábitos com gamificação, incluindo pontos, níveis e sorteio de recompensas.

## 🏗️ Arquitetura

- **Spring Boot 3.2.1** com Java 17
- **PostgreSQL** como banco de dados
- **Spring Data JPA** para persistência
- **Spring Security** com criptografia BCrypt
- **Validação com Jakarta Validation**
- **Tratamento centralizado de exceções**

## 📋 Estrutura do Projeto

```
src/main/java/com/habitosapp/
├── controller/      # REST Endpoints
├── service/         # Lógica de negócio
├── model/           # Entidades JPA
├── repository/      # Spring Data repositories
├── dto/             # Data Transfer Objects
├── config/          # Configurações
└── exception/       # Tratamento de erros
```

## 🚀 Como Executar

### Requisitos
- Java 17+
- Maven 3.6+
- PostgreSQL 12+

### 1. Configurar Banco de Dados

```bash
# Criar banco de dados PostgreSQL
CREATE DATABASE habitos_db;
```

### 2. Atualize as credenciais em `application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/habitos_db
spring.datasource.username=postgres
spring.datasource.password=sua_senha
```

### 3. Execute a aplicação

```bash
# Via Maven
mvn spring-boot:run

# Ou compile e execute o JAR
mvn clean package
java -jar target/habitos-monitor-1.0.0.jar
```

A aplicação estará disponível em: `http://localhost:8080`

## 📡 Endpoints da API

### Usuários

#### POST /api/usuarios
Criar novo usuário

```json
{
  "nome": "João Silva",
  "email": "joao@example.com",
  "senha": "senha123"
}
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao@example.com",
  "pontos": 0,
  "nivel": 1
}
```

#### GET /api/usuarios/{usuarioId}
Obter dados do usuário

#### GET /api/usuarios
Listar todos os usuários

---

### Hábitos

#### POST /api/habitos?usuarioId=1
Criar novo hábito

```json
{
  "titulo": "Fazer exercício",
  "descricao": "30 minutos de corrida",
  "pontosPorConclusao": 10
}
```

#### GET /api/habitos?usuarioId=1
Listar hábitos ativos do usuário

#### GET /api/habitos/{habitoId}
Obter detalhes de um hábito

#### PUT /api/habitos/{habitoId}
Atualizar hábito

#### DELETE /api/habitos/{habitoId}
Desativar hábito

---

### Progresso Diário

#### POST /api/progresso?usuarioId=1
Registrar progresso do dia

```json
{
  "habitosConcluidos": 2,
  "totalHabitos": 3
}
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "data": "2024-01-15",
  "habitosConcluidos": 2,
  "totalHabitos": 3,
  "percentualConclusao": 66.67,
  "pontosGanhos": 20,
  "elixirRecompensa": false
}
```

#### GET /api/progresso/hoje?usuarioId=1
Obter progresso de hoje

#### GET /api/progresso/historico?usuarioId=1&dias=7
Obter histórico de progresso (últimos 7 dias)

#### GET /api/progresso/elixir-recompensa?usuarioId=1
Verificar se usuário atingiu 80% e pode receber recompensa

---

### Recompensas

#### POST /api/recompensas?usuarioId=1
Criar recompensa

```json
{
  "descricao": "Assistir um filme"
}
```

#### GET /api/recompensas?usuarioId=1
Listar recompensas do usuário

#### POST /api/recompensas/sorteio?usuarioId=1
Sortear recompensa (apenas se ≥ 80% de conclusão)

**Resposta (200 OK):**
```json
{
  "id": 1,
  "descricao": "Assistir um filme",
  "dataCriacao": "2024-01-15T10:30:00"
}
```

#### GET /api/recompensas/recebidas?usuarioId=1
Listar recompensas recebidas

---

## 🎮 Fluxo da Gamificação

1. **Usuário cadastra hábitos**
2. **Marca hábitos como concluídos** durante o dia
3. **Registra progresso** com o número de hábitos concluídos
4. **Ganha pontos** automaticamente (10 pontos por hábito)
5. **Sobe de nível** a cada 100 pontos acumulados
6. **Se ≥ 80% de conclusão**, pode sortear uma recompensa

### Exemplos de Pontos
- Cumprir 1 hábito = 10 pontos
- Cumprir 3 hábitos = 30 pontos
- Nível 2 = 100 pontos acumulados
- Nível 3 = 200 pontos acumulados

---

## 🧪 Teste com cURL

### 1. Criar usuário
```bash
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Maria",
    "email": "maria@example.com",
    "senha": "senha123"
  }'
```

### 2. Criar hábitos
```bash
curl -X POST "http://localhost:8080/api/habitos?usuarioId=1" \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Meditar",
    "descricao": "10 minutos de meditação",
    "pontosPorConclusao": 10
  }'
```

### 3. Registrar progresso
```bash
curl -X POST "http://localhost:8080/api/progresso?usuarioId=1" \
  -H "Content-Type: application/json" \
  -d '{
    "habitosConcluidos": 3,
    "totalHabitos": 3
  }'
```

### 4. Sortear recompensa
```bash
curl -X POST "http://localhost:8080/api/recompensas/sorteio?usuarioId=1"
```

---

## 🧬 Estrutura do Banco de Dados

```sql
-- Usuários com gamificação
CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    pontos BIGINT DEFAULT 0,
    nivel INTEGER DEFAULT 1,
    data_criacao TIMESTAMP
);

-- Hábitos a serem rastreados
CREATE TABLE habito (
    id SERIAL PRIMARY KEY,
    usuario_id INT REFERENCES usuario(id),
    titulo VARCHAR(100) NOT NULL,
    descricao TEXT,
    ativo BOOLEAN DEFAULT TRUE,
    pontos_por_conclusao INTEGER DEFAULT 10,
    data_criacao TIMESTAMP
);

-- Progresso diário
CREATE TABLE progresso_diario (
    id SERIAL PRIMARY KEY,
    usuario_id INT REFERENCES usuario(id),
    data DATE UNIQUE NOT NULL,
    habitos_concluidos INT DEFAULT 0,
    total_habitos INT DEFAULT 0,
    pontos_ganhos BIGINT DEFAULT 0
);

-- Recompensas cadastradas
CREATE TABLE recompensa (
    id SERIAL PRIMARY KEY,
    usuario_id INT REFERENCES usuario(id),
    descricao VARCHAR(255) NOT NULL,
    data_criacao TIMESTAMP
);

-- Registro de recompensas recebidas
CREATE TABLE recompensa_recebida (
    id SERIAL PRIMARY KEY,
    usuario_id INT REFERENCES usuario(id),
    recompensa_id INT REFERENCES recompensa(id),
    data DATE
);
```

---

## 🔒 Segurança

- Senhas criptografadas com BCrypt (configuração em `SecurityConfig`)
- Validação de entrada em todos os DTOs
- Tratamento centralizado de exceções
- Validação de email único

---

## 📊 Próximos Passos

1. ✅ Backend com todos os endpoints
2. 🔄 Adicionar autenticação JWT
3. 🔄 Testes unitários e de integração
4. 🔄 Frontend em React
5. 🔄 Deploy em produção

---

## 📝 Observações

- A aplicação usa **Hibernate** para auto-criar/atualizar as tabelas (ddl-auto=update)
- Logs estão configurados para DEBUG em `com.habitosapp`
- Timestamps são salvos em UTC
- Validações são realizadas no lado do servidor

---

## 🚨 Troubleshooting

### Erro: "FATAL: Ident authentication failed"
Verifique as credenciais do PostgreSQL em `application.properties`

### Erro: "Database does not exist"
Crie o banco de dados conforme instruído:
```bash
createdb habitos_db
```

### Erro: "ClassNotFoundException: org.postgresql.Driver"
Execute `mvn clean install` para baixar as dependências

---

Desenvolvido com ❤️ para ajudar você a alcançar seus objetivos! 🎯
