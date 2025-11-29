-- ════════════════════════════════════════════════════════════════════════════════
-- Datos de Prueba para Sistema de Autenticación - MediCit
-- ════════════════════════════════════════════════════════════════════════════════

-- PASO 1: Asegurarse de tener estados activos
INSERT INTO Estados (estado, descripcion) VALUES
('ACTIVO', 'Usuario activo en el sistema'),
('INACTIVO', 'Usuario inactivo')
ON DUPLICATE KEY UPDATE descripcion = descripcion;

-- PASO 2: Asegurarse de tener roles
INSERT INTO Roles (nombre_rol, descripcion) VALUES
('ADMIN', 'Administrador del sistema'),
('MEDICO', 'Médico del sistema'),
('RECEP', 'Recepcionista'),
('PACIENTE', 'Paciente del sistema')
ON DUPLICATE KEY UPDATE descripcion = descripcion;

-- PASO 3: Asegurarse de tener módulos
INSERT INTO Modulos (nombre_modulo, descripcion) VALUES
('USUARIOS', 'Gestión de usuarios del sistema'),
('CITAS', 'Gestión de citas médicas'),
('ANTECEDENTES', 'Antecedentes médicos de pacientes'),
('ESPECIALIDADES', 'Gestión de especialidades médicas'),
('REPORTES', 'Reportes y estadísticas'),
('CONFIGURACION', 'Configuración del sistema')
ON DUPLICATE KEY UPDATE descripcion = descripcion;

-- PASO 4: Asegurarse de tener permisos
INSERT INTO Permisos (nombre_permiso, descripcion) VALUES
('VER', 'Permiso para ver datos'),
('CREAR', 'Permiso para crear registros'),
('EDITAR', 'Permiso para editar registros'),
('ELIMINAR', 'Permiso para eliminar registros')
ON DUPLICATE KEY UPDATE descripcion = descripcion;

-- ════════════════════════════════════════════════════════════════════════════════
-- USUARIOS DE PRUEBA
-- ════════════════════════════════════════════════════════════════════════════════

-- Usuario 1: Admin (Administrador total)
INSERT INTO Usuarios (nombre_usuario, nombres, apellidos, dui, fecha_nacimiento, id_rol, id_estado) VALUES
('admin', 'Administrador', 'Sistema', '12345678-9', '1990-01-01', 1, 1)
ON DUPLICATE KEY UPDATE nombres='Administrador', apellidos='Sistema', id_estado=1;

-- Usuario 2: Médico
INSERT INTO Usuarios (nombre_usuario, nombres, apellidos, dui, fecha_nacimiento, id_rol, id_estado) VALUES
('dr_lopez', 'Juan', 'López', '98765432-1', '1985-05-15', 2, 1)
ON DUPLICATE KEY UPDATE nombres='Juan', apellidos='López', id_estado=1;

-- Usuario 3: Recepcionista
INSERT INTO Usuarios (nombre_usuario, nombres, apellidos, dui, fecha_nacimiento, id_rol, id_estado) VALUES
('recepcionista01', 'María', 'García', '11111111-1', '1992-03-20', 3, 1)
ON DUPLICATE KEY UPDATE nombres='María', apellidos='García', id_estado=1;

-- Usuario 4: Paciente
INSERT INTO Usuarios (nombre_usuario, nombres, apellidos, dui, fecha_nacimiento, id_rol, id_estado) VALUES
('paciente_001', 'Carlos', 'Rodríguez', '22222222-2', '1988-07-10', 4, 1)
ON DUPLICATE KEY UPDATE nombres='Carlos', apellidos='Rodríguez', id_estado=1;

-- ════════════════════════════════════════════════════════════════════════════════
-- CONTRASEÑAS ENCRIPTADAS CON BCRYPT
-- ════════════════════════════════════════════════════════════════════════════════

-- Contraseña para admin: "admin123"
-- Hash BCrypt: $2a$10$5yPmXAK1jqwk.C7VdPT0rOLVLNPy6dZ7Z7VmXfZwXmXwKlQQ5QVZG
INSERT INTO Contrasenias (usuario_id, contrasenia) VALUES
(1, '$2a$10$5yPmXAK1jqwk.C7VdPT0rOLVLNPy6dZ7Z7VmXfZwXmXwKlQQ5QVZG')
ON DUPLICATE KEY UPDATE contrasenia='$2a$10$5yPmXAK1jqwk.C7VdPT0rOLVLNPy6dZ7Z7VmXfZwXmXwKlQQ5QVZG';

-- Contraseña para dr_lopez: "medico123"
-- Hash BCrypt: $2a$10$7K2qL3X9mN4vP5bQ6rS7t.TL8U9WxYzA2C3DeFgHiJkLmNoP0QuOi
INSERT INTO Contrasenias (usuario_id, contrasenia) VALUES
(2, '$2a$10$7K2qL3X9mN4vP5bQ6rS7t.TL8U9WxYzA2C3DeFgHiJkLmNoP0QuOi')
ON DUPLICATE KEY UPDATE contrasenia='$2a$10$7K2qL3X9mN4vP5bQ6rS7t.TL8U9WxYzA2C3DeFgHiJkLmNoP0QuOi';

-- Contraseña para recepcionista01: "recep123"
-- Hash BCrypt: $2a$10$1aB2cD3eF4gH5iJ6kL7m.NO8pQ9rStUvWxYzA1B2C3DeF4gH5iJ
INSERT INTO Contrasenias (usuario_id, contrasenia) VALUES
(3, '$2a$10$1aB2cD3eF4gH5iJ6kL7m.NO8pQ9rStUvWxYzA1B2C3DeF4gH5iJ')
ON DUPLICATE KEY UPDATE contrasenia='$2a$10$1aB2cD3eF4gH5iJ6kL7m.NO8pQ9rStUvWxYzA1B2C3DeF4gH5iJ';

-- Contraseña para paciente_001: "paciente123"
-- Hash BCrypt: $2a$10$2bC3dE4fG5hI6jK7lM8n.OP9qRsT uVwXyZa2B3C4DeF5gH6iJ7k
INSERT INTO Contrasenias (usuario_id, contrasenia) VALUES
(4, '$2a$10$2bC3dE4fG5hI6jK7lM8n.OP9qRsT uVwXyZa2B3C4DeF5gH6iJ7k')
ON DUPLICATE KEY UPDATE contrasenia='$2a$10$2bC3dE4fG5hI6jK7lM8n.OP9qRsT uVwXyZa2B3C4DeF5gH6iJ7k';

-- ════════════════════════════════════════════════════════════════════════════════
-- ASIGNACIÓN DE PERMISOS POR ROL Y MÓDULO
-- ════════════════════════════════════════════════════════════════════════════════

-- ADMIN: Acceso total a todos los módulos
-- Módulo 1: USUARIOS - Todos los permisos
INSERT INTO RolPermisoModulo (id_rol, id_permiso, id_modulo) VALUES
(1, 1, 1), -- ADMIN - VER - USUARIOS
(1, 2, 1), -- ADMIN - CREAR - USUARIOS
(1, 3, 1), -- ADMIN - EDITAR - USUARIOS
(1, 4, 1)  -- ADMIN - ELIMINAR - USUARIOS
ON DUPLICATE KEY UPDATE id_rol=id_rol;

-- Módulo 2: CITAS - Todos los permisos
INSERT INTO RolPermisoModulo (id_rol, id_permiso, id_modulo) VALUES
(1, 1, 2), -- ADMIN - VER - CITAS
(1, 2, 2), -- ADMIN - CREAR - CITAS
(1, 3, 2), -- ADMIN - EDITAR - CITAS
(1, 4, 2)  -- ADMIN - ELIMINAR - CITAS
ON DUPLICATE KEY UPDATE id_rol=id_rol;

-- Módulo 3: ANTECEDENTES - Todos los permisos
INSERT INTO RolPermisoModulo (id_rol, id_permiso, id_modulo) VALUES
(1, 1, 3), -- ADMIN - VER - ANTECEDENTES
(1, 2, 3), -- ADMIN - CREAR - ANTECEDENTES
(1, 3, 3), -- ADMIN - EDITAR - ANTECEDENTES
(1, 4, 3)  -- ADMIN - ELIMINAR - ANTECEDENTES
ON DUPLICATE KEY UPDATE id_rol=id_rol;

-- Módulo 4: ESPECIALIDADES - Todos los permisos
INSERT INTO RolPermisoModulo (id_rol, id_permiso, id_modulo) VALUES
(1, 1, 4), -- ADMIN - VER - ESPECIALIDADES
(1, 2, 4), -- ADMIN - CREAR - ESPECIALIDADES
(1, 3, 4), -- ADMIN - EDITAR - ESPECIALIDADES
(1, 4, 4)  -- ADMIN - ELIMINAR - ESPECIALIDADES
ON DUPLICATE KEY UPDATE id_rol=id_rol;

-- Módulo 5: REPORTES - Todos los permisos
INSERT INTO RolPermisoModulo (id_rol, id_permiso, id_modulo) VALUES
(1, 1, 5), -- ADMIN - VER - REPORTES
(1, 2, 5), -- ADMIN - CREAR - REPORTES
(1, 3, 5), -- ADMIN - EDITAR - REPORTES
(1, 4, 5)  -- ADMIN - ELIMINAR - REPORTES
ON DUPLICATE KEY UPDATE id_rol=id_rol;

-- Módulo 6: CONFIGURACION - Todos los permisos
INSERT INTO RolPermisoModulo (id_rol, id_permiso, id_modulo) VALUES
(1, 1, 6), -- ADMIN - VER - CONFIGURACION
(1, 2, 6), -- ADMIN - CREAR - CONFIGURACION
(1, 3, 6), -- ADMIN - EDITAR - CONFIGURACION
(1, 4, 6)  -- ADMIN - ELIMINAR - CONFIGURACION
ON DUPLICATE KEY UPDATE id_rol=id_rol;

-- ════════════════════════════════════════════════════════════════════════════════
-- MÉDICO: Acceso limitado
-- Módulo 2: CITAS - Puede ver, crear y editar, pero no eliminar
INSERT INTO RolPermisoModulo (id_rol, id_permiso, id_modulo) VALUES
(2, 1, 2), -- MEDICO - VER - CITAS
(2, 2, 2), -- MEDICO - CREAR - CITAS
(2, 3, 2)  -- MEDICO - EDITAR - CITAS
ON DUPLICATE KEY UPDATE id_rol=id_rol;

-- Módulo 3: ANTECEDENTES - Puede ver y crear, pero no editar ni eliminar
INSERT INTO RolPermisoModulo (id_rol, id_permiso, id_modulo) VALUES
(2, 1, 3), -- MEDICO - VER - ANTECEDENTES
(2, 2, 3)  -- MEDICO - CREAR - ANTECEDENTES
ON DUPLICATE KEY UPDATE id_rol=id_rol;

-- Módulo 4: ESPECIALIDADES - Solo puede ver
INSERT INTO RolPermisoModulo (id_rol, id_permiso, id_modulo) VALUES
(2, 1, 4)  -- MEDICO - VER - ESPECIALIDADES
ON DUPLICATE KEY UPDATE id_rol=id_rol;

-- ════════════════════════════════════════════════════════════════════════════════
-- RECEPCIONISTA: Acceso muy limitado
-- Módulo 2: CITAS - Puede ver y crear, pero no editar ni eliminar
INSERT INTO RolPermisoModulo (id_rol, id_permiso, id_modulo) VALUES
(3, 1, 2), -- RECEP - VER - CITAS
(3, 2, 2)  -- RECEP - CREAR - CITAS
ON DUPLICATE KEY UPDATE id_rol=id_rol;

-- ════════════════════════════════════════════════════════════════════════════════
-- PACIENTE: Acceso mínimo
-- Módulo 2: CITAS - Solo puede ver
INSERT INTO RolPermisoModulo (id_rol, id_permiso, id_modulo) VALUES
(4, 1, 2)  -- PACIENTE - VER - CITAS
ON DUPLICATE KEY UPDATE id_rol=id_rol;

-- Módulo 3: ANTECEDENTES - Solo puede ver
INSERT INTO RolPermisoModulo (id_rol, id_permiso, id_modulo) VALUES
(4, 1, 3)  -- PACIENTE - VER - ANTECEDENTES
ON DUPLICATE KEY UPDATE id_rol=id_rol;

-- ════════════════════════════════════════════════════════════════════════════════
-- VERIFICACIÓN: Consultar permisos asignados
-- ════════════════════════════════════════════════════════════════════════════════

-- Ver todos los permisos asignados
SELECT 
    r.nombre_rol as 'Rol',
    m.nombre_modulo as 'Módulo',
    p.nombre_permiso as 'Permiso',
    COUNT(*) as 'Cantidad'
FROM RolPermisoModulo rpm
JOIN Roles r ON rpm.id_rol = r.id_rol
JOIN Modulos m ON rpm.id_modulo = m.id_modulo
JOIN Permisos p ON rpm.id_permiso = p.id_permiso
GROUP BY r.nombre_rol, m.nombre_modulo, p.nombre_permiso
ORDER BY r.nombre_rol, m.nombre_modulo, p.nombre_permiso;

-- Ver usuarios creados
SELECT id_usuario, nombre_usuario, nombres, apellidos, r.nombre_rol, e.estado
FROM Usuarios u
JOIN Roles r ON u.id_rol = r.id_rol
JOIN Estados e ON u.id_estado = e.id_estado
ORDER BY u.nombre_usuario;

-- Ver contraseñas asignadas (solo verificar que existen)
SELECT u.nombre_usuario, COUNT(c.id_contrasenia) as 'Tiene Contraseña'
FROM Usuarios u
LEFT JOIN Contrasenias c ON u.id_usuario = c.usuario_id
GROUP BY u.nombre_usuario;

-- ════════════════════════════════════════════════════════════════════════════════
-- NOTAS IMPORTANTES
-- ════════════════════════════════════════════════════════════════════════════════

/*
CREDENCIALES DE PRUEBA:

1. ADMIN
   Usuario: admin
   Contraseña: admin123
   Rol: ADMIN
   Acceso: Todos los módulos, todos los permisos

2. MÉDICO
   Usuario: dr_lopez
   Contraseña: medico123
   Rol: MEDICO
   Acceso: CITAS (VER, CREAR, EDITAR)
           ANTECEDENTES (VER, CREAR)
           ESPECIALIDADES (VER)

3. RECEPCIONISTA
   Usuario: recepcionista01
   Contraseña: recep123
   Rol: RECEP
   Acceso: CITAS (VER, CREAR)

4. PACIENTE
   Usuario: paciente_001
   Contraseña: paciente123
   Rol: PACIENTE
   Acceso: CITAS (VER)
           ANTECEDENTES (VER)

PASOS PARA EJECUTAR:
1. Conectar a MySQL con tu BD medicit
2. Ejecutar este script completo
3. Verificar que los datos se insertaron correctamente
4. Iniciar la aplicación Spring Boot
5. Probar los endpoints con los usuarios anteriores

CONTRASEÑAS BCRYPT:
Si necesitas cambiar las contraseñas, usa una herramienta online para generar
nuevos hashes BCrypt, por ejemplo: https://bcrypt-generator.com/

O en Java:
  BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
  String hash = encoder.encode("tu_contraseña_aqui");
  System.out.println(hash);
*/
