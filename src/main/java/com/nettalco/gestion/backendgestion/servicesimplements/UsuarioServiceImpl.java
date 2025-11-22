package com.nettalco.gestion.backendgestion.servicesimplements;

import com.nettalco.gestion.backendgestion.dtos.UsuarioDTO;
import com.nettalco.gestion.backendgestion.dtos.UsuarioResponseDTO;
import com.nettalco.gestion.backendgestion.entities.Rol;
import com.nettalco.gestion.backendgestion.entities.Usuario;
import com.nettalco.gestion.backendgestion.repositories.RolRepository;
import com.nettalco.gestion.backendgestion.repositories.UsuarioRepository;
import com.nettalco.gestion.backendgestion.servicesinterfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsuarioServiceImpl implements IUsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private RolRepository rolRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public UsuarioResponseDTO crear(UsuarioDTO usuarioDTO) {
        // Validar que el username no exista
        if (usuarioRepository.existsByUsername(usuarioDTO.getUsername())) {
            throw new RuntimeException("Ya existe un usuario con el username: " + usuarioDTO.getUsername());
        }
        
        // Validar que el email no exista
        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con el email: " + usuarioDTO.getEmail());
        }
        
        // Validar que el rol exista
        Rol rol = rolRepository.findById(usuarioDTO.getIdRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + usuarioDTO.getIdRol()));
        
        Usuario usuario = new Usuario();
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setEmail(usuarioDTO.getEmail());
        
        // Hash de la contraseña usando BCrypt
        usuario.setPasswordHash(passwordEncoder.encode(usuarioDTO.getPassword()));
        
        usuario.setNombreCompleto(usuarioDTO.getNombreCompleto());
        usuario.setRol(rol);
        usuario.setActivo(usuarioDTO.getActivo() != null ? usuarioDTO.getActivo() : true);
        usuario.setMetadata(usuarioDTO.getMetadata());
        
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return convertirAResponseDTO(usuarioGuardado);
    }
    
    @Override
    public UsuarioResponseDTO actualizar(Integer id, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
        
        // Validar que el username no exista en otro usuario
        if (usuarioRepository.existsByUsernameAndIdUsuarioNot(usuarioDTO.getUsername(), id)) {
            throw new RuntimeException("Ya existe otro usuario con el username: " + usuarioDTO.getUsername());
        }
        
        // Validar que el email no exista en otro usuario
        if (usuarioRepository.existsByEmailAndIdUsuarioNot(usuarioDTO.getEmail(), id)) {
            throw new RuntimeException("Ya existe otro usuario con el email: " + usuarioDTO.getEmail());
        }
        
        // Validar que el rol exista
        Rol rol = rolRepository.findById(usuarioDTO.getIdRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + usuarioDTO.getIdRol()));
        
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setEmail(usuarioDTO.getEmail());
        
        // Actualizar contraseña solo si se proporciona
        if (usuarioDTO.getPassword() != null && !usuarioDTO.getPassword().isEmpty()) {
            usuario.setPasswordHash(passwordEncoder.encode(usuarioDTO.getPassword()));
        }
        
        usuario.setNombreCompleto(usuarioDTO.getNombreCompleto());
        usuario.setRol(rol);
        if (usuarioDTO.getActivo() != null) {
            usuario.setActivo(usuarioDTO.getActivo());
        }
        usuario.setMetadata(usuarioDTO.getMetadata());
        
        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        return convertirAResponseDTO(usuarioActualizado);
    }
    
    @Override
    public void eliminar(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }
        usuarioRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listar() {
        return usuarioRepository.findAll().stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO listarPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
        return convertirAResponseDTO(usuario);
    }
    
    private UsuarioResponseDTO convertirAResponseDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getIdUsuario(),
                usuario.getUsername(),
                usuario.getEmail(),
                usuario.getNombreCompleto(),
                usuario.getRol() != null ? usuario.getRol().getIdRol() : null,
                usuario.getRol() != null ? usuario.getRol().getNombreRol() : null,
                usuario.getActivo(),
                usuario.getFechaCreacion(),
                usuario.getFechaUltimoAcceso(),
                usuario.getMetadata()
        );
    }
}

