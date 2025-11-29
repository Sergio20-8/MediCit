package sv.medicit.app.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sv.medicit.app.DTOs.LoginDTO;
import sv.medicit.app.DTOs.LoginResponseDTO;
import sv.medicit.app.Entidades.Contrasenias;
import sv.medicit.app.Entidades.Usuarios;
import sv.medicit.app.Repositorios.ContraseniasRepository;
import sv.medicit.app.Repositorios.UsuariosRepository;

/**
 * Servicio de autenticación.
 * Maneja el login de usuarios, validación de credenciales y sesiones.
 */
@Service
public class AutenticacionService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private ContraseniasRepository contraseniasRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Realiza el login del usuario.
     * Valida que:
     * 1. El usuario exista
     * 2. La contraseña sea correcta
     * 3. El usuario esté activo
     * 
     * @param loginDTO Credenciales del usuario
     * @return LoginResponseDTO con datos del usuario si es exitoso
     * @throws RuntimeException si falla la autenticación
     */
    public LoginResponseDTO login(LoginDTO loginDTO) {
        // Buscar usuario por nombre
        Usuarios usuario = usuariosRepository.findByNombreUsuario(loginDTO.getNombreUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Obtener contraseña almacenada
        Contrasenias contraseniaGuardada = contraseniasRepository.findByUsuario(usuario);
        if (contraseniaGuardada == null) {
            throw new RuntimeException("Error: Usuario sin contraseña asignada");
        }

        // Verificar contraseña
        if (!passwordEncoder.matches(loginDTO.getContrasenia(), contraseniaGuardada.getContrasenia())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        // Verificar que el usuario esté activo
        if (usuario.getEstado().getEstado().equalsIgnoreCase("INACTIVO")) {
            throw new RuntimeException("Usuario inactivo. Contacte al administrador");
        }

        // Retornar DTO con datos del usuario
        String sessionToken = "SESSION_" + usuario.getIdUsuario() + "_" + System.currentTimeMillis();
        return new LoginResponseDTO(usuario, sessionToken);
    }

    /**
     * Obtiene el usuario actualmente logueado desde la sesión.
     * 
     * @param usuario El objeto usuario almacenado en sesión
     * @return El usuario si está autenticado
     * @throws RuntimeException si no hay usuario autenticado
     */
    public Usuarios obtenerUsuarioLogueado(Usuarios usuario) {
        if (usuario == null) {
            throw new RuntimeException("No hay usuario autenticado");
        }
        return usuario;
    }

    /**
     * Encripta una contraseña usando BCrypt.
     * 
     * @param contrasenia La contraseña en texto plano
     * @return La contraseña encriptada
     */
    public String encriptarContrasenia(String contrasenia) {
        return passwordEncoder.encode(contrasenia);
    }

    /**
     * Verifica si una contraseña en texto plano coincide con una encriptada.
     * 
     * @param contraseniaPlana La contraseña en texto plano
     * @param contraseniaEncriptada La contraseña encriptada
     * @return true si coinciden, false si no
     */
    public boolean verificarContrasenia(String contraseniaPlana, String contraseniaEncriptada) {
        return passwordEncoder.matches(contraseniaPlana, contraseniaEncriptada);
    }
}
