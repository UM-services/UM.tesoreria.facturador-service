# Changelog

Todos los cambios notables en este proyecto serán documentados en este archivo.

El formato está basado en [Keep a Changelog](https://keepachangelog.com/es-ES/1.0.0/),
y este proyecto adhiere a [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added
- Integración con Spring Cloud 2024.0.1
- Implementación de caché con Caffeine
- Documentación automática con OpenAPI 2.8.6
- Soporte para Kotlin 2.1.20
- Integración con Eureka para registro de servicios
- Sistema de monitoreo con Spring Actuator
- Validación de datos con Spring Validation
- Soporte para Docker con Dockerfile y Dockerfile.local
- Endpoints de monitoreo y métricas
- Reactivación del envío programado de recibos pendientes
- Nuevo cliente Feign para integración con tesoreria-sender-service

### Changed
- Actualización a Spring Boot 3.4.4
- Optimización del rendimiento con caché distribuido
- Reestructuración del código para mejor mantenibilidad
- Mejora en la documentación del proyecto
- Actualización de dependencias a sus últimas versiones estables
- Migración de RabbitMQ a comunicación síncrona con tesoreria-sender-service

### Removed
- Eliminación de RabbitMQ y todas sus dependencias
- Eliminación de Spring AMQP
- Eliminación de Spring TX
- Eliminación de colas de mensajes y servicios relacionados
- Eliminación de endpoints de prueba de colas

### Fixed
- Corrección en el manejo de mensajes asíncronos
- Mejora en la gestión de errores
- Optimización de la memoria con mejor manejo de recursos

### Security
- Implementación de validaciones de seguridad
- Mejora en el manejo de datos sensibles

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