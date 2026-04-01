# 🚀 Como Rodar a Aplicação Habitos Monitor

## Método 1: Compilar e Rodar JAR (Recomendado)

```bash
# Compilar uma vez
mvn clean package -DskipTests

# Rodar a aplicação
java -jar target/habitos-monitor-1.0.0.jar
```

**Saída esperada:**
```
Started HabitosMonitorApplication in X seconds (JVM running for Y.XXX seconds)
```

## Método 2: Usar o Script `run.sh`

```bash
./run.sh
```

## Método 3: Rodar com Gradle (se preferir)

```bash
./gradlew bootRun
```

---

## 📍 Endpoints Disponíveis

Assim que a aplicação iniciar (Puerto `8080`), você pode acessar:

- **Health Check**: 
  ```bash
  curl http://localhost:8080/actuator/health
  ```

- **Swagger/API Docs** (se configurado):
  ```
  http://localhost:8080/swagger-ui.html
  ```

- **Criar Usuário**:
  ```bash
  curl -X POST http://localhost:8080/api/usuarios \
    -H "Content-Type: application/json" \
    -d '{"nome":"João","email":"joao@example.com","senha":"123456"}'
  ```

---

## 🐛 Troubleshooting

### Maven `spring-boot:run` está falhando?
Isso é um problema conhecido com Lombok + Java 21 no maven-compiler-plugin. Use o método JAR acima.

### Porta 8080 já está em uso?
```bash
# Ver o que está usando a porta
lsof -i :8080

# Matar o processo
kill -9 <PID>
```

### Banco de dados não está conectando?
Certifique-se de que PostgreSQL está rodando:
```bash
podman-compose ps
```

---

## ✅ Checklist de Setup Completo

- [x] Java 21 LTS instalado
- [x] Maven 3.11+ instalado  
- [x] PostgreSQL rodando via podman-compose
- [x] PgAdmin disponível em http://localhost:5050
- [x] Aplicação compilada com sucesso

🎉 Você está pronto!
