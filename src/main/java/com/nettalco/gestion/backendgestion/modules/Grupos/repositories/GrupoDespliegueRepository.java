package com.nettalco.gestion.backendgestion.modules.Grupos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nettalco.gestion.backendgestion.modules.Grupos.entities.GrupoDespliegue;

import java.util.List;
import java.util.Optional;

@Repository
public interface GrupoDespliegueRepository extends JpaRepository<GrupoDespliegue, Integer> {
    
    Optional<GrupoDespliegue> findByNombreGrupo(String nombreGrupo);
    
    boolean existsByNombreGrupo(String nombreGrupo);
    
    boolean existsByNombreGrupoAndIdGrupoNot(String nombreGrupo, Integer idGrupo);
    
    List<GrupoDespliegue> findByActivo(Boolean activo);
    
    List<GrupoDespliegue> findByOrdenPrioridadNotNullOrderByOrdenPrioridadAsc();
}

