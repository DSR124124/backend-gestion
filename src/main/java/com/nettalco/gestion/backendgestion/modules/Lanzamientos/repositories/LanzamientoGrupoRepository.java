package com.nettalco.gestion.backendgestion.modules.Lanzamientos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nettalco.gestion.backendgestion.modules.Lanzamientos.entities.LanzamientoGrupo;

import java.util.List;
import java.util.Optional;

@Repository
public interface LanzamientoGrupoRepository extends JpaRepository<LanzamientoGrupo, Integer> {
    
    Optional<LanzamientoGrupo> findByLanzamiento_IdLanzamientoAndGrupoDespliegue_IdGrupo(Integer idLanzamiento, Integer idGrupo);
    
    boolean existsByLanzamiento_IdLanzamientoAndGrupoDespliegue_IdGrupo(Integer idLanzamiento, Integer idGrupo);
    
    List<LanzamientoGrupo> findByLanzamiento_IdLanzamiento(Integer idLanzamiento);
    
    List<LanzamientoGrupo> findByGrupoDespliegue_IdGrupo(Integer idGrupo);
    
    List<LanzamientoGrupo> findByActivo(Boolean activo);
    
    List<LanzamientoGrupo> findByLanzamiento_IdLanzamientoAndActivo(Integer idLanzamiento, Boolean activo);
    
    List<LanzamientoGrupo> findByGrupoDespliegue_IdGrupoAndActivo(Integer idGrupo, Boolean activo);
}

