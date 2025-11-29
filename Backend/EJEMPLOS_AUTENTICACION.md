╔════════════════════════════════════════════════════════════════════════════════╗
║                      📝 EJEMPLOS DE USO - AUTENTICACIÓN                        ║
║                                                                                ║
║                    Casos de Prueba y Peticiones REST                          ║
╚════════════════════════════════════════════════════════════════════════════════╝

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ 1. LOGIN - Obtener sesión                                                      ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

CURL:
─────
curl -X POST http://localhost:8080/api/autenticacion/login \
  -H "Content-Type: application/json" \
  -d '{
    "nombreUsuario": "admin",
    "contrasenia": "admin123"
  }'

RESPUESTA (200 OK):
──────────────────
{
  "idUsuario": 1,
  "nombreUsuario": "admin",
  "nombres": "Administrador",
  "apellidos": "Sistema",
  "nombreRol": "ADMIN",
  "estado": "ACTIVO",
  "mensaje": "Login exitoso",
  "sessionToken": "SESSION_1_1733052633000"
}

ERRORES POSIBLES:
──────────────────
❌ 401 Unauthorized → Usuario no encontrado
   Respuesta: {"error": "Usuario no encontrado"}

❌ 401 Unauthorized → Contraseña incorrecta
   Respuesta: {"error": "Contraseña incorrecta"}

❌ 401 Unauthorized → Usuario inactivo
   Respuesta: {"error": "Usuario inactivo. Contacte al administrador"}

❌ 400 Bad Request → Entrada vacía
   Respuesta: {"error": "El nombre de usuario es requerido"}

────────────────────────────────────────────────────────────────────────────────────

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ 2. VERIFICAR SESIÓN - Comprobar si hay usuario logueado                        ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

CURL:
─────
curl -X GET http://localhost:8080/api/autenticacion/verificar-sesion

RESPUESTA SIN SESIÓN (200 OK):
──────────────────────────────
{
  "autenticado": false
}

RESPUESTA CON SESIÓN (200 OK):
───────────────────────────────
{
  "autenticado": true,
  "usuario": "admin"
}

NOTA: El navegador o cliente debe almacenar las cookies de sesión y enviarlas
      en las siguientes peticiones automáticamente.

────────────────────────────────────────────────────────────────────────────────────

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ 3. OBTENER USUARIO ACTUAL - Ver datos del usuario logueado                     ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

CURL:
─────
curl -X GET http://localhost:8080/api/autenticacion/usuario-actual

RESPUESTA CON SESIÓN (200 OK):
───────────────────────────────
{
  "idUsuario": 1,
  "nombreUsuario": "admin",
  "nombres": "Administrador",
  "apellidos": "Sistema",
  "dui": "12345678",
  "fechaNacimiento": "1990-01-01",
  "rol": {
    "idRol": 1,
    "nombreRol": "ADMIN",
    "descripcion": "Administrador del sistema"
  },
  "estado": {
    "idEstado": 1,
    "estado": "ACTIVO",
    "descripcion": "Usuario activo"
  }
}

RESPUESTA SIN SESIÓN (401 Unauthorized):
─────────────────────────────────────────
{
  "error": "No hay usuario autenticado"
}

────────────────────────────────────────────────────────────────────────────────────

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ 4. MIS MÓDULOS - Obtener módulos accesibles del usuario                        ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

CURL:
─────
curl -X GET http://localhost:8080/api/permisos/mis-modulos

RESPUESTA (200 OK):
───────────────────
{
  "usuario": "admin",
  "modulos": [
    {
      "idModulo": 1,
      "nombreModulo": "USUARIOS",
      "descripcion": "Gestión de usuarios del sistema"
    },
    {
      "idModulo": 2,
      "nombreModulo": "CITAS",
      "descripcion": "Gestión de citas médicas"
    },
    {
      "idModulo": 3,
      "nombreModulo": "ANTECEDENTES",
      "descripcion": "Antecedentes médicos de pacientes"
    }
  ]
}

NOTA: Para un usuario MEDICO podría retornar solo CITAS y ANTECEDENTES.
      Para un usuario RECEPCIONISTA solo CITAS.

────────────────────────────────────────────────────────────────────────────────────

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ 5. VERIFICAR ACCESO A MÓDULO - ¿Puedo acceder al módulo X?                    ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

CURL:
─────
curl -X GET http://localhost:8080/api/permisos/usuario/modulo/1/acceso

RESPUESTA CON ACCESO (200 OK):
──────────────────────────────
{
  "idModulo": 1,
  "tieneAcceso": true
}

RESPUESTA SIN ACCESO (200 OK):
──────────────────────────────
{
  "idModulo": 1,
  "tieneAcceso": false
}

────────────────────────────────────────────────────────────────────────────────────

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ 6. VERIFICAR PERMISO ESPECÍFICO - ¿Puedo CREAR en el módulo X?               ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

CURL - Verificar si puede VER:
──────────────────────────────
curl -X GET http://localhost:8080/api/permisos/usuario/modulo/1/permiso/VER

CURL - Verificar si puede CREAR:
─────────────────────────────────
curl -X GET http://localhost:8080/api/permisos/usuario/modulo/1/permiso/CREAR

CURL - Verificar si puede EDITAR:
──────────────────────────────────
curl -X GET http://localhost:8080/api/permisos/usuario/modulo/1/permiso/EDITAR

CURL - Verificar si puede ELIMINAR:
────────────────────────────────────
curl -X GET http://localhost:8080/api/permisos/usuario/modulo/1/permiso/ELIMINAR

RESPUESTA CON PERMISO (200 OK):
────────────────────────────────
{
  "idModulo": 1,
  "permiso": "CREAR",
  "tienePermiso": true
}

RESPUESTA SIN PERMISO (200 OK):
────────────────────────────────
{
  "idModulo": 1,
  "permiso": "CREAR",
  "tienePermiso": false
}

────────────────────────────────────────────────────────────────────────────────────

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ 7. LOGOUT - Cerrar sesión                                                      ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

CURL:
─────
curl -X POST http://localhost:8080/api/autenticacion/logout

RESPUESTA (200 OK):
───────────────────
{
  "mensaje": "Logout exitoso"
}

RESULTADO:
──────────
• La sesión se invalida
• Las cookies se limpian
• El usuario necesita hacer login de nuevo para acceder a recursos protegidos

────────────────────────────────────────────────────────────────────────────────────

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ 📊 FLUJO COMPLETO DE EJEMPLO                                                   ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

Paso 1: Login
────────────
curl -X POST http://localhost:8080/api/autenticacion/login \
  -H "Content-Type: application/json" \
  -d '{"nombreUsuario":"admin","contrasenia":"admin123"}'

✓ Respuesta: 200 OK + Set-Cookie: JSESSIONID=...

Paso 2: Verificar sesión
────────────────────────
curl -X GET http://localhost:8080/api/autenticacion/verificar-sesion

✓ Respuesta: {"autenticado": true, "usuario": "admin"}

Paso 3: Obtener módulos accesibles
──────────────────────────────────
curl -X GET http://localhost:8080/api/permisos/mis-modulos

✓ Respuesta: {"usuario":"admin", "modulos":[...]}

Paso 4: Verificar acceso al módulo CITAS
─────────────────────────────────────────
curl -X GET http://localhost:8080/api/permisos/usuario/modulo/2/acceso

✓ Respuesta: {"idModulo":2, "tieneAcceso":true}

Paso 5: Verificar permiso CREAR en CITAS
────────────────────────────────────────
curl -X GET http://localhost:8080/api/permisos/usuario/modulo/2/permiso/CREAR

✓ Respuesta: {"idModulo":2, "permiso":"CREAR", "tienePermiso":true}

Paso 6: Hacer operación CREAR (en controlador integrado)
────────────────────────────────────────────────────────
POST /api/citas
{
  "descripcion": "Nueva cita",
  ...
}

✓ El controlador:
  • Obtiene usuario de sesión (ya validado por interceptor)
  • Verifica permiso CREAR
  • Si tiene permiso → crea la cita
  • Si no tiene permiso → retorna 403 Forbidden

Paso 7: Logout
──────────────
curl -X POST http://localhost:8080/api/autenticacion/logout

✓ Respuesta: {"mensaje":"Logout exitoso"}

Paso 8: Intentar acceder a recurso protegido
──────────────────────────────────────────────
curl -X GET http://localhost:8080/api/permisos/mis-modulos

❌ Respuesta: 401 Unauthorized (no hay sesión)

────────────────────────────────────────────────────────────────────────────────────

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ 🧪 PRUEBAS CON DIFERENTES USUARIOS                                             ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

CASO 1: Admin (acceso total)
─────────────────────────────
POST /login → admin / admin123
GET /modulos → Retorna TODOS los módulos
GET /modulo/1/permiso/CREAR → true
GET /modulo/2/permiso/CREAR → true
GET /modulo/3/permiso/CREAR → true

CASO 2: Médico (acceso limitado)
──────────────────────────────────
POST /login → dr_lopez / medico123
GET /modulos → Retorna solo CITAS, ANTECEDENTES
GET /modulo/1/acceso → false (USUARIOS)
GET /modulo/2/acceso → true (CITAS)
GET /modulo/3/acceso → true (ANTECEDENTES)
GET /modulo/2/permiso/CREAR → true (CITAS)
GET /modulo/2/permiso/ELIMINAR → false

CASO 3: Recepcionista (acceso mínimo)
──────────────────────────────────────
POST /login → recepcionista01 / recep123
GET /modulos → Retorna solo CITAS
GET /modulo/1/acceso → false (USUARIOS)
GET /modulo/2/acceso → true (CITAS)
GET /modulo/3/acceso → false (ANTECEDENTES)
GET /modulo/2/permiso/VER → true
GET /modulo/2/permiso/CREAR → true
GET /modulo/2/permiso/EDITAR → false

────────────────────────────────────────────────────────────────────────────────────

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ ⚠️  ERRORES COMUNES Y SOLUCIONES                                               ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

ERROR: 401 Unauthorized en endpoint protegido
──────────────────────────────────────────────
Causa: No hay sesión válida
Solución: 
  1. Hacer login primero
  2. Guardar las cookies de sesión
  3. Enviarlas en las siguientes peticiones

ERROR: 403 Forbidden - Usuario logueado pero sin permiso
─────────────────────────────────────────────────────────
Causa: El usuario no tiene el permiso requerido
Solución:
  1. Verificar en BD que RolPermisoModulo tiene la asignación correcta
  2. Usar endpoint /api/permisos/usuario/modulo/{id}/permiso/{permiso}
     para verificar qué permisos tiene

ERROR: "Usuario inactivo"
──────────────────────────
Causa: El usuario tiene estado INACTIVO en BD
Solución:
  UPDATE Usuarios SET id_estado = 1 WHERE nombre_usuario = 'admin';

ERROR: "Usuario sin contraseña asignada"
─────────────────────────────────────────
Causa: El usuario existe pero no tiene entrada en tabla Contrasenias
Solución:
  INSERT INTO Contrasenias (usuario_id, contrasenia) 
  VALUES (1, 'contraseña_encriptada_bcrypt');

═══════════════════════════════════════════════════════════════════════════════════

              ✅ Todos los ejemplos están listos para probar

═══════════════════════════════════════════════════════════════════════════════════
