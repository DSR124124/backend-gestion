package com.nettalco.gestion.backendgestion.modules.Notificaciones.servicesinterfaces;

import com.nettalco.gestion.backendgestion.modules.Notificaciones.dtos.NotificacionDTO;
import com.nettalco.gestion.backendgestion.modules.Notificaciones.dtos.NotificacionResponseDTO;
import com.nettalco.gestion.backendgestion.modules.Notificaciones.dtos.NotificacionUsuarioDTO;

import java.util.List;

public interface INotificacionService {
    
    NotificacionResponseDTO crear(NotificacionDTO notificacionDTO);
    
    NotificacionResponseDTO actualizar(Integer id, NotificacionDTO notificacionDTO);
    
    void eliminar(Integer id);
    
    NotificacionResponseDTO obtenerPorId(Integer id);
    
    List<NotificacionResponseDTO> listar();
    
    List<NotificacionResponseDTO> listarPorAplicacion(Integer idAplicacion);
    
    List<NotificacionUsuarioDTO> obtenerNotificacionesNoLeidas(Integer idUsuario);
    
    List<NotificacionUsuarioDTO> obtenerNotificacionesNoLeidasPorAplicacion(Integer idUsuario, Integer idAplicacion);
    
    List<NotificacionUsuarioDTO> obtenerTodasNotificaciones(Integer idUsuario);
    
    List<NotificacionUsuarioDTO> obtenerTodasNotificacionesPorAplicacion(Integer idUsuario, Integer idAplicacion);
    
    void marcarComoLeida(Integer idNotificacion, Integer idUsuario);
    
    void confirmarNotificacion(Integer idNotificacion, Integer idUsuario);
    
    void asignarNotificacionAUsuarios(Integer idNotificacion, List<Integer> idUsuarios);
}

