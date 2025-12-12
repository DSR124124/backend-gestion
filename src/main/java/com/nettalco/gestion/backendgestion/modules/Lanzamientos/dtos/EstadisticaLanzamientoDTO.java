package com.nettalco.gestion.backendgestion.modules.Lanzamientos.dtos;

import java.time.LocalDateTime;

public class EstadisticaLanzamientoDTO {
    
    private Integer idAplicacion;
    private String nombreAplicacion;
    private Integer idLanzamiento;
    private String version;
    private String estado;
    private LocalDateTime fechaLanzamiento;
    private Long usuariosConAcceso;
    private Long gruposAsignados;
    private Boolean esCritico;
    
    // Constructors
    public EstadisticaLanzamientoDTO() {
    }
    
    public EstadisticaLanzamientoDTO(Integer idAplicacion, String nombreAplicacion, Integer idLanzamiento, 
                                    String version, String estado, LocalDateTime fechaLanzamiento, 
                                    Long usuariosConAcceso, Long gruposAsignados, Boolean esCritico) {
        this.idAplicacion = idAplicacion;
        this.nombreAplicacion = nombreAplicacion;
        this.idLanzamiento = idLanzamiento;
        this.version = version;
        this.estado = estado;
        this.fechaLanzamiento = fechaLanzamiento;
        this.usuariosConAcceso = usuariosConAcceso;
        this.gruposAsignados = gruposAsignados;
        this.esCritico = esCritico;
    }
    
    // Getters and Setters
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
    
    public Long getUsuariosConAcceso() {
        return usuariosConAcceso;
    }
    
    public void setUsuariosConAcceso(Long usuariosConAcceso) {
        this.usuariosConAcceso = usuariosConAcceso;
    }
    
    public Long getGruposAsignados() {
        return gruposAsignados;
    }
    
    public void setGruposAsignados(Long gruposAsignados) {
        this.gruposAsignados = gruposAsignados;
    }
    
    public Boolean getEsCritico() {
        return esCritico;
    }
    
    public void setEsCritico(Boolean esCritico) {
        this.esCritico = esCritico;
    }
}

