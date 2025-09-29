package com.upeu.recuperacion.service.impl;

import com.upeu.recuperacion.controller.exceptions.ResourceNotFoundException;
import com.upeu.recuperacion.controller.exceptions.ResourceValidationException;
import com.upeu.recuperacion.dto.DetalleAlquilerDTO;
import com.upeu.recuperacion.entity.Alquiler;
import com.upeu.recuperacion.entity.Cd;
import com.upeu.recuperacion.entity.DetalleAlquiler;
import com.upeu.recuperacion.mappers.DetalleAlquilerMapper;
import com.upeu.recuperacion.repository.AlquilerRepository;
import com.upeu.recuperacion.repository.CdRepository;
import com.upeu.recuperacion.repository.DetalleAlquilerRepository;
import com.upeu.recuperacion.service.service.DetalleAlquilerService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleAlquilerServiceImpl implements DetalleAlquilerService {

    private final DetalleAlquilerRepository detalleAlquilerRepository;
    private final DetalleAlquilerMapper detalleAlquilerMapper;
    private final AlquilerRepository alquilerRepository;
    private final CdRepository cdRepository;

    public DetalleAlquilerServiceImpl(DetalleAlquilerRepository detalleAlquilerRepository,
                                      DetalleAlquilerMapper detalleAlquilerMapper,
                                      AlquilerRepository alquilerRepository,
                                      CdRepository cdRepository) {
        this.detalleAlquilerRepository = detalleAlquilerRepository;
        this.detalleAlquilerMapper = detalleAlquilerMapper;
        this.alquilerRepository = alquilerRepository;
        this.cdRepository = cdRepository;
    }

    @Override
    public DetalleAlquilerDTO create(DetalleAlquilerDTO detalleDTO) throws ServiceException {
        if (detalleDTO == null) {
            throw new IllegalArgumentException("El detalle de alquiler no puede ser nulo.");
        }

        if (detalleDTO.getDiasPrestamo() == null || detalleDTO.getDiasPrestamo() <= 0) {
            throw new ResourceValidationException("Los días de préstamo deben ser mayores que 0.");
        }

        if (detalleDTO.getFechaDevolucion() == null) {
            throw new ResourceValidationException("La fecha de devolución es obligatoria.");
        }

        if (detalleDTO.getAlquilerId() == null) {
            throw new ResourceValidationException("El alquilerId no puede ser nulo.");
        }

        if (detalleDTO.getCdId() == null) {
            throw new ResourceValidationException("El cdId no puede ser nulo.");
        }

        Alquiler alquiler = alquilerRepository.findById(detalleDTO.getAlquilerId())
                .orElseThrow(() -> new ResourceNotFoundException("Alquiler no encontrado con ID: " + detalleDTO.getAlquilerId()));

        Cd cd = cdRepository.findById(detalleDTO.getCdId())
                .orElseThrow(() -> new ResourceNotFoundException("CD no encontrado con ID: " + detalleDTO.getCdId()));

        DetalleAlquiler detalle = new DetalleAlquiler();
        detalle.setDiasPrestamo(detalleDTO.getDiasPrestamo());
        detalle.setFechaDevolucion(detalleDTO.getFechaDevolucion());
        detalle.setAlquiler(alquiler);
        detalle.setCd(cd);

        return detalleAlquilerMapper.toDTO(detalleAlquilerRepository.save(detalle));
    }

    @Override
    public DetalleAlquilerDTO update(Long id, DetalleAlquilerDTO detalleDTO) throws ServiceException {
        if (id == null || detalleDTO == null) {
            throw new IllegalArgumentException("El ID y el detalle de alquiler no pueden ser nulos.");
        }

        DetalleAlquiler existente = detalleAlquilerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Detalle de alquiler no encontrado con ID: " + id));

        if (detalleDTO.getDiasPrestamo() == null || detalleDTO.getDiasPrestamo() <= 0) {
            throw new ResourceValidationException("Los días de préstamo deben ser mayores que 0.");
        }

        if (detalleDTO.getFechaDevolucion() == null) {
            throw new ResourceValidationException("La fecha de devolución es obligatoria.");
        }

        existente.setDiasPrestamo(detalleDTO.getDiasPrestamo());
        existente.setFechaDevolucion(detalleDTO.getFechaDevolucion());

        if (detalleDTO.getAlquilerId() != null) {
            Alquiler alquiler = alquilerRepository.findById(detalleDTO.getAlquilerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Alquiler no encontrado con ID: " + detalleDTO.getAlquilerId()));
            existente.setAlquiler(alquiler);
        }

        if (detalleDTO.getCdId() != null) {
            Cd cd = cdRepository.findById(detalleDTO.getCdId())
                    .orElseThrow(() -> new ResourceNotFoundException("CD no encontrado con ID: " + detalleDTO.getCdId()));
            existente.setCd(cd);
        }

        return detalleAlquilerMapper.toDTO(detalleAlquilerRepository.save(existente));
    }

    @Override
    public DetalleAlquilerDTO findById(Long id) throws ServiceException {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }

        DetalleAlquiler detalle = detalleAlquilerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Detalle de alquiler no encontrado con ID: " + id));

        return detalleAlquilerMapper.toDTO(detalle);
    }

    @Override
    public void deleteById(Long id) throws ServiceException {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }

        if (!detalleAlquilerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Detalle de alquiler no encontrado con ID: " + id);
        }

        detalleAlquilerRepository.deleteById(id);
    }

    @Override
    public List<DetalleAlquilerDTO> findAll() throws ServiceException {
        List<DetalleAlquiler> lista = detalleAlquilerRepository.findAll();

        if (lista.isEmpty()) {
            throw new ResourceNotFoundException("No hay registros de detalle de alquiler.");
        }

        return lista.stream()
                .map(detalleAlquilerMapper::toDTO)
                .toList();
    }
}
