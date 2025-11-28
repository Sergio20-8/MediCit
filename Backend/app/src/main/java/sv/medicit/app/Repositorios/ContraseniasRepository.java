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
    
    // Métodos personalizados (opcional, se pueden agregar según necesidad)
}
