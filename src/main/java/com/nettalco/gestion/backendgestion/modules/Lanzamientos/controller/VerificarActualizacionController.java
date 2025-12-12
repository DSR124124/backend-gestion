package com.nettalco.gestion.backendgestion.modules.Lanzamientos.controller;

import com.nettalco.gestion.backendgestion.modules.Lanzamientos.dtos.VerificarActualizacionDTO;
import com.nettalco.gestion.backendgestion.modules.Lanzamientos.servicesinterfaces.IVerificarActualizacionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador para verificar actualizaciones de aplicaciones móviles
 * 
 * Flujo:
 * 1. La app móvil hace login y obtiene el idUsuario
 * 2. La app consulta este endpoint con su codigoProducto y versionActual
 * 3. El backend verifica si hay lanzamientos disponibles para ese usuario
 * 4. Si hay una versión más nueva, devuelve la información de descarga
 */
@RestController
@RequestMapping("/api/verificar-actualizacion")
@CrossOrigin(origins = "*")
public class VerificarActualizacionController {
    
    @Autowired
    private IVerificarActualizacionService verificarActualizacionService;
    
    /**
     * Verifica si hay una actualización disponible para el usuario
     * 
     * GET /api/verificar-actualizacion/{idUsuario}/{codigoProducto}/{versionActual}
     * 
     * @param idUsuario ID del usuario logueado
     * @param codigoProducto Código de la aplicación (ej: FLUTTER_APP_SERVICIOS)
     * @param versionActual Versión actual de la app (ej: 1.0.0)
     * @return Información de la actualización o indicador de que no hay
     */
    @GetMapping("/{idUsuario}/{codigoProducto}/{versionActual}")
    public ResponseEntity<VerificarActualizacionDTO> verificarActualizacion(
            @PathVariable Integer idUsuario,
            @PathVariable String codigoProducto,
            @PathVariable String versionActual) {
        try {
            VerificarActualizacionDTO resultado = verificarActualizacionService.verificarActualizacion(
                    idUsuario, codigoProducto, versionActual);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            // En caso de error, devolver que no hay actualización
            // para no bloquear la app
            return ResponseEntity.ok(VerificarActualizacionDTO.sinActualizacion());
        }
    }
    
    /**
     * Verifica actualización usando POST (alternativa)
     * 
     * POST /api/verificar-actualizacion
     * Body: { "idUsuario": 1, "codigoProducto": "FLUTTER_APP_SERVICIOS", "versionActual": "1.0.0" }
     */
    @PostMapping
    public ResponseEntity<VerificarActualizacionDTO> verificarActualizacionPost(
            @RequestBody VerificarActualizacionDTO request) {
        try {
            VerificarActualizacionDTO resultado = verificarActualizacionService.verificarActualizacion(
                    request.getIdUsuario(), 
                    request.getCodigoProducto(), 
                    request.getVersionActual());
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.ok(VerificarActualizacionDTO.sinActualizacion());
        }
    }
    
    /**
     * Endpoint de health check
     */
    @GetMapping("/health")
    public ResponseEntity<?> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "ok");
        response.put("service", "verificar-actualizacion");
        return ResponseEntity.ok(response);
    }
}

