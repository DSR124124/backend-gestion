package com.nettalco.gestion.backendgestion.dtos;

import java.time.LocalDateTime;

public class LoginResponseDTO {
    
    private String token;
    private String type = "Bearer";
    private Integer idUsuario;
    private String username;
    private String email;
    private String nombreCompleto;
    private Integer idRol;
    private String nombreRol;
    private LocalDateTime fechaUltimoAcceso;
    
    // Constructors
    public LoginResponseDTO() {
    }
    
    public LoginResponseDTO(String token, Integer idUsuario, String username, String email, 
                           String nombreCompleto, Integer idRol, String nombreRol, LocalDateTime fechaUltimoAcceso) {
        this.token = token;
        this.idUsuario = idUsuario;
        this.username = username;
        this.email = email;
        this.nombreCompleto = nombreCompleto;
        this.idRol = idRol;
        this.nombreRol = nombreRol;
        this.fechaUltimoAcceso = fechaUltimoAcceso;
    }
    
    // Getters and Setters
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public Integer getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    
    public Integer getIdRol() {
        return idRol;
    }
    
    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }
    
    public String getNombreRol() {
        return nombreRol;
    }
    
    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }
    
    public LocalDateTime getFechaUltimoAcceso() {
        return fechaUltimoAcceso;
    }
    
    public void setFechaUltimoAcceso(LocalDateTime fechaUltimoAcceso) {
        this.fechaUltimoAcceso = fechaUltimoAcceso;
    }
}

