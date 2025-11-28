package sv.medicit.app.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sv.medicit.app.Entidades.Antecedentes;

/**
 * Repositorio para la entidad Antecedentes.
 * Proporciona métodos CRUD y operaciones de base de datos.
 */
@Repository
public interface AntecedentesRepository extends JpaRepository<Antecedentes, Integer> {
    
    // Métodos personalizados (opcional, se pueden agregar según necesidad)
}
