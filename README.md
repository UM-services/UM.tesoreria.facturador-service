# UM.tesoreria.facturador-service

### Tecnologías

#### Lenguajes y Plataformas
![Java](https://img.shields.io/badge/Java-21-red?style=for-the-badge&logo=openjdk)
![Kotlin](https://img.shields.io/badge/Kotlin-2.1.20-purple?style=for-the-badge&logo=kotlin)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.4-green?style=for-the-badge&logo=spring-boot)
![Spring Cloud](https://img.shields.io/badge/Spring_Cloud-2024.0.1-green?style=for-the-badge&logo=spring-cloud)

#### Bases de Datos y Caché
![Caffeine](https://img.shields.io/badge/Caffeine_Cache-3.1.8-blue?style=for-the-badge)

#### Herramientas de Desarrollo
![Maven](https://img.shields.io/badge/Maven-3.9+-blue?style=for-the-badge&logo=apache-maven)
![Docker](https://img.shields.io/badge/Docker-24.0+-blue?style=for-the-badge&logo=docker)
![OpenAPI](https://img.shields.io/badge/OpenAPI-2.8.6-green?style=for-the-badge&logo=openapi-initiative)

#### Frameworks y Librerías
![Spring WebFlux](https://img.shields.io/badge/Spring_WebFlux-3.4.4-green?style=for-the-badge&logo=spring)
![Spring Actuator](https://img.shields.io/badge/Spring_Actuator-3.4.4-green?style=for-the-badge&logo=spring)
![Lombok](https://img.shields.io/badge/Lombok-1.18.30-pink?style=for-the-badge)

### Estado del Pipeline
[![UM.tesoreria.facturador-service CI](https://github.com/UM-services/UM.tesoreria.facturador-service/actions/workflows/maven.yml/badge.svg)](https://github.com/UM-services/UM.tesoreria.facturador-service/actions/workflows/maven.yml)

### Estado de la Documentación
[![GitHub Pages](https://github.com/UM-services/UM.tesoreria.facturador-service/actions/workflows/pages/pages-build-deployment/badge.svg)](https://github.com/UM-services/UM.tesoreria.facturador-service/actions/workflows/pages/pages-build-deployment)

## 📋 Descripción

Microservicio de facturación electrónica para UM Tesorería. Se encarga de:
- Generación y gestión de facturas electrónicas
- Integración con AFIP para validación de comprobantes
- Procesamiento de pagos y recibos
- Gestión de transacciones y estados de facturación
- Caché de datos para optimización de rendimiento
- Trazabilidad de mensajes con UUID
- Envío programado de recibos pendientes

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
- Java 21
- Spring Boot 3.4.4
- Spring Cloud 2024.0.1
- Kotlin 2.1.20
- Maven 3.9+

### Herramientas y Utilidades
- SpringDoc OpenAPI 2.8.6
- Spring AOP
- Spring Validation
- Spring TX

## 📚 Documentación

- [Documentación Técnica](https://um-services.github.io/UM.tesoreria.facturador-service/)
- [Wiki del Proyecto](https://github.com/UM-services/UM.tesoreria.facturador-service/wiki)
- [CHANGELOG.md](CHANGELOG.md)

## 🔄 API Endpoints

### Facturación
- `GET /facturador/facturaPendientes`: Procesa facturas pendientes
- `GET /facturador/facturaOne/{chequeraPagoId}`: Procesa una factura específica
- `GET /facturador/sendPendientes`: Envía recibos pendientes
- `GET /facturador/sendOne/pago/{chequeraPagoId}`: Envía recibo por ID de chequera
- `GET /facturador/sendOne/factura/{facturacionElectronicaId}`: Envía recibo por ID de factura
- `GET /facturador/testInvoiceQueue/{facturaElectronicaId}`: Prueba el envío de recibos
- `GET /facturador/testManyInvoiceQueue`: Prueba el envío de múltiples recibos

### Características Principales
- Integración con Eureka para registro de servicios
- Caché distribuido con Caffeine
- Validación de datos con Spring Validation
- Documentación automática con OpenAPI
- Soporte para Kotlin
- Gestión de transacciones con Spring TX
- Monitoreo con Spring Actuator
- Trazabilidad de mensajes con UUID
- Envío programado de recibos pendientes

## 🛠️ Desarrollo

### Requisitos
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
- El envío automático de recibos pendientes está activado con programación horaria
- Los endpoints de prueba son solo para desarrollo
- Se recomienda revisar el CHANGELOG.md para conocer las últimas actualizaciones
- El servicio utiliza UUID para la trazabilidad de mensajes

## ✍️ Autor
- Universidad de Mendoza - Ing. Daniel Quinteros
