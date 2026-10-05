package dev.eder.empleados.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table (name = "empleados")
@Data 
@NoArgsConstructor 
public class Empleado {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length=100)
    private String apellido;

    //El DNI no se puede repetir es unico por empleado
    @Column (nullable= false, unique = true, length=8 )
    private String dni;

    @Column(nullable = false, length=50)
    private String cargo;

    //Para el dinero se utiliza BigDecimal para evitar problemas de redondeo
    @Column (nullable=false, precision=10, scale=2)
    private BigDecimal sueldo;

    // Campo INTERNO: el cliente no debería poder verlo ni cambiarlo
    @Column(nullable=false)
    private Boolean activo = true;

    // Lo pone el servidor, no el cliente
    @Column(nullable=false, updatable=false)
    private LocalDateTime fechaRegistro;

    @PrePersist 
    void alCrear(){
        this.fechaRegistro = LocalDateTime.now();
    }

}
