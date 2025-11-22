package com.nettalco.gestion.backendgestion.servicesinterfaces;

import com.nettalco.gestion.backendgestion.dtos.UsuarioDTO;
import com.nettalco.gestion.backendgestion.dtos.UsuarioResponseDTO;

import java.util.List;

public interface IUsuarioService {
    
    UsuarioResponseDTO crear(UsuarioDTO usuarioDTO);
    
    UsuarioResponseDTO actualizar(Integer id, UsuarioDTO usuarioDTO);
    
    void eliminar(Integer id);
    
    List<UsuarioResponseDTO> listar();
    
    UsuarioResponseDTO listarPorId(Integer id);
}

