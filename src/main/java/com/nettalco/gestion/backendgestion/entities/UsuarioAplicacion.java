package com.nettalco.gestion.backendgestion.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuario_aplicacion", 
       uniqueConstraints = @UniqueConstraint(name = "uk_usuario_aplicacion", columnNames = {"id_usuario", "id_aplicacion"}))
public class UsuarioAplicacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario_aplicacion")
    private Integer idUsuarioAplicacion;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", nullable = false, foreignKey = @ForeignKey(name = "fk_ua_usuario"))
    private Usuario usuario;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_aplicacion", nullable = false, foreignKey = @ForeignKey(name = "fk_ua_aplicacion"))
    private Aplicacion aplicacion;
    
    @CreationTimestamp
    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;
    
    @Column(name = "fecha_ultimo_acceso")
    private LocalDateTime fechaUltimoAcceso;
    
    @Column(name = "licencia_activa", nullable = false)
    private Boolean licenciaActiva = true;
    
    @Column(name = "fecha_expiracion_licencia")
    private LocalDateTime fechaExpiracionLicencia;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "registrado_por", foreignKey = @ForeignKey(name = "fk_ua_registrador"))
    private Usuario registradoPor;
    
    @Column(name = "notas", columnDefinition = "TEXT")
    private String notas;
    
    // Constructors
    public UsuarioAplicacion() {
    }
    
    public UsuarioAplicacion(Usuario usuario, Aplicacion aplicacion, Boolean licenciaActiva, 
                            LocalDateTime fechaExpiracionLicencia, Usuario registradoPor, String notas) {
        this.usuario = usuario;
        this.aplicacion = aplicacion;
        this.licenciaActiva = licenciaActiva;
        this.fechaExpiracionLicencia = fechaExpiracionLicencia;
        this.registradoPor = registradoPor;
        this.notas = notas;
    }
    
    // Getters and Setters
    public Integer getIdUsuarioAplicacion() {
        return idUsuarioAplicacion;
    }
    
    public void setIdUsuarioAplicacion(Integer idUsuarioAplicacion) {
        this.idUsuarioAplicacion = idUsuarioAplicacion;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public Aplicacion getAplicacion() {
        return aplicacion;
    }
    
    public void setAplicacion(Aplicacion aplicacion) {
        this.aplicacion = aplicacion;
    }
    
    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    public LocalDateTime getFechaUltimoAcceso() {
        return fechaUltimoAcceso;
    }
    
    public void setFechaUltimoAcceso(LocalDateTime fechaUltimoAcceso) {
        this.fechaUltimoAcceso = fechaUltimoAcceso;
    }
    
    public Boolean getLicenciaActiva() {
        return licenciaActiva;
    }
    
    public void setLicenciaActiva(Boolean licenciaActiva) {
        this.licenciaActiva = licenciaActiva;
    }
    
    public LocalDateTime getFechaExpiracionLicencia() {
        return fechaExpiracionLicencia;
    }
    
    public void setFechaExpiracionLicencia(LocalDateTime fechaExpiracionLicencia) {
        this.fechaExpiracionLicencia = fechaExpiracionLicencia;
    }
    
    public Usuario getRegistradoPor() {
        return registradoPor;
    }
    
    public void setRegistradoPor(Usuario registradoPor) {
        this.registradoPor = registradoPor;
    }
    
    public String getNotas() {
        return notas;
    }
    
    public void setNotas(String notas) {
        this.notas = notas;
    }
}

