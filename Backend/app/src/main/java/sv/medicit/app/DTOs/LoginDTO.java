package sv.medicit.app.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para recibir credenciales de login.
 * Contiene el nombre de usuario y contraseña enviados por el cliente.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    
    private String nombreUsuario;
    private String contrasenia;
}
