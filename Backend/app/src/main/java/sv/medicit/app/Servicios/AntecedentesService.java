package sv.medicit.app.Servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sv.medicit.app.Entidades.Antecedentes;
import sv.medicit.app.Repositorios.AntecedentesRepository;

/**
 * Servicio para la lógica de negocio de Antecedentes.
 * Proporciona métodos CRUD y validaciones.
 */
@Service
public class AntecedentesService {

    @Autowired
    private AntecedentesRepository antecedentesRepository;

    /**
     * Obtener todos los antecedentes.
     */
    public List<Antecedentes> obtenerTodos() {
        return antecedentesRepository.findAll();
    }

    /**
     * Obtener un antecedente por ID.
     */
    public Optional<Antecedentes> obtenerPorId(Integer id) {
        return antecedentesRepository.findById(id);
    }

    /**
     * Crear un nuevo antecedente.
     */
    public Antecedentes crear(Antecedentes antecedente) {
        if (antecedente.getUsuario() == null) {
            throw new IllegalArgumentException("El usuario es requerido");
        }
        if (antecedente.getAntecedente() == null || antecedente.getAntecedente().isEmpty()) {
            throw new IllegalArgumentException("El antecedente es requerido");
        }
        return antecedentesRepository.save(antecedente);
    }

    /**
     * Actualizar un antecedente existente.
     */
    public Antecedentes actualizar(Integer id, Antecedentes antecedenteActualizado) {
        Optional<Antecedentes> antecedenteExistente = antecedentesRepository.findById(id);
        
        if (antecedenteExistente.isPresent()) {
            Antecedentes antecedente = antecedenteExistente.get();
            
            if (antecedenteActualizado.getUsuario() != null) {
                antecedente.setUsuario(antecedenteActualizado.getUsuario());
            }
            if (antecedenteActualizado.getAntecedente() != null) {
                antecedente.setAntecedente(antecedenteActualizado.getAntecedente());
            }
            
            return antecedentesRepository.save(antecedente);
        } else {
            throw new RuntimeException("Antecedente no encontrado con ID: " + id);
        }
    }

    /**
     * Eliminar un antecedente por ID.
     */
    public void eliminar(Integer id) {
        if (!antecedentesRepository.existsById(id)) {
            throw new RuntimeException("Antecedente no encontrado con ID: " + id);
        }
        antecedentesRepository.deleteById(id);
    }
}
