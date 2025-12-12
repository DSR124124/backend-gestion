package com.nettalco.gestion.backendgestion.modules.Notificaciones.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class NotificacionDTO {
    
    @NotBlank(message = "El título es obligatorio")
    @Size(max = 255, message = "El título no puede exceder 255 caracteres")
    private String titulo;
    
    @NotBlank(message = "El mensaje es obligatorio")
    private String mensaje;
    
    private String tipoNotificacion = "info"; // info, warning, error, success, critical
    
    private String prioridad = "normal"; // baja, normal, alta, urgente
    
    @NotNull(message = "La aplicación es obligatoria")
    private Integer idAplicacion;
    
    @NotNull(message = "El usuario creador es obligatorio")
    private Integer creadoPor;
    
    private LocalDateTime fechaExpiracion;
    
    private LocalDateTime fechaEnvio;
    
    private Boolean requiereConfirmacion = false;
    
    private Boolean mostrarComoRecordatorio = true;
    
    private Boolean activo = true;
    
    private Map<String, Object> datosAdicionales;
    
    // Para asignar a usuarios individuales
    private List<Integer> idUsuarios;
    
    // Para asignar a grupos
    private List<Integer> idGrupos;
    
    // Constructors
    public NotificacionDTO() {
    }
    
    // Getters and Setters
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
    
    public Integer getIdAplicacion() {
        return idAplicacion;
    }
    
    public void setIdAplicacion(Integer idAplicacion) {
        this.idAplicacion = idAplicacion;
    }
    
    public Integer getCreadoPor() {
        return creadoPor;
    }
    
    public void setCreadoPor(Integer creadoPor) {
        this.creadoPor = creadoPor;
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
    
    public List<Integer> getIdUsuarios() {
        return idUsuarios;
    }
    
    public void setIdUsuarios(List<Integer> idUsuarios) {
        this.idUsuarios = idUsuarios;
    }
    
    public List<Integer> getIdGrupos() {
        return idGrupos;
    }
    
    public void setIdGrupos(List<Integer> idGrupos) {
        this.idGrupos = idGrupos;
    }
}

