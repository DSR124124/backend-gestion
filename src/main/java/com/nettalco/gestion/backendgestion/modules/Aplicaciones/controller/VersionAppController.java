package com.nettalco.gestion.backendgestion.modules.Aplicaciones.controller;

import com.nettalco.gestion.backendgestion.modules.Aplicaciones.dtos.VersionAppDTO;
import com.nettalco.gestion.backendgestion.modules.Aplicaciones.servicesinterfaces.IVersionAppService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador para obtener información de versión de aplicaciones
 * 
 * Endpoints:
 * - GET /api/version-app/{codigoProducto} - Obtiene la versión actual de una app
 */
@RestController
@RequestMapping("/api/version-app")
@CrossOrigin(origins = "*")
public class VersionAppController {
    
    @Autowired
    private IVersionAppService versionAppService;
    
    /**
     * Obtiene la versión actual de una aplicación por su código de producto
     * 
     * GET /api/version-app/{codigoProducto}
     * 
     * @param codigoProducto Código de la aplicación (ej: FLUTTER_APP_SERVICIOS)
     * @return Información de la versión actual
     */
    @GetMapping("/{codigoProducto}")
    public ResponseEntity<VersionAppDTO> obtenerVersionActual(@PathVariable String codigoProducto) {
        try {
            VersionAppDTO version = versionAppService.obtenerVersionActual(codigoProducto);
            return ResponseEntity.ok(version);
        } catch (Exception e) {
            // En caso de error, devolver versión por defecto
            return ResponseEntity.ok(VersionAppDTO.sinVersion(codigoProducto));
        }
    }
    
    /**
     * Endpoint de health check
     */
    @GetMapping("/health")
    public ResponseEntity<?> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "ok");
        response.put("service", "version-app");
        return ResponseEntity.ok(response);
    }
}

