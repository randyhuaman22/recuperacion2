package com.upeu.recuperacion.service.impl;

import com.upeu.recuperacion.controller.exceptions.ResourceNotFoundException;
import com.upeu.recuperacion.controller.exceptions.ResourceValidationException;
import com.upeu.recuperacion.dto.AlquilerDTO;
import com.upeu.recuperacion.dto.DetalleAlquilerDTO;
import com.upeu.recuperacion.entity.Alquiler;
import com.upeu.recuperacion.entity.Cliente;
import com.upeu.recuperacion.entity.DetalleAlquiler;
import com.upeu.recuperacion.entity.Cd;
import com.upeu.recuperacion.mappers.AlquilerMapper;
import com.upeu.recuperacion.repository.AlquilerRepository;
import com.upeu.recuperacion.repository.CdRepository;
import com.upeu.recuperacion.repository.ClienteRepository;
import com.upeu.recuperacion.service.service.AlquilerService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlquilerServiceImpl implements AlquilerService {

    private final AlquilerRepository alquilerRepository;
    private final ClienteRepository clienteRepository;
    private final CdRepository cdRepository;
    private final AlquilerMapper alquilerMapper;

    public AlquilerServiceImpl(AlquilerRepository alquilerRepository,
                               ClienteRepository clienteRepository,
                               CdRepository cdRepository,
                               AlquilerMapper alquilerMapper) {
        this.alquilerRepository = alquilerRepository;
        this.clienteRepository = clienteRepository;
        this.cdRepository = cdRepository;
        this.alquilerMapper = alquilerMapper;
    }

    @Transactional
    @Override
    public AlquilerDTO create(AlquilerDTO alquilerDTO) throws ServiceException {
        if (alquilerDTO == null) throw new IllegalArgumentException("El alquiler no puede ser nulo");

        Cliente cliente = clienteRepository.findById(alquilerDTO.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + alquilerDTO.getClienteId()));

        if (alquilerDTO.getDetalleAlquilers() == null || alquilerDTO.getDetalleAlquilers().isEmpty()) {
            throw new ResourceValidationException("El alquiler debe tener al menos un detalle");
        }

        Alquiler alquiler = new Alquiler();
        alquiler.setFechaAlquiler(alquilerDTO.getFechaAlquiler());
        alquiler.setPrecio(alquilerDTO.getPrecio());
        alquiler.setCliente(cliente);

        List<DetalleAlquiler> detalles = new ArrayList<>();
        for (DetalleAlquilerDTO detalleDTO : alquilerDTO.getDetalleAlquilers()) {
            Cd cd = cdRepository.findById(detalleDTO.getCdId())
                    .orElseThrow(() -> new ResourceNotFoundException("CD no encontrado con ID: " + detalleDTO.getCdId()));

            DetalleAlquiler detalle = new DetalleAlquiler();
            detalle.setAlquiler(alquiler);
            detalle.setCd(cd);
            detalle.setDiasPrestamo(detalleDTO.getDiasPrestamo());
            detalle.setFechaDevolucion(detalleDTO.getFechaDevolucion());

            detalles.add(detalle);
        }

        alquiler.setDetalleAlquilers(detalles);
        return alquilerMapper.toDTO(alquilerRepository.save(alquiler));
    }

    @Transactional
    @Override
    public AlquilerDTO update(Long id, AlquilerDTO alquilerDTO) throws ServiceException {
        Alquiler existente = alquilerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alquiler no encontrado con ID: " + id));

        Cliente cliente = clienteRepository.findById(alquilerDTO.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + alquilerDTO.getClienteId()));

        existente.setFechaAlquiler(alquilerDTO.getFechaAlquiler());
        existente.setPrecio(alquilerDTO.getPrecio());
        existente.setCliente(cliente);

        List<DetalleAlquiler> detallesExistentes = existente.getDetalleAlquilers();

        detallesExistentes.removeIf(detalle -> alquilerDTO.getDetalleAlquilers().stream()
                .noneMatch(dto -> dto.getId() != null && dto.getId().equals(detalle.getId())));

        for (DetalleAlquilerDTO dto : alquilerDTO.getDetalleAlquilers()) {
            if (dto.getId() != null) {
                DetalleAlquiler detalleExistente = detallesExistentes.stream()
                        .filter(d -> d.getId().equals(dto.getId()))
                        .findFirst()
                        .orElseThrow(() -> new ResourceNotFoundException("Detalle no encontrado con ID: " + dto.getId()));

                Cd cd = cdRepository.findById(dto.getCdId())
                        .orElseThrow(() -> new ResourceNotFoundException("CD no encontrado con ID: " + dto.getCdId()));

                detalleExistente.setCd(cd);
                detalleExistente.setDiasPrestamo(dto.getDiasPrestamo());
                detalleExistente.setFechaDevolucion(dto.getFechaDevolucion());

            } else {
                Cd cd = cdRepository.findById(dto.getCdId())
                        .orElseThrow(() -> new ResourceNotFoundException("CD no encontrado con ID: " + dto.getCdId()));

                DetalleAlquiler nuevoDetalle = new DetalleAlquiler();
                nuevoDetalle.setAlquiler(existente);
                nuevoDetalle.setCd(cd);
                nuevoDetalle.setDiasPrestamo(dto.getDiasPrestamo());
                nuevoDetalle.setFechaDevolucion(dto.getFechaDevolucion());

                detallesExistentes.add(nuevoDetalle);
            }
        }

        return alquilerMapper.toDTO(alquilerRepository.save(existente));
    }

    @Transactional(readOnly = true)
    @Override
    public AlquilerDTO findById(Long id) throws ServiceException {
        Alquiler alquiler = alquilerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alquiler no encontrado con ID: " + id));
        return alquilerMapper.toDTO(alquiler);
    }

    @Transactional
    @Override
    public void deleteById(Long id) throws ServiceException {
        if (!alquilerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Alquiler no encontrado con ID: " + id);
        }
        try {
            alquilerRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("No se puede eliminar porque tiene detalles asociados");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<AlquilerDTO> findAll() throws ServiceException {
        return alquilerRepository.findAll().stream()
                .map(alquilerMapper::toDTO)
                .toList();
    }
}
