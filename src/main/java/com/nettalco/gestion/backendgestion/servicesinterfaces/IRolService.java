package com.nettalco.gestion.backendgestion.servicesinterfaces;

import com.nettalco.gestion.backendgestion.dtos.RolDTO;
import com.nettalco.gestion.backendgestion.dtos.RolResponseDTO;

import java.util.List;

public interface IRolService {
    
    RolResponseDTO crear(RolDTO rolDTO);
    
    RolResponseDTO actualizar(Integer id, RolDTO rolDTO);
    
    void eliminar(Integer id);
    
    List<RolResponseDTO> listar();
    
    RolResponseDTO listarPorId(Integer id);
}

