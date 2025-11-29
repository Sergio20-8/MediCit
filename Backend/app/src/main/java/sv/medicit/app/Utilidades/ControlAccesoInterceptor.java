package sv.medicit.app.Utilidades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import sv.medicit.app.Entidades.Usuarios;
import sv.medicit.app.Servicios.PermisosService;

/**
 * Interceptor para validar permisos en las peticiones HTTP.
 * Verifica que el usuario logueado tenga acceso al recurso solicitado.
 */
@Component
public class ControlAccesoInterceptor implements HandlerInterceptor {

    @Autowired
    private PermisosService permisosService;

    /**
     * Se ejecuta antes de que se procese la petición.
     * Valida que el usuario tenga una sesión activa.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 
            throws Exception {
        
        // Obtener la ruta de la petición
        String requestPath = request.getRequestURI();
        
        // Rutas públicas que no requieren autenticación
        if (esRutaPublica(requestPath)) {
            return true;
        }
        
        // Obtener la sesión
        HttpSession session = request.getSession(false);
        
        // Si no hay sesión, retornar 401
        if (session == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Sesión expirada o no existe");
            return false;
        }
        
        // Obtener el usuario de la sesión
        Usuarios usuario = (Usuarios) session.getAttribute("usuarioLogueado");
        
        // Si no hay usuario logueado, retornar 401
        if (usuario == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Usuario no autenticado");
            return false;
        }
        
        // Log de acceso (opcional)
        System.out.println("[ACCESO] Usuario: " + usuario.getNombreUsuario() + 
                         " | Método: " + request.getMethod() + 
                         " | Ruta: " + requestPath);
        
        // Permitir acceso
        return true;
    }
    
    /**
     * Verifica si una ruta es pública (no requiere autenticación).
     * 
     * @param requestPath Ruta de la petición
     * @return true si es pública, false si requiere autenticación
     */
    private boolean esRutaPublica(String requestPath) {
        // Rutas públicas de autenticación
        if (requestPath.contains("/api/autenticacion/login")) return true;
        if (requestPath.contains("/api/autenticacion/logout")) return true;
        if (requestPath.contains("/api/autenticacion/recuperar-contrasenia")) return true;
        if (requestPath.contains("/api/autenticacion/preguntas-seguridad")) return true;
        
        // Rutas de documentación y health checks (si existen)
        if (requestPath.contains("/swagger")) return true;
        if (requestPath.contains("/actuator/health")) return true;
        
        return false;
    }

}
