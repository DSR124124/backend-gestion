package com.nettalco.gestion.backendgestion.modules.Notificaciones.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import com.nettalco.gestion.backendgestion.core.util.JsonbConverter;
import com.nettalco.gestion.backendgestion.modules.Aplicaciones.entities.Aplicacion;
import com.nettalco.gestion.backendgestion.modules.Usuarios.entities.Usuario;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "notificaciones")
public class Notificacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacion")
    private Integer idNotificacion;
    
    @Column(name = "titulo", nullable = false, length = 255)
    private String titulo;
    
    @Column(name = "mensaje", nullable = false, columnDefinition = "TEXT")
    private String mensaje;
    
    @Column(name = "tipo_notificacion", nullable = false, length = 50)
    private String tipoNotificacion = "info"; // info, warning, error, success, critical
    
    @Column(name = "prioridad", nullable = false, length = 20)
    private String prioridad = "normal"; // baja, normal, alta, urgente
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_aplicacion", nullable = false, foreignKey = @ForeignKey(name = "fk_notificacion_aplicacion"))
    private Aplicacion aplicacion;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creado_por", nullable = false, foreignKey = @ForeignKey(name = "fk_notificacion_creador"))
    private Usuario creadoPor;
    
    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_expiracion")
    private LocalDateTime fechaExpiracion;
    
    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;
    
    @Column(name = "requiere_confirmacion", nullable = false)
    private Boolean requiereConfirmacion = false;
    
    @Column(name = "mostrar_como_recordatorio", nullable = false)
    private Boolean mostrarComoRecordatorio = true;
    
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "datos_adicionales", columnDefinition = "JSONB")
    @Convert(converter = JsonbConverter.class)
    private Map<String, Object> datosAdicionales;
    
    @UpdateTimestamp
    @Column(name = "fecha_modificacion", nullable = false)
    private LocalDateTime fechaModificacion;
    
    // Constructors
    public Notificacion() {
    }
    
    public Notificacion(String titulo, String mensaje, String tipoNotificacion, String prioridad,
                       Aplicacion aplicacion, Usuario creadoPor, Boolean requiereConfirmacion,
                       Boolean mostrarComoRecordatorio, Boolean activo, Map<String, Object> datosAdicionales) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.tipoNotificacion = tipoNotificacion;
        this.prioridad = prioridad;
        this.aplicacion = aplicacion;
        this.creadoPor = creadoPor;
        this.requiereConfirmacion = requiereConfirmacion;
        this.mostrarComoRecordatorio = mostrarComoRecordatorio;
        this.activo = activo;
        this.datosAdicionales = datosAdicionales;
    }
    
    // Getters and Setters
    public Integer getIdNotificacion() {
        return idNotificacion;
    }
    
    public void setIdNotificacion(Integer idNotificacion) {
        this.idNotificacion = idNotificacion;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getMensaje() {
        return mensaje;
    }
    
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public String getTipoNotificacion() {
        return tipoNotificacion;
    }
    
    public void setTipoNotificacion(String tipoNotificacion) {
        this.tipoNotificacion = tipoNotificacion;
    }
    
    public String getPrioridad() {
        return prioridad;
    }
    
    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }
    
    public Aplicacion getAplicacion() {
        return aplicacion;
    }
    
    public void setAplicacion(Aplicacion aplicacion) {
        this.aplicacion = aplicacion;
    }
    
    public Usuario getCreadoPor() {
        return creadoPor;
    }
    
    public void setCreadoPor(Usuario creadoPor) {
        this.creadoPor = creadoPor;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public LocalDateTime getFechaExpiracion() {
        return fechaExpiracion;
    }
    
    public void setFechaExpiracion(LocalDateTime fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }
    
    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }
    
    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }
    
    public Boolean getRequiereConfirmacion() {
        return requiereConfirmacion;
    }
    
    public void setRequiereConfirmacion(Boolean requiereConfirmacion) {
        this.requiereConfirmacion = requiereConfirmacion;
    }
    
    public Boolean getMostrarComoRecordatorio() {
        return mostrarComoRecordatorio;
    }
    
    public void setMostrarComoRecordatorio(Boolean mostrarComoRecordatorio) {
        this.mostrarComoRecordatorio = mostrarComoRecordatorio;
    }
    
    public Boolean getActivo() {
        return activo;
    }
    
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    
    public Map<String, Object> getDatosAdicionales() {
        return datosAdicionales;
    }
    
    public void setDatosAdicionales(Map<String, Object> datosAdicionales) {
        this.datosAdicionales = datosAdicionales;
    }
    
    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }
    
    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
}

