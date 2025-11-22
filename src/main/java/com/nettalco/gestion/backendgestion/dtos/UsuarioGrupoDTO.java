package com.nettalco.gestion.backendgestion.dtos;

import jakarta.validation.constraints.NotNull;

public class UsuarioGrupoDTO {
    
    @NotNull(message = "El ID del usuario es obligatorio")
    private Integer idUsuario;
    
    @NotNull(message = "El ID del grupo es obligatorio")
    private Integer idGrupo;
    
    private Integer asignadoPor;
    
    private Boolean activo;
    
    private String notas;
    
    // Constructors
    public UsuarioGrupoDTO() {
    }
    
    public UsuarioGrupoDTO(Integer idUsuario, Integer idGrupo, Integer asignadoPor, Boolean activo, String notas) {
        this.idUsuario = idUsuario;
        this.idGrupo = idGrupo;
        this.asignadoPor = asignadoPor;
        this.activo = activo;
        this.notas = notas;
    }
    
    // Getters and Setters
    public Integer getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public Integer getIdGrupo() {
        return idGrupo;
    }
    
    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }
    
    public Integer getAsignadoPor() {
        return asignadoPor;
    }
    
    public void setAsignadoPor(Integer asignadoPor) {
        this.asignadoPor = asignadoPor;
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

