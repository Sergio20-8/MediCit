package sv.medicit.app.Controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sv.medicit.app.Entidades.Usuarios;
import sv.medicit.app.Servicios.UsuariosService;
import sv.medicit.app.DTOs.UsuarioCreacionDTO;

/**
 * RestController para la gestión de Usuarios.
 * Proporciona endpoints REST para operaciones CRUD.
 * Base URL: /api/usuarios
 */
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuariosRestController {

    @Autowired
    private UsuariosService usuariosService;

    /**
     * GET /api/usuarios
     * Obtener todos los usuarios.
     */
    @GetMapping
    public ResponseEntity<List<Usuarios>> obtenerTodos() {
        List<Usuarios> usuarios = usuariosService.obtenerTodos();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    /**
     * GET /api/usuarios/{id}
     * Obtener un usuario por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> obtenerPorId(@PathVariable Integer id) {
        Optional<Usuarios> usuario = usuariosService.obtenerPorId(id);
        if (usuario.isPresent()) {
            return new ResponseEntity<>(usuario.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * POST /api/usuarios
     * Crear un nuevo usuario.
     */
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Usuarios usuario) {
        try {
            Usuarios usuarioCreado = usuariosService.crear(usuario);
            return new ResponseEntity<>(usuarioCreado, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(
                new ErrorResponse("Validación fallida", e.getMessage()),
                HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                new ErrorResponse("Error", e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    /**
     * POST /api/usuarios/completo
     * Crear un nuevo usuario con contraseña, teléfono, correo y preguntas/respuestas.
     */
    @PostMapping("/completo")
    public ResponseEntity<?> crearCompleto(@RequestBody UsuarioCreacionDTO usuarioDTO) {
        try {
            Usuarios usuarioCreado = usuariosService.crearUsuarioCompleto(usuarioDTO);
            return new ResponseEntity<>(usuarioCreado, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(
                new ErrorResponse("Validación fallida", e.getMessage()),
                HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                new ErrorResponse("Error", e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    /**
     * PUT /api/usuarios/{id}
     * Actualizar un usuario existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Usuarios usuarioActualizado) {
        try {
            Usuarios usuarioUpdated = usuariosService.actualizar(id, usuarioActualizado);
            return new ResponseEntity<>(usuarioUpdated, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                new ErrorResponse("Error", e.getMessage()),
                HttpStatus.NOT_FOUND
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                new ErrorResponse("Error", e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    /**
     * DELETE /api/usuarios/{id}
     * Eliminar un usuario por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            usuariosService.eliminar(id);
            return new ResponseEntity<>(
                new SuccessResponse("Usuario eliminado exitosamente", id),
                HttpStatus.OK
            );
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                new ErrorResponse("Error", e.getMessage()),
                HttpStatus.NOT_FOUND
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                new ErrorResponse("Error", e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    /**
     * Clase interna para respuestas de error.
     */
    public static class ErrorResponse {
        public String tipo;
        public String mensaje;

        public ErrorResponse(String tipo, String mensaje) {
            this.tipo = tipo;
            this.mensaje = mensaje;
        }

        public String getTipo() {
            return tipo;
        }

        public String getMensaje() {
            return mensaje;
        }
    }

    /**
     * Clase interna para respuestas exitosas.
     */
    public static class SuccessResponse {
        public String mensaje;
        public Object dato;

        public SuccessResponse(String mensaje, Object dato) {
            this.mensaje = mensaje;
            this.dato = dato;
        }

        public String getMensaje() {
            return mensaje;
        }

        public Object getDato() {
            return dato;
        }
    }
}
