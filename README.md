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

## 🚀 Stack Tecnológico

- Java 21
- Spring Boot 3.4.3
- Spring Cloud 2024.0.0
- Spring AMQP
- Kotlin 2.1.10
- RabbitMQ 3.12+
- Maven 3.9+

## 📚 Documentación

- [Documentación Técnica](https://um-services.github.io/UM.tesoreria.facturador-service/)
- [Wiki del Proyecto](https://github.com/UM-services/UM.tesoreria.facturador-service/wiki)

## 🔄 API Endpoints

### Facturación
- `GET /facturador/facturaPendientes`: Procesa facturas pendientes
- `GET /facturador/facturaOne/{chequeraPagoId}`: Procesa una factura específica
- `GET /facturador/sendPendientes`: Envía recibos pendientes
- `GET /facturador/sendOne/pago/{chequeraPagoId}`: Envía recibo por ID de chequera
- `GET /facturador/sendOne/factura/{facturacionElectronicaId}`: Envía recibo por ID de factura
- `GET /facturador/testInvoiceQueue/{facturaElectronicaId}`: Prueba el envío de recibos
- `GET /facturador/testManyInvoiceQueue`: Prueba el envío de múltiples recibos

### Notas
- El envío automático de recibos pendientes está temporalmente desactivado
- Los endpoints de prueba son solo para desarrollo

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

## ✍️ Autor
- Universidad de Mendoza - Ing. Daniel Quinteros
