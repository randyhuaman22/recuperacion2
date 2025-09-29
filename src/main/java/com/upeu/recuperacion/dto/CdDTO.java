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
public class CdDTO {
    private Long id;
    private String condicion;
    private String ubicacion;
    private String estado;
}

