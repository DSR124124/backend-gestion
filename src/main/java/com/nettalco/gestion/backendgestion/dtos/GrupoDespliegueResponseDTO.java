package com.nettalco.gestion.backendgestion.dtos;

import java.time.LocalDateTime;
import java.util.Map;

public class GrupoDespliegueResponseDTO {
    
    private Integer idGrupo;
    private String nombreGrupo;
    private String descripcion;
    private Integer ordenPrioridad;
    private Integer porcentajeUsuarios;
    private Boolean activo;
    private LocalDateTime fechaCreacion;
    private Map<String, Object> metadata;
    
    // Constructors
    public GrupoDespliegueResponseDTO() {
    }
    
    public GrupoDespliegueResponseDTO(Integer idGrupo, String nombreGrupo, String descripcion, 
                                     Integer ordenPrioridad, Integer porcentajeUsuarios, 
                                     Boolean activo, LocalDateTime fechaCreacion, 
                                     Map<String, Object> metadata) {
        this.idGrupo = idGrupo;
        this.nombreGrupo = nombreGrupo;
        this.descripcion = descripcion;
        this.ordenPrioridad = ordenPrioridad;
        this.porcentajeUsuarios = porcentajeUsuarios;
        this.activo = activo;
        this.fechaCreacion = fechaCreacion;
        this.metadata = metadata;
    }
    
    // Getters and Setters
    public Integer getIdGrupo() {
        return idGrupo;
    }
    
    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }
    
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
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public Map<String, Object> getMetadata() {
        return metadata;
    }
    
    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
}

