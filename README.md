# UM.tesoreria.facturador-service

### Tecnologías

#### Lenguajes y Plataformas
![Java](https://img.shields.io/badge/Java-24-red?style=for-the-badge&logo=openjdk)
![Kotlin](https://img.shields.io/badge/Kotlin-2.2.0-purple?style=for-the-badge&logo=kotlin)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.4-green?style=for-the-badge&logo=spring-boot)
![Spring Cloud](https://img.shields.io/badge/Spring_Cloud-2025.0.0-green?style=for-the-badge&logo=spring-cloud)

#### Bases de Datos y Caché
![Caffeine](https://img.shields.io/badge/Caffeine_Cache-3.1.8-blue?style=for-the-badge)

#### Herramientas de Desarrollo
![Maven](https://img.shields.io/badge/Maven-3.9+-blue?style=for-the-badge&logo=apache-maven)
![Docker](https://img.shields.io/badge/Docker-24.0+-blue?style=for-the-badge&logo=docker)
![SonarCloud](https://img.shields.io/badge/SonarCloud-latest-blue?style=for-the-badge&logo=sonarcloud)

#### Frameworks y Librerías
![Spring WebFlux](https://img.shields.io/badge/Spring_WebFlux-3.4.4-green?style=for-the-badge&logo=spring)
![Spring Actuator](https://img.shields.io/badge/Spring_Actuator-3.4.4-green?style=for-the-badge&logo=spring)
![Lombok](https://img.shields.io/badge/Lombok-1.18.30-pink?style=for-the-badge)

#### Documentación y API
![OpenAPI](https://img.shields.io/badge/OpenAPI-2.8.9-blue?style=for-the-badge&logo=openapi)

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
- Java 24
- Spring Boot 3.5.3
- Spring Cloud 2025.0.0
- Kotlin 2.2.0
- Maven 3.9+

### Herramientas y Utilidades
- SpringDoc OpenAPI 2.8.9
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
- `GET /facturador/facturaPendientes`: Procesa facturas pendientes
- `GET /facturador/facturaOne/{chequeraPagoId}`: Procesa una factura específica
- `GET /facturador/sendOne/pago/{chequeraPagoId}`: Envía recibo por ID de chequera
- `GET /facturador/sendOne/factura/{facturacionElectronicaId}`: Envía recibo por ID de factura

### Características Principales
- Integración con Eureka para registro de servicios
- Caché distribuido con Caffeine
- Validación de datos con Spring Validation
- Documentación automática con OpenAPI
- Soporte para Kotlin
- Monitoreo con Spring Actuator
- Comunicación síncrona con tesoreria-sender-service
- Envío programado de facturas pendientes cada hora
- Procesamiento automático de lotes de hasta 100 facturas

## 🛠️ Desarrollo

### Requisitos
- JDK 24
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
- JDK 21
- Maven 3.9+
- Docker y Docker Compose

### Instalación Local

```bash
# Clonar repositorio
git clone https://github.com/UM-services/UM.tesoreria.facturador-service.git

# Instalar dependencias
mvn clean install

# Ejecutar
mvn spring-boot:run
```

### Docker

```bash
# Construir imagen
docker build -t um-tesoreria-facturador-service .

# Ejecutar contenedor
docker run -p 8080:8080 um-tesoreria-facturador-service
```

## 🔍 Monitoreo y Métricas

El servicio expone endpoints de monitoreo a través de Spring Actuator:
- `/actuator/health`: Estado de salud del servicio
- `/actuator/metrics`: Métricas del sistema
- `/actuator/info`: Información del servicio

## 📝 Notas
- El envío automático de facturas pendientes se ejecuta cada hora (cron: "0 0 * * * *")
- Se procesan hasta 100 facturas pendientes por ejecución
- La comunicación con tesoreria-sender-service es síncrona
- Se recomienda revisar el CHANGELOG.md para conocer las últimas actualizaciones

## ✍️ Autor
- Universidad de Mendoza - Ing. Daniel Quinteros

### Características
- Comunicación síncrona con tesoreria-sender-service
- Procesamiento optimizado de facturas pendientes
- Sistema de caché con Caffeine
- Documentación automática con OpenAPI
- Integración con Eureka para registro de servicios
- Sistema de monitoreo con Spring Actuator
- Validación de datos con Spring Validation

### Notas
- El servicio procesa facturas pendientes de forma programada
- Se utiliza comunicación síncrona directa con tesoreria-sender-service
- El sistema implementa un procesamiento optimizado por lotes
- Se mantiene un sistema de logging detallado para seguimiento de operaciones
- Se utiliza caché para optimizar el rendimiento
- La documentación de la API está disponible a través de OpenAPI

### Endpoints
- `/facturador/recibo`: Endpoint para el procesamiento de recibos
- `/facturador/recibo/pendientes`: Endpoint para consultar recibos pendientes
- `/actuator`: Endpoints de monitoreo y métricas
- `/v3/api-docs`: Documentación OpenAPI
- `/swagger-ui.html`: Interfaz de Swagger UI

### Dependencias Principales
- Spring Boot 3.5.0
- Spring Cloud 2025.0.0
- Spring WebFlux
- Spring Cloud OpenFeign
- Spring Boot Actuator
- Spring Boot Validation
- Spring Boot Cache
- Caffeine Cache
- SpringDoc OpenAPI 2.8.8
- Kotlin 2.1.21
- Lombok
