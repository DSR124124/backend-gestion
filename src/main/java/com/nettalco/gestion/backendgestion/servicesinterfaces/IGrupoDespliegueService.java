package com.nettalco.gestion.backendgestion.servicesinterfaces;

import com.nettalco.gestion.backendgestion.dtos.GrupoDespliegueDTO;
import com.nettalco.gestion.backendgestion.dtos.GrupoDespliegueResponseDTO;

import java.util.List;

public interface IGrupoDespliegueService {
    
    GrupoDespliegueResponseDTO crear(GrupoDespliegueDTO grupoDespliegueDTO);
    
    GrupoDespliegueResponseDTO actualizar(Integer id, GrupoDespliegueDTO grupoDespliegueDTO);
    
    void eliminar(Integer id);
    
    List<GrupoDespliegueResponseDTO> listar();
    
    GrupoDespliegueResponseDTO listarPorId(Integer id);
}

