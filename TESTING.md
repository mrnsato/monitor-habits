# 🧪 Guia de Testes - Monitor de Hábitos

Este guia descreve como testar todos os endpoints do backend passo a passo.

## 📋 Pré-requisitos

- Backend rodando em `http://localhost:8080`
- Cliente HTTP (Postman, Insomnia, cURL, ou Thunder Client)
- PostgreSQL configurado e rodando

## ✅ Passo a Passo de Testes

### 1️⃣ Criar Usuário

**Objetivo:** Testar criação de usuário e validações

#### Teste 1.1: Criar usuário válido

```bash
POST http://localhost:8080/api/usuarios
Content-Type: application/json

{
  "nome": "João Silva",
  "email": "joao@example.com",
  "senha": "senha123"
}
```

**Resultado esperado (201 Created):**
```json
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao@example.com",
  "pontos": 0,
  "nivel": 1
}
```

#### Teste 1.2: Tentar criar usuário com email duplicado

```bash
POST http://localhost:8080/api/usuarios
Content-Type: application/json

{
  "nome": "Maria Silva",
  "email": "joao@example.com",
  "senha": "senha456"
}
```

**Resultado esperado (400 Bad Request):**
```json
{
  "status": 400,
  "error": "Email já cadastrado"
}
```

#### Teste 1.3: Criar usuário com dados inválidos

```bash
POST http://localhost:8080/api/usuarios
Content-Type: application/json

{
  "nome": "",
  "email": "email-invalido",
  "senha": "senha"
}
```

**Resultado esperado (400 Bad Request):**
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Erro de Validação",
  "validationErrors": {
    "nome": "Nome é obrigatório",
    "email": "Email deve ser válido"
  }
}
```

#### Teste 1.4: Obter usuário

```bash
GET http://localhost:8080/api/usuarios/1
```

**Resultado esperado (200 OK):**
```json
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao@example.com",
  "pontos": 0,
  "nivel": 1
}
```

#### Teste 1.5: Listar usuários

```bash
GET http://localhost:8080/api/usuarios
```

**Resultado esperado (200 OK):**
```json
[
  {
    "id": 1,
    "nome": "João Silva",
    "email": "joao@example.com",
    "pontos": 0,
    "nivel": 1
  }
]
```

---

### 2️⃣ Gerenciar Hábitos

**Objetivo:** Testar CRUD de hábitos

#### Teste 2.1: Criar primeiro hábito

```bash
POST http://localhost:8080/api/habitos?usuarioId=1
Content-Type: application/json

{
  "titulo": "Meditar",
  "descricao": "10 minutos de meditação",
  "pontosPorConclusao": 10
}
```

**Resultado esperado (201 Created):**
```json
{
  "id": 1,
  "titulo": "Meditar",
  "descricao": "10 minutos de meditação",
  "ativo": true,
  "pontosPorConclusao": 10,
  "dataCriacao": "2024-01-15T10:30:00"
}
```

#### Teste 2.2: Criar mais hábitos

```bash
POST http://localhost:8080/api/habitos?usuarioId=1
Content-Type: application/json

{
  "titulo": "Fazer exercício",
  "descricao": "30 minutos de corrida",
  "pontosPorConclusao": 15
}
```

```bash
POST http://localhost:8080/api/habitos?usuarioId=1
Content-Type: application/json

{
  "titulo": "Ler",
  "descricao": "20 páginas de um livro",
  "pontosPorConclusao": 10
}
```

#### Teste 2.3: Listar hábitos do usuário

```bash
GET http://localhost:8080/api/habitos?usuarioId=1
```

**Resultado esperado (200 OK):**
```json
[
  {
    "id": 1,
    "titulo": "Meditar",
    "descricao": "10 minutos de meditação",
    "ativo": true,
    "pontosPorConclusao": 10,
    "dataCriacao": "2024-01-15T10:30:00"
  },
  {
    "id": 2,
    "titulo": "Fazer exercício",
    "descricao": "30 minutos de corrida",
    "ativo": true,
    "pontosPorConclusao": 15,
    "dataCriacao": "2024-01-15T10:31:00"
  },
  {
    "id": 3,
    "titulo": "Ler",
    "descricao": "20 páginas de um livro",
    "ativo": true,
    "pontosPorConclusao": 10,
    "dataCriacao": "2024-01-15T10:32:00"
  }
]
```

#### Teste 2.4: Atualizar um hábito

```bash
PUT http://localhost:8080/api/habitos/1
Content-Type: application/json

{
  "titulo": "Meditar 20 min",
  "descricao": "20 minutos de meditação profunda",
  "pontosPorConclusao": 15
}
```

**Resultado esperado (200 OK):**
```json
{
  "id": 1,
  "titulo": "Meditar 20 min",
  "descricao": "20 minutos de meditação profunda",
  "ativo": true,
  "pontosPorConclusao": 15,
  "dataCriacao": "2024-01-15T10:30:00"
}
```

#### Teste 2.5: Obter um hábito específico

```bash
GET http://localhost:8080/api/habitos/1
```

**Resultado esperado (200 OK):**
```json
{
  "id": 1,
  "titulo": "Meditar 20 min",
  "descricao": "20 minutos de meditação profunda",
  "ativo": true,
  "pontosPorConclusao": 15,
  "dataCriacao": "2024-01-15T10:30:00"
}
```

---

### 3️⃣ Registrar Progresso

**Objetivo:** Testar registro de progresso e ganho de pontos

#### Teste 3.1: Registrar progresso de 2 hábitos concluídos de 3

```bash
POST http://localhost:8080/api/progresso?usuarioId=1
Content-Type: application/json

{
  "habitosConcluidos": 2,
  "totalHabitos": 3
}
```

**Resultado esperado (201 Created):**
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

**O que acontece:**
- Percentual de conclusão: 2/3 * 100 = 66.67%
- Pontos ganhos: 2 hábitos * 10 pontos = 20 pontos
- elixirRecompensa: false (porque < 80%)
- Usuário agora tem 20 pontos no total

#### Teste 3.2: Verificar usuário após progresso

```bash
GET http://localhost:8080/api/usuarios/1
```

**Resultado esperado (200 OK):**
```json
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao@example.com",
  "pontos": 20,
  "nivel": 1
}
```

#### Teste 3.3: Registrar progresso com 3/3 hábitos concluídos (≥80%)

```bash
POST http://localhost:8080/api/progresso?usuarioId=1
Content-Type: application/json

{
  "habitosConcluidos": 3,
  "totalHabitos": 3
}
```

**Resultado esperado (201 Created):**
```json
{
  "id": 2,
  "data": "2024-01-15",
  "habitosConcluidos": 3,
  "totalHabitos": 3,
  "percentualConclusao": 100,
  "pontosGanhos": 30,
  "elixirRecompensa": true
}
```

**O que acontece:**
- Percentual de conclusão: 3/3 * 100 = 100%
- Pontos ganhos: 3 hábitos * 10 pontos = 30 pontos
- elixirRecompensa: **true** (porque ≥ 80%)
- Usuário agora tem 50 pontos totais

#### Teste 3.4: Obter progresso de hoje

```bash
GET http://localhost:8080/api/progresso/hoje?usuarioId=1
```

**Resultado esperado (200 OK):**
```json
{
  "id": 2,
  "data": "2024-01-15",
  "habitosConcluidos": 3,
  "totalHabitos": 3,
  "percentualConclusao": 100,
  "pontosGanhos": 30,
  "elixirRecompensa": true
}
```

#### Teste 3.5: Verificar se pode sortear recompensa

```bash
GET http://localhost:8080/api/progresso/elixir-recompensa?usuarioId=1
```

**Resultado esperado (200 OK):**
```json
true
```

---

### 4️⃣ Gerenciar Recompensas

**Objetivo:** Testar criação de recompensas e sorteio

#### Teste 4.1: Criar primeira recompensa

```bash
POST http://localhost:8080/api/recompensas?usuarioId=1
Content-Type: application/json

{
  "descricao": "Assistir um filme"
}
```

**Resultado esperado (201 Created):**
```json
{
  "id": 1,
  "descricao": "Assistir um filme",
  "dataCriacao": "2024-01-15T10:35:00"
}
```

#### Teste 4.2: Criar mais recompensas

```bash
POST http://localhost:8080/api/recompensas?usuarioId=1
Content-Type: application/json

{
  "descricao": "Comer pizza"
}
```

```bash
POST http://localhost:8080/api/recompensas?usuarioId=1
Content-Type: application/json

{
  "descricao": "Uma hora de jogo"
}
```

#### Teste 4.3: Listar recompensas

```bash
GET http://localhost:8080/api/recompensas?usuarioId=1
```

**Resultado esperado (200 OK):**
```json
[
  {
    "id": 1,
    "descricao": "Assistir um filme",
    "dataCriacao": "2024-01-15T10:35:00"
  },
  {
    "id": 2,
    "descricao": "Comer pizza",
    "dataCriacao": "2024-01-15T10:36:00"
  },
  {
    "id": 3,
    "descricao": "Uma hora de jogo",
    "dataCriacao": "2024-01-15T10:37:00"
  }
]
```

#### Teste 4.4: Sortear recompensa (deve ter ≥80% de conclusão)

```bash
POST http://localhost:8080/api/recompensas/sorteio?usuarioId=1
```

**Resultado esperado (200 OK):**
```json
{
  "id": 2,
  "descricao": "Comer pizza",
  "dataCriacao": "2024-01-15T10:36:00"
}
```

**Nota:** A recompensa sorteada é aleatória entre as cadastradas.

#### Teste 4.5: Listar recompensas recebidas

```bash
GET http://localhost:8080/api/recompensas/recebidas?usuarioId=1
```

**Resultado esperado (200 OK):**
```json
[
  {
    "id": 2,
    "descricao": "Comer pizza",
    "dataCriacao": "2024-01-15T10:36:00"
  }
]
```

#### Teste 4.6: Tentar sortear recompensa sem atingir 80% (deve falhar)

Primeiro, registre progresso com menos de 80%:

```bash
POST http://localhost:8080/api/progresso?usuarioId=1
Content-Type: application/json

{
  "habitosConcluidos": 1,
  "totalHabitos": 3
}
```

Depois tente sortear:

```bash
POST http://localhost:8080/api/recompensas/sorteio?usuarioId=1
```

**Resultado esperado (400 Bad Request):**
```json
{
  "timestamp": "2024-01-15T11:00:00",
  "status": 400,
  "error": "Estado Inválido",
  "message": "Usuário não atingiu 80% de conclusão dos hábitos hoje",
  "path": "/api/recompensas/sorteio"
}
```

---

### 5️⃣ Teste de Níveis e Pontos

**Objetivo:** Testar evolução de níveis

#### Teste 5.1: Registrar progresso múltiplas vezes para ganhar 100 pontos

Execute o progresso 5 vezes com 2 hábitos concluídos (20 pontos cada vez):

```bash
POST http://localhost:8080/api/progresso?usuarioId=1
Content-Type: application/json

{
  "habitosConcluidos": 2,
  "totalHabitos": 3
}
```

#### Teste 5.2: Verificar usuário (deve estar nível 2)

```bash
GET http://localhost:8080/api/usuarios/1
```

**Resultado esperado (200 OK):**
```json
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao@example.com",
  "pontos": 100,
  "nivel": 2
}
```

---

### 6️⃣ Teste de Histórico

#### Teste 6.1: Obter histórico de progresso

```bash
GET http://localhost:8080/api/progresso/historico?usuarioId=1&dias=7
```

**Resultado esperado (200 OK):**
Lista de todos os progressos dos últimos 7 dias, sendo hoje o mais recente.

#### Teste 6.2: Obter histórico limitado

```bash
GET http://localhost:8080/api/progresso/historico?usuarioId=1&dias=3
```

**Resultado esperado (200 OK):**
Lista de no máximo 3 progressos recentes.

---

## 🔍 Cenário Completo (Happy Path)

Este é um teste de fluxo completo da gamificação:

1. ✅ Usuário João se cadastra
2. ✅ João cria 3 hábitos
3. ✅ João registra que cumpriu 2/3 habitos = 20 pontos, nível 1
4. ✅ João registra que cumpriu 3/3 habitos = 30 pontos (50 total)
5. ✅ Como atingiu ≥80%, João pode sortear recompensa e ganha uma
6. ✅ Repetindo múltiplas vezes, João atinge 100 pontos = nível 2

---

## 🚨 Testes de Tratamento de Erros

### Teste: Usuário não encontrado

```bash
GET http://localhost:8080/api/usuarios/999
```

**Resultado esperado (404 Not Found):**
```json
{
  "timestamp": "2024-01-15T11:05:00",
  "status": 404,
  "error": "Não Encontrado",
  "message": "Usuário não encontrado",
  "path": "/api/usuarios/999"
}
```

### Teste: Hábito não encontrado

```bash
GET http://localhost:8080/api/habitos/999
```

**Resultado esperado (404 Not Found):**
```json
{
  "timestamp": "2024-01-15T11:05:00",
  "status": 404,
  "error": "Não Encontrado",
  "message": "Hábito não encontrado",
  "path": "/api/habitos/999"
}
```

### Teste: Nenhuma recompensa cadastrada

```bash
POST http://localhost:8080/api/recompensas/sorteio?usuarioId=1
```

(Quando o usuário não tem recompensas cadastradas)

**Resultado esperado (404 Not Found):**
```json
{
  "timestamp": "2024-01-15T11:05:00",
  "status": 404,
  "error": "Não Encontrado",
  "message": "Nenhuma recompensa cadastrada para este usuário",
  "path": "/api/recompensas/sorteio"
}
```

---

## 💡 Dicas para Testes

1. **Use o Postman:** Importe `postman-collection.json` para ter todos os endpoints prontos
2. **Altere usuarioId:** Teste com diferentes usuários (mude o parâmetro `usuarioId`)
3. **Use timestamps:** Acompanhe as datas de criação para verificar a ordem
4. **Verifique no DB:** Faça queries SQL para confirmar os dados salvos
5. **Teste validações:** Tente enviar dados inválidos/incompletos para testar validações

---

## 📊 Verificação no Banco de Dados

Para ver os dados diretamente no PostgreSQL:

```sql
-- Ver todos os usuários com seus pontos e níveis
SELECT * FROM usuario;

-- Ver todos os hábitos ativos
SELECT * FROM habito WHERE ativo = true;

-- Ver progresso do usuário 1
SELECT * FROM progresso_diario WHERE usuario_id = 1;

-- Ver recompensas do usuário 1
SELECT * FROM recompensa WHERE usuario_id = 1;

-- Ver recompensas recebidas
SELECT rr.*, r.descricao FROM recompensa_recebida rr
JOIN recompensa r ON rr.recompensa_id = r.id
WHERE rr.usuario_id = 1;
```

---

## 🎉 Próximos Passos

Após testar todos os endpoints:
1. Documente os resultados
2. Identifique possíveis melhorias
3. Comece o desenvolvimento do Frontend em React
