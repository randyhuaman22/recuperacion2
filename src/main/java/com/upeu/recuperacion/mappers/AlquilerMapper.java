package com.upeu.recuperacion.mappers;

import com.upeu.recuperacion.dto.AlquilerDTO;
import com.upeu.recuperacion.entity.Alquiler;
import com.upeu.recuperacion.mappers.base.BaseMappers;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DetalleAlquilerMapper.class})
public interface AlquilerMapper extends BaseMappers<Alquiler, AlquilerDTO> {

    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "detalleAlquilers", target = "detalleAlquilers")
    AlquilerDTO toDTO(Alquiler alquiler);

    @InheritInverseConfiguration
    Alquiler toEntity(AlquilerDTO alquilerDTO);
}
