# Habitos Monitor - Deployment Complete ✅

## Summary
Your Habitos Monitor application is now **fully deployed and running** with **Java 21 LTS** in a Docker containerized environment with PostgreSQL database.

## Running Services

### 1. **PostgreSQL Database** (Port 5432)
- **Status**: ✅ Running and Healthy
- **Container**: habitos_postgres
- **Image**: postgres:15-alpine
- **Database**: habitos_db
- **Credentials**: postgres / postgres

### 2. **PgAdmin** (Port 5050)
- **Status**: ✅ Running
- **Container**: habitos_pgadmin
- **Web Interface**: http://localhost:5050
- **Credentials**: admin@example.com / admin

### 3. **Habitos Monitor API** (Port 8080)
- **Status**: ✅ Running  
- **Container**: habitos_app
- **Base URL**: http://localhost:8080
- **Java Version**: 21.0.10 LTS
- **Spring Boot**: 3.2.1

## API Endpoints

### Health Check
```bash
GET /actuator/health
Expected: 200 OK
```

### User Management
```bash
# Create a new user
POST /api/usuarios
Content-Type: application/json

{
  "nome": "User Name",
  "email": "user@example.com",
  "senha": "password"
}

# List all users
GET /api/usuarios

# Get user by ID
GET /api/usuarios/{usuarioId}
```

### Example Requests
```bash
# Create user
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nome":"Maria Silva","email":"maria@example.com","senha":"senha123"}'

# List users
curl http://localhost:8080/api/usuarios

# Get specific user
curl http://localhost:8080/api/usuarios/1
```

## Managing the Application

### View Logs
```bash
# Application logs
podman logs habitos_app -f

# All containers logs
podman-compose logs -f
```

### Start/Stop Services
```bash
# Start all services
cd /home/melsantos/code/habits-app
podman-compose up -d

# Stop all services
podman-compose down

# Stop and remove data
podman-compose down -v
```

### Rebuild and Deploy
```bash
# Rebuild application and restart
podman-compose down
podman-compose build --no-cache
podman-compose up -d
```

## Database Connection

### Using psql (PostgreSQL CLI)
```bash
psql -h localhost -U postgres -d habitos_db
```

### PgAdmin Web Interface
- URL: http://localhost:5050
- Email: admin@example.com
- Password: admin

## Technology Stack

| Component | Version | Status |
|-----------|---------|--------|
| Java | 21 LTS (21.0.10) | ✅ |
| Spring Boot | 3.2.1 | ✅ |
| Spring Data JPA | 3.2.1 | ✅ |
| PostgreSQL | 15-alpine | ✅ |
| Maven | 3.9 | ✅ |
| Hibernate | 6.4.1 | ✅ |

## Key Configuration Files

| File | Purpose |
|------|---------|
| `docker-compose.yml` | Define all services and networking |
| `Dockerfile` | Multi-stage build for containerized JAR |
| `src/main/resources/application.properties` | Spring Boot configuration |
| `src/main/java/com/habitosapp/config/SecurityConfig.java` | Security and CORS setup |
| `pom.xml` | Maven dependencies and build config |

## Security Features

✅ CORS configured for localhost ports (3000, 5173, 8080)  
✅ Public endpoints for user creation  
✅ CSRF protection disabled for API  
✅ Spring Security configured for REST API

## Troubleshooting

### Container won't start
```bash
podman-compose logs habitos_app  # Check logs
podman-compose ps  # Check status
```

### Cannot connect to database
```bash
# Test database connection
curl http://localhost:8080/actuator/health

# Check PostgreSQL container
podman ps | grep postgres
```

### Port already in use
```bash
# Find process using port
lsof -i :8080  # For port 8080
lsof -i :5432  # For database port
lsof -i :5050  # For PgAdmin port
```

### Rebuild everything fresh
```bash
cd /home/melsantos/code/habits-app
podman-compose down -v
podman volume prune
podman-compose build --no-cache
podman-compose up -d
```

## Performance Notes

- **Build time**: ~60-90 seconds (Maven compilation in container)
- **Startup time**: ~5 seconds
- **Database initialization**: Automatic with Hibernate ddl-auto=update
- **Memory usage**: ~300-400MB for full stack

## Next Steps

1. **Deploy to production**: Replace localhost with actual domain names
2. **Update security**: Set strong JWT secrets and update credentials
3. **Configure logging**: Enable ELK stack or other log aggregation
4. **Set up monitoring**: Use Prometheus + Grafana for metrics
5. **Add persistence**: Configure backup strategy for PostgreSQL

## Support

For issues or questions:
1. Check application logs: `podman logs habitos_app`
2. Verify containers are running: `podman-compose ps`
3. Test API connectivity: `curl http://localhost:8080/actuator/health`

---

**Deployment Date**: April 1, 2026  
**Java Target**: 21 LTS (Upgraded from Java 17)  
**Runtime Environment**: Podman & Docker Compose
