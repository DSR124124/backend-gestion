package com.nettalco.gestion.backendgestion.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

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
    
    @Column(name = "repositorio_url", length = 500)
    private String repositorioUrl;
    
    @Column(name = "url", length = 500)
    private String url;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "responsable_id", foreignKey = @ForeignKey(name = "fk_aplicacion_responsable"))
    private Usuario responsable;
    
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
    
    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_modificacion", nullable = false, insertable = false, updatable = false)
    private LocalDateTime fechaModificacion;
    
    // Constructors
    public Aplicacion() {
    }
    
    public Aplicacion(String nombreAplicacion, String descripcion, String codigoProducto, 
                     String repositorioUrl, Usuario responsable, Boolean activo) {
        this.nombreAplicacion = nombreAplicacion;
        this.descripcion = descripcion;
        this.codigoProducto = codigoProducto;
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
    
    public String getRepositorioUrl() {
        return repositorioUrl;
    }
    
    public void setRepositorioUrl(String repositorioUrl) {
        this.repositorioUrl = repositorioUrl;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
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
}

