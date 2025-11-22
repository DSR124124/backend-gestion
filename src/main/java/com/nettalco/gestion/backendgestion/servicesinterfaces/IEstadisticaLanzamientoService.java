package com.nettalco.gestion.backendgestion.servicesinterfaces;

import com.nettalco.gestion.backendgestion.dtos.EstadisticaLanzamientoDTO;

import java.util.List;

public interface IEstadisticaLanzamientoService {
    
    List<EstadisticaLanzamientoDTO> listarTodas();
    
    List<EstadisticaLanzamientoDTO> listarPorAplicacion(Integer idAplicacion);
}

