package com.nettalco.gestion.backendgestion.controllers;

import com.nettalco.gestion.backendgestion.dtos.GrupoDespliegueDTO;
import com.nettalco.gestion.backendgestion.dtos.GrupoDespliegueResponseDTO;
import com.nettalco.gestion.backendgestion.servicesinterfaces.IGrupoDespliegueService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/grupos-despliegue")
@CrossOrigin(origins = "*")
public class GrupoDespliegueController {
    
    @Autowired
    private IGrupoDespliegueService grupoDespliegueService;
    
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody GrupoDespliegueDTO grupoDespliegueDTO) {
        try {
            GrupoDespliegueResponseDTO grupoDespliegueCreado = grupoDespliegueService.crear(grupoDespliegueDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(grupoDespliegueCreado);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @Valid @RequestBody GrupoDespliegueDTO grupoDespliegueDTO) {
        try {
            GrupoDespliegueResponseDTO grupoDespliegueActualizado = grupoDespliegueService.actualizar(id, grupoDespliegueDTO);
            return ResponseEntity.ok(grupoDespliegueActualizado);
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
            grupoDespliegueService.eliminar(id);
            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Grupo de despliegue eliminado correctamente");
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    @GetMapping
    public ResponseEntity<List<GrupoDespliegueResponseDTO>> listar() {
        List<GrupoDespliegueResponseDTO> gruposDespliegue = grupoDespliegueService.listar();
        return ResponseEntity.ok(gruposDespliegue);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable Integer id) {
        try {
            GrupoDespliegueResponseDTO grupoDespliegue = grupoDespliegueService.listarPorId(id);
            return ResponseEntity.ok(grupoDespliegue);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}

