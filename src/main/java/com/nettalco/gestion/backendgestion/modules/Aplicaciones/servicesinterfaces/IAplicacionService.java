package com.nettalco.gestion.backendgestion.modules.Aplicaciones.servicesinterfaces;

import com.nettalco.gestion.backendgestion.modules.Aplicaciones.dtos.AplicacionDTO;
import com.nettalco.gestion.backendgestion.modules.Aplicaciones.dtos.AplicacionResponseDTO;

import java.util.List;

public interface IAplicacionService {
    
    AplicacionResponseDTO crear(AplicacionDTO aplicacionDTO);
    
    AplicacionResponseDTO actualizar(Integer id, AplicacionDTO aplicacionDTO);
    
    void eliminar(Integer id);
    
    List<AplicacionResponseDTO> listar();
    
    AplicacionResponseDTO listarPorId(Integer id);
    
    AplicacionResponseDTO listarPorCodigo(String codigo);
}

