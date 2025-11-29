# Ejemplos CRUD de Usuarios - MediCit API

Base URL: `http://localhost:8080/api/usuarios`

---

## 1. CREATE (Crear Usuario)
 
### 1.2 Crear Usuario Completo (Con Contraseña, Teléfono, Correo y Preguntas)

**Endpoint:** `POST /api/usuarios/completo`

**Request:**
```json
{
  "nombreUsuario": "maria456",
  "nombres": "María",
  "apellidos": "López Martínez",
  "dui": "98765432-1",
  "fechaNacimiento": "1985-08-20",
  "idRol": 2,
  "idEstado": 1,
  "contrasenia": "MiContraseña123!",
  "telefono": "+503 7890-1234",
  "correo": "maria@medicit.com",
  "preguntasRespuestas": [
    {
      "pregunta": "¿Cuál es el nombre de tu mascota?",
      "respuesta": "Fluffy"
    },
    {
      "pregunta": "¿Cuál es tu ciudad natal?",
      "respuesta": "San Salvador"
    }
  ],
  "idEspecialidades": [1, 2]
}
```

**Response (201 Created):**
```json
{
  "idUsuario": 2,
  "nombreUsuario": "maria456",
  "nombres": "María",
  "apellidos": "López Martínez",
  "dui": "98765432-1",
  "fechaNacimiento": "1985-08-20T00:00:00.000+00:00",
  "rol": {
    "idRol": 2,
    "nombreRol": "Médico"
  },
  "estado": {
    "idEstado": 1,
    "nombreEstado": "Activo"
  },
  "especialidades": [
    {
      "idEspecialidad": 1,
      "nombreEspecialidad": "Cardiología"
    },
    {
      "idEspecialidad": 2,
      "nombreEspecialidad": "Neurocirugía"
    }
  ]
}
```

**Nota:** La contraseña se encripta con BCrypt antes de guardarse.

---

### Errores Posibles en CREATE

**Error 400 - Campo requerido faltante:**
```json
{
  "tipo": "Validación fallida",
  "mensaje": "El nombre de usuario es requerido"
}
```

**Error 400 - nombreUsuario duplicado:**
```json
{
  "tipo": "Validación fallida",
  "mensaje": "El nombre de usuario 'juan123' ya existe"
}
```

**Error 400 - DUI duplicado:**
```json
{
  "tipo": "Validación fallida",
  "mensaje": "El DUI '12345678-9' ya existe"
}
```

**Error 400 - Correo duplicado:**
```json
{
  "tipo": "Validación fallida",
  "mensaje": "El correo 'maria@medicit.com' ya existe"
}
```

---

## 2. READ (Leer/Obtener Usuarios)

### 2.1 Obtener Todos los Usuarios

**Endpoint:** `GET /api/usuarios`

**Response (200 OK):**
```json
[
  {
    "idUsuario": 1,
    "nombreUsuario": "juan123",
    "nombres": "Juan",
    "apellidos": "Pérez García",
    "dui": "12345678-9",
    "fechaNacimiento": "1990-05-15T00:00:00.000+00:00",
    "rol": {
      "idRol": 1,
      "nombreRol": "Paciente"
    },
    "estado": {
      "idEstado": 1,
      "nombreEstado": "Activo"
    },
    "especialidades": null
  },
  {
    "idUsuario": 2,
    "nombreUsuario": "maria456",
    "nombres": "María",
    "apellidos": "López Martínez",
    "dui": "98765432-1",
    "fechaNacimiento": "1985-08-20T00:00:00.000+00:00",
    "rol": {
      "idRol": 2,
      "nombreRol": "Médico"
    },
    "estado": {
      "idEstado": 1,
      "nombreEstado": "Activo"
    },
    "especialidades": [
      {
        "idEspecialidad": 1,
        "nombreEspecialidad": "Cardiología"
      },
      {
        "idEspecialidad": 2,
        "nombreEspecialidad": "Neurocirugía"
      }
    ]
  }
]
```

---

### 2.2 Obtener Usuario por ID

**Endpoint:** `GET /api/usuarios/{id}`

**Request:** `GET /api/usuarios/1`

**Response (200 OK):**
```json
{
  "idUsuario": 1,
  "nombreUsuario": "juan123",
  "nombres": "Juan",
  "apellidos": "Pérez García",
  "dui": "12345678-9",
  "fechaNacimiento": "1990-05-15T00:00:00.000+00:00",
  "rol": {
    "idRol": 1,
    "nombreRol": "Paciente"
  },
  "estado": {
    "idEstado": 1,
    "nombreEstado": "Activo"
  },
  "especialidades": null
}
```

**Error 404 - Usuario no encontrado:**
```
(Sin body, solo Status 404)
```

---

## 3. UPDATE (Actualizar Usuario)

### 3.1 Actualizar Usuario

**Endpoint:** `PUT /api/usuarios/{id}`

**Request:** `PUT /api/usuarios/1`
```json
{
  "nombreUsuario": "juan123",
  "nombres": "Juan Carlos",
  "apellidos": "Pérez García Ruiz",
  "dui": "12345678-9",
  "fechaNacimiento": "1990-05-15",
  "idRol": 1,
  "idEstado": 1
}
```

**Response (200 OK):**
```json
{
  "idUsuario": 1,
  "nombreUsuario": "juan123",
  "nombres": "Juan Carlos",
  "apellidos": "Pérez García Ruiz",
  "dui": "12345678-9",
  "fechaNacimiento": "1990-05-15T00:00:00.000+00:00",
  "rol": {
    "idRol": 1,
    "nombreRol": "Paciente"
  },
  "estado": {
    "idEstado": 1,
    "nombreEstado": "Activo"
  },
  "especialidades": null
}
```

---

### 3.2 Asignar Especialidades a un Médico

**Endpoint:** `POST /api/usuarios/{id}/especialidades`

**Request:** `POST /api/usuarios/2/especialidades`
```json
[1, 2, 3]
```

**Response (200 OK):**
```json
{
  "idUsuario": 2,
  "nombreUsuario": "maria456",
  "nombres": "María",
  "apellidos": "López Martínez",
  "dui": "98765432-1",
  "fechaNacimiento": "1985-08-20T00:00:00.000+00:00",
  "rol": {
    "idRol": 2,
    "nombreRol": "Médico"
  },
  "estado": {
    "idEstado": 1,
    "nombreEstado": "Activo"
  },
  "especialidades": [
    {
      "idEspecialidad": 1,
      "nombreEspecialidad": "Cardiología"
    },
    {
      "idEspecialidad": 2,
      "nombreEspecialidad": "Neurocirugía"
    },
    {
      "idEspecialidad": 3,
      "nombreEspecialidad": "Oftalmología"
    }
  ]
}
```

---

### 3.3 Asignar Una Sola Especialidad

**Endpoint:** `POST /api/usuarios/{id}/especialidades/{idEspecialidad}`

**Request:** `POST /api/usuarios/2/especialidades/4`

**Response (200 OK):**
```json
{
  "idUsuario": 2,
  "nombreUsuario": "maria456",
  "nombres": "María",
  "apellidos": "López Martínez",
  "dui": "98765432-1",
  "fechaNacimiento": "1985-08-20T00:00:00.000+00:00",
  "rol": {
    "idRol": 2,
    "nombreRol": "Médico"
  },
  "estado": {
    "idEstado": 1,
    "nombreEstado": "Activo"
  },
  "especialidades": [
    {
      "idEspecialidad": 1,
      "nombreEspecialidad": "Cardiología"
    },
    {
      "idEspecialidad": 2,
      "nombreEspecialidad": "Neurocirugía"
    },
    {
      "idEspecialidad": 3,
      "nombreEspecialidad": "Oftalmología"
    },
    {
      "idEspecialidad": 4,
      "nombreEspecialidad": "Dermatología"
    }
  ]
}
```

---

## 4. DELETE (Eliminar Usuario)

### 4.1 Eliminar Usuario por ID

**Endpoint:** `DELETE /api/usuarios/{id}`

**Request:** `DELETE /api/usuarios/1`

**Response (200 OK):**
```json
{
  "mensaje": "Usuario eliminado exitosamente",
  "dato": 1
}
```

**Error 404 - Usuario no encontrado:**
```json
{
  "tipo": "Error",
  "mensaje": "Usuario no encontrado con ID: 1"
}
```

---

### 4.2 Remover Una Especialidad de un Usuario

**Endpoint:** `DELETE /api/usuarios/{id}/especialidades/{idEspecialidad}`

**Request:** `DELETE /api/usuarios/2/especialidades/3`

**Response (200 OK):**
```json
{
  "idUsuario": 2,
  "nombreUsuario": "maria456",
  "nombres": "María",
  "apellidos": "López Martínez",
  "dui": "98765432-1",
  "fechaNacimiento": "1985-08-20T00:00:00.000+00:00",
  "rol": {
    "idRol": 2,
    "nombreRol": "Médico"
  },
  "estado": {
    "idEstado": 1,
    "nombreEstado": "Activo"
  },
  "especialidades": [
    {
      "idEspecialidad": 1,
      "nombreEspecialidad": "Cardiología"
    },
    {
      "idEspecialidad": 2,
      "nombreEspecialidad": "Neurocirugía"
    },
    {
      "idEspecialidad": 4,
      "nombreEspecialidad": "Dermatología"
    }
  ]
}
```

---

## Resumen de Endpoints

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| **POST** | `/api/usuarios` | Crear usuario simple |
| **POST** | `/api/usuarios/completo` | Crear usuario completo con contraseña y más |
| **GET** | `/api/usuarios` | Obtener todos los usuarios |
| **GET** | `/api/usuarios/{id}` | Obtener usuario por ID |
| **PUT** | `/api/usuarios/{id}` | Actualizar usuario |
| **POST** | `/api/usuarios/{id}/especialidades` | Asignar varias especialidades |
| **POST** | `/api/usuarios/{id}/especialidades/{idEspecialidad}` | Asignar una especialidad |
| **DELETE** | `/api/usuarios/{id}` | Eliminar usuario |
| **DELETE** | `/api/usuarios/{id}/especialidades/{idEspecialidad}` | Remover especialidad |

---

## Notas Importantes

1. **Encriptación de Contraseñas**: Las contraseñas se encriptan con BCrypt y no son visibles en el JSON de respuesta.

2. **Validaciones Implementadas**:
   - `nombreUsuario` debe ser único
   - `dui` debe ser único (si está presente)
   - `correo` debe ser único (en creación completa)
   - Todos los campos requeridos deben estar presentes

3. **Roles Válidos**: Asegúrate de que el `idRol` exista en la base de datos

4. **Estados Válidos**: Asegúrate de que el `idEstado` exista en la base de datos

5. **Especialidades**: Solo los usuarios con rol "Médico" pueden tener especialidades asignadas

6. **Fechas**: Se esperan en formato ISO 8601 (YYYY-MM-DD)
