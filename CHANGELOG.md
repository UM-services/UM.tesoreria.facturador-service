# Changelog

Todos los cambios notables en este proyecto serán documentados en este archivo.

El formato está basado en [Keep a Changelog](https://keepachangelog.com/es-ES/1.0.0/),
y este proyecto adhiere a [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added
- Integración con Spring Cloud 2025.0.0
- Implementación de caché con Caffeine
- Documentación automática con OpenAPI 2.8.8
- Soporte para Kotlin 2.1.21
- Integración con Eureka para registro de servicios
- Sistema de monitoreo con Spring Actuator
- Validación de datos con Spring Validation
- Soporte para Docker con Dockerfile y Dockerfile.local
- Endpoints de monitoreo y métricas
- Nuevo cliente Feign para integración con tesoreria-sender-service
- Implementación de envío programado de facturas pendientes cada hora
- Procesamiento automático de lotes de hasta 100 facturas

### Changed
- Optimización del procesamiento de facturas pendientes
  - Reducción del lote de procesamiento
  - Ajuste de la frecuencia de procesamiento
  - Mejora en el logging
- Migración de RabbitMQ a comunicación síncrona
  - Implementación de cliente Feign para comunicación directa
  - Actualización de servicios para comunicación síncrona
  - Mejora en el manejo de respuestas
- Actualización de dependencias
  - Spring Boot a 3.5.0
  - Spring Cloud a 2025.0.0
  - Kotlin a 2.1.21
  - OpenAPI a 2.8.8
- Actualización a Spring Boot 3.4.4
- Optimización del rendimiento con caché distribuido
- Reestructuración del código para mejor mantenibilidad
- Mejora en la documentación del proyecto
- Actualización de dependencias a sus últimas versiones estables

### Removed
- Eliminación de la configuración de RabbitMQ (RabbitMQConfig.java)
- Eliminación de los servicios de cola (ReciboQueueService.java)
- Eliminación de la comunicación asíncrona
- Eliminación de dependencias de RabbitMQ y Spring AMQP
- Eliminación de endpoints de prueba de colas
- Eliminación del endpoint manual de envío de recibos pendientes
- Eliminación de la configuración de RabbitMQ
- Eliminación de los servicios de cola

### Fixed
- Mejora en la gestión de transacciones
- Optimización del proceso de facturación
- Mejora en el sistema de logging
- Corrección en el manejo de errores en la comunicación síncrona
- Corrección en el manejo de mensajes asíncronos
- Mejora en la gestión de errores
- Optimización de la memoria con mejor manejo de recursos
- Mejora en la comunicación entre servicios

### Security
- Mejora en la validación de datos
- Actualización de dependencias de seguridad
- Implementación de validaciones en la comunicación entre servicios
- Implementación de validaciones de seguridad
- Mejora en el manejo de datos sensibles
- Mejora en la seguridad de la comunicación entre servicios

## [0.0.1-SNAPSHOT] - 2024-03-29

### Added
- Versión inicial del proyecto
- Configuración básica de Spring Boot
- Endpoints básicos para facturación
- Estructura base del proyecto
- Integración inicial con AFIP
- Sistema básico de logging

### Changed
- N/A

### Deprecated
- N/A

### Removed
- N/A

### Fixed
- N/A

### Security
- N/A 