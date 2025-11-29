package sv.medicit.app.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sv.medicit.app.Entidades.Usuarios;

/**
 * DTO para responder al cliente después de un login exitoso.
 * Contiene datos del usuario y información de sesión.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    
    private Integer idUsuario;
    private String nombreUsuario;
    private String nombres;
    private String apellidos;
    private String nombreRol;
    private String estado;
    private String mensaje;
    private String sessionToken;
    
    /**
     * Constructor que mapea desde una entidad Usuarios.
     * Oculta detalles internos y solo expone lo necesario.
     */
    public LoginResponseDTO(Usuarios usuario, String sessionToken) {
        this.idUsuario = usuario.getIdUsuario();
        this.nombreUsuario = usuario.getNombreUsuario();
        this.nombres = usuario.getNombres();
        this.apellidos = usuario.getApellidos();
        this.nombreRol = usuario.getRol().getNombreRol();
        this.estado = usuario.getEstado().getEstado();
        this.sessionToken = sessionToken;
        this.mensaje = "Login exitoso";
    }
}
