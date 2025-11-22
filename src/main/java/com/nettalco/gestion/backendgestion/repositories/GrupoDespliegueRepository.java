package com.nettalco.gestion.backendgestion.repositories;

import com.nettalco.gestion.backendgestion.entities.GrupoDespliegue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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

