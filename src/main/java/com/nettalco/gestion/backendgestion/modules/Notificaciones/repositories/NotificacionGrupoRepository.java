package com.nettalco.gestion.backendgestion.modules.Notificaciones.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nettalco.gestion.backendgestion.modules.Notificaciones.entities.NotificacionGrupo;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificacionGrupoRepository extends JpaRepository<NotificacionGrupo, Integer> {
    
    Optional<NotificacionGrupo> findByNotificacion_IdNotificacionAndGrupo_IdGrupo(
            Integer idNotificacion, Integer idGrupo);
    
    List<NotificacionGrupo> findByNotificacion_IdNotificacion(Integer idNotificacion);
    
    List<NotificacionGrupo> findByGrupo_IdGrupo(Integer idGrupo);
    
    boolean existsByNotificacion_IdNotificacionAndGrupo_IdGrupo(Integer idNotificacion, Integer idGrupo);
}

