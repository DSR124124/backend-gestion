package com.nettalco.gestion.backendgestion.controllers;

import com.nettalco.gestion.backendgestion.dtos.EstadisticaLanzamientoDTO;
import com.nettalco.gestion.backendgestion.servicesinterfaces.IEstadisticaLanzamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estadisticas-lanzamientos")
@CrossOrigin(origins = "*")
public class EstadisticaLanzamientoController {
    
    @Autowired
    private IEstadisticaLanzamientoService estadisticaLanzamientoService;
    
    @GetMapping
    public ResponseEntity<List<EstadisticaLanzamientoDTO>> listarTodas() {
        List<EstadisticaLanzamientoDTO> estadisticas = estadisticaLanzamientoService.listarTodas();
        return ResponseEntity.ok(estadisticas);
    }
    
    @GetMapping("/aplicacion/{idAplicacion}")
    public ResponseEntity<List<EstadisticaLanzamientoDTO>> listarPorAplicacion(@PathVariable Integer idAplicacion) {
        List<EstadisticaLanzamientoDTO> estadisticas = estadisticaLanzamientoService.listarPorAplicacion(idAplicacion);
        return ResponseEntity.ok(estadisticas);
    }
}

