package com.nettalco.gestion.backendgestion.modules.Grupos.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import com.nettalco.gestion.backendgestion.modules.Usuarios.entities.Usuario;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuario_grupo",
       uniqueConstraints = @UniqueConstraint(name = "uk_usuario_grupo", columnNames = {"id_usuario", "id_grupo"}))
public class UsuarioGrupo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario_grupo")
    private Integer idUsuarioGrupo;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", nullable = false, foreignKey = @ForeignKey(name = "fk_ug_usuario"))
    private Usuario usuario;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_grupo", nullable = false, foreignKey = @ForeignKey(name = "fk_ug_grupo"))
    private GrupoDespliegue grupoDespliegue;
    
    @CreationTimestamp
    @Column(name = "fecha_asignacion", nullable = false, updatable = false)
    private LocalDateTime fechaAsignacion;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "asignado_por", foreignKey = @ForeignKey(name = "fk_ug_asignador"))
    private Usuario asignadoPor;
    
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
    
    @Column(name = "notas", columnDefinition = "TEXT")
    private String notas;
    
    // Constructors
    public UsuarioGrupo() {
    }
    
    public UsuarioGrupo(Usuario usuario, GrupoDespliegue grupoDespliegue, Usuario asignadoPor, Boolean activo, String notas) {
        this.usuario = usuario;
        this.grupoDespliegue = grupoDespliegue;
        this.asignadoPor = asignadoPor;
        this.activo = activo;
        this.notas = notas;
    }
    
    // Getters and Setters
    public Integer getIdUsuarioGrupo() {
        return idUsuarioGrupo;
    }
    
    public void setIdUsuarioGrupo(Integer idUsuarioGrupo) {
        this.idUsuarioGrupo = idUsuarioGrupo;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
    
    public Usuario getAsignadoPor() {
        return asignadoPor;
    }
    
    public void setAsignadoPor(Usuario asignadoPor) {
        this.asignadoPor = asignadoPor;
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

