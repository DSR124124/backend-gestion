package com.nettalco.gestion.backendgestion.servicesimplements;

import com.nettalco.gestion.backendgestion.dtos.LoginDTO;
import com.nettalco.gestion.backendgestion.dtos.LoginResponseDTO;
import com.nettalco.gestion.backendgestion.entities.Usuario;
import com.nettalco.gestion.backendgestion.repositories.UsuarioRepository;
import com.nettalco.gestion.backendgestion.servicesinterfaces.IAuthenticationService;
import com.nettalco.gestion.backendgestion.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class AuthenticationServiceImpl implements IAuthenticationService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Override
    public LoginResponseDTO login(LoginDTO loginDTO) {
        // Buscar usuario por username o email
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(loginDTO.getUsernameOrEmail());
        
        if (usuarioOpt.isEmpty()) {
            usuarioOpt = usuarioRepository.findByEmail(loginDTO.getUsernameOrEmail());
        }
        
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Credenciales inválidas");
        }
        
        Usuario usuario = usuarioOpt.get();
        
        // Verificar si el usuario está activo
        if (!usuario.getActivo()) {
            throw new RuntimeException("Usuario inactivo");
        }
        
        // Verificar contraseña
        if (!passwordEncoder.matches(loginDTO.getPassword(), usuario.getPasswordHash())) {
            throw new RuntimeException("Credenciales inválidas");
        }
        
        // Actualizar fecha de último acceso
        usuario.setFechaUltimoAcceso(LocalDateTime.now());
        usuarioRepository.save(usuario);
        
        String token = jwtUtil.generateToken(
                usuario.getUsername(),
                usuario.getIdUsuario(),
                usuario.getRol() != null ? usuario.getRol().getIdRol() : null,
                usuario.getRol() != null ? usuario.getRol().getNombreRol() : null
        );
        
        LoginResponseDTO response = new LoginResponseDTO();
        response.setToken(token);
        response.setIdUsuario(usuario.getIdUsuario());
        response.setUsername(usuario.getUsername());
        response.setEmail(usuario.getEmail());
        response.setNombreCompleto(usuario.getNombreCompleto());
        response.setIdRol(usuario.getRol() != null ? usuario.getRol().getIdRol() : null);
        response.setNombreRol(usuario.getRol() != null ? usuario.getRol().getNombreRol() : null);
        response.setFechaUltimoAcceso(usuario.getFechaUltimoAcceso());
        
        return response;
    }
}

