package com.upeu.recuperacion.service.service;

import com.upeu.recuperacion.dto.AlquilerDTO;
import com.upeu.recuperacion.dto.DetalleAlquilerDTO;
import com.upeu.recuperacion.entity.Alquiler;
import com.upeu.recuperacion.entity.DetalleAlquiler;
import com.upeu.recuperacion.service.base.GenericService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleAlquilerService extends GenericService<DetalleAlquiler, DetalleAlquilerDTO, Long> {
}
