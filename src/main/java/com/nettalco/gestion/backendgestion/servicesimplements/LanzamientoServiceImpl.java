package com.nettalco.gestion.backendgestion.servicesimplements;

import com.nettalco.gestion.backendgestion.dtos.LanzamientoDTO;
import com.nettalco.gestion.backendgestion.dtos.LanzamientoResponseDTO;
import com.nettalco.gestion.backendgestion.entities.Aplicacion;
import com.nettalco.gestion.backendgestion.entities.Lanzamiento;
import com.nettalco.gestion.backendgestion.entities.Usuario;
import com.nettalco.gestion.backendgestion.repositories.AplicacionRepository;
import com.nettalco.gestion.backendgestion.repositories.LanzamientoRepository;
import com.nettalco.gestion.backendgestion.repositories.UsuarioRepository;
import com.nettalco.gestion.backendgestion.servicesinterfaces.ILanzamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LanzamientoServiceImpl implements ILanzamientoService {
    
    @Autowired
    private LanzamientoRepository lanzamientoRepository;
    
    @Autowired
    private AplicacionRepository aplicacionRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Override
    public LanzamientoResponseDTO crear(LanzamientoDTO lanzamientoDTO) {
        // Validar que la aplicación exista
        Aplicacion aplicacion = aplicacionRepository.findById(lanzamientoDTO.getIdAplicacion())
                .orElseThrow(() -> new RuntimeException("Aplicación no encontrada con id: " + lanzamientoDTO.getIdAplicacion()));
        
        // Validar que no exista otra versión con el mismo número para la misma aplicación
        if (lanzamientoRepository.existsByAplicacion_IdAplicacionAndVersion(lanzamientoDTO.getIdAplicacion(), lanzamientoDTO.getVersion())) {
            throw new RuntimeException("Ya existe un lanzamiento con la versión " + lanzamientoDTO.getVersion() + 
                                     " para la aplicación con id: " + lanzamientoDTO.getIdAplicacion());
        }
        
        // Validar estado
        String estado = lanzamientoDTO.getEstado() != null ? lanzamientoDTO.getEstado() : "borrador";
        if (!estado.equals("borrador") && !estado.equals("activo") && 
            !estado.equals("deprecado") && !estado.equals("retirado")) {
            throw new RuntimeException("Estado inválido. Debe ser: borrador, activo, deprecado o retirado");
        }
        
        // Validar que el usuario publicador exista (si se proporciona)
        Usuario publicadoPor = null;
        if (lanzamientoDTO.getPublicadoPor() != null) {
            publicadoPor = usuarioRepository.findById(lanzamientoDTO.getPublicadoPor())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + lanzamientoDTO.getPublicadoPor()));
        }
        
        Lanzamiento lanzamiento = new Lanzamiento();
        lanzamiento.setAplicacion(aplicacion);
        lanzamiento.setVersion(lanzamientoDTO.getVersion());
        lanzamiento.setEstado(estado);
        lanzamiento.setFechaLanzamiento(lanzamientoDTO.getFechaLanzamiento());
        lanzamiento.setNotasVersion(lanzamientoDTO.getNotasVersion());
        lanzamiento.setUrlDescarga(lanzamientoDTO.getUrlDescarga());
        lanzamiento.setTamanoArchivo(lanzamientoDTO.getTamanoArchivo());
        lanzamiento.setChecksumSha256(lanzamientoDTO.getChecksumSha256());
        lanzamiento.setEsCritico(lanzamientoDTO.getEsCritico() != null ? lanzamientoDTO.getEsCritico() : false);
        lanzamiento.setRequiereReinicio(lanzamientoDTO.getRequiereReinicio() != null ? lanzamientoDTO.getRequiereReinicio() : false);
        lanzamiento.setPublicadoPor(publicadoPor);
        lanzamiento.setMetadata(lanzamientoDTO.getMetadata());
        
        Lanzamiento lanzamientoGuardado = lanzamientoRepository.save(lanzamiento);
        return convertirAResponseDTO(lanzamientoGuardado);
    }
    
    @Override
    public LanzamientoResponseDTO actualizar(Integer id, LanzamientoDTO lanzamientoDTO) {
        Lanzamiento lanzamiento = lanzamientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lanzamiento no encontrado con id: " + id));
        
        // Validar que la aplicación exista
        Aplicacion aplicacion = aplicacionRepository.findById(lanzamientoDTO.getIdAplicacion())
                .orElseThrow(() -> new RuntimeException("Aplicación no encontrada con id: " + lanzamientoDTO.getIdAplicacion()));
        
        // Validar que no exista otra versión con el mismo número para la misma aplicación (excluyendo el actual)
        if (lanzamientoRepository.existsByAplicacion_IdAplicacionAndVersionAndIdLanzamientoNot(
                lanzamientoDTO.getIdAplicacion(), lanzamientoDTO.getVersion(), id)) {
            throw new RuntimeException("Ya existe otro lanzamiento con la versión " + lanzamientoDTO.getVersion() + 
                                     " para la aplicación con id: " + lanzamientoDTO.getIdAplicacion());
        }
        
        // Validar estado
        if (lanzamientoDTO.getEstado() != null) {
            String estado = lanzamientoDTO.getEstado();
            if (!estado.equals("borrador") && !estado.equals("activo") && 
                !estado.equals("deprecado") && !estado.equals("retirado")) {
                throw new RuntimeException("Estado inválido. Debe ser: borrador, activo, deprecado o retirado");
            }
            lanzamiento.setEstado(estado);
        }
        
        // Validar que el usuario publicador exista (si se proporciona)
        if (lanzamientoDTO.getPublicadoPor() != null) {
            Usuario publicadoPor = usuarioRepository.findById(lanzamientoDTO.getPublicadoPor())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + lanzamientoDTO.getPublicadoPor()));
            lanzamiento.setPublicadoPor(publicadoPor);
        }
        
        lanzamiento.setAplicacion(aplicacion);
        lanzamiento.setVersion(lanzamientoDTO.getVersion());
        if (lanzamientoDTO.getFechaLanzamiento() != null) {
            lanzamiento.setFechaLanzamiento(lanzamientoDTO.getFechaLanzamiento());
        }
        lanzamiento.setNotasVersion(lanzamientoDTO.getNotasVersion());
        lanzamiento.setUrlDescarga(lanzamientoDTO.getUrlDescarga());
        lanzamiento.setTamanoArchivo(lanzamientoDTO.getTamanoArchivo());
        lanzamiento.setChecksumSha256(lanzamientoDTO.getChecksumSha256());
        if (lanzamientoDTO.getEsCritico() != null) {
            lanzamiento.setEsCritico(lanzamientoDTO.getEsCritico());
        }
        if (lanzamientoDTO.getRequiereReinicio() != null) {
            lanzamiento.setRequiereReinicio(lanzamientoDTO.getRequiereReinicio());
        }
        lanzamiento.setMetadata(lanzamientoDTO.getMetadata());
        
        Lanzamiento lanzamientoActualizado = lanzamientoRepository.save(lanzamiento);
        return convertirAResponseDTO(lanzamientoActualizado);
    }
    
    @Override
    public void eliminar(Integer id) {
        if (!lanzamientoRepository.existsById(id)) {
            throw new RuntimeException("Lanzamiento no encontrado con id: " + id);
        }
        lanzamientoRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<LanzamientoResponseDTO> listar() {
        return lanzamientoRepository.findAll().stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public LanzamientoResponseDTO listarPorId(Integer id) {
        Lanzamiento lanzamiento = lanzamientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lanzamiento no encontrado con id: " + id));
        return convertirAResponseDTO(lanzamiento);
    }
    
    private LanzamientoResponseDTO convertirAResponseDTO(Lanzamiento lanzamiento) {
        return new LanzamientoResponseDTO(
                lanzamiento.getIdLanzamiento(),
                lanzamiento.getAplicacion() != null ? lanzamiento.getAplicacion().getIdAplicacion() : null,
                lanzamiento.getAplicacion() != null ? lanzamiento.getAplicacion().getNombreAplicacion() : null,
                lanzamiento.getVersion(),
                lanzamiento.getEstado(),
                lanzamiento.getFechaLanzamiento(),
                lanzamiento.getFechaPublicacion(),
                lanzamiento.getNotasVersion(),
                lanzamiento.getUrlDescarga(),
                lanzamiento.getTamanoArchivo(),
                lanzamiento.getChecksumSha256(),
                lanzamiento.getEsCritico(),
                lanzamiento.getRequiereReinicio(),
                lanzamiento.getPublicadoPor() != null ? lanzamiento.getPublicadoPor().getIdUsuario() : null,
                lanzamiento.getPublicadoPor() != null ? lanzamiento.getPublicadoPor().getNombreCompleto() : null,
                lanzamiento.getMetadata()
        );
    }
}

