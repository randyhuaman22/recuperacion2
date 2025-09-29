package com.upeu.recuperacion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name="TBL_CLIENTE")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DIRECCION", nullable = false, length = 100)
    private String direccion;

    @Column(name = "TELEFONO", nullable = false, length = 50)
    private String telefono;

    @Column(name = "NOMBRE", nullable = false, length = 150)
    private String nombre;

    @Column(name = "EMAIL", nullable = false, length = 100)
    private String email;

    @Column(name = "DNI", nullable = false, length = 20)
    private String dni;

    @Column(name = "FECHA_NACIMIENTO", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "FECHA_INSCRIPCION", nullable = false)
    private LocalDate fechaInscripcion;

    @Column(name = "TEMA_INTERES", nullable = false, length = 100)
    private String temaInteres;

    @Column(name = "ESTADO", nullable = false, length = 1)
    private String estado;
}
