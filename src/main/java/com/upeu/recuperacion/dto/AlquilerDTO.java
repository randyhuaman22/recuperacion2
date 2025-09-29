package com.upeu.recuperacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AlquilerDTO {
    private Long id;
    private LocalDate fechaAlquiler;
    private BigDecimal precio;
    private Long clienteId;
    private List<DetalleAlquilerDTO> detalleAlquilers;
}

