package com.upeu.recuperacion.controller.general;

import com.upeu.recuperacion.dto.AlquilerDTO;
import com.upeu.recuperacion.service.impl.AlquilerServiceImpl;
import com.upeu.recuperacion.service.impl.DetalleAlquilerServiceImpl;
import com.upeu.recuperacion.service.service.AlquilerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/alquiler")
public class AlquilerController {
    private final AlquilerService alquilerService;

    public AlquilerController(AlquilerService alquilerService) {
        this.alquilerService = alquilerService;
    }
    @GetMapping
    public ResponseEntity<List<AlquilerDTO>> listarAlquilers() {
        List<AlquilerDTO> alquilers = alquilerService.findAll();
        return ResponseEntity.ok(alquilers);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AlquilerDTO> obtenerAlquiler(@PathVariable Long id) {
        AlquilerDTO alquiler = alquilerService.findById(id);
        return ResponseEntity.ok(alquiler);
    }
    @PostMapping
    public ResponseEntity<AlquilerDTO> crearAlquiler(@RequestBody AlquilerDTO alquilerDTO) {
        AlquilerDTO creada = alquilerService.create(alquilerDTO);
        return ResponseEntity.ok(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlquilerDTO> actualizarAlquiler(@PathVariable Long id,
                                                          @RequestBody AlquilerDTO alquilerDTO) {
        AlquilerDTO actualizada = alquilerService.update(id, alquilerDTO);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAlquiler(@PathVariable Long id) {
        alquilerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
