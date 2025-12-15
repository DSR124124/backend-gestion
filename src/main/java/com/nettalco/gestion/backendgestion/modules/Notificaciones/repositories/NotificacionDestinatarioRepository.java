package com.nettalco.gestion.backendgestion.modules.Notificaciones.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nettalco.gestion.backendgestion.modules.Notificaciones.entities.NotificacionDestinatario;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotificacionDestinatarioRepository extends JpaRepository<NotificacionDestinatario, Integer> {
    
    Optional<NotificacionDestinatario> findByNotificacion_IdNotificacionAndUsuario_IdUsuario(
            Integer idNotificacion, Integer idUsuario);
    
    List<NotificacionDestinatario> findByUsuario_IdUsuario(Integer idUsuario);
    
    List<NotificacionDestinatario> findByNotificacion_IdNotificacion(Integer idNotificacion);
    
    List<NotificacionDestinatario> findByUsuario_IdUsuarioAndLeida(Integer idUsuario, Boolean leida);
    
    @Query("SELECT nd FROM NotificacionDestinatario nd " +
           "WHERE nd.usuario.idUsuario = :idUsuario " +
           "AND nd.leida = false " +
           "AND nd.notificacion.activo = true " +
           "AND (nd.notificacion.fechaEnvio IS NULL OR nd.notificacion.fechaEnvio <= :ahora) " +
           "ORDER BY " +
           "CASE nd.notificacion.prioridad " +
           "WHEN 'urgente' THEN 1 " +
           "WHEN 'alta' THEN 2 " +
           "WHEN 'normal' THEN 3 " +
           "WHEN 'baja' THEN 4 " +
           "END, " +
           "nd.notificacion.fechaCreacion DESC")
    List<NotificacionDestinatario> findNotificacionesNoLeidasByUsuario(@Param("idUsuario") Integer idUsuario, 
                                                                        @Param("ahora") LocalDateTime ahora);
    
    @Query("SELECT nd FROM NotificacionDestinatario nd " +
           "WHERE nd.usuario.idUsuario = :idUsuario " +
           "AND nd.leida = false " +
           "AND nd.notificacion.activo = true " +
           "AND nd.notificacion.aplicacion.idAplicacion = :idAplicacion " +
           "AND (nd.notificacion.fechaEnvio IS NULL OR nd.notificacion.fechaEnvio <= :ahora) " +
           "ORDER BY " +
           "CASE nd.notificacion.prioridad " +
           "WHEN 'urgente' THEN 1 " +
           "WHEN 'alta' THEN 2 " +
           "WHEN 'normal' THEN 3 " +
           "WHEN 'baja' THEN 4 " +
           "END, " +
           "nd.notificacion.fechaCreacion DESC")
    List<NotificacionDestinatario> findNotificacionesNoLeidasByUsuarioAndAplicacion(
            @Param("idUsuario") Integer idUsuario, 
            @Param("idAplicacion") Integer idAplicacion,
            @Param("ahora") LocalDateTime ahora);
    
    @Query("SELECT nd FROM NotificacionDestinatario nd " +
           "WHERE nd.usuario.idUsuario = :idUsuario " +
           "AND nd.notificacion.activo = true " +
           "AND (nd.notificacion.fechaEnvio IS NULL OR nd.notificacion.fechaEnvio <= :ahora) " +
           "ORDER BY " +
           "CASE nd.notificacion.prioridad " +
           "WHEN 'urgente' THEN 1 " +
           "WHEN 'alta' THEN 2 " +
           "WHEN 'normal' THEN 3 " +
           "WHEN 'baja' THEN 4 " +
           "END, " +
           "nd.notificacion.fechaCreacion DESC")
    List<NotificacionDestinatario> findTodasNotificacionesByUsuario(@Param("idUsuario") Integer idUsuario, 
                                                                     @Param("ahora") LocalDateTime ahora);
    
    @Query("SELECT nd FROM NotificacionDestinatario nd " +
           "WHERE nd.usuario.idUsuario = :idUsuario " +
           "AND nd.notificacion.activo = true " +
           "AND nd.notificacion.aplicacion.idAplicacion = :idAplicacion " +
           "AND (nd.notificacion.fechaEnvio IS NULL OR nd.notificacion.fechaEnvio <= :ahora) " +
           "ORDER BY " +
           "CASE nd.notificacion.prioridad " +
           "WHEN 'urgente' THEN 1 " +
           "WHEN 'alta' THEN 2 " +
           "WHEN 'normal' THEN 3 " +
           "WHEN 'baja' THEN 4 " +
           "END, " +
           "nd.notificacion.fechaCreacion DESC")
    List<NotificacionDestinatario> findTodasNotificacionesByUsuarioAndAplicacion(
            @Param("idUsuario") Integer idUsuario, 
            @Param("idAplicacion") Integer idAplicacion,
            @Param("ahora") LocalDateTime ahora);
    
    boolean existsByNotificacion_IdNotificacionAndUsuario_IdUsuario(Integer idNotificacion, Integer idUsuario);
}

