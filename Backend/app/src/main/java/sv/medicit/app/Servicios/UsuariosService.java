package sv.medicit.app.Servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sv.medicit.app.Entidades.Usuarios;
import sv.medicit.app.Repositorios.UsuariosRepository;

/**
 * Servicio para la lógica de negocio de Usuarios.
 * Proporciona métodos CRUD y validaciones.
 */
@Service
public class UsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;

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
