package com.nettalco.gestion.backendgestion.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "lanzamiento_grupo",
       uniqueConstraints = @UniqueConstraint(name = "uk_lanzamiento_grupo", columnNames = {"id_lanzamiento", "id_grupo"}))
public class LanzamientoGrupo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lanzamiento_grupo")
    private Integer idLanzamientoGrupo;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_lanzamiento", nullable = false, foreignKey = @ForeignKey(name = "fk_lg_lanzamiento"))
    private Lanzamiento lanzamiento;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_grupo", nullable = false, foreignKey = @ForeignKey(name = "fk_lg_grupo"))
    private GrupoDespliegue grupoDespliegue;
    
    @CreationTimestamp
    @Column(name = "fecha_asignacion", nullable = false, updatable = false)
    private LocalDateTime fechaAsignacion;
    
    @Column(name = "fecha_disponibilidad")
    private LocalDateTime fechaDisponibilidad;
    
    @Column(name = "fecha_fin_disponibilidad")
    private LocalDateTime fechaFinDisponibilidad;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "asignado_por", foreignKey = @ForeignKey(name = "fk_lg_asignador"))
    private Usuario asignadoPor;
    
    @Column(name = "notificacion_enviada", nullable = false)
    private Boolean notificacionEnviada = false;
    
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
    
    @Column(name = "notas", columnDefinition = "TEXT")
    private String notas;
    
    // Constructors
    public LanzamientoGrupo() {
    }
    
    public LanzamientoGrupo(Lanzamiento lanzamiento, GrupoDespliegue grupoDespliegue, 
                           LocalDateTime fechaDisponibilidad, LocalDateTime fechaFinDisponibilidad, 
                           Usuario asignadoPor, Boolean notificacionEnviada, Boolean activo, String notas) {
        this.lanzamiento = lanzamiento;
        this.grupoDespliegue = grupoDespliegue;
        this.fechaDisponibilidad = fechaDisponibilidad;
        this.fechaFinDisponibilidad = fechaFinDisponibilidad;
        this.asignadoPor = asignadoPor;
        this.notificacionEnviada = notificacionEnviada;
        this.activo = activo;
        this.notas = notas;
    }
    
    // Getters and Setters
    public Integer getIdLanzamientoGrupo() {
        return idLanzamientoGrupo;
    }
    
    public void setIdLanzamientoGrupo(Integer idLanzamientoGrupo) {
        this.idLanzamientoGrupo = idLanzamientoGrupo;
    }
    
    public Lanzamiento getLanzamiento() {
        return lanzamiento;
    }
    
    public void setLanzamiento(Lanzamiento lanzamiento) {
        this.lanzamiento = lanzamiento;
    }
    
    public GrupoDespliegue getGrupoDespliegue() {
        return grupoDespliegue;
    }
    
    public void setGrupoDespliegue(GrupoDespliegue grupoDespliegue) {
        this.grupoDespliegue = grupoDespliegue;
    }
    
    public LocalDateTime getFechaAsignacion() {
        return fechaAsignacion;
    }
    
    public void setFechaAsignacion(LocalDateTime fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }
    
    public LocalDateTime getFechaDisponibilidad() {
        return fechaDisponibilidad;
    }
    
    public void setFechaDisponibilidad(LocalDateTime fechaDisponibilidad) {
        this.fechaDisponibilidad = fechaDisponibilidad;
    }
    
    public LocalDateTime getFechaFinDisponibilidad() {
        return fechaFinDisponibilidad;
    }
    
    public void setFechaFinDisponibilidad(LocalDateTime fechaFinDisponibilidad) {
        this.fechaFinDisponibilidad = fechaFinDisponibilidad;
    }
    
    public Usuario getAsignadoPor() {
        return asignadoPor;
    }
    
    public void setAsignadoPor(Usuario asignadoPor) {
        this.asignadoPor = asignadoPor;
    }
    
    public Boolean getNotificacionEnviada() {
        return notificacionEnviada;
    }
    
    public void setNotificacionEnviada(Boolean notificacionEnviada) {
        this.notificacionEnviada = notificacionEnviada;
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

