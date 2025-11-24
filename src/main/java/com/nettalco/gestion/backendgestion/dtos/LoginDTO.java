package com.nettalco.gestion.backendgestion.dtos;

import jakarta.validation.constraints.NotBlank;

public class LoginDTO {
    
    @NotBlank(message = "El username o email es obligatorio")
    private String usernameOrEmail;
    
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
    
    // Opcional: solo requerido para aplicaciones móviles/externas
    // Si es null o vacío, se asume login desde el sistema web de gestión
    private String appCode;
    
    // Constructors
    public LoginDTO() {
    }
    
    public LoginDTO(String usernameOrEmail, String password, String appCode) {
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
        this.appCode = appCode;
    }
    
    // Getters and Setters
    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }
    
    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getAppCode() {
        return appCode;
    }
    
    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }
}

