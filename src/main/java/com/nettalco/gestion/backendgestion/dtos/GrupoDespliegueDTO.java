package com.nettalco.gestion.backendgestion.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Map;

public class GrupoDespliegueDTO {
    
    @NotBlank(message = "El nombre del grupo es obligatorio")
    @Size(max = 100, message = "El nombre del grupo no puede exceder 100 caracteres")
    private String nombreGrupo;
    
    private String descripcion;
    
    private Integer ordenPrioridad;
    
    @Min(value = 0, message = "El porcentaje de usuarios debe ser mayor o igual a 0")
    @Max(value = 100, message = "El porcentaje de usuarios debe ser menor o igual a 100")
    private Integer porcentajeUsuarios;
    
    private Boolean activo;
    
    private Map<String, Object> metadata;
    
    // Constructors
    public GrupoDespliegueDTO() {
    }
    
    public GrupoDespliegueDTO(String nombreGrupo, String descripcion, Integer ordenPrioridad, 
                             Integer porcentajeUsuarios, Boolean activo) {
        this.nombreGrupo = nombreGrupo;
        this.descripcion = descripcion;
        this.ordenPrioridad = ordenPrioridad;
        this.porcentajeUsuarios = porcentajeUsuarios;
        this.activo = activo;
    }
    
    // Getters and Setters
    public String getNombreGrupo() {
        return nombreGrupo;
    }
    
    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Integer getOrdenPrioridad() {
        return ordenPrioridad;
    }
    
    public void setOrdenPrioridad(Integer ordenPrioridad) {
        this.ordenPrioridad = ordenPrioridad;
    }
    
    public Integer getPorcentajeUsuarios() {
        return porcentajeUsuarios;
    }
    
    public void setPorcentajeUsuarios(Integer porcentajeUsuarios) {
        this.porcentajeUsuarios = porcentajeUsuarios;
    }
    
    public Boolean getActivo() {
        return activo;
    }
    
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    
    public Map<String, Object> getMetadata() {
        return metadata;
    }
    
    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
}

