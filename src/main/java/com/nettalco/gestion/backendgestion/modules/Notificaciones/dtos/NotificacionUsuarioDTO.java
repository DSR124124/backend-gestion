package com.nettalco.gestion.backendgestion.modules.Notificaciones.dtos;

import java.time.LocalDateTime;
import java.util.Map;

public class NotificacionUsuarioDTO {
    
    private Integer idNotificacion;
    private String titulo;
    private String mensaje;
    private String tipoNotificacion;
    private String prioridad;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaExpiracion;
    private Boolean requiereConfirmacion;
    private Map<String, Object> datosAdicionales;
    private String nombreAplicacion;
    private Boolean leida;
    private LocalDateTime fechaLectura;
    private Boolean confirmada;
    private LocalDateTime fechaConfirmacion;
    
    // Constructors
    public NotificacionUsuarioDTO() {
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
    
    public Boolean getRequiereConfirmacion() {
        return requiereConfirmacion;
    }
    
    public void setRequiereConfirmacion(Boolean requiereConfirmacion) {
        this.requiereConfirmacion = requiereConfirmacion;
    }
    
    public Map<String, Object> getDatosAdicionales() {
        return datosAdicionales;
    }
    
    public void setDatosAdicionales(Map<String, Object> datosAdicionales) {
        this.datosAdicionales = datosAdicionales;
    }
    
    public String getNombreAplicacion() {
        return nombreAplicacion;
    }
    
    public void setNombreAplicacion(String nombreAplicacion) {
        this.nombreAplicacion = nombreAplicacion;
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
}

