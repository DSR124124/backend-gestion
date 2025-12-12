package com.nettalco.gestion.backendgestion.modules.Notificaciones.controller;

import com.nettalco.gestion.backendgestion.modules.Notificaciones.dtos.NotificacionDTO;
import com.nettalco.gestion.backendgestion.modules.Notificaciones.dtos.NotificacionResponseDTO;
import com.nettalco.gestion.backendgestion.modules.Notificaciones.dtos.NotificacionUsuarioDTO;
import com.nettalco.gestion.backendgestion.modules.Notificaciones.servicesinterfaces.INotificacionService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notificaciones")
@CrossOrigin(origins = "*")
public class NotificacionController {
    
    @Autowired
    private INotificacionService notificacionService;
    
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody NotificacionDTO notificacionDTO) {
        try {
            NotificacionResponseDTO notificacionCreada = notificacionService.crear(notificacionDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(notificacionCreada);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, 
                                        @Valid @RequestBody NotificacionDTO notificacionDTO) {
        try {
            NotificacionResponseDTO notificacionActualizada = notificacionService.actualizar(id, notificacionDTO);
            return ResponseEntity.ok(notificacionActualizada);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            HttpStatus status = e.getMessage().contains("no encontrada") ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body(error);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            notificacionService.eliminar(id);
            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Notificación eliminada correctamente");
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        try {
            NotificacionResponseDTO notificacion = notificacionService.obtenerPorId(id);
            return ResponseEntity.ok(notificacion);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    @GetMapping
    public ResponseEntity<List<NotificacionResponseDTO>> listar() {
        List<NotificacionResponseDTO> notificaciones = notificacionService.listar();
        return ResponseEntity.ok(notificaciones);
    }
    
    @GetMapping("/por-aplicacion/{idAplicacion}")
    public ResponseEntity<List<NotificacionResponseDTO>> listarPorAplicacion(
            @PathVariable Integer idAplicacion) {
        List<NotificacionResponseDTO> notificaciones = notificacionService.listarPorAplicacion(idAplicacion);
        return ResponseEntity.ok(notificaciones);
    }
    
    @GetMapping("/usuario/{idUsuario}/no-leidas")
    public ResponseEntity<List<NotificacionUsuarioDTO>> obtenerNotificacionesNoLeidas(
            @PathVariable Integer idUsuario) {
        List<NotificacionUsuarioDTO> notificaciones = notificacionService.obtenerNotificacionesNoLeidas(idUsuario);
        return ResponseEntity.ok(notificaciones);
    }
    
    @GetMapping("/usuario/{idUsuario}/aplicacion/{idAplicacion}/no-leidas")
    public ResponseEntity<List<NotificacionUsuarioDTO>> obtenerNotificacionesNoLeidasPorAplicacion(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idAplicacion) {
        List<NotificacionUsuarioDTO> notificaciones = 
                notificacionService.obtenerNotificacionesNoLeidasPorAplicacion(idUsuario, idAplicacion);
        return ResponseEntity.ok(notificaciones);
    }
    
    @PutMapping("/{idNotificacion}/usuario/{idUsuario}/marcar-leida")
    public ResponseEntity<?> marcarComoLeida(@PathVariable Integer idNotificacion,
                                            @PathVariable Integer idUsuario) {
        try {
            notificacionService.marcarComoLeida(idNotificacion, idUsuario);
            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Notificación marcada como leída");
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PutMapping("/{idNotificacion}/usuario/{idUsuario}/confirmar")
    public ResponseEntity<?> confirmarNotificacion(@PathVariable Integer idNotificacion,
                                                  @PathVariable Integer idUsuario) {
        try {
            notificacionService.confirmarNotificacion(idNotificacion, idUsuario);
            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Notificación confirmada");
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PostMapping("/{idNotificacion}/asignar-grupo/{idGrupo}")
    public ResponseEntity<?> asignarNotificacionAGrupo(@PathVariable Integer idNotificacion,
                                                      @PathVariable Integer idGrupo) {
        try {
            notificacionService.asignarNotificacionAGrupo(idNotificacion, idGrupo);
            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Notificación asignada al grupo correctamente");
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PostMapping("/{idNotificacion}/asignar-usuarios")
    public ResponseEntity<?> asignarNotificacionAUsuarios(@PathVariable Integer idNotificacion,
                                                          @RequestBody List<Integer> idUsuarios) {
        try {
            notificacionService.asignarNotificacionAUsuarios(idNotificacion, idUsuarios);
            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Notificación asignada a los usuarios correctamente");
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}

