package com.nettalco.gestion.backendgestion.modules.Notificaciones.dtos;

import java.time.LocalDateTime;
import java.util.Map;

public class NotificacionResponseDTO {
    
    private Integer idNotificacion;
    private String titulo;
    private String mensaje;
    private String tipoNotificacion;
    private String prioridad;
    private Integer idAplicacion;
    private String nombreAplicacion;
    private Integer creadoPor;
    private String creadorNombre;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaEnvio;
    private Boolean requiereConfirmacion;
    private Boolean mostrarComoRecordatorio;
    private Boolean activo;
    private Map<String, Object> datosAdicionales;
    private LocalDateTime fechaModificacion;
    
    // Estadísticas opcionales
    private Long totalDestinatarios;
    private Long totalLeidas;
    private Long totalConfirmadas;
    
    // Constructors
    public NotificacionResponseDTO() {
    }
    
    public NotificacionResponseDTO(Integer idNotificacion, String titulo, String mensaje, 
                                  String tipoNotificacion, String prioridad, Integer idAplicacion,
                                  String nombreAplicacion, Integer creadoPor, String creadorNombre,
                                  LocalDateTime fechaCreacion,
                                  LocalDateTime fechaEnvio, Boolean requiereConfirmacion,
                                  Boolean mostrarComoRecordatorio, Boolean activo,
                                  Map<String, Object> datosAdicionales, LocalDateTime fechaModificacion) {
        this.idNotificacion = idNotificacion;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.tipoNotificacion = tipoNotificacion;
        this.prioridad = prioridad;
        this.idAplicacion = idAplicacion;
        this.nombreAplicacion = nombreAplicacion;
        this.creadoPor = creadoPor;
        this.creadorNombre = creadorNombre;
        this.fechaCreacion = fechaCreacion;
        this.fechaEnvio = fechaEnvio;
        this.requiereConfirmacion = requiereConfirmacion;
        this.mostrarComoRecordatorio = mostrarComoRecordatorio;
        this.activo = activo;
        this.datosAdicionales = datosAdicionales;
        this.fechaModificacion = fechaModificacion;
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
    
    public Integer getCreadoPor() {
        return creadoPor;
    }
    
    public void setCreadoPor(Integer creadoPor) {
        this.creadoPor = creadoPor;
    }
    
    public String getCreadorNombre() {
        return creadorNombre;
    }
    
    public void setCreadorNombre(String creadorNombre) {
        this.creadorNombre = creadorNombre;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
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
    
    public Long getTotalDestinatarios() {
        return totalDestinatarios;
    }
    
    public void setTotalDestinatarios(Long totalDestinatarios) {
        this.totalDestinatarios = totalDestinatarios;
    }
    
    public Long getTotalLeidas() {
        return totalLeidas;
    }
    
    public void setTotalLeidas(Long totalLeidas) {
        this.totalLeidas = totalLeidas;
    }
    
    public Long getTotalConfirmadas() {
        return totalConfirmadas;
    }
    
    public void setTotalConfirmadas(Long totalConfirmadas) {
        this.totalConfirmadas = totalConfirmadas;
    }
}

