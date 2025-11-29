package sv.medicit.app.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sv.medicit.app.DTOs.LoginDTO;
import sv.medicit.app.DTOs.LoginResponseDTO;
import sv.medicit.app.Entidades.Usuarios;
import sv.medicit.app.Servicios.AutenticacionService;
import sv.medicit.app.Servicios.PermisosService;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador REST para autenticación y manejo de sesiones.
 * Proporciona endpoints para login, logout y verificación de sesión.
 */
@RestController
@RequestMapping("/api/autenticacion")
@CrossOrigin(origins = "*")
public class AutenticacionRestController {

    @Autowired
    private AutenticacionService autenticacionService;

    @Autowired
    private PermisosService permisosService;

    /**
     * Endpoint para login.
     * Valida credenciales y almacena el usuario en sesión.
     * 
     * @param loginDTO Contiene nombreUsuario y contrasenia
     * @param session La sesión HTTP
     * @return LoginResponseDTO con datos del usuario
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO, HttpSession session) {
        try {
            // Validar entrada
            if (loginDTO.getNombreUsuario() == null || loginDTO.getNombreUsuario().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(
                    Map.of("error", "El nombre de usuario es requerido")
                );
            }

            if (loginDTO.getContrasenia() == null || loginDTO.getContrasenia().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(
                    Map.of("error", "La contraseña es requerida")
                );
            }

            // Realizar login
            LoginResponseDTO response = autenticacionService.login(loginDTO);

            // Obtener usuario completo de la BD para almacenar en sesión
            Usuarios usuario = new Usuarios();
            usuario.setIdUsuario(response.getIdUsuario());
            usuario.setNombreUsuario(response.getNombreUsuario());
            usuario.setNombres(response.getNombres());
            usuario.setApellidos(response.getApellidos());

            // Almacenar usuario en sesión
            session.setAttribute("usuarioLogueado", usuario);
            session.setMaxInactiveInterval(1800); // 30 minutos

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                Map.of("error", e.getMessage())
            );
        }
    }

    /**
     * Endpoint para logout.
     * Invalida la sesión del usuario.
     * 
     * @param session La sesión HTTP
     * @return Mensaje de confirmación
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(Map.of("mensaje", "Logout exitoso"));
    }

    /**
     * Endpoint para verificar si hay sesión activa.
     * 
     * @param session La sesión HTTP
     * @return true si hay sesión, false si no
     */
    @GetMapping("/verificar-sesion")
    public ResponseEntity<?> verificarSesion(HttpSession session) {
        Usuarios usuario = (Usuarios) session.getAttribute("usuarioLogueado");
        
        if (usuario == null) {
            return ResponseEntity.ok(Map.of("autenticado", false));
        }

        return ResponseEntity.ok(Map.of("autenticado", true, "usuario", usuario.getNombreUsuario()));
    }

    /**
     * Endpoint para obtener datos del usuario actual.
     * 
     * @param session La sesión HTTP
     * @return Datos del usuario logueado
     */
    @GetMapping("/usuario-actual")
    public ResponseEntity<?> usuarioActual(HttpSession session) {
        Usuarios usuario = (Usuarios) session.getAttribute("usuarioLogueado");
        
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                Map.of("error", "No hay usuario autenticado")
            );
        }

        return ResponseEntity.ok(usuario);
    }

    /**
     * Endpoint para obtener los módulos accesibles del usuario logueado.
     * 
     * @param session La sesión HTTP
     * @return Lista de módulos accesibles
     */
    @GetMapping("/mis-modulos")
    public ResponseEntity<?> misModulos(HttpSession session) {
        Usuarios usuario = (Usuarios) session.getAttribute("usuarioLogueado");
        
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                Map.of("error", "No hay usuario autenticado")
            );
        }

        try {
            // Para este endpoint, necesitamos un Usuarios con rol cargado
            // Por ahora solo retornamos un mensaje
            return ResponseEntity.ok(Map.of("modulos", "Implementar carga de módulos"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                Map.of("error", e.getMessage())
            );
        }
    }
}
