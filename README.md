# UM.tesoreria.facturador-service

### Estado del Pipeline
[![UM.tesoreria.facturador-service CI](https://github.com/UM-services/UM.tesoreria.facturador-service/actions/workflows/maven.yml/badge.svg)](https://github.com/UM-services/UM.tesoreria.facturador-service/actions/workflows/maven.yml)

### Estado de la Documentación
[![GitHub Pages](https://github.com/UM-services/UM.tesoreria.facturador-service/actions/workflows/pages/pages-build-deployment/badge.svg)](https://github.com/UM-services/UM.tesoreria.facturador-service/actions/workflows/pages/pages-build-deployment)

## 📋 Descripción

Microservicio de facturación electrónica para UM Tesorería. Se encarga de:
- Generación y gestión de facturas electrónicas
- Integración con AFIP para validación de comprobantes
- Procesamiento de pagos y recibos
- Comunicación asíncrona mediante RabbitMQ
- Gestión de transacciones y estados de facturación
- Caché de datos para optimización de rendimiento

## 🚀 Stack Tecnológico

- Java 21
- Spring Boot 3.4.4
- Spring Cloud 2024.0.1
- Spring AMQP
- Kotlin 2.1.20
- RabbitMQ 3.12+
- Maven 3.9+
- SpringDoc OpenAPI 2.8.6
- Caffeine Cache
- Lombok
- Spring AOP
- Spring WebFlux

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

## 🛠️ Desarrollo

### Requisitos
- JDK 21
- Maven 3.9+
- RabbitMQ 3.12+
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

## 📝 Notas
- El envío automático de recibos pendientes está temporalmente desactivado
- Los endpoints de prueba son solo para desarrollo
- Se recomienda revisar el CHANGELOG.md para conocer las últimas actualizaciones

## ✍️ Autor
- Universidad de Mendoza - Ing. Daniel Quinteros
