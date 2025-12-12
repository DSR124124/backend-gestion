package com.nettalco.gestion.backendgestion.modules.Aplicaciones.dtos;

import java.time.LocalDateTime;

public class UsuarioAplicacionResponseDTO {
    
    private Integer idUsuarioAplicacion;
    private Integer idUsuario;
    private String usuarioNombre;
    private String usuarioEmail;
    private Integer idAplicacion;
    private String aplicacionNombre;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaUltimoAcceso;
    private Boolean licenciaActiva;
    private LocalDateTime fechaExpiracionLicencia;
    private Integer registradoPor;
    private String registradoPorNombre;
    private String notas;
    
    // Constructors
    public UsuarioAplicacionResponseDTO() {
    }
    
    public UsuarioAplicacionResponseDTO(Integer idUsuarioAplicacion, Integer idUsuario, String usuarioNombre, 
                                       String usuarioEmail, Integer idAplicacion, String aplicacionNombre, 
                                       LocalDateTime fechaRegistro, LocalDateTime fechaUltimoAcceso, 
                                       Boolean licenciaActiva, LocalDateTime fechaExpiracionLicencia, 
                                       Integer registradoPor, String registradoPorNombre, String notas) {
        this.idUsuarioAplicacion = idUsuarioAplicacion;
        this.idUsuario = idUsuario;
        this.usuarioNombre = usuarioNombre;
        this.usuarioEmail = usuarioEmail;
        this.idAplicacion = idAplicacion;
        this.aplicacionNombre = aplicacionNombre;
        this.fechaRegistro = fechaRegistro;
        this.fechaUltimoAcceso = fechaUltimoAcceso;
        this.licenciaActiva = licenciaActiva;
        this.fechaExpiracionLicencia = fechaExpiracionLicencia;
        this.registradoPor = registradoPor;
        this.registradoPorNombre = registradoPorNombre;
        this.notas = notas;
    }
    
    // Getters and Setters
    public Integer getIdUsuarioAplicacion() {
        return idUsuarioAplicacion;
    }
    
    public void setIdUsuarioAplicacion(Integer idUsuarioAplicacion) {
        this.idUsuarioAplicacion = idUsuarioAplicacion;
    }
    
    public Integer getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String getUsuarioNombre() {
        return usuarioNombre;
    }
    
    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }
    
    public String getUsuarioEmail() {
        return usuarioEmail;
    }
    
    public void setUsuarioEmail(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
    }
    
    public Integer getIdAplicacion() {
        return idAplicacion;
    }
    
    public void setIdAplicacion(Integer idAplicacion) {
        this.idAplicacion = idAplicacion;
    }
    
    public String getAplicacionNombre() {
        return aplicacionNombre;
    }
    
    public void setAplicacionNombre(String aplicacionNombre) {
        this.aplicacionNombre = aplicacionNombre;
    }
    
    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    public LocalDateTime getFechaUltimoAcceso() {
        return fechaUltimoAcceso;
    }
    
    public void setFechaUltimoAcceso(LocalDateTime fechaUltimoAcceso) {
        this.fechaUltimoAcceso = fechaUltimoAcceso;
    }
    
    public Boolean getLicenciaActiva() {
        return licenciaActiva;
    }
    
    public void setLicenciaActiva(Boolean licenciaActiva) {
        this.licenciaActiva = licenciaActiva;
    }
    
    public LocalDateTime getFechaExpiracionLicencia() {
        return fechaExpiracionLicencia;
    }
    
    public void setFechaExpiracionLicencia(LocalDateTime fechaExpiracionLicencia) {
        this.fechaExpiracionLicencia = fechaExpiracionLicencia;
    }
    
    public Integer getRegistradoPor() {
        return registradoPor;
    }
    
    public void setRegistradoPor(Integer registradoPor) {
        this.registradoPor = registradoPor;
    }
    
    public String getRegistradoPorNombre() {
        return registradoPorNombre;
    }
    
    public void setRegistradoPorNombre(String registradoPorNombre) {
        this.registradoPorNombre = registradoPorNombre;
    }
    
    public String getNotas() {
        return notas;
    }
    
    public void setNotas(String notas) {
        this.notas = notas;
    }
}

