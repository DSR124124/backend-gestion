package com.nettalco.gestion.backendgestion.modules.Notificaciones.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import com.nettalco.gestion.backendgestion.modules.Grupos.entities.GrupoDespliegue;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificacion_grupos",
       uniqueConstraints = @UniqueConstraint(name = "uk_notificacion_grupo",
                                            columnNames = {"id_notificacion", "id_grupo"}))
public class NotificacionGrupo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacion_grupo")
    private Integer idNotificacionGrupo;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_notificacion", nullable = false, foreignKey = @ForeignKey(name = "fk_ng_notificacion"))
    private Notificacion notificacion;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_grupo", nullable = false, foreignKey = @ForeignKey(name = "fk_ng_grupo"))
    private GrupoDespliegue grupo;
    
    @CreationTimestamp
    @Column(name = "fecha_asignacion", nullable = false, updatable = false)
    private LocalDateTime fechaAsignacion;
    
    // Constructors
    public NotificacionGrupo() {
    }
    
    public NotificacionGrupo(Notificacion notificacion, GrupoDespliegue grupo) {
        this.notificacion = notificacion;
        this.grupo = grupo;
    }
    
    // Getters and Setters
    public Integer getIdNotificacionGrupo() {
        return idNotificacionGrupo;
    }
    
    public void setIdNotificacionGrupo(Integer idNotificacionGrupo) {
        this.idNotificacionGrupo = idNotificacionGrupo;
    }
    
    public Notificacion getNotificacion() {
        return notificacion;
    }
    
    public void setNotificacion(Notificacion notificacion) {
        this.notificacion = notificacion;
    }
    
    public GrupoDespliegue getGrupo() {
        return grupo;
    }
    
    public void setGrupo(GrupoDespliegue grupo) {
        this.grupo = grupo;
    }
    
    public LocalDateTime getFechaAsignacion() {
        return fechaAsignacion;
    }
    
    public void setFechaAsignacion(LocalDateTime fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }
}

