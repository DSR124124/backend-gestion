package com.nettalco.gestion.backendgestion.modules.Lanzamientos.dtos;

import java.time.LocalDateTime;

public class LanzamientoGrupoResponseDTO {
    
    private Integer idLanzamientoGrupo;
    private Integer idLanzamiento;
    private String lanzamientoVersion;
    private String aplicacionNombre;
    private Integer idGrupo;
    private String grupoNombre;
    private Integer ordenPrioridad;
    private LocalDateTime fechaAsignacion;
    private LocalDateTime fechaDisponibilidad;
    private LocalDateTime fechaFinDisponibilidad;
    private Integer asignadoPor;
    private String asignadoPorNombre;
    private Boolean notificacionEnviada;
    private Boolean activo;
    private String notas;
    
    // Constructors
    public LanzamientoGrupoResponseDTO() {
    }
    
    public LanzamientoGrupoResponseDTO(Integer idLanzamientoGrupo, Integer idLanzamiento, String lanzamientoVersion, 
                                      String aplicacionNombre, Integer idGrupo, String grupoNombre, 
                                      Integer ordenPrioridad, LocalDateTime fechaAsignacion, 
                                      LocalDateTime fechaDisponibilidad, LocalDateTime fechaFinDisponibilidad, 
                                      Integer asignadoPor, String asignadoPorNombre, Boolean notificacionEnviada, 
                                      Boolean activo, String notas) {
        this.idLanzamientoGrupo = idLanzamientoGrupo;
        this.idLanzamiento = idLanzamiento;
        this.lanzamientoVersion = lanzamientoVersion;
        this.aplicacionNombre = aplicacionNombre;
        this.idGrupo = idGrupo;
        this.grupoNombre = grupoNombre;
        this.ordenPrioridad = ordenPrioridad;
        this.fechaAsignacion = fechaAsignacion;
        this.fechaDisponibilidad = fechaDisponibilidad;
        this.fechaFinDisponibilidad = fechaFinDisponibilidad;
        this.asignadoPor = asignadoPor;
        this.asignadoPorNombre = asignadoPorNombre;
        this.notificacionEnviada = notificacionEnviada;
        this.activo = activo;
        this.notas = notas;
    }
    
    // Getters and Setters
    public Integer getIdLanzamientoGrupo() {
        return idLanzamientoGrupo;
    }
    
    public void setIdLanzamientoGrupo(Integer idLanzamientoGrupo) {
        this.idLanzamientoGrupo = idLanzamientoGrupo;
    }
    
    public Integer getIdLanzamiento() {
        return idLanzamiento;
    }
    
    public void setIdLanzamiento(Integer idLanzamiento) {
        this.idLanzamiento = idLanzamiento;
    }
    
    public String getLanzamientoVersion() {
        return lanzamientoVersion;
    }
    
    public void setLanzamientoVersion(String lanzamientoVersion) {
        this.lanzamientoVersion = lanzamientoVersion;
    }
    
    public String getAplicacionNombre() {
        return aplicacionNombre;
    }
    
    public void setAplicacionNombre(String aplicacionNombre) {
        this.aplicacionNombre = aplicacionNombre;
    }
    
    public Integer getIdGrupo() {
        return idGrupo;
    }
    
    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }
    
    public String getGrupoNombre() {
        return grupoNombre;
    }
    
    public void setGrupoNombre(String grupoNombre) {
        this.grupoNombre = grupoNombre;
    }
    
    public Integer getOrdenPrioridad() {
        return ordenPrioridad;
    }
    
    public void setOrdenPrioridad(Integer ordenPrioridad) {
        this.ordenPrioridad = ordenPrioridad;
    }
    
    public LocalDateTime getFechaAsignacion() {
        return fechaAsignacion;
    }
    
    public void setFechaAsignacion(LocalDateTime fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
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
    
    public String getAsignadoPorNombre() {
        return asignadoPorNombre;
    }
    
    public void setAsignadoPorNombre(String asignadoPorNombre) {
        this.asignadoPorNombre = asignadoPorNombre;
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

