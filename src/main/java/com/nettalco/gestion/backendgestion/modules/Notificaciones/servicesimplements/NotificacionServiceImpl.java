package com.nettalco.gestion.backendgestion.modules.Notificaciones.servicesimplements;

import com.nettalco.gestion.backendgestion.modules.Aplicaciones.entities.Aplicacion;
import com.nettalco.gestion.backendgestion.modules.Aplicaciones.repositories.AplicacionRepository;
import com.nettalco.gestion.backendgestion.modules.Notificaciones.dtos.NotificacionDTO;
import com.nettalco.gestion.backendgestion.modules.Notificaciones.dtos.NotificacionResponseDTO;
import com.nettalco.gestion.backendgestion.modules.Notificaciones.dtos.NotificacionUsuarioDTO;
import com.nettalco.gestion.backendgestion.modules.Notificaciones.entities.Notificacion;
import com.nettalco.gestion.backendgestion.modules.Notificaciones.entities.NotificacionDestinatario;
import com.nettalco.gestion.backendgestion.modules.Notificaciones.repositories.NotificacionDestinatarioRepository;
import com.nettalco.gestion.backendgestion.modules.Notificaciones.repositories.NotificacionRepository;
import com.nettalco.gestion.backendgestion.modules.Notificaciones.servicesinterfaces.INotificacionService;
import com.nettalco.gestion.backendgestion.modules.Usuarios.entities.Usuario;
import com.nettalco.gestion.backendgestion.modules.Usuarios.repositories.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    private AplicacionRepository aplicacionRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
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
        notificacion.setFechaEnvio(notificacionDTO.getFechaEnvio());
        notificacion.setRequiereConfirmacion(notificacionDTO.getRequiereConfirmacion() != null ? 
                                           notificacionDTO.getRequiereConfirmacion() : false);
        notificacion.setMostrarComoRecordatorio(notificacionDTO.getMostrarComoRecordatorio() != null ? 
                                               notificacionDTO.getMostrarComoRecordatorio() : true);
        notificacion.setActivo(notificacionDTO.getActivo() != null ? notificacionDTO.getActivo() : true);
        notificacion.setDatosAdicionales(notificacionDTO.getDatosAdicionales());
        
        Notificacion notificacionGuardada = notificacionRepository.save(notificacion);
        
        // Validar que se proporcionen usuarios (obligatorio)
        if (notificacionDTO.getIdUsuarios() == null || notificacionDTO.getIdUsuarios().isEmpty()) {
            throw new RuntimeException("Debe seleccionar al menos un usuario destinatario");
        }
        
        // Asignar a usuarios individuales seleccionados
        asignarNotificacionAUsuarios(notificacionGuardada.getIdNotificacion(), notificacionDTO.getIdUsuarios());

        NotificacionResponseDTO responseDTO = convertirAResponseDTO(notificacionGuardada);

        // Notificar en tiempo real a través de WebSocket (broadcast)
        // El frontend puede filtrar por creadoPor / destinatarios según corresponda
        messagingTemplate.convertAndSend("/topic/notificaciones-nuevas", responseDTO);
        
        return responseDTO;
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
    @Transactional(readOnly = true)
    public List<NotificacionUsuarioDTO> obtenerTodasNotificaciones(Integer idUsuario) {
        LocalDateTime ahora = LocalDateTime.now();
        List<NotificacionDestinatario> destinatarios = 
                notificacionDestinatarioRepository.findTodasNotificacionesByUsuario(idUsuario, ahora);
        
        return destinatarios.stream()
                .map(this::convertirAUsuarioDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<NotificacionUsuarioDTO> obtenerTodasNotificacionesPorAplicacion(Integer idUsuario, Integer idAplicacion) {
        LocalDateTime ahora = LocalDateTime.now();
        List<NotificacionDestinatario> destinatarios = 
                notificacionDestinatarioRepository.findTodasNotificacionesByUsuarioAndAplicacion(
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
    public void asignarNotificacionAUsuarios(Integer idNotificacion, List<Integer> idUsuarios) {
        // Validar que se proporcionen usuarios
        if (idUsuarios == null || idUsuarios.isEmpty()) {
            throw new RuntimeException("Debe seleccionar al menos un usuario destinatario");
        }
        
        Notificacion notificacion = notificacionRepository.findById(idNotificacion)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada con id: " + idNotificacion));
        
        // Validar que la notificación esté activa
        if (notificacion.getActivo() == null || !notificacion.getActivo()) {
            throw new RuntimeException("La notificación no está activa");
        }
        
        for (Integer idUsuario : idUsuarios) {
            Usuario usuario = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + idUsuario));
            
            // Validar que el usuario esté activo
            if (usuario.getActivo() == null || !usuario.getActivo()) {
                throw new RuntimeException("El usuario con id " + idUsuario + " no está activo");
            }
            
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
        dto.setFechaEnvio(notificacion.getFechaEnvio());
        dto.setRequiereConfirmacion(notificacion.getRequiereConfirmacion());
        dto.setDatosAdicionales(notificacion.getDatosAdicionales());
        dto.setNombreAplicacion(notificacion.getAplicacion() != null ?
                               notificacion.getAplicacion().getNombreAplicacion() : null);
        dto.setCreadorNombre(notificacion.getCreadoPor() != null ?
                             notificacion.getCreadoPor().getNombreCompleto() : null);
        dto.setLeida(destinatario.getLeida());
        dto.setFechaLectura(destinatario.getFechaLectura());
        dto.setConfirmada(destinatario.getConfirmada());
        dto.setFechaConfirmacion(destinatario.getFechaConfirmacion());
        
        return dto;
    }
}

