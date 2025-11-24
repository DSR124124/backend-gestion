package com.nettalco.gestion.backendgestion.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador para health checks y verificación de estado del servicio
 * Este endpoint es público y no requiere autenticación
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class HealthController {
    
    /**
     * Health check público
     * GET /api/health
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "ok");
        response.put("service", "backend-gestion");
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("message", "Backend de Gestión está funcionando correctamente");
        return ResponseEntity.ok(response);
    }
}

