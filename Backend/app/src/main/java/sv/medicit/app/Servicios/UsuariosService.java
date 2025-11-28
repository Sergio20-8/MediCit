package sv.medicit.app.Servicios;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sv.medicit.app.Entidades.Usuarios;
import sv.medicit.app.Entidades.Contrasenias;
import sv.medicit.app.Entidades.Telefonos;
import sv.medicit.app.Entidades.Correos;
import sv.medicit.app.Entidades.Preguntas;
import sv.medicit.app.Entidades.Respuestas;
import sv.medicit.app.Repositorios.UsuariosRepository;
import sv.medicit.app.Repositorios.RolesRepository;
import sv.medicit.app.Repositorios.EstadosRepository;
import sv.medicit.app.DTOs.UsuarioCreacionDTO;

/**
 * Servicio para la lógica de negocio de Usuarios.
 * Proporciona métodos CRUD y validaciones.
 */
@Service
public class UsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private EstadosRepository estadosRepository;

    @Autowired
    private ContraseniasService contraseniasService;

    @Autowired
    private TelefonosService telefonosService;

    @Autowired
    private CorreosService correosService;

    @Autowired
    private PreguntasService preguntasService;

    @Autowired
    private RespuestasService respuestasService;

    /**
     * Obtener todos los usuarios.
     */
    public List<Usuarios> obtenerTodos() {
        return usuariosRepository.findAll();
    }

    /**
     * Obtener un usuario por ID.
     */
    public Optional<Usuarios> obtenerPorId(Integer id) {
        return usuariosRepository.findById(id);
    }

    /**
     * Crear un nuevo usuario.
     */
    public Usuarios crear(Usuarios usuario) {
        // Validaciones básicas
        if (usuario.getNombreUsuario() == null || usuario.getNombreUsuario().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario es requerido");
        }
        if (usuario.getNombres() == null || usuario.getNombres().isEmpty()) {
            throw new IllegalArgumentException("Los nombres son requeridos");
        }
        return usuariosRepository.save(usuario);
    }

    /**
     * Crear un nuevo usuario con contraseña, teléfono, correo y preguntas/respuestas.
     * Este método maneja la creación completa de un usuario con todos sus datos relacionados.
     */
    public Usuarios crearUsuarioCompleto(UsuarioCreacionDTO usuarioDTO) {
        // Validaciones básicas
        if (usuarioDTO.getNombreUsuario() == null || usuarioDTO.getNombreUsuario().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario es requerido");
        }
        if (usuarioDTO.getNombres() == null || usuarioDTO.getNombres().isEmpty()) {
            throw new IllegalArgumentException("Los nombres son requeridos");
        }
        if (usuarioDTO.getContrasenia() == null || usuarioDTO.getContrasenia().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es requerida");
        }
        if (usuarioDTO.getTelefono() == null || usuarioDTO.getTelefono().isEmpty()) {
            throw new IllegalArgumentException("El teléfono es requerido");
        }
        if (usuarioDTO.getCorreo() == null || usuarioDTO.getCorreo().isEmpty()) {
            throw new IllegalArgumentException("El correo es requerido");
        }
        
        // Crear usuario base
        Usuarios usuario = new Usuarios();
        usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());
        usuario.setNombres(usuarioDTO.getNombres());
        usuario.setApellidos(usuarioDTO.getApellidos());
        usuario.setDui(usuarioDTO.getDui());
        usuario.setFechaNacimiento(usuarioDTO.getFechaNacimiento());
        
        // Asignar Rol y Estado si están disponibles
        if (usuarioDTO.getIdRol() != null) {
            usuario.setRol(rolesRepository.findById(usuarioDTO.getIdRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + usuarioDTO.getIdRol())));
        }
        
        if (usuarioDTO.getIdEstado() != null) {
            usuario.setEstado(estadosRepository.findById(usuarioDTO.getIdEstado())
                .orElseThrow(() -> new RuntimeException("Estado no encontrado con ID: " + usuarioDTO.getIdEstado())));
        }
        
        // Guardar usuario
        Usuarios usuarioGuardado = usuariosRepository.save(usuario);
        
        // Crear contraseña
        try {
            Contrasenias contrasenia = new Contrasenias();
            contrasenia.setUsuario(usuarioGuardado);
            contrasenia.setContrasenia(usuarioDTO.getContrasenia());
            contraseniasService.crear(contrasenia);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear contraseña: " + e.getMessage());
        }
        
        // Crear teléfono
        try {
            Telefonos telefono = new Telefonos();
            telefono.setUsuario(usuarioGuardado);
            telefono.setTelefono(usuarioDTO.getTelefono());
            telefonosService.crear(telefono);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear teléfono: " + e.getMessage());
        }
        
        // Crear correo
        try {
            Correos correo = new Correos();
            correo.setUsuario(usuarioGuardado);
            correo.setCorreo(usuarioDTO.getCorreo());
            correosService.crear(correo);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear correo: " + e.getMessage());
        }
        
        // Crear preguntas y respuestas
        if (usuarioDTO.getPreguntasRespuestas() != null && !usuarioDTO.getPreguntasRespuestas().isEmpty()) {
            try {
                for (UsuarioCreacionDTO.PreguntaRespuestaDTO pR : usuarioDTO.getPreguntasRespuestas()) {
                    // Crear pregunta
                    Preguntas pregunta = new Preguntas();
                    pregunta.setPregunta(pR.getPregunta());
                    pregunta.setCreado(LocalDateTime.now());
                    pregunta.setCreadoPor(usuarioDTO.getNombreUsuario());
                    Preguntas preguntaGuardada = preguntasService.crear(pregunta);
                    
                    // Crear respuesta asociada
                    Respuestas respuesta = new Respuestas();
                    respuesta.setUsuario(usuarioGuardado);
                    respuesta.setPregunta(preguntaGuardada);
                    respuesta.setRespuesta(pR.getRespuesta());
                    respuestasService.crear(respuesta);
                }
            } catch (Exception e) {
                throw new RuntimeException("Error al crear preguntas y respuestas: " + e.getMessage());
            }
        }
        
        return usuarioGuardado;
    }

    /**
     * Actualizar un usuario existente.
     */
    public Usuarios actualizar(Integer id, Usuarios usuarioActualizado) {
        Optional<Usuarios> usuarioExistente = usuariosRepository.findById(id);
        
        if (usuarioExistente.isPresent()) {
            Usuarios usuario = usuarioExistente.get();
            
            if (usuarioActualizado.getNombreUsuario() != null) {
                usuario.setNombreUsuario(usuarioActualizado.getNombreUsuario());
            }
            if (usuarioActualizado.getNombres() != null) {
                usuario.setNombres(usuarioActualizado.getNombres());
            }
            if (usuarioActualizado.getApellidos() != null) {
                usuario.setApellidos(usuarioActualizado.getApellidos());
            }
            if (usuarioActualizado.getDui() != null) {
                usuario.setDui(usuarioActualizado.getDui());
            }
            if (usuarioActualizado.getFechaNacimiento() != null) {
                usuario.setFechaNacimiento(usuarioActualizado.getFechaNacimiento());
            }
            if (usuarioActualizado.getRol() != null) {
                usuario.setRol(usuarioActualizado.getRol());
            }
            if (usuarioActualizado.getEstado() != null) {
                usuario.setEstado(usuarioActualizado.getEstado());
            }
            if (usuarioActualizado.getEspecialidades() != null) {
                usuario.setEspecialidades(usuarioActualizado.getEspecialidades());
            }
            
            return usuariosRepository.save(usuario);
        } else {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
    }

    /**
     * Eliminar un usuario por ID.
     */
    public void eliminar(Integer id) {
        if (!usuariosRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
        usuariosRepository.deleteById(id);
    }
}
