# Etapa 1: Build
FROM maven:3.8.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copiar archivos de configuración de Maven
COPY pom.xml .
COPY src ./src

# Copiar Wallet de Oracle Cloud (necesario para la conexión)
COPY Wallet ./Wallet

# Construir el proyecto (omitir tests para acelerar build)
RUN mvn clean package -DskipTests

# Etapa 2: Runtime
FROM eclipse-temurin:17-jre-alpine

LABEL maintainer="tiendamascotas@example.com"
LABEL description="Microservicio de Tienda de Mascotas - Spring Boot"

WORKDIR /app

# Copiar el JAR desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Copiar el Wallet de Oracle Cloud
COPY --from=build /app/Wallet ./Wallet

# Exponer el puerto de la aplicación
EXPOSE 8082

# Variables de entorno con valores por defecto
ENV SPRING_PROFILES_ACTIVE=prod
ENV SERVER_PORT=8082
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Healthcheck para verificar que la aplicación está corriendo
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8082/actuator/health || exit 1

# Comando de inicio
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
