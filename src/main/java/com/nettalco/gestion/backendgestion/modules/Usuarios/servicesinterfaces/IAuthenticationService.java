package com.nettalco.gestion.backendgestion.modules.Usuarios.servicesinterfaces;

import com.nettalco.gestion.backendgestion.modules.Usuarios.dtos.LoginDTO;
import com.nettalco.gestion.backendgestion.modules.Usuarios.dtos.LoginResponseDTO;

public interface IAuthenticationService {
    
    LoginResponseDTO login(LoginDTO loginDTO);
}

