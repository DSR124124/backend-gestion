package com.nettalco.gestion.backendgestion.servicesinterfaces;

import com.nettalco.gestion.backendgestion.dtos.UsuarioLanzamientoDisponibleDTO;

import java.util.List;

public interface IUsuarioLanzamientoDisponibleService {
    
    List<UsuarioLanzamientoDisponibleDTO> listarTodos();
    
    List<UsuarioLanzamientoDisponibleDTO> listarPorUsuario(Integer idUsuario);
    
    List<UsuarioLanzamientoDisponibleDTO> listarPorAplicacion(Integer idAplicacion);
    
    List<UsuarioLanzamientoDisponibleDTO> listarPorGrupo(Integer idGrupo);
}

