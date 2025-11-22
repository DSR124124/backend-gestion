package com.nettalco.gestion.backendgestion.repositories;

import com.nettalco.gestion.backendgestion.entities.UsuarioAplicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioAplicacionRepository extends JpaRepository<UsuarioAplicacion, Integer> {
    
    Optional<UsuarioAplicacion> findByUsuario_IdUsuarioAndAplicacion_IdAplicacion(Integer idUsuario, Integer idAplicacion);
    
    boolean existsByUsuario_IdUsuarioAndAplicacion_IdAplicacion(Integer idUsuario, Integer idAplicacion);
    
    List<UsuarioAplicacion> findByUsuario_IdUsuario(Integer idUsuario);
    
    List<UsuarioAplicacion> findByAplicacion_IdAplicacion(Integer idAplicacion);
    
    List<UsuarioAplicacion> findByLicenciaActiva(Boolean licenciaActiva);
    
    List<UsuarioAplicacion> findByUsuario_IdUsuarioAndLicenciaActiva(Integer idUsuario, Boolean licenciaActiva);
    
    List<UsuarioAplicacion> findByAplicacion_IdAplicacionAndLicenciaActiva(Integer idAplicacion, Boolean licenciaActiva);
}

