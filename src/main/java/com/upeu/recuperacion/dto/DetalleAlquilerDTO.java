package com.upeu.recuperacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DetalleAlquilerDTO {
    private Long id;
    private Integer diasPrestamo;
    private LocalDate fechaDevolucion;
    private Long alquilerId;
    private Long cdId;
}
