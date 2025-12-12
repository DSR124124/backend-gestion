package com.nettalco.gestion.backendgestion.modules.Grupos.controller;

import com.nettalco.gestion.backendgestion.modules.Grupos.dtos.UsuarioGrupoDTO;
import com.nettalco.gestion.backendgestion.modules.Grupos.dtos.UsuarioGrupoResponseDTO;
import com.nettalco.gestion.backendgestion.modules.Grupos.servicesinterfaces.IUsuarioGrupoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios-grupos")
@CrossOrigin(origins = "*")
public class UsuarioGrupoController {
    
    @Autowired
    private IUsuarioGrupoService usuarioGrupoService;
    
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody UsuarioGrupoDTO usuarioGrupoDTO) {
        try {
            UsuarioGrupoResponseDTO usuarioGrupoCreado = usuarioGrupoService.crear(usuarioGrupoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioGrupoCreado);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @Valid @RequestBody UsuarioGrupoDTO usuarioGrupoDTO) {
        try {
            UsuarioGrupoResponseDTO usuarioGrupoActualizado = usuarioGrupoService.actualizar(id, usuarioGrupoDTO);
            return ResponseEntity.ok(usuarioGrupoActualizado);
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
            usuarioGrupoService.eliminar(id);
            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Asignación usuario-grupo eliminada correctamente");
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    @GetMapping
    public ResponseEntity<List<UsuarioGrupoResponseDTO>> listar() {
        List<UsuarioGrupoResponseDTO> usuariosGrupos = usuarioGrupoService.listar();
        return ResponseEntity.ok(usuariosGrupos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable Integer id) {
        try {
            UsuarioGrupoResponseDTO usuarioGrupo = usuarioGrupoService.listarPorId(id);
            return ResponseEntity.ok(usuarioGrupo);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}

