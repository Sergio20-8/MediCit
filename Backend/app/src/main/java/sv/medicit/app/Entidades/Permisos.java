package sv.medicit.app.Entidades;

 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table; 
import jakarta.persistence.FetchType; 
import jakarta.persistence.OneToMany;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Entidad JPA que representa la tabla `usuarios`.
 * Esta clase fue adaptada para mapear columnas y permitir que JPA/Hibernate
 * cree/actualice la tabla desde las entidades (ddl-auto=update).
 */
@Entity
@Table(name = "Permisos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permisos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permiso")
    private Integer idPermiso;

    @Column(name = "nombre_permiso", length = 30, nullable = false)
    private String estado;

    @Column(name = "descripcion", length = 200, nullable = false)
    private String descripcion;

    // Relación inversa - lista de RolPermisoModulo para este permiso
    @OneToMany(mappedBy = "permiso", fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<RolPermisoModulo> rolesPermisosModulos;

}
