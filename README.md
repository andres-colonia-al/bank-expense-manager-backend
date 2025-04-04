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

1. Ejecuta la aplicación desde tu IDE o con el siguiente comando:
```
mvn spring-boot:run
Accede a la API a través de http://localhost:8080/
```
(El backend se despliega como un .jar)

2. El proyecto contiene un documento import.sql que se ejecuta al inicializar el proyecto, si se genera algun error,
por favor ejecutar el siguiente script para poblar los datos iniciales a la base de datos

```
-- ======================================
-- INSERCIÓN DE DATOS DE INICIO
-- ======================================

-- Departamentos
INSERT INTO departamento (nombre_departamento, monto_maximo, monto_acumulado) VALUES
('Finanzas', 10000.00, 0.0),
('Recursos Humanos', 5000.00, 0.0),
('Marketing', 7000.00, 0.0);

-- Roles (Basado en tu enum RoleName)
INSERT INTO rol (nombre_rol, account_enable, account_non_expired, credential_non_expired, account_non_locked) VALUES
('ADMIN', true, true, true, true),
('USER', true, true, true, true);

-- Usuarios
INSERT INTO usuario (nombre, apellido, username, email, password, id_departamento) VALUES
('Andrés', 'Colonia', 'andrescolonia', 'andres@example.com', '1234', 1),
('Felipe', 'Aldana', 'felipealdana', 'felipe@example.com', '1234', 2),
('Maria', 'Perez', 'mariap', 'maria@example.com', '1234', 3);

-- Relación Usuario-Rol
INSERT INTO user_roles (id_usuario, id_rol) VALUES
(1, 1), -- Andrés es ADMIN
(2, 2), -- Felipe es USER
(3, 2); -- Maria es USER

-- Transacciones (Basado en tu enum RoleCategory)
INSERT INTO transacciones (nombre_transaccion, descripcion, monto, created_at, category, id_usuario) VALUES
('Capacitación en Java', 'Curso avanzado de Java para el equipo técnico.', 500.00, NOW(), 'CAPACITACION', 1),
('Viaje a Conferencia', 'Asistencia a conferencia internacional en tecnología.', 2500.00, NOW(), 'VIAJES', 1),
('Compra de Papelería', 'Material de oficina (hojas, bolígrafos, carpetas).', 150.00, NOW(), 'PAPELERIA', 2),
('Servicio de Mensajería', 'Envío de documentación importante.', 75.00, NOW(), 'SERVICIOS_MENSAJERIA', 3),
('Reembolso de Transporte', 'Taxi para desplazamiento local.', 30.00, NOW(), 'TRANSPORTE_LOCAL', 2),
('Reparaciones Menores', 'Reparación de equipos de oficina.', 400.00, NOW(), 'REPARACIONES_MENORES', 1),
('Servicios Externos', 'Contratación de consultoría externa.', 1200.00, NOW(), 'SERVICIOS_EXTERNOS', 3),
('Suministros de Oficina', 'Compra de escritorios y sillas.', 700.00, NOW(), 'SUMINISTROS_DE_OFICINA', 2),
('Mantenimiento General', 'Revisión anual de sistemas eléctricos.', 900.00, NOW(), 'MANTENIMIENTO', 1),
('Operativo Diario', 'Gastos operativos menores.', 150.00, NOW(), 'OPERATIVO', 3),
('Reembolsos Varios', 'Reembolso por gastos imprevistos.', 50.00, NOW(), 'REEMBOLSOS_VARIOS', 2);
```

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
