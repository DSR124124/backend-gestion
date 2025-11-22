package com.nettalco.gestion.backendgestion.servicesinterfaces;

import com.nettalco.gestion.backendgestion.dtos.VerificarAccesoDTO;

public interface IVerificarAccesoService {
    
    VerificarAccesoDTO verificarAcceso(Integer idUsuario, Integer idLanzamiento);
}

