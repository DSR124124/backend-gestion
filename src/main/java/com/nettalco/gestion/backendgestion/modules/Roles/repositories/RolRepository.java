package com.nettalco.gestion.backendgestion.modules.Roles.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nettalco.gestion.backendgestion.modules.Roles.entities.Rol;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    
    Optional<Rol> findByNombreRol(String nombreRol);
    
    boolean existsByNombreRol(String nombreRol);
    
    boolean existsByNombreRolAndIdRolNot(String nombreRol, Integer idRol);
}

