# Proyecto de Gestión de Gastos Menores por Departamento

Este proyecto consiste en un backend desarrollado con Spring Boot y Hibernate, 
que expone una API RESTful para la gestión de gastos menores por departamento en un banco. 
el backend permite el registro, actualización, eliminación y consulta de transacciones, usuarios y departamentos 
con su respectivo presupuesto. Además, contiene la logica de autenticación mediante base de datos.

## Requisitos Previos
- Java 21
- Spring Boot 3.x
- Maven 3.8+
- MySQL 8.x
- IDE (recomendado: IntelliJ IDEA)
- Git

## Configuración del Proyecto
1. Clona este repositorio:
```
git clone https://github.com/andres-colonia-al/bank-expense-manager-backend
```

2. Importa el proyecto en tu IDE (IntelliJ IDEA recomendado).
3. Asegúrate de tener configurado Java 21.
4. Configura tu base de datos MySQL y crea una base de datos con el nombre
`bankexpensemanager`.
6. Configura el archivo `application.properties` con tus credenciales de base de datos.

### Configuración de `application.properties`
```properties
#Configuración de base de datos
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña

#configuración de las direcciones ip (app) y local (web) para permitir la conexión con el frontend.
app.cors.allowed-origins=http://localhost:8081,http://192.168.1.100:8082
```

## Estructura del Proyecto
El proyecto sigue una estructura basada en la Arquitectura Limpia (Clean Architecture) o DDD (Domain-Driven Design), con las siguientes capas:

- application: Contiene los casos de uso y la lógica de negocio principal.
- domain: Define las entidades.
- infrastructure: Implementaciones concretas de repositorios y configuración de bases de datos.
- web: Controladores que manejan las solicitudes HTTP y generan los endpoints para su consumo.

## Endpoints Disponibles

### Transacciones
- GET /transaction/all - Obtener todas las transacciones.
- GET /transaction/usuario/{userId} - Obtener transacciones de un usuario específico.
- GET /transaction/category/{roleCategory} - Obtener transacciones de un categoria específica.
- GET /transaction/departments/{deptId} - Obtener transacciones de un departamento específico.
- GET /transaction/{idTransaction} - Obtener una transaccion a partir de su id.
- POST /transaction - Crear una nueva transacción.
- DELETE /transaction/{idTransaction} - Eliminar una transacción.

### Usuarios
- GET /user - Obtener todos los usuarios.
- GET /user/{userId} - Obtener información detallada de un usuario.
- POST /user - Registrar un nuevo usuario.
- PUT /user/{userId} - Actualizar la información de un usuario.
- DELETE /user/{userId} - Eliminar un usuario.
- POST /user/login - Iniciar sesión (autenticación básica).

### Departamentos
- GET /department - Obtener todos los departamentos.
- GET /department/{idDepartment} - Obtener información detallada de un departamento.
- POST /department - Registrar un nuevo departamento.
- PUT /department/{idDepartment} - Actualizar la información de un departamento.
- DELETE /department/{idDepartment} - Eliminar un departamento.


## Ejecución del Proyecto

Ejecuta la aplicación desde tu IDE o con el siguiente comando:
```
mvn spring-boot:run
Accede a la API a través de http://localhost:8080/
```
El backend se despliega como un .jar

## Tecnologías Utilizadas
- Spring Boot
- Hibernate (JPA)
- MySQL
- Lombook
- Spring Validation


## Próximos Pasos
Integrar con el frontend web (JSP) o app (React Native), por favor refirigirse al siguiente repositorio:

## Autor
Andrés Felipe Colonia Aldana
