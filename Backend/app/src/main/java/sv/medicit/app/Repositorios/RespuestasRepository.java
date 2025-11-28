package sv.medicit.app.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sv.medicit.app.Entidades.Respuestas;

/**
 * Repositorio para la entidad Respuestas.
 * Proporciona métodos CRUD y operaciones de base de datos.
 */
@Repository
public interface RespuestasRepository extends JpaRepository<Respuestas, Integer> {
    
    // Métodos personalizados (opcional, se pueden agregar según necesidad)
}
