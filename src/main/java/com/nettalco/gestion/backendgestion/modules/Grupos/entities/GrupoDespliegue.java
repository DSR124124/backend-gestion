package com.nettalco.gestion.backendgestion.modules.Grupos.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.nettalco.gestion.backendgestion.core.util.JsonbConverter;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "grupos_despliegue")
public class GrupoDespliegue {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grupo")
    private Integer idGrupo;
    
    @Column(name = "nombre_grupo", nullable = false, unique = true, length = 100)
    private String nombreGrupo;
    
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "orden_prioridad")
    private Integer ordenPrioridad;
    
    @Column(name = "porcentaje_usuarios")
    private Integer porcentajeUsuarios;
    
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
    
    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", columnDefinition = "JSONB")
    @Convert(converter = JsonbConverter.class)
    private Map<String, Object> metadata;
    
    // Constructors
    public GrupoDespliegue() {
    }
    
    public GrupoDespliegue(String nombreGrupo, String descripcion, Integer ordenPrioridad, 
                          Integer porcentajeUsuarios, Boolean activo) {
        this.nombreGrupo = nombreGrupo;
        this.descripcion = descripcion;
        this.ordenPrioridad = ordenPrioridad;
        this.porcentajeUsuarios = porcentajeUsuarios;
        this.activo = activo;
    }
    
    // Getters and Setters
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
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Integer getOrdenPrioridad() {
        return ordenPrioridad;
    }
    
    public void setOrdenPrioridad(Integer ordenPrioridad) {
        this.ordenPrioridad = ordenPrioridad;
    }
    
    public Integer getPorcentajeUsuarios() {
        return porcentajeUsuarios;
    }
    
    public void setPorcentajeUsuarios(Integer porcentajeUsuarios) {
        this.porcentajeUsuarios = porcentajeUsuarios;
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
    
    public Map<String, Object> getMetadata() {
        return metadata;
    }
    
    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
}

