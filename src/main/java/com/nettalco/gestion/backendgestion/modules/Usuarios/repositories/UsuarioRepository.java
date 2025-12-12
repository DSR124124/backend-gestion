package com.nettalco.gestion.backendgestion.modules.Usuarios.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nettalco.gestion.backendgestion.modules.Usuarios.entities.Usuario;

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
    
    @Query("SELECT u FROM Usuario u WHERE LOWER(u.rol.nombreRol) = LOWER(:nombreRol) AND u.activo = true")
    List<Usuario> findByRol_NombreRolIgnoreCaseAndActivoTrue(@Param("nombreRol") String nombreRol);
}

