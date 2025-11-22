package com.nettalco.gestion.backendgestion.servicesinterfaces;

import com.nettalco.gestion.backendgestion.dtos.LoginDTO;
import com.nettalco.gestion.backendgestion.dtos.LoginResponseDTO;

public interface IAuthenticationService {
    
    LoginResponseDTO login(LoginDTO loginDTO);
}

