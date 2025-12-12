package com.nettalco.gestion.backendgestion.modules.Lanzamientos.servicesinterfaces;

import com.nettalco.gestion.backendgestion.modules.Lanzamientos.dtos.EstadisticaLanzamientoDTO;

import java.util.List;

public interface IEstadisticaLanzamientoService {
    
    List<EstadisticaLanzamientoDTO> listarTodas();
    
    List<EstadisticaLanzamientoDTO> listarPorAplicacion(Integer idAplicacion);
}

