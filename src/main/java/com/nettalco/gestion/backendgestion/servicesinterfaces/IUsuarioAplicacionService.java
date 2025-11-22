package com.nettalco.gestion.backendgestion.servicesinterfaces;

import com.nettalco.gestion.backendgestion.dtos.UsuarioAplicacionDTO;
import com.nettalco.gestion.backendgestion.dtos.UsuarioAplicacionResponseDTO;

import java.util.List;

public interface IUsuarioAplicacionService {
    
    UsuarioAplicacionResponseDTO crear(UsuarioAplicacionDTO usuarioAplicacionDTO);
    
    UsuarioAplicacionResponseDTO actualizar(Integer id, UsuarioAplicacionDTO usuarioAplicacionDTO);
    
    void eliminar(Integer id);
    
    List<UsuarioAplicacionResponseDTO> listar();
    
    UsuarioAplicacionResponseDTO listarPorId(Integer id);
}

