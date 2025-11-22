package com.nettalco.gestion.backendgestion.dtos;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class LanzamientoGrupoDTO {
    
    @NotNull(message = "El ID del lanzamiento es obligatorio")
    private Integer idLanzamiento;
    
    @NotNull(message = "El ID del grupo es obligatorio")
    private Integer idGrupo;
    
    private LocalDateTime fechaDisponibilidad;
    
    private LocalDateTime fechaFinDisponibilidad;
    
    private Integer asignadoPor;
    
    private Boolean notificacionEnviada;
    
    private Boolean activo;
    
    private String notas;
    
    // Constructors
    public LanzamientoGrupoDTO() {
    }
    
    public LanzamientoGrupoDTO(Integer idLanzamiento, Integer idGrupo, LocalDateTime fechaDisponibilidad, 
                              LocalDateTime fechaFinDisponibilidad, Integer asignadoPor, 
                              Boolean notificacionEnviada, Boolean activo, String notas) {
        this.idLanzamiento = idLanzamiento;
        this.idGrupo = idGrupo;
        this.fechaDisponibilidad = fechaDisponibilidad;
        this.fechaFinDisponibilidad = fechaFinDisponibilidad;
        this.asignadoPor = asignadoPor;
        this.notificacionEnviada = notificacionEnviada;
        this.activo = activo;
        this.notas = notas;
    }
    
    // Getters and Setters
    public Integer getIdLanzamiento() {
        return idLanzamiento;
    }
    
    public void setIdLanzamiento(Integer idLanzamiento) {
        this.idLanzamiento = idLanzamiento;
    }
    
    public Integer getIdGrupo() {
        return idGrupo;
    }
    
    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }
    
    public LocalDateTime getFechaDisponibilidad() {
        return fechaDisponibilidad;
    }
    
    public void setFechaDisponibilidad(LocalDateTime fechaDisponibilidad) {
        this.fechaDisponibilidad = fechaDisponibilidad;
    }
    
    public LocalDateTime getFechaFinDisponibilidad() {
        return fechaFinDisponibilidad;
    }
    
    public void setFechaFinDisponibilidad(LocalDateTime fechaFinDisponibilidad) {
        this.fechaFinDisponibilidad = fechaFinDisponibilidad;
    }
    
    public Integer getAsignadoPor() {
        return asignadoPor;
    }
    
    public void setAsignadoPor(Integer asignadoPor) {
        this.asignadoPor = asignadoPor;
    }
    
    public Boolean getNotificacionEnviada() {
        return notificacionEnviada;
    }
    
    public void setNotificacionEnviada(Boolean notificacionEnviada) {
        this.notificacionEnviada = notificacionEnviada;
    }
    
    public Boolean getActivo() {
        return activo;
    }
    
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    
    public String getNotas() {
        return notas;
    }
    
    public void setNotas(String notas) {
        this.notas = notas;
    }
}

