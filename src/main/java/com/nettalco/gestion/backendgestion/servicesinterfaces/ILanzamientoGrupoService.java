package com.nettalco.gestion.backendgestion.servicesinterfaces;

import com.nettalco.gestion.backendgestion.dtos.LanzamientoGrupoDTO;
import com.nettalco.gestion.backendgestion.dtos.LanzamientoGrupoResponseDTO;

import java.util.List;

public interface ILanzamientoGrupoService {
    
    LanzamientoGrupoResponseDTO crear(LanzamientoGrupoDTO lanzamientoGrupoDTO);
    
    LanzamientoGrupoResponseDTO actualizar(Integer id, LanzamientoGrupoDTO lanzamientoGrupoDTO);
    
    void eliminar(Integer id);
    
    List<LanzamientoGrupoResponseDTO> listar();
    
    LanzamientoGrupoResponseDTO listarPorId(Integer id);
}

