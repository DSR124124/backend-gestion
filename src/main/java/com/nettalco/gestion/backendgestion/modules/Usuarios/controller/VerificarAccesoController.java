package com.nettalco.gestion.backendgestion.modules.Usuarios.controller;

import com.nettalco.gestion.backendgestion.modules.Usuarios.dtos.VerificarAccesoDTO;
import com.nettalco.gestion.backendgestion.modules.Usuarios.servicesinterfaces.IVerificarAccesoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/verificar-acceso")
@CrossOrigin(origins = "*")
public class VerificarAccesoController {
    
    @Autowired
    private IVerificarAccesoService verificarAccesoService;
    
    @GetMapping("/usuario/{idUsuario}/lanzamiento/{idLanzamiento}")
    public ResponseEntity<VerificarAccesoDTO> verificarAcceso(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idLanzamiento) {
        try {
            VerificarAccesoDTO resultado = verificarAccesoService.verificarAcceso(idUsuario, idLanzamiento);
            return ResponseEntity.ok(resultado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<VerificarAccesoDTO> verificarAccesoPost(
            @RequestBody VerificarAccesoDTO request) {
        try {
            VerificarAccesoDTO resultado = verificarAccesoService.verificarAcceso(
                    request.getIdUsuario(), 
                    request.getIdLanzamiento()
            );
            return ResponseEntity.ok(resultado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}

