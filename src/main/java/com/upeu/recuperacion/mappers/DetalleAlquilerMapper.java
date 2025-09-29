package com.upeu.recuperacion.mappers;

import com.upeu.recuperacion.dto.DetalleAlquilerDTO;
import com.upeu.recuperacion.entity.DetalleAlquiler;
import com.upeu.recuperacion.mappers.base.BaseMappers;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DetalleAlquilerMapper extends BaseMappers<DetalleAlquiler, DetalleAlquilerDTO> {

    @Mapping(source = "alquiler.id", target = "alquilerId")
    @Mapping(source = "cd.id", target = "cdId")
    DetalleAlquilerDTO toDTO(DetalleAlquiler detalleAlquiler);

    @InheritInverseConfiguration
    DetalleAlquiler toEntity(DetalleAlquilerDTO detalleAlquilerDTO);
}
