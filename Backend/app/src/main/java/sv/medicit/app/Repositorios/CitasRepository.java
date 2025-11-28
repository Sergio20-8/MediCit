package sv.medicit.app.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sv.medicit.app.Entidades.Citas;

/**
 * Repositorio para la entidad Citas.
 * Proporciona métodos CRUD y operaciones de base de datos.
 */
@Repository
public interface CitasRepository extends JpaRepository<Citas, Integer> {
    
    // Métodos personalizados (opcional, se pueden agregar según necesidad)
}
