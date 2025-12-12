package com.nettalco.gestion.backendgestion.modules.Lanzamientos.controller;

import com.nettalco.gestion.backendgestion.modules.Lanzamientos.dtos.UsuarioLanzamientoDisponibleDTO;
import com.nettalco.gestion.backendgestion.modules.Lanzamientos.servicesinterfaces.IUsuarioLanzamientoDisponibleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios-lanzamientos-disponibles")
@CrossOrigin(origins = "*")
public class UsuarioLanzamientoDisponibleController {
    
    @Autowired
    private IUsuarioLanzamientoDisponibleService usuarioLanzamientoDisponibleService;
    
    @GetMapping
    public ResponseEntity<List<UsuarioLanzamientoDisponibleDTO>> listarTodos() {
        List<UsuarioLanzamientoDisponibleDTO> lanzamientos = usuarioLanzamientoDisponibleService.listarTodos();
        return ResponseEntity.ok(lanzamientos);
    }
    
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<UsuarioLanzamientoDisponibleDTO>> listarPorUsuario(@PathVariable Integer idUsuario) {
        List<UsuarioLanzamientoDisponibleDTO> lanzamientos = usuarioLanzamientoDisponibleService.listarPorUsuario(idUsuario);
        return ResponseEntity.ok(lanzamientos);
    }
    
    @GetMapping("/aplicacion/{idAplicacion}")
    public ResponseEntity<List<UsuarioLanzamientoDisponibleDTO>> listarPorAplicacion(@PathVariable Integer idAplicacion) {
        List<UsuarioLanzamientoDisponibleDTO> lanzamientos = usuarioLanzamientoDisponibleService.listarPorAplicacion(idAplicacion);
        return ResponseEntity.ok(lanzamientos);
    }
    
    @GetMapping("/grupo/{idGrupo}")
    public ResponseEntity<List<UsuarioLanzamientoDisponibleDTO>> listarPorGrupo(@PathVariable Integer idGrupo) {
        List<UsuarioLanzamientoDisponibleDTO> lanzamientos = usuarioLanzamientoDisponibleService.listarPorGrupo(idGrupo);
        return ResponseEntity.ok(lanzamientos);
    }
}

