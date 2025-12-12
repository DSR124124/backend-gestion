package com.nettalco.gestion.backendgestion.modules.Usuarios.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Map;

public class UsuarioDTO {
    
    @NotBlank(message = "El username es obligatorio")
    @Size(max = 100, message = "El username no puede exceder 100 caracteres")
    private String username;
    
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato válido")
    @Size(max = 255, message = "El email no puede exceder 255 caracteres")
    private String email;
    
    // Password es opcional (solo se valida si se proporciona)
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;
    
    @Size(max = 255, message = "El nombre completo no puede exceder 255 caracteres")
    private String nombreCompleto;
    
    @NotNull(message = "El rol es obligatorio")
    private Integer idRol;
    
    private Boolean activo;
    
    private Map<String, Object> metadata;
    
    // Constructors
    public UsuarioDTO() {
    }
    
    public UsuarioDTO(String username, String email, String password, String nombreCompleto, Integer idRol, Boolean activo) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nombreCompleto = nombreCompleto;
        this.idRol = idRol;
        this.activo = activo;
    }
    
    // Getters and Setters
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
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
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
    
    public Boolean getActivo() {
        return activo;
    }
    
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    
    public Map<String, Object> getMetadata() {
        return metadata;
    }
    
    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
}

