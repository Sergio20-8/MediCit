package sv.medicit.app.Entidades;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad JPA que representa la tabla `usuarios`.
 * Esta clase fue adaptada para mapear columnas y permitir que JPA/Hibernate
 * cree/actualice la tabla desde las entidades (ddl-auto=update).
 */
@Entity
@Table(name = "Usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private int idUsuario;

    @Column(name = "nombre_usuario", length = 15, nullable = false)
    private String nombreUsuario;

    @Column(name = "nombres", length = 35, nullable = false)
    private String nombres;

    @Column(name = "apellidos", length = 35, nullable = false)
    private String apellidos;

    @Column(name = "dui", length = 10, nullable = true)
    private String dui;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_nacimiento", nullable = false)
    private Date fechaNacimiento;

     
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol", referencedColumnName = "id_rol", nullable = false)
    private Roles rol;

    // Relación ManyToOne con Estados. La columna en la tabla usuarios se llama `id_estado` y guarda el id (id_estado)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado", referencedColumnName = "id_estado", nullable = false)
    private Estados estado;
}
