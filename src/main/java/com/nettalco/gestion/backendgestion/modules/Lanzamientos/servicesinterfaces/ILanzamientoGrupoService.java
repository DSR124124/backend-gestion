package com.nettalco.gestion.backendgestion.modules.Lanzamientos.servicesinterfaces;

import com.nettalco.gestion.backendgestion.modules.Lanzamientos.dtos.LanzamientoGrupoResponseDTO;
import com.nettalco.gestion.backendgestion.modules.Lanzamientos.dtos.LanzamientoGrupoDTO;

import java.util.List;

public interface ILanzamientoGrupoService {
    
    LanzamientoGrupoResponseDTO crear(LanzamientoGrupoDTO lanzamientoGrupoDTO);
    
    LanzamientoGrupoResponseDTO actualizar(Integer id, LanzamientoGrupoDTO lanzamientoGrupoDTO);
    
    void eliminar(Integer id);
    
    List<LanzamientoGrupoResponseDTO> listar();
    
    LanzamientoGrupoResponseDTO listarPorId(Integer id);
}

