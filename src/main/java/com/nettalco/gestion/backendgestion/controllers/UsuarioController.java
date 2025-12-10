package com.nettalco.gestion.backendgestion.controllers;

import com.nettalco.gestion.backendgestion.dtos.UsuarioDTO;
import com.nettalco.gestion.backendgestion.dtos.UsuarioResponseDTO;
import com.nettalco.gestion.backendgestion.servicesinterfaces.IUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {
    
    @Autowired
    private IUsuarioService usuarioService;
    
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        try {
            // Validar que el password sea obligatorio en creación
            if (usuarioDTO.getPassword() == null || usuarioDTO.getPassword().trim().isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("mensaje", "La contraseña es obligatoria");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            
            UsuarioResponseDTO usuarioCreado = usuarioService.crear(usuarioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @Valid @RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioResponseDTO usuarioActualizado = usuarioService.actualizar(id, usuarioDTO);
            return ResponseEntity.ok(usuarioActualizado);
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
            usuarioService.eliminar(id);
            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Usuario eliminado correctamente");
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        List<UsuarioResponseDTO> usuarios = usuarioService.listar();
        return ResponseEntity.ok(usuarios);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable Integer id) {
        try {
            UsuarioResponseDTO usuario = usuarioService.listarPorId(id);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}

