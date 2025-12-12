package com.nettalco.gestion.backendgestion.modules.Notificaciones.servicesimplements;

import com.nettalco.gestion.backendgestion.modules.Aplicaciones.entities.Aplicacion;
import com.nettalco.gestion.backendgestion.modules.Aplicaciones.repositories.AplicacionRepository;
import com.nettalco.gestion.backendgestion.modules.Grupos.entities.GrupoDespliegue;
import com.nettalco.gestion.backendgestion.modules.Grupos.entities.UsuarioGrupo;
import com.nettalco.gestion.backendgestion.modules.Grupos.repositories.GrupoDespliegueRepository;
import com.nettalco.gestion.backendgestion.modules.Grupos.repositories.UsuarioGrupoRepository;
import com.nettalco.gestion.backendgestion.modules.Notificaciones.dtos.NotificacionDTO;
import com.nettalco.gestion.backendgestion.modules.Notificaciones.dtos.NotificacionResponseDTO;
import com.nettalco.gestion.backendgestion.modules.Notificaciones.dtos.NotificacionUsuarioDTO;
import com.nettalco.gestion.backendgestion.modules.Notificaciones.entities.Notificacion;
import com.nettalco.gestion.backendgestion.modules.Notificaciones.entities.NotificacionDestinatario;
import com.nettalco.gestion.backendgestion.modules.Notificaciones.entities.NotificacionGrupo;
import com.nettalco.gestion.backendgestion.modules.Notificaciones.repositories.NotificacionDestinatarioRepository;
import com.nettalco.gestion.backendgestion.modules.Notificaciones.repositories.NotificacionGrupoRepository;
import com.nettalco.gestion.backendgestion.modules.Notificaciones.repositories.NotificacionRepository;
import com.nettalco.gestion.backendgestion.modules.Notificaciones.servicesinterfaces.INotificacionService;
import com.nettalco.gestion.backendgestion.modules.Usuarios.entities.Usuario;
import com.nettalco.gestion.backendgestion.modules.Usuarios.repositories.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class NotificacionServiceImpl implements INotificacionService {
    
    @Autowired
    private NotificacionRepository notificacionRepository;
    
    @Autowired
    private NotificacionDestinatarioRepository notificacionDestinatarioRepository;
    
    @Autowired
    private NotificacionGrupoRepository notificacionGrupoRepository;
    
    @Autowired
    private AplicacionRepository aplicacionRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private GrupoDespliegueRepository grupoDespliegueRepository;
    
    @Autowired
    private UsuarioGrupoRepository usuarioGrupoRepository;
    
    @Override
    public NotificacionResponseDTO crear(NotificacionDTO notificacionDTO) {
        // Validar aplicación
        Aplicacion aplicacion = aplicacionRepository.findById(notificacionDTO.getIdAplicacion())
                .orElseThrow(() -> new RuntimeException("Aplicación no encontrada con id: " + notificacionDTO.getIdAplicacion()));
        
        if (aplicacion.getActivo() == null || !aplicacion.getActivo()) {
            throw new RuntimeException("La aplicación está inactiva");
        }
        
        // Validar usuario creador
        Usuario creador = usuarioRepository.findById(notificacionDTO.getCreadoPor())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + notificacionDTO.getCreadoPor()));
        
        // Crear notificación
        Notificacion notificacion = new Notificacion();
        notificacion.setTitulo(notificacionDTO.getTitulo());
        notificacion.setMensaje(notificacionDTO.getMensaje());
        notificacion.setTipoNotificacion(notificacionDTO.getTipoNotificacion() != null ? 
                                        notificacionDTO.getTipoNotificacion() : "info");
        notificacion.setPrioridad(notificacionDTO.getPrioridad() != null ? 
                                 notificacionDTO.getPrioridad() : "normal");
        notificacion.setAplicacion(aplicacion);
        notificacion.setCreadoPor(creador);
        notificacion.setFechaExpiracion(notificacionDTO.getFechaExpiracion());
        notificacion.setFechaEnvio(notificacionDTO.getFechaEnvio());
        notificacion.setRequiereConfirmacion(notificacionDTO.getRequiereConfirmacion() != null ? 
                                           notificacionDTO.getRequiereConfirmacion() : false);
        notificacion.setMostrarComoRecordatorio(notificacionDTO.getMostrarComoRecordatorio() != null ? 
                                               notificacionDTO.getMostrarComoRecordatorio() : true);
        notificacion.setActivo(notificacionDTO.getActivo() != null ? notificacionDTO.getActivo() : true);
        notificacion.setDatosAdicionales(notificacionDTO.getDatosAdicionales());
        
        Notificacion notificacionGuardada = notificacionRepository.save(notificacion);
        
        // Asignar a usuarios individuales si se proporcionan
        if (notificacionDTO.getIdUsuarios() != null && !notificacionDTO.getIdUsuarios().isEmpty()) {
            asignarNotificacionAUsuarios(notificacionGuardada.getIdNotificacion(), notificacionDTO.getIdUsuarios());
        }
        
        // Asignar a grupos si se proporcionan
        if (notificacionDTO.getIdGrupos() != null && !notificacionDTO.getIdGrupos().isEmpty()) {
            for (Integer idGrupo : notificacionDTO.getIdGrupos()) {
                asignarNotificacionAGrupo(notificacionGuardada.getIdNotificacion(), idGrupo);
            }
        }
        
        return convertirAResponseDTO(notificacionGuardada);
    }
    
    @Override
    public NotificacionResponseDTO actualizar(Integer id, NotificacionDTO notificacionDTO) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada con id: " + id));
        
        // Validar aplicación si se cambia
        if (!notificacion.getAplicacion().getIdAplicacion().equals(notificacionDTO.getIdAplicacion())) {
            Aplicacion aplicacion = aplicacionRepository.findById(notificacionDTO.getIdAplicacion())
                    .orElseThrow(() -> new RuntimeException("Aplicación no encontrada con id: " + notificacionDTO.getIdAplicacion()));
            notificacion.setAplicacion(aplicacion);
        }
        
        notificacion.setTitulo(notificacionDTO.getTitulo());
        notificacion.setMensaje(notificacionDTO.getMensaje());
        if (notificacionDTO.getTipoNotificacion() != null) {
            notificacion.setTipoNotificacion(notificacionDTO.getTipoNotificacion());
        }
        if (notificacionDTO.getPrioridad() != null) {
            notificacion.setPrioridad(notificacionDTO.getPrioridad());
        }
        notificacion.setFechaExpiracion(notificacionDTO.getFechaExpiracion());
        notificacion.setFechaEnvio(notificacionDTO.getFechaEnvio());
        if (notificacionDTO.getRequiereConfirmacion() != null) {
            notificacion.setRequiereConfirmacion(notificacionDTO.getRequiereConfirmacion());
        }
        if (notificacionDTO.getMostrarComoRecordatorio() != null) {
            notificacion.setMostrarComoRecordatorio(notificacionDTO.getMostrarComoRecordatorio());
        }
        if (notificacionDTO.getActivo() != null) {
            notificacion.setActivo(notificacionDTO.getActivo());
        }
        notificacion.setDatosAdicionales(notificacionDTO.getDatosAdicionales());
        
        Notificacion notificacionActualizada = notificacionRepository.save(notificacion);
        return convertirAResponseDTO(notificacionActualizada);
    }
    
    @Override
    public void eliminar(Integer id) {
        if (!notificacionRepository.existsById(id)) {
            throw new RuntimeException("Notificación no encontrada con id: " + id);
        }
        notificacionRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public NotificacionResponseDTO obtenerPorId(Integer id) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada con id: " + id));
        return convertirAResponseDTO(notificacion);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<NotificacionResponseDTO> listar() {
        return notificacionRepository.findAll().stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<NotificacionResponseDTO> listarPorAplicacion(Integer idAplicacion) {
        return notificacionRepository.findByAplicacion_IdAplicacion(idAplicacion).stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<NotificacionUsuarioDTO> obtenerNotificacionesNoLeidas(Integer idUsuario) {
        LocalDateTime ahora = LocalDateTime.now();
        List<NotificacionDestinatario> destinatarios = 
                notificacionDestinatarioRepository.findNotificacionesNoLeidasByUsuario(idUsuario, ahora);
        
        return destinatarios.stream()
                .map(this::convertirAUsuarioDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<NotificacionUsuarioDTO> obtenerNotificacionesNoLeidasPorAplicacion(Integer idUsuario, Integer idAplicacion) {
        LocalDateTime ahora = LocalDateTime.now();
        List<NotificacionDestinatario> destinatarios = 
                notificacionDestinatarioRepository.findNotificacionesNoLeidasByUsuarioAndAplicacion(
                        idUsuario, idAplicacion, ahora);
        
        return destinatarios.stream()
                .map(this::convertirAUsuarioDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public void marcarComoLeida(Integer idNotificacion, Integer idUsuario) {
        NotificacionDestinatario destinatario = 
                notificacionDestinatarioRepository.findByNotificacion_IdNotificacionAndUsuario_IdUsuario(
                        idNotificacion, idUsuario)
                .orElseThrow(() -> new RuntimeException("Notificación no asignada al usuario"));
        
        destinatario.setLeida(true);
        destinatario.setFechaLectura(LocalDateTime.now());
        notificacionDestinatarioRepository.save(destinatario);
    }
    
    @Override
    public void confirmarNotificacion(Integer idNotificacion, Integer idUsuario) {
        NotificacionDestinatario destinatario = 
                notificacionDestinatarioRepository.findByNotificacion_IdNotificacionAndUsuario_IdUsuario(
                        idNotificacion, idUsuario)
                .orElseThrow(() -> new RuntimeException("Notificación no asignada al usuario"));
        
        if (!destinatario.getNotificacion().getRequiereConfirmacion()) {
            throw new RuntimeException("Esta notificación no requiere confirmación");
        }
        
        destinatario.setConfirmada(true);
        destinatario.setFechaConfirmacion(LocalDateTime.now());
        notificacionDestinatarioRepository.save(destinatario);
    }
    
    @Override
    public void asignarNotificacionAGrupo(Integer idNotificacion, Integer idGrupo) {
        Notificacion notificacion = notificacionRepository.findById(idNotificacion)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada con id: " + idNotificacion));
        
        if (notificacion.getActivo() == null || !notificacion.getActivo()) {
            throw new RuntimeException("La notificación no está activa");
        }
        
        GrupoDespliegue grupo = grupoDespliegueRepository.findById(idGrupo)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado con id: " + idGrupo));
        
        if (grupo.getActivo() == null || !grupo.getActivo()) {
            throw new RuntimeException("El grupo no está activo");
        }
        
        // Crear relación notificación-grupo si no existe
        if (!notificacionGrupoRepository.existsByNotificacion_IdNotificacionAndGrupo_IdGrupo(
                idNotificacion, idGrupo)) {
            NotificacionGrupo notificacionGrupo = new NotificacionGrupo(notificacion, grupo);
            notificacionGrupoRepository.save(notificacionGrupo);
        }
        
        // Asignar a todos los usuarios activos del grupo
        List<UsuarioGrupo> usuariosGrupo = usuarioGrupoRepository.findByGrupoDespliegue_IdGrupoAndActivo(
                idGrupo, true);
        
        for (UsuarioGrupo usuarioGrupo : usuariosGrupo) {
            Usuario usuario = usuarioGrupo.getUsuario();
            if (usuario.getActivo() != null && usuario.getActivo()) {
                // Verificar si ya existe la asignación
                if (!notificacionDestinatarioRepository.existsByNotificacion_IdNotificacionAndUsuario_IdUsuario(
                        idNotificacion, usuario.getIdUsuario())) {
                    NotificacionDestinatario destinatario = new NotificacionDestinatario(notificacion, usuario);
                    notificacionDestinatarioRepository.save(destinatario);
                }
            }
        }
    }
    
    @Override
    public void asignarNotificacionAUsuarios(Integer idNotificacion, List<Integer> idUsuarios) {
        Notificacion notificacion = notificacionRepository.findById(idNotificacion)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada con id: " + idNotificacion));
        
        for (Integer idUsuario : idUsuarios) {
            Usuario usuario = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + idUsuario));
            
            // Verificar si ya existe la asignación
            if (!notificacionDestinatarioRepository.existsByNotificacion_IdNotificacionAndUsuario_IdUsuario(
                    idNotificacion, idUsuario)) {
                NotificacionDestinatario destinatario = new NotificacionDestinatario(notificacion, usuario);
                notificacionDestinatarioRepository.save(destinatario);
            }
        }
    }
    
    private NotificacionResponseDTO convertirAResponseDTO(Notificacion notificacion) {
        NotificacionResponseDTO dto = new NotificacionResponseDTO(
                notificacion.getIdNotificacion(),
                notificacion.getTitulo(),
                notificacion.getMensaje(),
                notificacion.getTipoNotificacion(),
                notificacion.getPrioridad(),
                notificacion.getAplicacion() != null ? notificacion.getAplicacion().getIdAplicacion() : null,
                notificacion.getAplicacion() != null ? notificacion.getAplicacion().getNombreAplicacion() : null,
                notificacion.getCreadoPor() != null ? notificacion.getCreadoPor().getIdUsuario() : null,
                notificacion.getCreadoPor() != null ? notificacion.getCreadoPor().getNombreCompleto() : null,
                notificacion.getFechaCreacion(),
                notificacion.getFechaExpiracion(),
                notificacion.getFechaEnvio(),
                notificacion.getRequiereConfirmacion(),
                notificacion.getMostrarComoRecordatorio(),
                notificacion.getActivo(),
                notificacion.getDatosAdicionales(),
                notificacion.getFechaModificacion()
        );
        
        // Calcular estadísticas
        List<NotificacionDestinatario> destinatarios = 
                notificacionDestinatarioRepository.findByNotificacion_IdNotificacion(
                        notificacion.getIdNotificacion());
        dto.setTotalDestinatarios((long) destinatarios.size());
        dto.setTotalLeidas(destinatarios.stream().filter(NotificacionDestinatario::getLeida).count());
        dto.setTotalConfirmadas(destinatarios.stream().filter(NotificacionDestinatario::getConfirmada).count());
        
        List<NotificacionGrupo> grupos = 
                notificacionGrupoRepository.findByNotificacion_IdNotificacion(notificacion.getIdNotificacion());
        dto.setTotalGrupos((long) grupos.size());
        
        return dto;
    }
    
    private NotificacionUsuarioDTO convertirAUsuarioDTO(NotificacionDestinatario destinatario) {
        NotificacionUsuarioDTO dto = new NotificacionUsuarioDTO();
        Notificacion notificacion = destinatario.getNotificacion();
        
        dto.setIdNotificacion(notificacion.getIdNotificacion());
        dto.setTitulo(notificacion.getTitulo());
        dto.setMensaje(notificacion.getMensaje());
        dto.setTipoNotificacion(notificacion.getTipoNotificacion());
        dto.setPrioridad(notificacion.getPrioridad());
        dto.setFechaCreacion(notificacion.getFechaCreacion());
        dto.setFechaExpiracion(notificacion.getFechaExpiracion());
        dto.setRequiereConfirmacion(notificacion.getRequiereConfirmacion());
        dto.setDatosAdicionales(notificacion.getDatosAdicionales());
        dto.setNombreAplicacion(notificacion.getAplicacion() != null ? 
                               notificacion.getAplicacion().getNombreAplicacion() : null);
        dto.setLeida(destinatario.getLeida());
        dto.setFechaLectura(destinatario.getFechaLectura());
        dto.setConfirmada(destinatario.getConfirmada());
        dto.setFechaConfirmacion(destinatario.getFechaConfirmacion());
        
        return dto;
    }
}

