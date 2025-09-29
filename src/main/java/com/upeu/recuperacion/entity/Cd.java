package com.upeu.recuperacion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name="TBL_CD")
public class Cd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CONDICION", nullable = false, length = 100)
    private String condicion;

    @Column(name = "UBICACION", nullable = false, length = 100)
    private String ubicacion;

    @Column(name = "ESTADO", nullable = false, length = 1)
    private String estado;
}
