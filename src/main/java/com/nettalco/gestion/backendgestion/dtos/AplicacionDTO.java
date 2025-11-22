package com.nettalco.gestion.backendgestion.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Map;

public class AplicacionDTO {
    
    @NotBlank(message = "El nombre de la aplicación es obligatorio")
    @Size(max = 255, message = "El nombre de la aplicación no puede exceder 255 caracteres")
    private String nombreAplicacion;
    
    private String descripcion;
    
    @Size(max = 50, message = "El código de producto no puede exceder 50 caracteres")
    private String codigoProducto;
    
    @Size(max = 500, message = "La URL del icono no puede exceder 500 caracteres")
    private String iconoUrl;
    
    @Size(max = 500, message = "La URL del repositorio no puede exceder 500 caracteres")
    private String repositorioUrl;
    
    private Integer responsableId;
    
    private Boolean activo;
    
    private Map<String, Object> metadata;
    
    // Constructors
    public AplicacionDTO() {
    }
    
    public AplicacionDTO(String nombreAplicacion, String descripcion, String codigoProducto, 
                        String iconoUrl, String repositorioUrl, Integer responsableId, Boolean activo) {
        this.nombreAplicacion = nombreAplicacion;
        this.descripcion = descripcion;
        this.codigoProducto = codigoProducto;
        this.iconoUrl = iconoUrl;
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
    
    public String getIconoUrl() {
        return iconoUrl;
    }
    
    public void setIconoUrl(String iconoUrl) {
        this.iconoUrl = iconoUrl;
    }
    
    public String getRepositorioUrl() {
        return repositorioUrl;
    }
    
    public void setRepositorioUrl(String repositorioUrl) {
        this.repositorioUrl = repositorioUrl;
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
    
    public Map<String, Object> getMetadata() {
        return metadata;
    }
    
    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
}

