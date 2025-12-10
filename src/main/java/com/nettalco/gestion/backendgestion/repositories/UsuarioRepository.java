package com.nettalco.gestion.backendgestion.repositories;

import com.nettalco.gestion.backendgestion.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    Optional<Usuario> findByUsername(String username);
    
    Optional<Usuario> findByEmail(String email);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    boolean existsByUsernameAndIdUsuarioNot(String username, Integer idUsuario);
    
    boolean existsByEmailAndIdUsuarioNot(String email, Integer idUsuario);
    
    List<Usuario> findByRol_IdRolAndActivoTrue(Integer idRol);
    
    List<Usuario> findByRol_NombreRolIgnoreCaseAndActivoTrue(String nombreRol);
}

