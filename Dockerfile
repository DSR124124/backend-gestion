# ============================================
# Dockerfile para Backend de Gestión
# Optimizado para Dockploy
# ============================================

# Etapa 1: Build
FROM maven:3.9.5-eclipse-temurin-17-alpine AS build

# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivos de Maven para aprovechar cache de capas
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Descargar dependencias (se cachea esta capa)
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Copiar el código fuente
COPY src ./src

# Compilar la aplicación (sin tests para producción)
RUN ./mvnw clean package -DskipTests -B

# ============================================
# Etapa 2: Runtime
FROM eclipse-temurin:17-jre-alpine

# Instalar wget para health check
RUN apk add --no-cache wget

# Metadatos del contenedor
LABEL maintainer="Backend Gestión"
LABEL description="Backend de Gestión - Sistema de Autenticación y Control de Acceso"
LABEL version="1.0"

# Crear usuario no-root para seguridad
RUN addgroup -S spring && adduser -S spring -G spring

# Establecer directorio de trabajo
WORKDIR /app

# Copiar el JAR desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Cambiar propietario del archivo
RUN chown spring:spring app.jar

# Cambiar al usuario no-root
USER spring:spring

# Exponer el puerto (debe coincidir con server.port en application.properties)
EXPOSE 8080

# Variables de entorno por defecto (se pueden sobrescribir en Dockploy)
ENV JAVA_OPTS="-Xms256m -Xmx512m" \
    SERVER_ADDRESS="0.0.0.0" \
    SERVER_PORT="8080"

# Health check para Docker (más tiempo de inicio para Spring Boot)
HEALTHCHECK --interval=30s --timeout=10s --start-period=120s --retries=5 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || wget --no-verbose --tries=1 --spider http://localhost:8080/api/health || exit 1

# Comando para ejecutar la aplicación
# Asegurar que escucha en 0.0.0.0 para ser accesible desde fuera del contenedor
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Dserver.address=0.0.0.0 -Dserver.port=8080 -jar app.jar"]

