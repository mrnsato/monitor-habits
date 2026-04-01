# 🚀 Guia de Configuração - Habitos Monitor

## 📋 Índice
1. [Requisitos](#requisitos)
2. [Configuração Rápida (Docker)](#configuração-rápida-docker)
3. [Configuração Manual (PostgreSQL Local)](#configuração-manual-postgresql-local)
4. [Compilar e Executar](#compilar-e-executar)
5. [Primeira Execução](#primeira-execução)
6. [Solução de Problemas](#solução-de-problemas)

---

## 📦 Requisitos

### Opção 1: Com Docker Compose (Recomendado)
- Docker Desktop
- Docker Compose
- Java 17+
- Maven 3.6+

### Opção 2: Sem Docker
- PostgreSQL 12+
- Java 17+
- Maven 3.6+
- Git

---

## 🐳 Configuração Rápida (Docker)

Esta é a forma mais rápida de começar. O Docker Compose vai subir PostgreSQL + PgAdmin automaticamente.

### Passo 1: Instalar Docker

[Instale o Docker Desktop](https://www.docker.com/products/docker-desktop)

### Passo 2: Subir os containers

```bash
# Navegue até a pasta do projeto
cd /home/melsantos/code/habits-app

# Suba os containers
docker-compose up -d
```

### Passo 3: Verificar se os containers estão rodando

```bash
docker-compose ps
```

Você deve ver:
```
NAME                 STATUS
habitos_postgres     Up (healthy)
habitos_pgadmin      Up
```

### Passo 4: Acessar PgAdmin (Opcional)

- URL: `http://localhost:5050`
- Email: `admin@example.com`
- Senha: `admin`

**Para conectar ao PostgreSQL no PgAdmin:**
1. Server → Register → Server
2. Nome: `Habitos`
3. Connection tab:
   - Host: `postgres`
   - Port: `5432`
   - Username: `postgres`
   - Password: `postgres`
   - Database: `habitos_db`

### Passo 5: Parar os containers

```bash
docker-compose down
```

**Nota:** Os dados são persistentes em `postgres_data` volume.

---

## 🛠️ Configuração Manual (PostgreSQL Local)

Se preferir não usar Docker, siga estes passos.

### Passo 1: Instalar PostgreSQL

#### Windows
[Download PostgreSQL Installer](https://www.postgresql.org/download/windows/)

#### macOS
```bash
brew install postgresql
brew services start postgresql
```

#### Linux
```bash
sudo apt update
sudo apt install postgresql postgresql-contrib
sudo service postgresql start
```

### Passo 2: Criar banco de dados

```bash
# Conectar ao PostgreSQL
psql -U postgres

# Dentro do psql:
CREATE DATABASE habitos_db;

# Verificar criação
\l

# Sair
\q
```

### Passo 3: Configurar credenciais

Edite `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/habitos_db
spring.datasource.username=postgres
spring.datasource.password=sua_senha
```

---

## 🏗️ Compilar e Executar

### Opção 1: Maven (Recomendado)

```bash
# Navegar para a pasta do projeto
cd /home/melsantos/code/habits-app

# Limpar build anterior
mvn clean

# Compilar
mvn compile

# Executar via Maven
mvn spring-boot:run
```

### Opção 2: Maven + Build Fat JAR

```bash
# Compilar e empacotar
mvn clean package

# Executar o JAR
java -jar target/habitos-monitor-1.0.0.jar
```

### Opção 3: IDE (IntelliJ/Eclipse/VS Code)

**IntelliJ IDEA:**
1. File → Open → Selecione a pasta do projeto
2. Espere indexação completar
3. Run → Run 'HabitosApplication'

**VS Code + Extension Pack for Java:**
1. Abra a pasta do projeto
2. Pressione `Ctrl+Shift+P` (Cmd+Shift+P no Mac)
3. Digite `Java: Debug Launch Configuration`
4. Selecione `HabitosApplication`

---

## ✅ Primeira Execução

Quando a aplicação inicia com sucesso, você vê:

```
.   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_|\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.1)

2024-01-15 10:30:45.123 INFO  Starting HabitosApplication using Java 17.0.5
...
2024-01-15 10:30:48.456 INFO  Started HabitosApplication in 2.789 seconds
```

### Validar que está funcionando

```bash
curl http://localhost:8080/api/usuarios
```

Resposta esperada:
```json
[]
```

---

## 🧪 Testar Endpoints

### Opção 1: Importar no Postman

1. Abra Postman
2. File → Import
3. Selecione `postman-collection.json`
4. Clique em `Import`
5. Comece a testar!

### Opção 2: cURL

```bash
# Criar usuário
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Test User",
    "email": "test@example.com",
    "senha": "password123"
  }'

# Listar usuários
curl http://localhost:8080/api/usuarios
```

### Opção 3: Thunder Client (VS Code)

1. Instale a extensão "Thunder Client"
2. Abra `postman-collection.json`
3. Clique em "Import"
4. Teste os endpoints

---

## 🔧 Configurações Importantes

### `application.properties`

```properties
# Servidor
server.port=8080

# Banco de dados
spring.datasource.url=jdbc:postgresql://localhost:5432/habitos_db
spring.datasource.username=postgres
spring.datasource.password=postgres

# JPA
spring.jpa.hibernate.ddl-auto=update  # auto-criar/atualizar tabelas
spring.jpa.show-sql=true               # mostrar SQL nos logs

# JWT (será usado no futuro)
jwt.secret=sua-chave-secreta-super-segura
jwt.expiration=86400000                # 24 horas
```

---

## 📂 Estrutura de Arquivos

```
habitos-app/
├── src/
│   ├── main/
│   │   ├── java/com/habitosapp/
│   │   │   ├── HabitosApplication.java     # Main class
│   │   │   ├── controller/                  # REST endpoints
│   │   │   ├── service/                     # Business logic
│   │   │   ├── model/                       # JPA entities
│   │   │   ├── repository/                  # Data access
│   │   │   ├── dto/                         # Request/response objects
│   │   │   ├── config/                      # Configurations
│   │   │   └── exception/                   # Error handling
│   │   └── resources/
│   │       └── application.properties       # App configuration
│   └── test/
│       └── java/...                         # Unit tests
├── pom.xml                                  # Maven configuration
├── docker-compose.yml                       # Docker setup
├── README.md                                # Documentation
├── TESTING.md                               # Test guide
├── SETUP.md                                 # This file
└── postman-collection.json                  # API tests
```

---

## 🚨 Solução de Problemas

### Erro: "Connection refused" / "PostgreSQL is not running"

**Solução com Docker:**
```bash
docker-compose ps
# Se não estiver rodando:
docker-compose up -d
```

**Solução com PostgreSQL local:**
```bash
# macOS
brew services start postgresql

# Linux
sudo service postgresql start

# Windows (via PowerShell)
net start postgresql-x64-15
```

### Erro: "FATAL: Ident authentication failed"

Edite `/etc/postgresql/15/main/pg_hba.conf` (Linux/Mac) ou `C:\Program Files\PostgreSQL\15\data\pg_hba.conf` (Windows):

Mude de:
```
local   all             all                                     ident
```

Para:
```
local   all             all                                     md5
```

Reinicie o PostgreSQL.

### Erro: "database 'habitos_db' does not exist"

```bash
# Crie o banco
psql -U postgres -c "CREATE DATABASE habitos_db;"

# Ou via Docker
docker-compose up -d
```

### Erro: "Port 5432 already in use"

```bash
# Mude a porta no docker-compose.yml
ports:
  - "5433:5432"  # mudou de 5432 para 5433

# Ou atualize application.properties
spring.datasource.url=jdbc:postgresql://localhost:5433/habitos_db
```

### Erro: "Maven command not found"

Instale Maven:
- [Download Maven](https://maven.apache.org/download.cgi)
- Adicione ao PATH do sistema

### Erro: "Java 17 not found"

Instale Java 17+:
- [Download JDK 17+](https://jdk.java.net/17/)
- Configure JAVA_HOME

```bash
# Verificar versão instalada
java -version

# Deve mostrar algo como:
# openjdk version "17.0.5" 2022-10-18
```

---

## 📞 Checklist de Setup

- [ ] Git clonado/projeto baixado
- [ ] Java 17+ instalado e configurado
- [ ] Maven instalado
- [ ] PostgreSQL rodando (Docker ou local)
- [ ] `application.properties` configurado
- [ ] `mvn clean compile` executado sem erros
- [ ] `mvn spring-boot:run` iniciado com sucesso
- [ ] `curl http://localhost:8080/api/usuarios` retorna `[]`
- [ ] Postman/Insomnia importado e testado
- [ ] Primeiro usuário criado e testado

---

## 🎉 Está Pronto!

Se chegou até aqui, o backend está pronto para:
1. ✅ Testes de endpoints
2. ✅ Integração com frontend
3. ✅ Deploy em produção

**Próximos passos:**
1. Leia [TESTING.md](TESTING.md) para testar os endpoints
2. Teste a gamificação (hábitos → progresso → recompensas)
3. Comece a trabalhar no Frontend em React
4. Configure JWT para autenticação (futura melhoria)

---

**Precisando de ajuda?** Consulte [README.md](README.md) para mais informações sobre os endpoints.
