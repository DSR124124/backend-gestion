package com.nettalco.gestion.backendgestion.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador personalizado para manejar errores 404 y otros
 */
@RestController
public class CustomErrorController implements ErrorController {
    
    @RequestMapping("/error")
    public ResponseEntity<Map<String, Object>> handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object path = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        
        Map<String, Object> error = new HashMap<>();
        
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            error.put("status", statusCode);
            error.put("error", HttpStatus.valueOf(statusCode).getReasonPhrase());
        } else {
            error.put("status", 500);
            error.put("error", "Internal Server Error");
        }
        
        error.put("message", message != null ? message : "Error desconocido");
        error.put("path", path != null ? path : request.getRequestURI());
        error.put("timestamp", java.time.LocalDateTime.now().toString());
        
        // Información útil para el usuario
        error.put("availableEndpoints", Map.of(
            "health", "/api/health",
            "login", "/api/auth/login",
            "info", "Consulta /api/health para verificar el estado del servicio"
        ));
        
        HttpStatus httpStatus = status != null ? 
            HttpStatus.valueOf(Integer.parseInt(status.toString())) : 
            HttpStatus.INTERNAL_SERVER_ERROR;
        
        return ResponseEntity.status(httpStatus).body(error);
    }
}

