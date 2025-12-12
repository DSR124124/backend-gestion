package com.nettalco.gestion.backendgestion.modules.Aplicaciones.dtos;

import java.time.LocalDateTime;

public class AplicacionResponseDTO {
    
    private Integer idAplicacion;
    private String nombreAplicacion;
    private String descripcion;
    private String codigoProducto;
    private String repositorioUrl;
    private String url;
    private Integer responsableId;
    private String responsableNombre;
    private Boolean activo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    
    // Constructors
    public AplicacionResponseDTO() {
    }
    
    public AplicacionResponseDTO(Integer idAplicacion, String nombreAplicacion, String descripcion, 
                                String codigoProducto, String repositorioUrl, 
                                String url, Integer responsableId, String responsableNombre, Boolean activo, 
                                LocalDateTime fechaCreacion, LocalDateTime fechaModificacion) {
        this.idAplicacion = idAplicacion;
        this.nombreAplicacion = nombreAplicacion;
        this.descripcion = descripcion;
        this.codigoProducto = codigoProducto;
        this.repositorioUrl = repositorioUrl;
        this.url = url;
        this.responsableId = responsableId;
        this.responsableNombre = responsableNombre;
        this.activo = activo;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
    }
    
    // Getters and Setters
    public Integer getIdAplicacion() {
        return idAplicacion;
    }
    
    public void setIdAplicacion(Integer idAplicacion) {
        this.idAplicacion = idAplicacion;
    }
    
    public String getNombreAplicacion() {
        return nombreAplicacion;
    }
    
    public void setNombreAplicacion(String nombreAplicacion) {
        this.nombreAplicacion = nombreAplicacion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getCodigoProducto() {
        return codigoProducto;
    }
    
    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }
    
    public String getRepositorioUrl() {
        return repositorioUrl;
    }
    
    public void setRepositorioUrl(String repositorioUrl) {
        this.repositorioUrl = repositorioUrl;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public Integer getResponsableId() {
        return responsableId;
    }
    
    public void setResponsableId(Integer responsableId) {
        this.responsableId = responsableId;
    }
    
    public String getResponsableNombre() {
        return responsableNombre;
    }
    
    public void setResponsableNombre(String responsableNombre) {
        this.responsableNombre = responsableNombre;
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

