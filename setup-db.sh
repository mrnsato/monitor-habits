#!/bin/bash

# Script para configurar o banco de dados PostgreSQL para o projeto Habitos Monitor

echo "======================================"
echo "Configurando Banco de Dados PostgreSQL"
echo "======================================"

# Cores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Usuário padrão do PostgreSQL
POSTGRES_USER="postgres"
DB_NAME="habitos_db"
DB_USER="postgres"
DB_PASSWORD="postgres"

echo -e "${YELLOW}Nota: Este script usa as credenciais padrão do PostgreSQL${NC}"
echo -e "${YELLOW}Altere as variáveis se necessário${NC}"
echo ""

# Verificar se psql está instalado
if ! command -v psql &> /dev/null; then
    echo -e "${RED}PostgreSQL não está instalado ou psql não está no PATH${NC}"
    exit 1
fi

echo -e "${YELLOW}Criando banco de dados '${DB_NAME}'...${NC}"

# Criar banco de dados
PGPASSWORD=$DB_PASSWORD psql -U $DB_USER -h localhost -c "CREATE DATABASE $DB_NAME;" 2>/dev/null

if [ $? -eq 0 ] || grep -q "already exists" <<< "$?"; then
    echo -e "${GREEN}✓ Banco de dados criado com sucesso (ou já existe)${NC}"
else
    echo -e "${RED}✗ Erro ao criar banco de dados${NC}"
    echo "Certifique-se de que PostgreSQL está rodando e as credenciais estão corretas"
    exit 1
fi

echo ""
echo -e "${GREEN}======================================"
echo "Configuração concluída!"
echo "=====================================${NC}"
echo ""
echo "Próximas etapas:"
echo "1. Atualize as credenciais em src/main/resources/application.properties"
echo "2. Execute: mvn spring-boot:run"
echo "3. Acesse: http://localhost:8080"
echo ""
