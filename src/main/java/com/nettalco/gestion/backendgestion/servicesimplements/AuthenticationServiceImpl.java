package com.nettalco.gestion.backendgestion.servicesimplements;

import com.nettalco.gestion.backendgestion.dtos.LoginDTO;
import com.nettalco.gestion.backendgestion.dtos.LoginResponseDTO;
import com.nettalco.gestion.backendgestion.entities.Aplicacion;
import com.nettalco.gestion.backendgestion.entities.Usuario;
import com.nettalco.gestion.backendgestion.entities.UsuarioAplicacion;
import com.nettalco.gestion.backendgestion.repositories.AplicacionRepository;
import com.nettalco.gestion.backendgestion.repositories.UsuarioAplicacionRepository;
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
    private AplicacionRepository aplicacionRepository;
    
    @Autowired
    private UsuarioAplicacionRepository usuarioAplicacionRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Override
    public LoginResponseDTO login(LoginDTO loginDTO) {
        // Determinar si es login desde aplicación externa o sistema web
        boolean isAppLogin = loginDTO.getAppCode() != null && !loginDTO.getAppCode().trim().isEmpty();
        Aplicacion aplicacion = null;
        UsuarioAplicacion usuarioAplicacion = null;
        
        // 1. Si es login desde aplicación externa, validar app y vinculación
        if (isAppLogin) {
            Optional<Aplicacion> aplicacionOpt = aplicacionRepository.findByCodigoProducto(loginDTO.getAppCode());
            
            if (aplicacionOpt.isEmpty()) {
                throw new RuntimeException("La aplicación no está registrada en el sistema");
            }
            
            aplicacion = aplicacionOpt.get();
            
            if (!aplicacion.getActivo()) {
                throw new RuntimeException("La aplicación está inactiva");
            }
        }
        
        // 2. Buscar usuario por username o email
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(loginDTO.getUsernameOrEmail());
        
        if (usuarioOpt.isEmpty()) {
            usuarioOpt = usuarioRepository.findByEmail(loginDTO.getUsernameOrEmail());
        }
        
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Credenciales inválidas");
        }
        
        Usuario usuario = usuarioOpt.get();
        
        // 3. Verificar si el usuario está activo
        if (!usuario.getActivo()) {
            throw new RuntimeException("Usuario inactivo");
        }
        
        // 4. Verificar contraseña
        if (!passwordEncoder.matches(loginDTO.getPassword(), usuario.getPasswordHash())) {
            throw new RuntimeException("Credenciales inválidas");
        }
        
        // 5. Si es login desde aplicación externa, validar vinculación y licencia
        if (isAppLogin) {
            Optional<UsuarioAplicacion> usuarioAplicacionOpt = usuarioAplicacionRepository
                    .findByUsuario_IdUsuarioAndAplicacion_IdAplicacion(
                            usuario.getIdUsuario(), 
                            aplicacion.getIdAplicacion()
                    );
            
            if (usuarioAplicacionOpt.isEmpty()) {
                throw new RuntimeException("El usuario no tiene acceso a esta aplicación");
            }
            
            usuarioAplicacion = usuarioAplicacionOpt.get();
            
            // 6. Verificar que la licencia esté activa
            if (!usuarioAplicacion.getLicenciaActiva()) {
                throw new RuntimeException("La licencia del usuario para esta aplicación está inactiva");
            }
            
            // 7. Verificar que la licencia no haya expirado
            if (usuarioAplicacion.getFechaExpiracionLicencia() != null && 
                usuarioAplicacion.getFechaExpiracionLicencia().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("La licencia del usuario para esta aplicación ha expirado");
            }
        }
        
        // 8. Actualizar fecha de último acceso del usuario
        usuario.setFechaUltimoAcceso(LocalDateTime.now());
        usuarioRepository.save(usuario);
        
        // 9. Si es login desde aplicación externa, actualizar fecha de último acceso en UsuarioAplicacion
        if (isAppLogin && usuarioAplicacion != null) {
            usuarioAplicacion.setFechaUltimoAcceso(LocalDateTime.now());
            usuarioAplicacionRepository.save(usuarioAplicacion);
        }
        
        // 10. Generar token JWT
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

