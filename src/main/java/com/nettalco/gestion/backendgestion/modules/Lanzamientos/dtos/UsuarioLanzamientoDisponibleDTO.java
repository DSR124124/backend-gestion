package com.nettalco.gestion.backendgestion.modules.Lanzamientos.dtos;

import java.time.LocalDateTime;

public class UsuarioLanzamientoDisponibleDTO {
    
    private Integer idUsuario;
    private String username;
    private String email;
    private Integer idAplicacion;
    private String nombreAplicacion;
    private Integer idLanzamiento;
    private String version;
    private String estado;
    private LocalDateTime fechaLanzamiento;
    private String notasVersion;
    private String urlDescarga;
    private Long tamanoArchivo;
    private Boolean esCritico;
    private Integer idGrupo;
    private String nombreGrupo;
    private LocalDateTime fechaDisponibilidad;
    private LocalDateTime fechaFinDisponibilidad;
    
    // Constructors
    public UsuarioLanzamientoDisponibleDTO() {
    }
    
    public UsuarioLanzamientoDisponibleDTO(Integer idUsuario, String username, String email, 
                                          Integer idAplicacion, String nombreAplicacion, 
                                          Integer idLanzamiento, String version, String estado, 
                                          LocalDateTime fechaLanzamiento, String notasVersion, 
                                          String urlDescarga, Long tamanoArchivo, Boolean esCritico, 
                                          Integer idGrupo, String nombreGrupo, 
                                          LocalDateTime fechaDisponibilidad, LocalDateTime fechaFinDisponibilidad) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.email = email;
        this.idAplicacion = idAplicacion;
        this.nombreAplicacion = nombreAplicacion;
        this.idLanzamiento = idLanzamiento;
        this.version = version;
        this.estado = estado;
        this.fechaLanzamiento = fechaLanzamiento;
        this.notasVersion = notasVersion;
        this.urlDescarga = urlDescarga;
        this.tamanoArchivo = tamanoArchivo;
        this.esCritico = esCritico;
        this.idGrupo = idGrupo;
        this.nombreGrupo = nombreGrupo;
        this.fechaDisponibilidad = fechaDisponibilidad;
        this.fechaFinDisponibilidad = fechaFinDisponibilidad;
    }
    
    // Getters and Setters
    public Integer getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
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
    
    public Integer getIdLanzamiento() {
        return idLanzamiento;
    }
    
    public void setIdLanzamiento(Integer idLanzamiento) {
        this.idLanzamiento = idLanzamiento;
    }
    
    public String getVersion() {
        return version;
    }
    
    public void setVersion(String version) {
        this.version = version;
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

