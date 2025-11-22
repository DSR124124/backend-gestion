package com.nettalco.gestion.backendgestion.servicesimplements;

import com.nettalco.gestion.backendgestion.dtos.LanzamientoGrupoDTO;
import com.nettalco.gestion.backendgestion.dtos.LanzamientoGrupoResponseDTO;
import com.nettalco.gestion.backendgestion.entities.GrupoDespliegue;
import com.nettalco.gestion.backendgestion.entities.Lanzamiento;
import com.nettalco.gestion.backendgestion.entities.LanzamientoGrupo;
import com.nettalco.gestion.backendgestion.entities.Usuario;
import com.nettalco.gestion.backendgestion.repositories.GrupoDespliegueRepository;
import com.nettalco.gestion.backendgestion.repositories.LanzamientoGrupoRepository;
import com.nettalco.gestion.backendgestion.repositories.LanzamientoRepository;
import com.nettalco.gestion.backendgestion.repositories.UsuarioRepository;
import com.nettalco.gestion.backendgestion.servicesinterfaces.ILanzamientoGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LanzamientoGrupoServiceImpl implements ILanzamientoGrupoService {
    
    @Autowired
    private LanzamientoGrupoRepository lanzamientoGrupoRepository;
    
    @Autowired
    private LanzamientoRepository lanzamientoRepository;
    
    @Autowired
    private GrupoDespliegueRepository grupoDespliegueRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Override
    public LanzamientoGrupoResponseDTO crear(LanzamientoGrupoDTO lanzamientoGrupoDTO) {
        // Validar que el lanzamiento exista
        Lanzamiento lanzamiento = lanzamientoRepository.findById(lanzamientoGrupoDTO.getIdLanzamiento())
                .orElseThrow(() -> new RuntimeException("Lanzamiento no encontrado con id: " + lanzamientoGrupoDTO.getIdLanzamiento()));
        
        // Validar que el grupo exista
        GrupoDespliegue grupoDespliegue = grupoDespliegueRepository.findById(lanzamientoGrupoDTO.getIdGrupo())
                .orElseThrow(() -> new RuntimeException("Grupo de despliegue no encontrado con id: " + lanzamientoGrupoDTO.getIdGrupo()));
        
        // Validar que no exista ya esta relación
        if (lanzamientoGrupoRepository.existsByLanzamiento_IdLanzamientoAndGrupoDespliegue_IdGrupo(
                lanzamientoGrupoDTO.getIdLanzamiento(), lanzamientoGrupoDTO.getIdGrupo())) {
            throw new RuntimeException("Ya existe una asignación entre el lanzamiento y el grupo especificados");
        }
        
        // Validar que el usuario asignador exista (si se proporciona)
        Usuario asignadoPor = null;
        if (lanzamientoGrupoDTO.getAsignadoPor() != null) {
            asignadoPor = usuarioRepository.findById(lanzamientoGrupoDTO.getAsignadoPor())
                    .orElseThrow(() -> new RuntimeException("Usuario asignador no encontrado con id: " + lanzamientoGrupoDTO.getAsignadoPor()));
        }
        
        LanzamientoGrupo lanzamientoGrupo = new LanzamientoGrupo();
        lanzamientoGrupo.setLanzamiento(lanzamiento);
        lanzamientoGrupo.setGrupoDespliegue(grupoDespliegue);
        lanzamientoGrupo.setFechaDisponibilidad(lanzamientoGrupoDTO.getFechaDisponibilidad());
        lanzamientoGrupo.setFechaFinDisponibilidad(lanzamientoGrupoDTO.getFechaFinDisponibilidad());
        lanzamientoGrupo.setAsignadoPor(asignadoPor);
        lanzamientoGrupo.setNotificacionEnviada(lanzamientoGrupoDTO.getNotificacionEnviada() != null ? lanzamientoGrupoDTO.getNotificacionEnviada() : false);
        lanzamientoGrupo.setActivo(lanzamientoGrupoDTO.getActivo() != null ? lanzamientoGrupoDTO.getActivo() : true);
        lanzamientoGrupo.setNotas(lanzamientoGrupoDTO.getNotas());
        
        LanzamientoGrupo lanzamientoGrupoGuardado = lanzamientoGrupoRepository.save(lanzamientoGrupo);
        return convertirAResponseDTO(lanzamientoGrupoGuardado);
    }
    
    @Override
    public LanzamientoGrupoResponseDTO actualizar(Integer id, LanzamientoGrupoDTO lanzamientoGrupoDTO) {
        LanzamientoGrupo lanzamientoGrupo = lanzamientoGrupoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignación lanzamiento-grupo no encontrada con id: " + id));
        
        // Validar que el lanzamiento exista
        Lanzamiento lanzamiento = lanzamientoRepository.findById(lanzamientoGrupoDTO.getIdLanzamiento())
                .orElseThrow(() -> new RuntimeException("Lanzamiento no encontrado con id: " + lanzamientoGrupoDTO.getIdLanzamiento()));
        
        // Validar que el grupo exista
        GrupoDespliegue grupoDespliegue = grupoDespliegueRepository.findById(lanzamientoGrupoDTO.getIdGrupo())
                .orElseThrow(() -> new RuntimeException("Grupo de despliegue no encontrado con id: " + lanzamientoGrupoDTO.getIdGrupo()));
        
        // Validar que no exista otra relación con el mismo lanzamiento y grupo (excluyendo la actual)
        if (!lanzamientoGrupo.getLanzamiento().getIdLanzamiento().equals(lanzamientoGrupoDTO.getIdLanzamiento()) ||
            !lanzamientoGrupo.getGrupoDespliegue().getIdGrupo().equals(lanzamientoGrupoDTO.getIdGrupo())) {
            if (lanzamientoGrupoRepository.existsByLanzamiento_IdLanzamientoAndGrupoDespliegue_IdGrupo(
                    lanzamientoGrupoDTO.getIdLanzamiento(), lanzamientoGrupoDTO.getIdGrupo())) {
                throw new RuntimeException("Ya existe otra asignación entre el lanzamiento y el grupo especificados");
            }
        }
        
        // Validar que el usuario asignador exista (si se proporciona)
        if (lanzamientoGrupoDTO.getAsignadoPor() != null) {
            Usuario asignadoPor = usuarioRepository.findById(lanzamientoGrupoDTO.getAsignadoPor())
                    .orElseThrow(() -> new RuntimeException("Usuario asignador no encontrado con id: " + lanzamientoGrupoDTO.getAsignadoPor()));
            lanzamientoGrupo.setAsignadoPor(asignadoPor);
        } else {
            lanzamientoGrupo.setAsignadoPor(null);
        }
        
        lanzamientoGrupo.setLanzamiento(lanzamiento);
        lanzamientoGrupo.setGrupoDespliegue(grupoDespliegue);
        lanzamientoGrupo.setFechaDisponibilidad(lanzamientoGrupoDTO.getFechaDisponibilidad());
        lanzamientoGrupo.setFechaFinDisponibilidad(lanzamientoGrupoDTO.getFechaFinDisponibilidad());
        if (lanzamientoGrupoDTO.getNotificacionEnviada() != null) {
            lanzamientoGrupo.setNotificacionEnviada(lanzamientoGrupoDTO.getNotificacionEnviada());
        }
        if (lanzamientoGrupoDTO.getActivo() != null) {
            lanzamientoGrupo.setActivo(lanzamientoGrupoDTO.getActivo());
        }
        lanzamientoGrupo.setNotas(lanzamientoGrupoDTO.getNotas());
        
        LanzamientoGrupo lanzamientoGrupoActualizado = lanzamientoGrupoRepository.save(lanzamientoGrupo);
        return convertirAResponseDTO(lanzamientoGrupoActualizado);
    }
    
    @Override
    public void eliminar(Integer id) {
        if (!lanzamientoGrupoRepository.existsById(id)) {
            throw new RuntimeException("Asignación lanzamiento-grupo no encontrada con id: " + id);
        }
        lanzamientoGrupoRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<LanzamientoGrupoResponseDTO> listar() {
        return lanzamientoGrupoRepository.findAll().stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public LanzamientoGrupoResponseDTO listarPorId(Integer id) {
        LanzamientoGrupo lanzamientoGrupo = lanzamientoGrupoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignación lanzamiento-grupo no encontrada con id: " + id));
        return convertirAResponseDTO(lanzamientoGrupo);
    }
    
    private LanzamientoGrupoResponseDTO convertirAResponseDTO(LanzamientoGrupo lanzamientoGrupo) {
        return new LanzamientoGrupoResponseDTO(
                lanzamientoGrupo.getIdLanzamientoGrupo(),
                lanzamientoGrupo.getLanzamiento() != null ? lanzamientoGrupo.getLanzamiento().getIdLanzamiento() : null,
                lanzamientoGrupo.getLanzamiento() != null ? lanzamientoGrupo.getLanzamiento().getVersion() : null,
                lanzamientoGrupo.getLanzamiento() != null && lanzamientoGrupo.getLanzamiento().getAplicacion() != null 
                    ? lanzamientoGrupo.getLanzamiento().getAplicacion().getNombreAplicacion() : null,
                lanzamientoGrupo.getGrupoDespliegue() != null ? lanzamientoGrupo.getGrupoDespliegue().getIdGrupo() : null,
                lanzamientoGrupo.getGrupoDespliegue() != null ? lanzamientoGrupo.getGrupoDespliegue().getNombreGrupo() : null,
                lanzamientoGrupo.getGrupoDespliegue() != null ? lanzamientoGrupo.getGrupoDespliegue().getOrdenPrioridad() : null,
                lanzamientoGrupo.getFechaAsignacion(),
                lanzamientoGrupo.getFechaDisponibilidad(),
                lanzamientoGrupo.getFechaFinDisponibilidad(),
                lanzamientoGrupo.getAsignadoPor() != null ? lanzamientoGrupo.getAsignadoPor().getIdUsuario() : null,
                lanzamientoGrupo.getAsignadoPor() != null ? lanzamientoGrupo.getAsignadoPor().getNombreCompleto() : null,
                lanzamientoGrupo.getNotificacionEnviada(),
                lanzamientoGrupo.getActivo(),
                lanzamientoGrupo.getNotas()
        );
    }
}

