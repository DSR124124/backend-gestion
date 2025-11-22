package com.nettalco.gestion.backendgestion.servicesinterfaces;

import com.nettalco.gestion.backendgestion.dtos.LanzamientoDTO;
import com.nettalco.gestion.backendgestion.dtos.LanzamientoResponseDTO;

import java.util.List;

public interface ILanzamientoService {
    
    LanzamientoResponseDTO crear(LanzamientoDTO lanzamientoDTO);
    
    LanzamientoResponseDTO actualizar(Integer id, LanzamientoDTO lanzamientoDTO);
    
    void eliminar(Integer id);
    
    List<LanzamientoResponseDTO> listar();
    
    LanzamientoResponseDTO listarPorId(Integer id);
}

