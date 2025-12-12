package com.nettalco.gestion.backendgestion.modules.Roles.controller;

import com.nettalco.gestion.backendgestion.modules.Roles.dtos.RolDTO;
import com.nettalco.gestion.backendgestion.modules.Roles.dtos.RolResponseDTO;
import com.nettalco.gestion.backendgestion.modules.Roles.servicesinterfaces.IRolService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
public class RolController {
    
    @Autowired
    private IRolService rolService;
    
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody RolDTO rolDTO) {
        try {
            RolResponseDTO rolCreado = rolService.crear(rolDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(rolCreado);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @Valid @RequestBody RolDTO rolDTO) {
        try {
            RolResponseDTO rolActualizado = rolService.actualizar(id, rolDTO);
            return ResponseEntity.ok(rolActualizado);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            rolService.eliminar(id);
            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Rol eliminado correctamente");
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    @GetMapping
    public ResponseEntity<List<RolResponseDTO>> listar() {
        List<RolResponseDTO> roles = rolService.listar();
        return ResponseEntity.ok(roles);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable Integer id) {
        try {
            RolResponseDTO rol = rolService.listarPorId(id);
            return ResponseEntity.ok(rol);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}

