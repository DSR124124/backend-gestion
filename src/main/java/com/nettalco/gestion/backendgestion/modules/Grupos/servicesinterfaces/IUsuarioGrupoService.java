package com.nettalco.gestion.backendgestion.modules.Grupos.servicesinterfaces;

import com.nettalco.gestion.backendgestion.modules.Grupos.dtos.UsuarioGrupoDTO;
import com.nettalco.gestion.backendgestion.modules.Grupos.dtos.UsuarioGrupoResponseDTO;

import java.util.List;

public interface IUsuarioGrupoService {
    
    UsuarioGrupoResponseDTO crear(UsuarioGrupoDTO usuarioGrupoDTO);
    
    UsuarioGrupoResponseDTO actualizar(Integer id, UsuarioGrupoDTO usuarioGrupoDTO);
    
    void eliminar(Integer id);
    
    List<UsuarioGrupoResponseDTO> listar();
    
    UsuarioGrupoResponseDTO listarPorId(Integer id);
}

