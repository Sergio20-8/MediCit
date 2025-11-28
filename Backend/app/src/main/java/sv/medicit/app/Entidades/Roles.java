package sv.medicit.app.Entidades;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Entidad JPA que representa la tabla `Estado`.
 * Esta clase fue adaptada para mapear columnas y permitir que JPA/Hibernate
 * cree/actualice la tabla desde las entidades (ddl-auto=update).
 */
@Entity
@Table(name = "Roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Integer idEstado;

    @Column(name = "nombre_rol", length = 15, nullable = false)
    private String estado;

    @Column(name = "descripcion", length = 200, nullable = false)
    private String descripcion;

    // Relación inversa (opcional) - lista de usuarios que referencian este estado
    @OneToMany(mappedBy = "rol", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Usuarios> usuarios;

    // Relación inversa - lista de RolPermisoModulo para este rol
    @OneToMany(mappedBy = "rol", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<RolPermisoModulo> rolesPermisosModulos;

}
