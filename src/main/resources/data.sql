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

