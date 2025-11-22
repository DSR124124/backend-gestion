package com.nettalco.gestion.backendgestion.controllers;

import com.nettalco.gestion.backendgestion.dtos.LanzamientoDTO;
import com.nettalco.gestion.backendgestion.dtos.LanzamientoResponseDTO;
import com.nettalco.gestion.backendgestion.servicesinterfaces.ILanzamientoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lanzamientos")
@CrossOrigin(origins = "*")
public class LanzamientoController {
    
    @Autowired
    private ILanzamientoService lanzamientoService;
    
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody LanzamientoDTO lanzamientoDTO) {
        try {
            LanzamientoResponseDTO lanzamientoCreado = lanzamientoService.crear(lanzamientoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(lanzamientoCreado);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @Valid @RequestBody LanzamientoDTO lanzamientoDTO) {
        try {
            LanzamientoResponseDTO lanzamientoActualizado = lanzamientoService.actualizar(id, lanzamientoDTO);
            return ResponseEntity.ok(lanzamientoActualizado);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            HttpStatus status = e.getMessage().contains("no encontrado") ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body(error);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            lanzamientoService.eliminar(id);
            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Lanzamiento eliminado correctamente");
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    @GetMapping
    public ResponseEntity<List<LanzamientoResponseDTO>> listar() {
        List<LanzamientoResponseDTO> lanzamientos = lanzamientoService.listar();
        return ResponseEntity.ok(lanzamientos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable Integer id) {
        try {
            LanzamientoResponseDTO lanzamiento = lanzamientoService.listarPorId(id);
            return ResponseEntity.ok(lanzamiento);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}

