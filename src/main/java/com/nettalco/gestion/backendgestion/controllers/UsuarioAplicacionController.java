package com.nettalco.gestion.backendgestion.controllers;

import com.nettalco.gestion.backendgestion.dtos.UsuarioAplicacionDTO;
import com.nettalco.gestion.backendgestion.dtos.UsuarioAplicacionResponseDTO;
import com.nettalco.gestion.backendgestion.servicesinterfaces.IUsuarioAplicacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios-aplicaciones")
@CrossOrigin(origins = "*")
public class UsuarioAplicacionController {
    
    @Autowired
    private IUsuarioAplicacionService usuarioAplicacionService;
    
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody UsuarioAplicacionDTO usuarioAplicacionDTO) {
        try {
            UsuarioAplicacionResponseDTO usuarioAplicacionCreado = usuarioAplicacionService.crear(usuarioAplicacionDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioAplicacionCreado);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @Valid @RequestBody UsuarioAplicacionDTO usuarioAplicacionDTO) {
        try {
            UsuarioAplicacionResponseDTO usuarioAplicacionActualizado = usuarioAplicacionService.actualizar(id, usuarioAplicacionDTO);
            return ResponseEntity.ok(usuarioAplicacionActualizado);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            HttpStatus status = e.getMessage().contains("no encontrado") || e.getMessage().contains("no encontrada") ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body(error);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            usuarioAplicacionService.eliminar(id);
            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Relación usuario-aplicación eliminada correctamente");
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    @GetMapping
    public ResponseEntity<List<UsuarioAplicacionResponseDTO>> listar() {
        List<UsuarioAplicacionResponseDTO> usuariosAplicaciones = usuarioAplicacionService.listar();
        return ResponseEntity.ok(usuariosAplicaciones);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable Integer id) {
        try {
            UsuarioAplicacionResponseDTO usuarioAplicacion = usuarioAplicacionService.listarPorId(id);
            return ResponseEntity.ok(usuarioAplicacion);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}

