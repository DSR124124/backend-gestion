package com.nettalco.gestion.backendgestion.modules.Grupos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nettalco.gestion.backendgestion.modules.Grupos.entities.UsuarioGrupo;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioGrupoRepository extends JpaRepository<UsuarioGrupo, Integer> {
    
    Optional<UsuarioGrupo> findByUsuario_IdUsuarioAndGrupoDespliegue_IdGrupo(Integer idUsuario, Integer idGrupo);
    
    boolean existsByUsuario_IdUsuarioAndGrupoDespliegue_IdGrupo(Integer idUsuario, Integer idGrupo);
    
    List<UsuarioGrupo> findByUsuario_IdUsuario(Integer idUsuario);
    
    List<UsuarioGrupo> findByGrupoDespliegue_IdGrupo(Integer idGrupo);
    
    List<UsuarioGrupo> findByActivo(Boolean activo);
    
    List<UsuarioGrupo> findByUsuario_IdUsuarioAndActivo(Integer idUsuario, Boolean activo);
    
    List<UsuarioGrupo> findByGrupoDespliegue_IdGrupoAndActivo(Integer idGrupo, Boolean activo);
}

