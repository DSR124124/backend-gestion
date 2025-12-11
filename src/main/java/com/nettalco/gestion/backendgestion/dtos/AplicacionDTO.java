package com.nettalco.gestion.backendgestion.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AplicacionDTO {
    
    @NotBlank(message = "El nombre de la aplicación es obligatorio")
    @Size(max = 255, message = "El nombre de la aplicación no puede exceder 255 caracteres")
    private String nombreAplicacion;
    
    private String descripcion;
    
    @Size(max = 50, message = "El código de producto no puede exceder 50 caracteres")
    private String codigoProducto;
    
    @Size(max = 500, message = "La URL del repositorio no puede exceder 500 caracteres")
    private String repositorioUrl;
    
    @Size(max = 500, message = "La URL no puede exceder 500 caracteres")
    private String url;
    
    private Integer responsableId;
    
    private Boolean activo;
    
    // Constructors
    public AplicacionDTO() {
    }
    
    public AplicacionDTO(String nombreAplicacion, String descripcion, String codigoProducto, 
                        String repositorioUrl, Integer responsableId, Boolean activo) {
        this.nombreAplicacion = nombreAplicacion;
        this.descripcion = descripcion;
        this.codigoProducto = codigoProducto;
        this.repositorioUrl = repositorioUrl;
        this.responsableId = responsableId;
        this.activo = activo;
    }
    
    // Getters and Setters
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
    
    public Boolean getActivo() {
        return activo;
    }
    
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}

