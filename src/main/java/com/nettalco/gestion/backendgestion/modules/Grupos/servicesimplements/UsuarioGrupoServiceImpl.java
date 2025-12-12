package com.nettalco.gestion.backendgestion.modules.Grupos.servicesimplements;

import com.nettalco.gestion.backendgestion.modules.Grupos.dtos.UsuarioGrupoDTO;
import com.nettalco.gestion.backendgestion.modules.Grupos.dtos.UsuarioGrupoResponseDTO;
import com.nettalco.gestion.backendgestion.modules.Usuarios.entities.Usuario;
import com.nettalco.gestion.backendgestion.modules.Grupos.entities.GrupoDespliegue;
import com.nettalco.gestion.backendgestion.modules.Grupos.entities.UsuarioGrupo;
import com.nettalco.gestion.backendgestion.modules.Grupos.repositories.GrupoDespliegueRepository;
import com.nettalco.gestion.backendgestion.modules.Grupos.repositories.UsuarioGrupoRepository;
import com.nettalco.gestion.backendgestion.modules.Usuarios.repositories.UsuarioRepository;
import com.nettalco.gestion.backendgestion.modules.Grupos.servicesinterfaces.IUsuarioGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsuarioGrupoServiceImpl implements IUsuarioGrupoService {
    
    @Autowired
    private UsuarioGrupoRepository usuarioGrupoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private GrupoDespliegueRepository grupoDespliegueRepository;
    
    @Override
    public UsuarioGrupoResponseDTO crear(UsuarioGrupoDTO usuarioGrupoDTO) {
        // Validar que el usuario exista
        Usuario usuario = usuarioRepository.findById(usuarioGrupoDTO.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + usuarioGrupoDTO.getIdUsuario()));
        
        // Validar que el grupo exista
        GrupoDespliegue grupoDespliegue = grupoDespliegueRepository.findById(usuarioGrupoDTO.getIdGrupo())
                .orElseThrow(() -> new RuntimeException("Grupo de despliegue no encontrado con id: " + usuarioGrupoDTO.getIdGrupo()));
        
        // Validar que no exista ya esta relación
        if (usuarioGrupoRepository.existsByUsuario_IdUsuarioAndGrupoDespliegue_IdGrupo(
                usuarioGrupoDTO.getIdUsuario(), usuarioGrupoDTO.getIdGrupo())) {
            throw new RuntimeException("Ya existe una asignación entre el usuario y el grupo especificados");
        }
        
        // Validar que el usuario asignador exista (si se proporciona)
        Usuario asignadoPor = null;
        if (usuarioGrupoDTO.getAsignadoPor() != null) {
            asignadoPor = usuarioRepository.findById(usuarioGrupoDTO.getAsignadoPor())
                    .orElseThrow(() -> new RuntimeException("Usuario asignador no encontrado con id: " + usuarioGrupoDTO.getAsignadoPor()));
        }
        
        UsuarioGrupo usuarioGrupo = new UsuarioGrupo();
        usuarioGrupo.setUsuario(usuario);
        usuarioGrupo.setGrupoDespliegue(grupoDespliegue);
        usuarioGrupo.setAsignadoPor(asignadoPor);
        usuarioGrupo.setActivo(usuarioGrupoDTO.getActivo() != null ? usuarioGrupoDTO.getActivo() : true);
        usuarioGrupo.setNotas(usuarioGrupoDTO.getNotas());
        
        UsuarioGrupo usuarioGrupoGuardado = usuarioGrupoRepository.save(usuarioGrupo);
        return convertirAResponseDTO(usuarioGrupoGuardado);
    }
    
    @Override
    public UsuarioGrupoResponseDTO actualizar(Integer id, UsuarioGrupoDTO usuarioGrupoDTO) {
        UsuarioGrupo usuarioGrupo = usuarioGrupoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignación usuario-grupo no encontrada con id: " + id));
        
        // Validar que el usuario exista
        Usuario usuario = usuarioRepository.findById(usuarioGrupoDTO.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + usuarioGrupoDTO.getIdUsuario()));
        
        // Validar que el grupo exista
        GrupoDespliegue grupoDespliegue = grupoDespliegueRepository.findById(usuarioGrupoDTO.getIdGrupo())
                .orElseThrow(() -> new RuntimeException("Grupo de despliegue no encontrado con id: " + usuarioGrupoDTO.getIdGrupo()));
        
        // Validar que no exista otra relación con el mismo usuario y grupo (excluyendo la actual)
        if (!usuarioGrupo.getUsuario().getIdUsuario().equals(usuarioGrupoDTO.getIdUsuario()) ||
            !usuarioGrupo.getGrupoDespliegue().getIdGrupo().equals(usuarioGrupoDTO.getIdGrupo())) {
            if (usuarioGrupoRepository.existsByUsuario_IdUsuarioAndGrupoDespliegue_IdGrupo(
                    usuarioGrupoDTO.getIdUsuario(), usuarioGrupoDTO.getIdGrupo())) {
                throw new RuntimeException("Ya existe otra asignación entre el usuario y el grupo especificados");
            }
        }
        
        // Validar que el usuario asignador exista (si se proporciona)
        if (usuarioGrupoDTO.getAsignadoPor() != null) {
            Usuario asignadoPor = usuarioRepository.findById(usuarioGrupoDTO.getAsignadoPor())
                    .orElseThrow(() -> new RuntimeException("Usuario asignador no encontrado con id: " + usuarioGrupoDTO.getAsignadoPor()));
            usuarioGrupo.setAsignadoPor(asignadoPor);
        } else {
            usuarioGrupo.setAsignadoPor(null);
        }
        
        usuarioGrupo.setUsuario(usuario);
        usuarioGrupo.setGrupoDespliegue(grupoDespliegue);
        if (usuarioGrupoDTO.getActivo() != null) {
            usuarioGrupo.setActivo(usuarioGrupoDTO.getActivo());
        }
        usuarioGrupo.setNotas(usuarioGrupoDTO.getNotas());
        
        UsuarioGrupo usuarioGrupoActualizado = usuarioGrupoRepository.save(usuarioGrupo);
        return convertirAResponseDTO(usuarioGrupoActualizado);
    }
    
    @Override
    public void eliminar(Integer id) {
        if (!usuarioGrupoRepository.existsById(id)) {
            throw new RuntimeException("Asignación usuario-grupo no encontrada con id: " + id);
        }
        usuarioGrupoRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<UsuarioGrupoResponseDTO> listar() {
        return usuarioGrupoRepository.findAll().stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public UsuarioGrupoResponseDTO listarPorId(Integer id) {
        UsuarioGrupo usuarioGrupo = usuarioGrupoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignación usuario-grupo no encontrada con id: " + id));
        return convertirAResponseDTO(usuarioGrupo);
    }
    
    private UsuarioGrupoResponseDTO convertirAResponseDTO(UsuarioGrupo usuarioGrupo) {
        return new UsuarioGrupoResponseDTO(
                usuarioGrupo.getIdUsuarioGrupo(),
                usuarioGrupo.getUsuario() != null ? usuarioGrupo.getUsuario().getIdUsuario() : null,
                usuarioGrupo.getUsuario() != null ? usuarioGrupo.getUsuario().getNombreCompleto() : null,
                usuarioGrupo.getUsuario() != null ? usuarioGrupo.getUsuario().getEmail() : null,
                usuarioGrupo.getGrupoDespliegue() != null ? usuarioGrupo.getGrupoDespliegue().getIdGrupo() : null,
                usuarioGrupo.getGrupoDespliegue() != null ? usuarioGrupo.getGrupoDespliegue().getNombreGrupo() : null,
                usuarioGrupo.getGrupoDespliegue() != null ? usuarioGrupo.getGrupoDespliegue().getOrdenPrioridad() : null,
                usuarioGrupo.getFechaAsignacion(),
                usuarioGrupo.getAsignadoPor() != null ? usuarioGrupo.getAsignadoPor().getIdUsuario() : null,
                usuarioGrupo.getAsignadoPor() != null ? usuarioGrupo.getAsignadoPor().getNombreCompleto() : null,
                usuarioGrupo.getActivo(),
                usuarioGrupo.getNotas()
        );
    }
}

