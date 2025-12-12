package com.nettalco.gestion.backendgestion.modules.Aplicaciones.dtos;

/**
 * DTO para devolver información de la versión actual de una aplicación
 */
public class VersionAppDTO {
    
    private String codigoProducto;
    private String nombreAplicacion;
    private String versionActual;
    private String fechaPublicacion;
    
    // Constructors
    public VersionAppDTO() {
    }
    
    public VersionAppDTO(String codigoProducto, String nombreAplicacion, String versionActual, String fechaPublicacion) {
        this.codigoProducto = codigoProducto;
        this.nombreAplicacion = nombreAplicacion;
        this.versionActual = versionActual;
        this.fechaPublicacion = fechaPublicacion;
    }
    
    // Factory para cuando no hay versión
    public static VersionAppDTO sinVersion(String codigoProducto) {
        VersionAppDTO dto = new VersionAppDTO();
        dto.setCodigoProducto(codigoProducto);
        dto.setVersionActual("0.0.0");
        return dto;
    }
    
    // Getters and Setters
    public String getCodigoProducto() {
        return codigoProducto;
    }
    
    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }
    
    public String getNombreAplicacion() {
        return nombreAplicacion;
    }
    
    public void setNombreAplicacion(String nombreAplicacion) {
        this.nombreAplicacion = nombreAplicacion;
    }
    
    public String getVersionActual() {
        return versionActual;
    }
    
    public void setVersionActual(String versionActual) {
        this.versionActual = versionActual;
    }
    
    public String getFechaPublicacion() {
        return fechaPublicacion;
    }
    
    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }
}

