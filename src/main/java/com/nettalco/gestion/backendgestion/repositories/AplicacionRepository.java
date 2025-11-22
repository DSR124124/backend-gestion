package com.nettalco.gestion.backendgestion.repositories;

import com.nettalco.gestion.backendgestion.entities.Aplicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AplicacionRepository extends JpaRepository<Aplicacion, Integer> {
    
    Optional<Aplicacion> findByNombreAplicacion(String nombreAplicacion);
    
    Optional<Aplicacion> findByCodigoProducto(String codigoProducto);
    
    boolean existsByNombreAplicacion(String nombreAplicacion);
    
    boolean existsByCodigoProducto(String codigoProducto);
    
    boolean existsByNombreAplicacionAndIdAplicacionNot(String nombreAplicacion, Integer idAplicacion);
    
    boolean existsByCodigoProductoAndIdAplicacionNot(String codigoProducto, Integer idAplicacion);
}

