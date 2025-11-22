package com.nettalco.gestion.backendgestion.repositories;

import com.nettalco.gestion.backendgestion.entities.Lanzamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LanzamientoRepository extends JpaRepository<Lanzamiento, Integer> {
    
    List<Lanzamiento> findByAplicacion_IdAplicacion(Integer idAplicacion);
    
    Optional<Lanzamiento> findByAplicacion_IdAplicacionAndVersion(Integer idAplicacion, String version);
    
    boolean existsByAplicacion_IdAplicacionAndVersion(Integer idAplicacion, String version);
    
    boolean existsByAplicacion_IdAplicacionAndVersionAndIdLanzamientoNot(Integer idAplicacion, String version, Integer idLanzamiento);
    
    List<Lanzamiento> findByEstado(String estado);
    
    List<Lanzamiento> findByAplicacion_IdAplicacionAndEstado(Integer idAplicacion, String estado);
}

