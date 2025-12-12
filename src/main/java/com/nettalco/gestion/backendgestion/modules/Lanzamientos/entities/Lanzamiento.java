package com.nettalco.gestion.backendgestion.modules.Lanzamientos.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.nettalco.gestion.backendgestion.core.util.JsonbConverter;
import com.nettalco.gestion.backendgestion.modules.Aplicaciones.entities.Aplicacion;
import com.nettalco.gestion.backendgestion.modules.Usuarios.entities.Usuario;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "lanzamientos")
public class Lanzamiento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lanzamiento")
    private Integer idLanzamiento;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_aplicacion", nullable = false, foreignKey = @ForeignKey(name = "fk_lanzamiento_aplicacion"))
    private Aplicacion aplicacion;
    
    @Column(name = "version", nullable = false, length = 50)
    private String version;
    
    @Column(name = "estado", nullable = false, length = 50)
    private String estado = "borrador";
    
    @Column(name = "fecha_lanzamiento")
    private LocalDateTime fechaLanzamiento;
    
    @CreationTimestamp
    @Column(name = "fecha_publicacion", nullable = false, updatable = false)
    private LocalDateTime fechaPublicacion;
    
    @Column(name = "notas_version", nullable = false, columnDefinition = "TEXT")
    private String notasVersion;
    
    @Column(name = "url_descarga", length = 500)
    private String urlDescarga;
    
    @Column(name = "tamano_archivo")
    private Long tamanoArchivo;
    
    @Column(name = "checksum_sha256", length = 64)
    private String checksumSha256;
    
    @Column(name = "es_critico", nullable = false)
    private Boolean esCritico = false;
    
    @Column(name = "requiere_reinicio", nullable = false)
    private Boolean requiereReinicio = false;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "publicado_por", foreignKey = @ForeignKey(name = "fk_lanzamiento_publicador"))
    private Usuario publicadoPor;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", columnDefinition = "JSONB")
    @Convert(converter = JsonbConverter.class)
    private Map<String, Object> metadata;
    
    // Constructors
    public Lanzamiento() {
    }
    
    public Lanzamiento(Aplicacion aplicacion, String version, String estado, 
                      LocalDateTime fechaLanzamiento, String notasVersion, 
                      String urlDescarga, Long tamanoArchivo, String checksumSha256, 
                      Boolean esCritico, Boolean requiereReinicio, Usuario publicadoPor) {
        this.aplicacion = aplicacion;
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
    public Integer getIdLanzamiento() {
        return idLanzamiento;
    }
    
    public void setIdLanzamiento(Integer idLanzamiento) {
        this.idLanzamiento = idLanzamiento;
    }
    
    public Aplicacion getAplicacion() {
        return aplicacion;
    }
    
    public void setAplicacion(Aplicacion aplicacion) {
        this.aplicacion = aplicacion;
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
    
    public Usuario getPublicadoPor() {
        return publicadoPor;
    }
    
    public void setPublicadoPor(Usuario publicadoPor) {
        this.publicadoPor = publicadoPor;
    }
    
    public Map<String, Object> getMetadata() {
        return metadata;
    }
    
    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
}

