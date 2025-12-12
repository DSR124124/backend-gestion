package com.nettalco.gestion.backendgestion.modules.Lanzamientos.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Map;

public class LanzamientoDTO {
    
    @NotNull(message = "El ID de la aplicación es obligatorio")
    private Integer idAplicacion;
    
    @NotBlank(message = "La versión es obligatoria")
    @Size(max = 50, message = "La versión no puede exceder 50 caracteres")
    private String version;
    
    @Size(max = 50, message = "El estado no puede exceder 50 caracteres")
    private String estado;
    
    private LocalDateTime fechaLanzamiento;
    
    @NotBlank(message = "Las notas de versión son obligatorias")
    private String notasVersion;
    
    @Size(max = 500, message = "La URL de descarga no puede exceder 500 caracteres")
    private String urlDescarga;
    
    private Long tamanoArchivo;
    
    @Size(max = 64, message = "El checksum SHA256 no puede exceder 64 caracteres")
    private String checksumSha256;
    
    private Boolean esCritico;
    
    private Boolean requiereReinicio;
    
    private Integer publicadoPor;
    
    private Map<String, Object> metadata;
    
    // Constructors
    public LanzamientoDTO() {
    }
    
    public LanzamientoDTO(Integer idAplicacion, String version, String estado, 
                         LocalDateTime fechaLanzamiento, String notasVersion, 
                         String urlDescarga, Long tamanoArchivo, String checksumSha256, 
                         Boolean esCritico, Boolean requiereReinicio, Integer publicadoPor) {
        this.idAplicacion = idAplicacion;
        this.version = version;
        this.estado = estado;
        this.fechaLanzamiento = fechaLanzamiento;
        this.notasVersion = notasVersion;
        this.urlDescarga = urlDescarga;
        this.tamanoArchivo = tamanoArchivo;
        this.checksumSha256 = checksumSha256;
        this.esCritico = esCritico;
        this.requiereReinicio = requiereReinicio;
        this.publicadoPor = publicadoPor;
    }
    
    // Getters and Setters
    public Integer getIdAplicacion() {
        return idAplicacion;
    }
    
    public void setIdAplicacion(Integer idAplicacion) {
        this.idAplicacion = idAplicacion;
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
    
    public Map<String, Object> getMetadata() {
        return metadata;
    }
    
    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
}

