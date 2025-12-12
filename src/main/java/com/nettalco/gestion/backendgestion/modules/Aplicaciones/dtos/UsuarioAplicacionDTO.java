package com.nettalco.gestion.backendgestion.modules.Aplicaciones.dtos;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class UsuarioAplicacionDTO {
    
    @NotNull(message = "El ID del usuario es obligatorio")
    private Integer idUsuario;
    
    @NotNull(message = "El ID de la aplicación es obligatorio")
    private Integer idAplicacion;
    
    private LocalDateTime fechaUltimoAcceso;
    
    private Boolean licenciaActiva;
    
    private LocalDateTime fechaExpiracionLicencia;
    
    private Integer registradoPor;
    
    private String notas;
    
    // Constructors
    public UsuarioAplicacionDTO() {
    }
    
    public UsuarioAplicacionDTO(Integer idUsuario, Integer idAplicacion, Boolean licenciaActiva, 
                               LocalDateTime fechaExpiracionLicencia, Integer registradoPor, String notas) {
        this.idUsuario = idUsuario;
        this.idAplicacion = idAplicacion;
        this.licenciaActiva = licenciaActiva;
        this.fechaExpiracionLicencia = fechaExpiracionLicencia;
        this.registradoPor = registradoPor;
        this.notas = notas;
    }
    
    // Getters and Setters
    public Integer getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public Integer getIdAplicacion() {
        return idAplicacion;
    }
    
    public void setIdAplicacion(Integer idAplicacion) {
        this.idAplicacion = idAplicacion;
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
    
    public String getNotas() {
        return notas;
    }
    
    public void setNotas(String notas) {
        this.notas = notas;
    }
}

