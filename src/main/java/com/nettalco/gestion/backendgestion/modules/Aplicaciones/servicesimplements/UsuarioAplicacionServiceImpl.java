package com.nettalco.gestion.backendgestion.modules.Aplicaciones.servicesimplements;

import com.nettalco.gestion.backendgestion.modules.Aplicaciones.dtos.UsuarioAplicacionDTO;
import com.nettalco.gestion.backendgestion.modules.Aplicaciones.dtos.UsuarioAplicacionResponseDTO;
import com.nettalco.gestion.backendgestion.modules.Aplicaciones.entities.Aplicacion;
import com.nettalco.gestion.backendgestion.modules.Aplicaciones.entities.UsuarioAplicacion;
import com.nettalco.gestion.backendgestion.modules.Aplicaciones.repositories.AplicacionRepository;
import com.nettalco.gestion.backendgestion.modules.Aplicaciones.repositories.UsuarioAplicacionRepository;
import com.nettalco.gestion.backendgestion.modules.Usuarios.entities.Usuario;
import com.nettalco.gestion.backendgestion.modules.Usuarios.repositories.UsuarioRepository;
import com.nettalco.gestion.backendgestion.modules.Aplicaciones.servicesinterfaces.IUsuarioAplicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsuarioAplicacionServiceImpl implements IUsuarioAplicacionService {
    
    @Autowired
    private UsuarioAplicacionRepository usuarioAplicacionRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private AplicacionRepository aplicacionRepository;
    
    @Override
    public UsuarioAplicacionResponseDTO crear(UsuarioAplicacionDTO usuarioAplicacionDTO) {
        // Validar que el usuario exista
        Usuario usuario = usuarioRepository.findById(usuarioAplicacionDTO.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + usuarioAplicacionDTO.getIdUsuario()));
        
        // Validar que la aplicación exista
        Aplicacion aplicacion = aplicacionRepository.findById(usuarioAplicacionDTO.getIdAplicacion())
                .orElseThrow(() -> new RuntimeException("Aplicación no encontrada con id: " + usuarioAplicacionDTO.getIdAplicacion()));
        
        // Validar que no exista ya esta relación
        if (usuarioAplicacionRepository.existsByUsuario_IdUsuarioAndAplicacion_IdAplicacion(
                usuarioAplicacionDTO.getIdUsuario(), usuarioAplicacionDTO.getIdAplicacion())) {
            throw new RuntimeException("Ya existe una relación entre el usuario y la aplicación especificados");
        }
        
        // Validar que el usuario registrador exista (si se proporciona)
        Usuario registradoPor = null;
        if (usuarioAplicacionDTO.getRegistradoPor() != null) {
            registradoPor = usuarioRepository.findById(usuarioAplicacionDTO.getRegistradoPor())
                    .orElseThrow(() -> new RuntimeException("Usuario registrador no encontrado con id: " + usuarioAplicacionDTO.getRegistradoPor()));
        }
        
        UsuarioAplicacion usuarioAplicacion = new UsuarioAplicacion();
        usuarioAplicacion.setUsuario(usuario);
        usuarioAplicacion.setAplicacion(aplicacion);
        usuarioAplicacion.setFechaUltimoAcceso(usuarioAplicacionDTO.getFechaUltimoAcceso());
        usuarioAplicacion.setLicenciaActiva(usuarioAplicacionDTO.getLicenciaActiva() != null ? usuarioAplicacionDTO.getLicenciaActiva() : true);
        usuarioAplicacion.setFechaExpiracionLicencia(usuarioAplicacionDTO.getFechaExpiracionLicencia());
        usuarioAplicacion.setRegistradoPor(registradoPor);
        usuarioAplicacion.setNotas(usuarioAplicacionDTO.getNotas());
        
        UsuarioAplicacion usuarioAplicacionGuardado = usuarioAplicacionRepository.save(usuarioAplicacion);
        return convertirAResponseDTO(usuarioAplicacionGuardado);
    }
    
    @Override
    public UsuarioAplicacionResponseDTO actualizar(Integer id, UsuarioAplicacionDTO usuarioAplicacionDTO) {
        UsuarioAplicacion usuarioAplicacion = usuarioAplicacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relación usuario-aplicación no encontrada con id: " + id));
        
        // Validar que el usuario exista
        Usuario usuario = usuarioRepository.findById(usuarioAplicacionDTO.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + usuarioAplicacionDTO.getIdUsuario()));
        
        // Validar que la aplicación exista
        Aplicacion aplicacion = aplicacionRepository.findById(usuarioAplicacionDTO.getIdAplicacion())
                .orElseThrow(() -> new RuntimeException("Aplicación no encontrada con id: " + usuarioAplicacionDTO.getIdAplicacion()));
        
        // Validar que no exista otra relación con el mismo usuario y aplicación (excluyendo la actual)
        if (!usuarioAplicacion.getUsuario().getIdUsuario().equals(usuarioAplicacionDTO.getIdUsuario()) ||
            !usuarioAplicacion.getAplicacion().getIdAplicacion().equals(usuarioAplicacionDTO.getIdAplicacion())) {
            if (usuarioAplicacionRepository.existsByUsuario_IdUsuarioAndAplicacion_IdAplicacion(
                    usuarioAplicacionDTO.getIdUsuario(), usuarioAplicacionDTO.getIdAplicacion())) {
                throw new RuntimeException("Ya existe otra relación entre el usuario y la aplicación especificados");
            }
        }
        
        // Validar que el usuario registrador exista (si se proporciona)
        if (usuarioAplicacionDTO.getRegistradoPor() != null) {
            Usuario registradoPor = usuarioRepository.findById(usuarioAplicacionDTO.getRegistradoPor())
                    .orElseThrow(() -> new RuntimeException("Usuario registrador no encontrado con id: " + usuarioAplicacionDTO.getRegistradoPor()));
            usuarioAplicacion.setRegistradoPor(registradoPor);
        } else {
            usuarioAplicacion.setRegistradoPor(null);
        }
        
        usuarioAplicacion.setUsuario(usuario);
        usuarioAplicacion.setAplicacion(aplicacion);
        if (usuarioAplicacionDTO.getFechaUltimoAcceso() != null) {
            usuarioAplicacion.setFechaUltimoAcceso(usuarioAplicacionDTO.getFechaUltimoAcceso());
        }
        if (usuarioAplicacionDTO.getLicenciaActiva() != null) {
            usuarioAplicacion.setLicenciaActiva(usuarioAplicacionDTO.getLicenciaActiva());
        }
        usuarioAplicacion.setFechaExpiracionLicencia(usuarioAplicacionDTO.getFechaExpiracionLicencia());
        usuarioAplicacion.setNotas(usuarioAplicacionDTO.getNotas());
        
        UsuarioAplicacion usuarioAplicacionActualizado = usuarioAplicacionRepository.save(usuarioAplicacion);
        return convertirAResponseDTO(usuarioAplicacionActualizado);
    }
    
    @Override
    public void eliminar(Integer id) {
        if (!usuarioAplicacionRepository.existsById(id)) {
            throw new RuntimeException("Relación usuario-aplicación no encontrada con id: " + id);
        }
        usuarioAplicacionRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<UsuarioAplicacionResponseDTO> listar() {
        return usuarioAplicacionRepository.findAll().stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public UsuarioAplicacionResponseDTO listarPorId(Integer id) {
        UsuarioAplicacion usuarioAplicacion = usuarioAplicacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relación usuario-aplicación no encontrada con id: " + id));
        return convertirAResponseDTO(usuarioAplicacion);
    }
    
    private UsuarioAplicacionResponseDTO convertirAResponseDTO(UsuarioAplicacion usuarioAplicacion) {
        return new UsuarioAplicacionResponseDTO(
                usuarioAplicacion.getIdUsuarioAplicacion(),
                usuarioAplicacion.getUsuario() != null ? usuarioAplicacion.getUsuario().getIdUsuario() : null,
                usuarioAplicacion.getUsuario() != null ? usuarioAplicacion.getUsuario().getNombreCompleto() : null,
                usuarioAplicacion.getUsuario() != null ? usuarioAplicacion.getUsuario().getEmail() : null,
                usuarioAplicacion.getAplicacion() != null ? usuarioAplicacion.getAplicacion().getIdAplicacion() : null,
                usuarioAplicacion.getAplicacion() != null ? usuarioAplicacion.getAplicacion().getNombreAplicacion() : null,
                usuarioAplicacion.getFechaRegistro(),
                usuarioAplicacion.getFechaUltimoAcceso(),
                usuarioAplicacion.getLicenciaActiva(),
                usuarioAplicacion.getFechaExpiracionLicencia(),
                usuarioAplicacion.getRegistradoPor() != null ? usuarioAplicacion.getRegistradoPor().getIdUsuario() : null,
                usuarioAplicacion.getRegistradoPor() != null ? usuarioAplicacion.getRegistradoPor().getNombreCompleto() : null,
                usuarioAplicacion.getNotas()
        );
    }
}

