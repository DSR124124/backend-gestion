package com.nettalco.gestion.backendgestion.modules.Usuarios.servicesinterfaces;

import com.nettalco.gestion.backendgestion.modules.Usuarios.dtos.VerificarAccesoDTO;

public interface IVerificarAccesoService {
    
    VerificarAccesoDTO verificarAcceso(Integer idUsuario, Integer idLanzamiento);
}

