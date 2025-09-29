package com.upeu.recuperacion.controller.general;

import com.upeu.recuperacion.dto.DetalleAlquilerDTO;
import com.upeu.recuperacion.service.service.DetalleAlquilerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/detalle")
public class DetalleAlquilerController {
    private final DetalleAlquilerService detalleAlquilerService;

    public DetalleAlquilerController(DetalleAlquilerService detalleAlquilerService) {
        this.detalleAlquilerService = detalleAlquilerService;
    }
    @GetMapping
    public ResponseEntity<List<DetalleAlquilerDTO>> listarDetalleAlquilers() {
        List<DetalleAlquilerDTO> detalleAlquilers = detalleAlquilerService.findAll();
        return ResponseEntity.ok(detalleAlquilers);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DetalleAlquilerDTO> obtenerDetalleAlquiler(@PathVariable Long id) {
        DetalleAlquilerDTO detalleAlquiler = detalleAlquilerService.findById(id);
        return ResponseEntity.ok(detalleAlquiler);
    }
    @PostMapping
    public ResponseEntity<DetalleAlquilerDTO> crearDetalleAlquiler(@RequestBody DetalleAlquilerDTO detalleAlquilerDTO) {
        DetalleAlquilerDTO creada = detalleAlquilerService.create(detalleAlquilerDTO);
        return ResponseEntity.ok(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleAlquilerDTO> actualizarDetalleAlquiler(@PathVariable Long id,
                                                                        @RequestBody DetalleAlquilerDTO detalleAlquilerDTO) {
        DetalleAlquilerDTO actualizada = detalleAlquilerService.update(id, detalleAlquilerDTO);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDetalleAlquiler(@PathVariable Long id) {
        detalleAlquilerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
