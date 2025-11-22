package com.nettalco.gestion.backendgestion.controllers;

import com.nettalco.gestion.backendgestion.dtos.AplicacionDTO;
import com.nettalco.gestion.backendgestion.dtos.AplicacionResponseDTO;
import com.nettalco.gestion.backendgestion.servicesinterfaces.IAplicacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/aplicaciones")
@CrossOrigin(origins = "*")
public class AplicacionController {
    
    @Autowired
    private IAplicacionService aplicacionService;
    
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody AplicacionDTO aplicacionDTO) {
        try {
            AplicacionResponseDTO aplicacionCreada = aplicacionService.crear(aplicacionDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(aplicacionCreada);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @Valid @RequestBody AplicacionDTO aplicacionDTO) {
        try {
            AplicacionResponseDTO aplicacionActualizada = aplicacionService.actualizar(id, aplicacionDTO);
            return ResponseEntity.ok(aplicacionActualizada);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            HttpStatus status = e.getMessage().contains("no encontrada") ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body(error);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            aplicacionService.eliminar(id);
            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Aplicación eliminada correctamente");
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    @GetMapping
    public ResponseEntity<List<AplicacionResponseDTO>> listar() {
        List<AplicacionResponseDTO> aplicaciones = aplicacionService.listar();
        return ResponseEntity.ok(aplicaciones);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable Integer id) {
        try {
            AplicacionResponseDTO aplicacion = aplicacionService.listarPorId(id);
            return ResponseEntity.ok(aplicacion);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}

