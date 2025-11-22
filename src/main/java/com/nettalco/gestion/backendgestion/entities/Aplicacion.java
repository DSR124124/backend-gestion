package com.nettalco.gestion.backendgestion.entities;

import com.nettalco.gestion.backendgestion.util.JsonbConverter;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "aplicaciones")
public class Aplicacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aplicacion")
    private Integer idAplicacion;
    
    @Column(name = "nombre_aplicacion", nullable = false, unique = true, length = 255)
    private String nombreAplicacion;
    
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "codigo_producto", unique = true, length = 50)
    private String codigoProducto;
    
    @Column(name = "icono_url", length = 500)
    private String iconoUrl;
    
    @Column(name = "repositorio_url", length = 500)
    private String repositorioUrl;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "responsable_id", foreignKey = @ForeignKey(name = "fk_aplicacion_responsable"))
    private Usuario responsable;
    
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
    
    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    
    @UpdateTimestamp
    @Column(name = "fecha_modificacion", nullable = false)
    private LocalDateTime fechaModificacion;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", columnDefinition = "JSONB")
    @Convert(converter = JsonbConverter.class)
    private Map<String, Object> metadata;
    
    // Constructors
    public Aplicacion() {
    }
    
    public Aplicacion(String nombreAplicacion, String descripcion, String codigoProducto, 
                     String iconoUrl, String repositorioUrl, Usuario responsable, Boolean activo) {
        this.nombreAplicacion = nombreAplicacion;
        this.descripcion = descripcion;
        this.codigoProducto = codigoProducto;
        this.iconoUrl = iconoUrl;
        this.repositorioUrl = repositorioUrl;
        this.responsable = responsable;
        this.activo = activo;
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
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getCodigoProducto() {
        return codigoProducto;
    }
    
    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }
    
    public String getIconoUrl() {
        return iconoUrl;
    }
    
    public void setIconoUrl(String iconoUrl) {
        this.iconoUrl = iconoUrl;
    }
    
    public String getRepositorioUrl() {
        return repositorioUrl;
    }
    
    public void setRepositorioUrl(String repositorioUrl) {
        this.repositorioUrl = repositorioUrl;
    }
    
    public Usuario getResponsable() {
        return responsable;
    }
    
    public void setResponsable(Usuario responsable) {
        this.responsable = responsable;
    }
    
    public Boolean getActivo() {
        return activo;
    }
    
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }
    
    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
    
    public Map<String, Object> getMetadata() {
        return metadata;
    }
    
    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
}

