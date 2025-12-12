package com.nettalco.gestion.backendgestion.modules.Roles.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Map;

public class RolDTO {
    
    @NotBlank(message = "El nombre del rol es obligatorio")
    @Size(max = 50, message = "El nombre del rol no puede exceder 50 caracteres")
    private String nombreRol;
    
    private String descripcion;
    
    private Map<String, Object> permisos;
    
    private Boolean activo;
    
    // Constructors
    public RolDTO() {
    }
    
    public RolDTO(String nombreRol, String descripcion, Map<String, Object> permisos, Boolean activo) {
        this.nombreRol = nombreRol;
        this.descripcion = descripcion;
        this.permisos = permisos;
        this.activo = activo;
    }
    
    // Getters and Setters
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
}

