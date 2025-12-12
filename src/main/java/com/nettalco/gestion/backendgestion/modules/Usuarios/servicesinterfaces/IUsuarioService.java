package com.nettalco.gestion.backendgestion.modules.Usuarios.servicesinterfaces;

import com.nettalco.gestion.backendgestion.modules.Usuarios.dtos.UsuarioResponseDTO;
import com.nettalco.gestion.backendgestion.modules.Usuarios.dtos.UsuarioDTO;

import java.util.List;

public interface IUsuarioService {
    
    UsuarioResponseDTO crear(UsuarioDTO usuarioDTO);
    
    UsuarioResponseDTO actualizar(Integer id, UsuarioDTO usuarioDTO);
    
    void eliminar(Integer id);
    
    List<UsuarioResponseDTO> listar();
    
    UsuarioResponseDTO listarPorId(Integer id);
    
    List<UsuarioResponseDTO> listarPorRol(Integer idRol);
    
    List<UsuarioResponseDTO> listarPorNombreRol(String nombreRol);
}

