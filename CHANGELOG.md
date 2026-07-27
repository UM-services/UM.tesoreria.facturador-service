

# Changelog

Todos los cambios notables en este proyecto serán documentados en este archivo.

El formato está basado en [Keep a Changelog](https://keepachangelog.com/es-ES/1.0.0/),
y este proyecto adhiere a [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.4.4] - 2026-07-27

### Changed
- chore(config): Configuración de timeouts de OpenFeign en `bootstrap.yml`. Se establece `connectTimeout` en 15 segundos y `readTimeout` en 300 segundos (5 minutos) para mejorar la resiliencia en comunicaciones síncronas con servicios externos.

### Fuente
- Basado en análisis de cambios staged (`git diff HEAD`) en `src/main/resources/bootstrap.yml`.

## [0.4.3] - 2026-07-10

### Changed
- refactor: Extracción de la interfaz `Jsonifyable` para unificar el método `jsonify()` en DTOs. `FacturacionElectronicaDto` ahora implementa `Jsonifyable` en lugar de duplicar la lógica de serialización JSON.
- chore: Mejora del logging en `FacturadorService.sendOneByChequeraPagoId` con mensajes más descriptivos y formato mejorado para facilitar debugging.

### Fuente
- Basado en análisis de cambios locales (`git diff HEAD`) en `Jsonifyable.java`, `FacturacionElectronicaDto.java` y `FacturadorService.java`.

## [0.4.2] - 2026-06-19

### Fixed
- fix: Corregido formato de serialización ISO 8601 para OffsetDateTime en DTOs. Cambio del patrón de timezone `Z` (ej. `+0000`) a `XX` (ej. `+00:00`) para cumplir con el estándar ISO 8601. Afecta a: ChequeraCuotaDto, ChequeraPagoDto, ChequeraSerieDto, DomicilioDto, FacturacionElectronicaDto, LectivoDto.

### Changed
- chore: Mejorado logging de errores en `FacturadorService.sendOneByFacturacionElectronicaId` al fallar el envío de recibos.
- chore: Limpieza cosmética del archivo `banner.txt`.

### Fuente
- Basado en análisis de cambios locales (`git diff HEAD`) en DTOs, FacturadorService.java y banner.txt.

## [0.4.1] - 2026-06-16

### Changed
- chore: Actualización de Spring Boot parent de 4.0.5 a 4.1.0.
- chore: Actualización de Spring Cloud de 2025.1.0 a 2025.1.2.
- chore: Actualización de springdoc-openapi-starter-webmvc-ui de 3.0.2 a 3.0.3.

### Fuente
- Basado en análisis de dependencias en `pom.xml` (`git diff HEAD`) y documentación.

## [0.4.0] - 2026-04-04

### Changed
- refactor: Migración completa de Kotlin a Java puro en todos los DTOs del proyecto.
  - Los DTOs en `kotlin.tesoreria.*` fueron reescritos en Java en `model.dto.*`
  - Se eliminaron las dependencias de Kotlin (`kotlin-test`, `kotlin-stdlib`, `jackson-module-kotlin`)
  - Se eliminó el plugin `kotlin-maven-plugin` de la configuración de Maven
  - Los clientes Feign ahora referencian los nuevos paquetes Java

### Changed
- chore: Actualización de Spring Boot parent de 4.0.2 a 4.0.5.
- chore: Actualización de springdoc-openapi-starter-webmvc-ui de 3.0.1 a 3.0.2.
- chore: Actualización de JDK de 24 a 25 en configuración de proyecto y workflows.
- chore: Actualización de acciones de GitHub a últimas versiones:
  - `actions/checkout@v4` -> `v6`
  - `actions/setup-java@v4` -> `v5`
  - `actions/cache@v4` -> `v5`
  - `actions/deploy-pages@v4` -> `v5`
  - `docker/login-action@v3` -> `v4`
  - `docker/metadata-action@v5` -> `v6`
  - `docker/setup-buildx-action@v3` -> `v4`
  - `docker/build-push-action@v6` -> `v7`

### Added
- feat: Agregada dependencia `commons-fileupload` versión 1.6.0.

### Removed
- remove: Eliminación total del soporte para Kotlin.
- remove: Eliminación de todas las dependencias de Kotlin.

### Fuente
- Basado en análisis profundo de cambios locales (`git diff HEAD`) y código fuente.
- Actualización verificada en `pom.xml` y archivos de DTOs.

## [0.3.1] - 2026-02-03

### Changed
- chore: Actualización de Spring Boot parent de 4.0.1 a 4.0.2 para incorporar correcciones de seguridad y bug fixes.

### Fuente
- Basado en análisis de cambios locales (`git diff HEAD`) y actualización de dependencias en `pom.xml`.

## [0.3.0] - 2025-08-10

### Changed
- refactor(controller): Uso de `ResponseEntity.ok(...)` en todos los endpoints para mayor claridad y modernización del código.
- feat(service): Se amplía el rango de procesamiento de facturas pendientes de 60 a 90 días.

### Fuente
- Basado en análisis de código (`git diff HEAD`) y revisión de lógica en `FacturadorController` y `FacturadorService`.

## [0.2.0] - 2025-08-07

### Added
- feat: Se agrega el campo `idCondicionIva` en el DTO de facturación y su propagación en el builder y en el servicio principal para mayor compatibilidad con AFIP.

### Changed
- chore: Actualización de Spring Boot a 3.5.4.
- chore: Dockerfile ahora ajusta permisos de la aplicación para el usuario no privilegiado.

### Fuente
- Basado en análisis de código (`git diff HEAD`), historial de commits y actualización de dependencias en `pom.xml`.

## [0.1.0] - 2025-07-20

### Added
- Soporte para Java 24
- Integración con SonarCloud y análisis de código
- Análisis de cobertura con JaCoCo
- Método `jsonify()` en DTOs para logging estructurado
- Nuevo sistema de generación de documentación con Mermaid
- Integración mejorada con GitHub Pages
- Nueva configuración de Docker multi-stage build
- Integración con Spring Cloud 2025.0.0
- Implementación de caché con Caffeine
- Documentación automática con OpenAPI 2.8.9
- Soporte para Kotlin 2.2.0
- Integración con Eureka para registro de servicios
- Sistema de monitoreo con Spring Actuator
- Validación de datos con Spring Validation
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