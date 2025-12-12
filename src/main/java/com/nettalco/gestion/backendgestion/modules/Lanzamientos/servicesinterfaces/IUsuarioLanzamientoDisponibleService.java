package com.nettalco.gestion.backendgestion.modules.Lanzamientos.servicesinterfaces;

import java.util.List;

import com.nettalco.gestion.backendgestion.modules.Lanzamientos.dtos.UsuarioLanzamientoDisponibleDTO;

public interface IUsuarioLanzamientoDisponibleService {
    
    List<UsuarioLanzamientoDisponibleDTO> listarTodos();
    
    List<UsuarioLanzamientoDisponibleDTO> listarPorUsuario(Integer idUsuario);
    
    List<UsuarioLanzamientoDisponibleDTO> listarPorAplicacion(Integer idAplicacion);
    
    List<UsuarioLanzamientoDisponibleDTO> listarPorGrupo(Integer idGrupo);
}

