package com.nettalco.gestion.backendgestion.modules.Notificaciones.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nettalco.gestion.backendgestion.modules.Notificaciones.entities.Notificacion;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {
    
    List<Notificacion> findByAplicacion_IdAplicacion(Integer idAplicacion);
    
    List<Notificacion> findByCreadoPor_IdUsuario(Integer idUsuario);
    
    List<Notificacion> findByActivo(Boolean activo);
    
    List<Notificacion> findByAplicacion_IdAplicacionAndActivo(Integer idAplicacion, Boolean activo);
    
    @Query("SELECT n FROM Notificacion n WHERE n.activo = true " +
           "AND (n.fechaExpiracion IS NULL OR n.fechaExpiracion > :ahora) " +
           "AND (n.fechaEnvio IS NULL OR n.fechaEnvio <= :ahora)")
    List<Notificacion> findNotificacionesActivasYVigentes(@Param("ahora") LocalDateTime ahora);
}

