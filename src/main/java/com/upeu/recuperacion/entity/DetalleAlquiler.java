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
@Table(name="TBL_DETALLE")
public class DetalleAlquiler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DIAS_PRESTAMO", nullable = false)
    private Integer diasPrestamo;

    @Column(name = "FECHA_DEVOLUCION", nullable = false)
    private LocalDate fechaDevolucion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_ALQUILER", nullable = false)
    private Alquiler alquiler;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_CD", nullable = false)
    private Cd cd;
}
