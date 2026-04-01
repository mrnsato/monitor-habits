#!/bin/bash

# Script para rodar a aplicação Habitos Monitor com Java 21
# Em caso de erro com Lombok, use: docker-compose up

set -e

echo "🔨 Compilando aplicação..."
mvn clean compile -q 2>&1 || {
    echo "❌ Erro na compilação - Tentando alternativa..."
    mvn install:install-file -Dfile=pom.xml -q 2>/dev/null || true
    exit 1
}

echo "📦 Empacotando aplicação..."
mvn package -q -DskipTests 2>&1 || {
    echo "⚠️  Erro no package. Tentando spring-boot-maven-plugin..."
    mvn spring-boot:run -q &
    sleep 3
    PID=$!
}

JAR_FILE=$(ls -1 target/habitos-monitor-*.jar 2>/dev/null | tail -1)

if [ -z "$JAR_FILE" ] || [ ! -f "$JAR_FILE" ]; then
    echo "❌ JAR file not found!"
    echo "📝 Alternativa: Use docker-compose"
    echo "   podman-compose up"
    exit 1
fi

echo "✅ JAR criado: $JAR_FILE"
echo "🚀 Iniciando aplicação..."
echo "📍 Acesse: http://localhost:8080"
echo "📊 Health check: http://localhost:8080/actuator/health"
echo "🧪 API Tester: file://$(pwd)/api-tester.html"
echo ""
echo "⏳ Aguardando inicialização (este processo pode levar 10-15 segundos)..."
echo ""

java -jar "$JAR_FILE"
