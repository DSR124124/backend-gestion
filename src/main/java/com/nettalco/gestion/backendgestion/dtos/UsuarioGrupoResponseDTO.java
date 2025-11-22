package com.nettalco.gestion.backendgestion.dtos;

import java.time.LocalDateTime;

public class UsuarioGrupoResponseDTO {
    
    private Integer idUsuarioGrupo;
    private Integer idUsuario;
    private String usuarioNombre;
    private String usuarioEmail;
    private Integer idGrupo;
    private String grupoNombre;
    private Integer ordenPrioridad;
    private LocalDateTime fechaAsignacion;
    private Integer asignadoPor;
    private String asignadoPorNombre;
    private Boolean activo;
    private String notas;
    
    // Constructors
    public UsuarioGrupoResponseDTO() {
    }
    
    public UsuarioGrupoResponseDTO(Integer idUsuarioGrupo, Integer idUsuario, String usuarioNombre, 
                                   String usuarioEmail, Integer idGrupo, String grupoNombre, 
                                   Integer ordenPrioridad, LocalDateTime fechaAsignacion, 
                                   Integer asignadoPor, String asignadoPorNombre, Boolean activo, String notas) {
        this.idUsuarioGrupo = idUsuarioGrupo;
        this.idUsuario = idUsuario;
        this.usuarioNombre = usuarioNombre;
        this.usuarioEmail = usuarioEmail;
        this.idGrupo = idGrupo;
        this.grupoNombre = grupoNombre;
        this.ordenPrioridad = ordenPrioridad;
        this.fechaAsignacion = fechaAsignacion;
        this.asignadoPor = asignadoPor;
        this.asignadoPorNombre = asignadoPorNombre;
        this.activo = activo;
        this.notas = notas;
    }
    
    // Getters and Setters
    public Integer getIdUsuarioGrupo() {
        return idUsuarioGrupo;
    }
    
    public void setIdUsuarioGrupo(Integer idUsuarioGrupo) {
        this.idUsuarioGrupo = idUsuarioGrupo;
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
    
    public Integer getIdGrupo() {
        return idGrupo;
    }
    
    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }
    
    public String getGrupoNombre() {
        return grupoNombre;
    }
    
    public void setGrupoNombre(String grupoNombre) {
        this.grupoNombre = grupoNombre;
    }
    
    public Integer getOrdenPrioridad() {
        return ordenPrioridad;
    }
    
    public void setOrdenPrioridad(Integer ordenPrioridad) {
        this.ordenPrioridad = ordenPrioridad;
    }
    
    public LocalDateTime getFechaAsignacion() {
        return fechaAsignacion;
    }
    
    public void setFechaAsignacion(LocalDateTime fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }
    
    public Integer getAsignadoPor() {
        return asignadoPor;
    }
    
    public void setAsignadoPor(Integer asignadoPor) {
        this.asignadoPor = asignadoPor;
    }
    
    public String getAsignadoPorNombre() {
        return asignadoPorNombre;
    }
    
    public void setAsignadoPorNombre(String asignadoPorNombre) {
        this.asignadoPorNombre = asignadoPorNombre;
    }
    
    public Boolean getActivo() {
        return activo;
    }
    
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    
    public String getNotas() {
        return notas;
    }
    
    public void setNotas(String notas) {
        this.notas = notas;
    }
}

