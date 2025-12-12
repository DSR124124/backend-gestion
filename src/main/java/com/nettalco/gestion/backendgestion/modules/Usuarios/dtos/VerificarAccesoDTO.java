package com.nettalco.gestion.backendgestion.modules.Usuarios.dtos;

public class VerificarAccesoDTO {
    
    private Integer idUsuario;
    private Integer idLanzamiento;
    private Boolean tieneAcceso;
    
    // Constructors
    public VerificarAccesoDTO() {
    }
    
    public VerificarAccesoDTO(Integer idUsuario, Integer idLanzamiento, Boolean tieneAcceso) {
        this.idUsuario = idUsuario;
        this.idLanzamiento = idLanzamiento;
        this.tieneAcceso = tieneAcceso;
    }
    
    // Getters and Setters
    public Integer getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public Integer getIdLanzamiento() {
        return idLanzamiento;
    }
    
    public void setIdLanzamiento(Integer idLanzamiento) {
        this.idLanzamiento = idLanzamiento;
    }
    
    public Boolean getTieneAcceso() {
        return tieneAcceso;
    }
    
    public void setTieneAcceso(Boolean tieneAcceso) {
        this.tieneAcceso = tieneAcceso;
    }
}

