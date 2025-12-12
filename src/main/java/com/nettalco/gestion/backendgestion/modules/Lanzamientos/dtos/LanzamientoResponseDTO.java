package com.nettalco.gestion.backendgestion.modules.Lanzamientos.dtos;

import java.time.LocalDateTime;
import java.util.Map;

public class LanzamientoResponseDTO {
    
    private Integer idLanzamiento;
    private Integer idAplicacion;
    private String nombreAplicacion;
    private String version;
    private String estado;
    private LocalDateTime fechaLanzamiento;
    private LocalDateTime fechaPublicacion;
    private String notasVersion;
    private String urlDescarga;
    private Long tamanoArchivo;
    private String checksumSha256;
    private Boolean esCritico;
    private Boolean requiereReinicio;
    private Integer publicadoPor;
    private String publicadoPorNombre;
    private Map<String, Object> metadata;
    
    // Constructors
    public LanzamientoResponseDTO() {
    }
    
    public LanzamientoResponseDTO(Integer idLanzamiento, Integer idAplicacion, String nombreAplicacion, 
                                 String version, String estado, LocalDateTime fechaLanzamiento, 
                                 LocalDateTime fechaPublicacion, String notasVersion, String urlDescarga, 
                                 Long tamanoArchivo, String checksumSha256, Boolean esCritico, 
                                 Boolean requiereReinicio, Integer publicadoPor, String publicadoPorNombre, 
                                 Map<String, Object> metadata) {
        this.idLanzamiento = idLanzamiento;
        this.idAplicacion = idAplicacion;
        this.nombreAplicacion = nombreAplicacion;
        this.version = version;
        this.estado = estado;
        this.fechaLanzamiento = fechaLanzamiento;
        this.fechaPublicacion = fechaPublicacion;
        this.notasVersion = notasVersion;
        this.urlDescarga = urlDescarga;
        this.tamanoArchivo = tamanoArchivo;
        this.checksumSha256 = checksumSha256;
        this.esCritico = esCritico;
        this.requiereReinicio = requiereReinicio;
        this.publicadoPor = publicadoPor;
        this.publicadoPorNombre = publicadoPorNombre;
        this.metadata = metadata;
    }
    
    // Getters and Setters
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
    
    public LocalDateTime getFechaPublicacion() {
        return fechaPublicacion;
    }
    
    public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
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
    
    public String getChecksumSha256() {
        return checksumSha256;
    }
    
    public void setChecksumSha256(String checksumSha256) {
        this.checksumSha256 = checksumSha256;
    }
    
    public Boolean getEsCritico() {
        return esCritico;
    }
    
    public void setEsCritico(Boolean esCritico) {
        this.esCritico = esCritico;
    }
    
    public Boolean getRequiereReinicio() {
        return requiereReinicio;
    }
    
    public void setRequiereReinicio(Boolean requiereReinicio) {
        this.requiereReinicio = requiereReinicio;
    }
    
    public Integer getPublicadoPor() {
        return publicadoPor;
    }
    
    public void setPublicadoPor(Integer publicadoPor) {
        this.publicadoPor = publicadoPor;
    }
    
    public String getPublicadoPorNombre() {
        return publicadoPorNombre;
    }
    
    public void setPublicadoPorNombre(String publicadoPorNombre) {
        this.publicadoPorNombre = publicadoPorNombre;
    }
    
    public Map<String, Object> getMetadata() {
        return metadata;
    }
    
    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
}

