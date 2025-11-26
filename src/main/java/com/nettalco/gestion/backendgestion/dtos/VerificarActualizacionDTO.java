package com.nettalco.gestion.backendgestion.dtos;

import java.time.LocalDateTime;

/**
 * DTO para verificar y devolver información de actualizaciones disponibles
 */
public class VerificarActualizacionDTO {
    
    // Request fields
    private Integer idUsuario;
    private String codigoProducto;
    private String versionActual;
    
    // Response fields
    private Boolean hayActualizacion;
    private Integer idLanzamiento;
    private Integer idAplicacion;
    private String nombreAplicacion;
    private String versionNueva;
    private String estado;
    private LocalDateTime fechaLanzamiento;
    private String notasVersion;
    private String urlDescarga;
    private Long tamanoArchivo;
    private Boolean esCritico;
    private String nombreGrupo;
    private LocalDateTime fechaDisponibilidad;
    private LocalDateTime fechaFinDisponibilidad;
    
    // Constructors
    public VerificarActualizacionDTO() {
    }
    
    // Builder-style constructor for response
    public static VerificarActualizacionDTO sinActualizacion() {
        VerificarActualizacionDTO dto = new VerificarActualizacionDTO();
        dto.setHayActualizacion(false);
        return dto;
    }
    
    // Getters and Setters
    public Integer getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String getCodigoProducto() {
        return codigoProducto;
    }
    
    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }
    
    public String getVersionActual() {
        return versionActual;
    }
    
    public void setVersionActual(String versionActual) {
        this.versionActual = versionActual;
    }
    
    public Boolean getHayActualizacion() {
        return hayActualizacion;
    }
    
    public void setHayActualizacion(Boolean hayActualizacion) {
        this.hayActualizacion = hayActualizacion;
    }
    
    public Integer getIdLanzamiento() {
        return idLanzamiento;
    }
    
    public void setIdLanzamiento(Integer idLanzamiento) {
        this.idLanzamiento = idLanzamiento;
    }
    
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
    
    public String getVersionNueva() {
        return versionNueva;
    }
    
    public void setVersionNueva(String versionNueva) {
        this.versionNueva = versionNueva;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public LocalDateTime getFechaLanzamiento() {
        return fechaLanzamiento;
    }
    
    public void setFechaLanzamiento(LocalDateTime fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }
    
    public String getNotasVersion() {
        return notasVersion;
    }
    
    public void setNotasVersion(String notasVersion) {
        this.notasVersion = notasVersion;
    }
    
    public String getUrlDescarga() {
        return urlDescarga;
    }
    
    public void setUrlDescarga(String urlDescarga) {
        this.urlDescarga = urlDescarga;
    }
    
    public Long getTamanoArchivo() {
        return tamanoArchivo;
    }
    
    public void setTamanoArchivo(Long tamanoArchivo) {
        this.tamanoArchivo = tamanoArchivo;
    }
    
    public Boolean getEsCritico() {
        return esCritico;
    }
    
    public void setEsCritico(Boolean esCritico) {
        this.esCritico = esCritico;
    }
    
    public String getNombreGrupo() {
        return nombreGrupo;
    }
    
    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
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
}

