package com.nettalco.gestion.backendgestion.servicesinterfaces;

import com.nettalco.gestion.backendgestion.dtos.UsuarioGrupoDTO;
import com.nettalco.gestion.backendgestion.dtos.UsuarioGrupoResponseDTO;

import java.util.List;

public interface IUsuarioGrupoService {
    
    UsuarioGrupoResponseDTO crear(UsuarioGrupoDTO usuarioGrupoDTO);
    
    UsuarioGrupoResponseDTO actualizar(Integer id, UsuarioGrupoDTO usuarioGrupoDTO);
    
    void eliminar(Integer id);
    
    List<UsuarioGrupoResponseDTO> listar();
    
    UsuarioGrupoResponseDTO listarPorId(Integer id);
}

