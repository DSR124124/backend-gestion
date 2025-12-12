package com.nettalco.gestion.backendgestion.modules.Roles.dtos;

import java.time.LocalDateTime;
import java.util.Map;

public class RolResponseDTO {
    
    private Integer idRol;
    private String nombreRol;
    private String descripcion;
    private Map<String, Object> permisos;
    private Boolean activo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    
    // Constructors
    public RolResponseDTO() {
    }
    
    public RolResponseDTO(Integer idRol, String nombreRol, String descripcion, Map<String, Object> permisos, 
                          Boolean activo, LocalDateTime fechaCreacion, LocalDateTime fechaModificacion) {
        this.idRol = idRol;
        this.nombreRol = nombreRol;
        this.descripcion = descripcion;
        this.permisos = permisos;
        this.activo = activo;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
    }
    
    // Getters and Setters
    public Integer getIdRol() {
        return idRol;
    }
    
    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }
    
    public String getNombreRol() {
        return nombreRol;
    }
    
    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Map<String, Object> getPermisos() {
        return permisos;
    }
    
    public void setPermisos(Map<String, Object> permisos) {
        this.permisos = permisos;
    }
    
    public Boolean getActivo() {
        return activo;
    }
    
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }
    
    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
}

