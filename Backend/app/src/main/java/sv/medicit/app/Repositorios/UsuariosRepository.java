package sv.medicit.app.Repositorios;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sv.medicit.app.Entidades.Usuarios;

/**
 * Repositorio para la entidad Usuarios.
 * Proporciona métodos CRUD y operaciones de base de datos.
 */
@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Integer> {
    
    // Métodos personalizados
    Optional<Usuarios> findByNombreUsuario(String nombreUsuario);
    Optional<Usuarios> findByDui(String dui);
}
