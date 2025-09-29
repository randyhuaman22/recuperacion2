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
public class ClienteDTO {
    private Long id;
    private String direccion;
    private String telefono;
    private String nombre;
    private String email;
    private String dni;
    private LocalDate fechaNacimiento;
    private LocalDate fechaInscripcion;
    private String temaInteres;
    private String estado;
}

