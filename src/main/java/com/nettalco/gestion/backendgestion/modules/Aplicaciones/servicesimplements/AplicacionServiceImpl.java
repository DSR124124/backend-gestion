package com.nettalco.gestion.backendgestion.modules.Aplicaciones.servicesimplements;

import com.nettalco.gestion.backendgestion.modules.Aplicaciones.dtos.AplicacionDTO;
import com.nettalco.gestion.backendgestion.modules.Aplicaciones.dtos.AplicacionResponseDTO;
import com.nettalco.gestion.backendgestion.modules.Aplicaciones.entities.Aplicacion;
import com.nettalco.gestion.backendgestion.modules.Aplicaciones.repositories.AplicacionRepository;
import com.nettalco.gestion.backendgestion.modules.Usuarios.entities.Usuario;
import com.nettalco.gestion.backendgestion.modules.Usuarios.repositories.UsuarioRepository;
import com.nettalco.gestion.backendgestion.modules.Aplicaciones.servicesinterfaces.IAplicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AplicacionServiceImpl implements IAplicacionService {
    
    @Autowired
    private AplicacionRepository aplicacionRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Override
    public AplicacionResponseDTO crear(AplicacionDTO aplicacionDTO) {
        // Validar que el nombre de la aplicación no exista
        if (aplicacionRepository.existsByNombreAplicacion(aplicacionDTO.getNombreAplicacion())) {
            throw new RuntimeException("Ya existe una aplicación con el nombre: " + aplicacionDTO.getNombreAplicacion());
        }
        
        // Validar que el código de producto no exista (si se proporciona)
        if (aplicacionDTO.getCodigoProducto() != null && !aplicacionDTO.getCodigoProducto().trim().isEmpty()) {
            if (aplicacionRepository.existsByCodigoProducto(aplicacionDTO.getCodigoProducto())) {
                throw new RuntimeException("Ya existe una aplicación con el código de producto: " + aplicacionDTO.getCodigoProducto());
            }
        }
        
        // Validar que el responsable exista (si se proporciona)
        Usuario responsable = null;
        if (aplicacionDTO.getResponsableId() != null) {
            responsable = usuarioRepository.findById(aplicacionDTO.getResponsableId())
                    .orElseThrow(() -> new RuntimeException("Usuario responsable no encontrado con id: " + aplicacionDTO.getResponsableId()));
        }
        
        Aplicacion aplicacion = new Aplicacion();
        aplicacion.setNombreAplicacion(aplicacionDTO.getNombreAplicacion());
        aplicacion.setDescripcion(aplicacionDTO.getDescripcion());
        aplicacion.setCodigoProducto(aplicacionDTO.getCodigoProducto());
        aplicacion.setRepositorioUrl(aplicacionDTO.getRepositorioUrl());
        aplicacion.setUrl(aplicacionDTO.getUrl());
        aplicacion.setResponsable(responsable);
        aplicacion.setActivo(aplicacionDTO.getActivo() != null ? aplicacionDTO.getActivo() : true);
        
        Aplicacion aplicacionGuardada = aplicacionRepository.save(aplicacion);
        return convertirAResponseDTO(aplicacionGuardada);
    }
    
    @Override
    public AplicacionResponseDTO actualizar(Integer id, AplicacionDTO aplicacionDTO) {
        Aplicacion aplicacion = aplicacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aplicación no encontrada con id: " + id));
        
        // Validar que el nombre de la aplicación no exista en otra aplicación
        if (aplicacionRepository.existsByNombreAplicacionAndIdAplicacionNot(aplicacionDTO.getNombreAplicacion(), id)) {
            throw new RuntimeException("Ya existe otra aplicación con el nombre: " + aplicacionDTO.getNombreAplicacion());
        }
        
        // Validar que el código de producto no exista en otra aplicación (si se proporciona)
        if (aplicacionDTO.getCodigoProducto() != null && !aplicacionDTO.getCodigoProducto().trim().isEmpty()) {
            if (aplicacionRepository.existsByCodigoProductoAndIdAplicacionNot(aplicacionDTO.getCodigoProducto(), id)) {
                throw new RuntimeException("Ya existe otra aplicación con el código de producto: " + aplicacionDTO.getCodigoProducto());
            }
        }
        
        // Validar que el responsable exista (si se proporciona)
        Usuario responsable = null;
        if (aplicacionDTO.getResponsableId() != null) {
            responsable = usuarioRepository.findById(aplicacionDTO.getResponsableId())
                    .orElseThrow(() -> new RuntimeException("Usuario responsable no encontrado con id: " + aplicacionDTO.getResponsableId()));
        }
        
        aplicacion.setNombreAplicacion(aplicacionDTO.getNombreAplicacion());
        aplicacion.setDescripcion(aplicacionDTO.getDescripcion());
        aplicacion.setCodigoProducto(aplicacionDTO.getCodigoProducto());
        aplicacion.setRepositorioUrl(aplicacionDTO.getRepositorioUrl());
        aplicacion.setUrl(aplicacionDTO.getUrl());
        aplicacion.setResponsable(responsable);
        if (aplicacionDTO.getActivo() != null) {
            aplicacion.setActivo(aplicacionDTO.getActivo());
        }
        
        Aplicacion aplicacionActualizada = aplicacionRepository.save(aplicacion);
        return convertirAResponseDTO(aplicacionActualizada);
    }
    
    @Override
    public void eliminar(Integer id) {
        if (!aplicacionRepository.existsById(id)) {
            throw new RuntimeException("Aplicación no encontrada con id: " + id);
        }
        aplicacionRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AplicacionResponseDTO> listar() {
        return aplicacionRepository.findAll().stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public AplicacionResponseDTO listarPorId(Integer id) {
        Aplicacion aplicacion = aplicacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aplicación no encontrada con id: " + id));
        return convertirAResponseDTO(aplicacion);
    }
    
    @Override
    @Transactional(readOnly = true)
    public AplicacionResponseDTO listarPorCodigo(String codigo) {
        Aplicacion aplicacion = aplicacionRepository.findByCodigoProducto(codigo)
                .orElseThrow(() -> new RuntimeException("Aplicación no encontrada con código: " + codigo));
        return convertirAResponseDTO(aplicacion);
    }
    
    private AplicacionResponseDTO convertirAResponseDTO(Aplicacion aplicacion) {
        return new AplicacionResponseDTO(
                aplicacion.getIdAplicacion(),
                aplicacion.getNombreAplicacion(),
                aplicacion.getDescripcion(),
                aplicacion.getCodigoProducto(),
                aplicacion.getRepositorioUrl(),
                aplicacion.getUrl(),
                aplicacion.getResponsable() != null ? aplicacion.getResponsable().getIdUsuario() : null,
                aplicacion.getResponsable() != null ? aplicacion.getResponsable().getNombreCompleto() : null,
                aplicacion.getActivo(),
                aplicacion.getFechaCreacion(),
                aplicacion.getFechaModificacion()
        );
    }
}

