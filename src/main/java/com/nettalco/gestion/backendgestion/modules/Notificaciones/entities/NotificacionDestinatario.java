package com.nettalco.gestion.backendgestion.modules.Notificaciones.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import com.nettalco.gestion.backendgestion.modules.Usuarios.entities.Usuario;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificacion_destinatarios", 
       uniqueConstraints = @UniqueConstraint(name = "uk_notificacion_usuario", 
                                            columnNames = {"id_notificacion", "id_usuario"}))
public class NotificacionDestinatario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacion_destinatario")
    private Integer idNotificacionDestinatario;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_notificacion", nullable = false, foreignKey = @ForeignKey(name = "fk_nd_notificacion"))
    private Notificacion notificacion;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", nullable = false, foreignKey = @ForeignKey(name = "fk_nd_usuario"))
    private Usuario usuario;
    
    @Column(name = "leida", nullable = false)
    private Boolean leida = false;
    
    @Column(name = "fecha_lectura")
    private LocalDateTime fechaLectura;
    
    @Column(name = "confirmada", nullable = false)
    private Boolean confirmada = false;
    
    @Column(name = "fecha_confirmacion")
    private LocalDateTime fechaConfirmacion;
    
    @CreationTimestamp
    @Column(name = "fecha_asignacion", nullable = false, updatable = false)
    private LocalDateTime fechaAsignacion;
    
    // Constructors
    public NotificacionDestinatario() {
    }
    
    public NotificacionDestinatario(Notificacion notificacion, Usuario usuario) {
        this.notificacion = notificacion;
        this.usuario = usuario;
    }
    
    // Getters and Setters
    public Integer getIdNotificacionDestinatario() {
        return idNotificacionDestinatario;
    }
    
    public void setIdNotificacionDestinatario(Integer idNotificacionDestinatario) {
        this.idNotificacionDestinatario = idNotificacionDestinatario;
    }
    
    public Notificacion getNotificacion() {
        return notificacion;
    }
    
    public void setNotificacion(Notificacion notificacion) {
        this.notificacion = notificacion;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public Boolean getLeida() {
        return leida;
    }
    
    public void setLeida(Boolean leida) {
        this.leida = leida;
    }
    
    public LocalDateTime getFechaLectura() {
        return fechaLectura;
    }
    
    public void setFechaLectura(LocalDateTime fechaLectura) {
        this.fechaLectura = fechaLectura;
    }
    
    public Boolean getConfirmada() {
        return confirmada;
    }
    
    public void setConfirmada(Boolean confirmada) {
        this.confirmada = confirmada;
    }
    
    public LocalDateTime getFechaConfirmacion() {
        return fechaConfirmacion;
    }
    
    public void setFechaConfirmacion(LocalDateTime fechaConfirmacion) {
        this.fechaConfirmacion = fechaConfirmacion;
    }
    
    public LocalDateTime getFechaAsignacion() {
        return fechaAsignacion;
    }
    
    public void setFechaAsignacion(LocalDateTime fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }
}

