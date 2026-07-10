# UM.tesoreria.facturador-service

### Tecnologías

#### Lenguajes y Plataformas
![Java](https://img.shields.io/badge/Java-25-red?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.1.0-green?style=for-the-badge&logo=spring-boot)
![Spring Cloud](https://img.shields.io/badge/Spring_Cloud-2025.1.2-green?style=for-the-badge&logo=spring-cloud)

#### Bases de Datos y Caché
![Caffeine](https://img.shields.io/badge/Caffeine_Cache-3.1.8-blue?style=for-the-badge)

#### Herramientas de Desarrollo
![Maven](https://img.shields.io/badge/Maven-3.9+-blue?style=for-the-badge&logo=apache-maven)
![Docker](https://img.shields.io/badge/Docker-24.0+-blue?style=for-the-badge&logo=docker)
![SonarCloud](https://img.shields.io/badge/SonarCloud-latest-blue?style=for-the-badge&logo=sonarcloud)

#### Frameworks y Librerías
![Spring WebFlux](https://img.shields.io/badge/Spring_WebFlux-4.1.x-green?style=for-the-badge&logo=spring)
![Spring Actuator](https://img.shields.io/badge/Spring_Actuator-4.1.x-green?style=for-the-badge&logo=spring)
![Lombok](https://img.shields.io/badge/Lombok-1.18.30-pink?style=for-the-badge)

#### Documentación y API
![OpenAPI](https://img.shields.io/badge/OpenAPI-3.0.3-blue?style=for-the-badge&logo=openapi)

### Estado del Pipeline
[![UM.tesoreria.facturador-service CI](https://github.com/UM-services/UM.tesoreria.facturador-service/actions/workflows/maven.yml/badge.svg)](https://github.com/UM-services/UM.tesoreria.facturador-service/actions/workflows/maven.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=UM-services_UM.tesoreria.facturador-service&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=UM-services_UM.tesoreria.facturador-service)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=UM-services_UM.tesoreria.facturador-service&metric=coverage)](https://sonarcloud.io/summary/new_code?id=UM-services_UM.tesoreria.facturador-service)

### Estado de la Documentación
[![GitHub Pages](https://github.com/UM-services/UM.tesoreria.facturador-service/actions/workflows/pages/pages-build-deployment/badge.svg)](https://github.com/UM-services/UM.tesoreria.facturador-service/actions/workflows/pages/pages-build-deployment)

## 📋 Descripción

Microservicio de facturación electrónica para UM Tesorería. Se encarga de:
- Generación y gestión de facturas electrónicas
- Integración con AFIP para validación de comprobantes
- Procesamiento de pagos y recibos
- Gestión de transacciones y estados de facturación
- Caché de datos para optimización de rendimiento
- Comunicación síncrona con tesoreria-sender-service
- Envío programado de facturas pendientes

## 🏗️ Arquitectura

### Componentes Principales
- **Controller**: Maneja las peticiones HTTP y expone los endpoints REST
- **Service**: Implementa la lógica de negocio y la gestión de transacciones
- **Model**: Define los DTOs y entidades del sistema
- **Configuration**: Configuración de Spring
- **Client**: Integración con servicios externos

### Flujo de Datos
1. Recepción de peticiones HTTP
2. Procesamiento de facturas y recibos
3. Integración con AFIP
4. Gestión de estados y transacciones

## 🚀 Stack Tecnológico

### Backend
- Java 25
- Spring Boot 4.1.0
- Spring Cloud 2025.1.2
- Maven 3.9+

### Herramientas y Utilidades
- SpringDoc OpenAPI 3.0.3
- Spring AOP
- Spring Validation
- Feign Client para comunicación síncrona
- SonarCloud para análisis de código
- JaCoCo para cobertura de pruebas

## 📚 Documentación

- [Documentación Técnica](https://um-services.github.io/UM.tesoreria.facturador-service/)
- [Wiki del Proyecto](https://github.com/UM-services/UM.tesoreria.facturador-service/wiki)
- [CHANGELOG.md](CHANGELOG.md)

## 🔄 API Endpoints

### Facturación
- `GET /api/tesoreria/facturador/facturaPendientes`: Procesa facturas pendientes
- `GET /api/tesoreria/facturador/facturaOne/{chequeraPagoId}`: Procesa una factura específica
- `GET /api/tesoreria/facturador/sendOne/pago/{chequeraPagoId}`: Envía recibo por ID de chequera
- `GET /api/tesoreria/facturador/sendOne/recibo/{facturacionElectronicaId}`: Envía recibo por ID de factura

### Características Principales
- Integración con Consul para registro de servicios
- Caché distribuido con Caffeine
- Validación de datos con Spring Validation
- Documentación automática con OpenAPI
- Monitoreo con Spring Actuator
- Comunicación síncrona con tesoreria-sender-service
- Envío programado de facturas pendientes cada 5 minutos
- Procesamiento automático de lotes de hasta 100 facturas

## 🛠️ Desarrollo

### Requisitos
- JDK 25
- Docker 24.0+
- Maven 3.9+

### Configuración del Entorno Local

1. **Clonar el Repositorio**
```bash
git clone https://github.com/UM-services/UM.tesoreria.facturador-service.git
cd UM.tesoreria.facturador-service
```

2. **Compilar el Proyecto**
```bash
mvn clean package
```

3. **Ejecutar con Docker**
```bash
docker build -t um-tesoreria-facturador-service .
docker run -p 8080:8080 um-tesoreria-facturador-service
```

### Herramientas de Calidad

El proyecto utiliza varias herramientas para mantener la calidad del código:

1. **SonarCloud**
   - Análisis automático en cada PR
   - Dashboard público en [SonarCloud](https://sonarcloud.io/project/overview?id=UM-services_UM.tesoreria.facturador-service)

2. **JaCoCo**
   - Generación de informes de cobertura
   - Integración con SonarCloud

3. **GitHub Actions**
   - CI/CD automatizado
   - Generación de documentación
   - Análisis de calidad

## ✍️ Autor
- Universidad de Mendoza - Ing. Daniel Quinteros
