package sv.medicit.app.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sv.medicit.app.Entidades.Contrasenias;

/**
 * Repositorio para la entidad Contrasenias.
 * Proporciona métodos CRUD y operaciones de base de datos.
 */
@Repository
public interface ContraseniasRepository extends JpaRepository<Contrasenias, Integer> {
    
    /**
     * Busca la contraseña de un usuario específico.
     * 
     * @param usuario El usuario
     * @return La contraseña del usuario, o null si no existe
     */
    Contrasenias findByUsuario(sv.medicit.app.Entidades.Usuarios usuario);
}
