package com.nettalco.gestion.backendgestion.modules.Usuarios.servicesimplements;

import com.nettalco.gestion.backendgestion.core.util.JwtUtil;
import com.nettalco.gestion.backendgestion.modules.Usuarios.dtos.LoginDTO;
import com.nettalco.gestion.backendgestion.modules.Usuarios.dtos.LoginResponseDTO;
import com.nettalco.gestion.backendgestion.modules.Aplicaciones.entities.Aplicacion;
import com.nettalco.gestion.backendgestion.modules.Aplicaciones.entities.UsuarioAplicacion;
import com.nettalco.gestion.backendgestion.modules.Aplicaciones.repositories.AplicacionRepository;
import com.nettalco.gestion.backendgestion.modules.Aplicaciones.repositories.UsuarioAplicacionRepository;
import com.nettalco.gestion.backendgestion.modules.Usuarios.entities.Usuario;
import com.nettalco.gestion.backendgestion.modules.Usuarios.repositories.UsuarioRepository;
import com.nettalco.gestion.backendgestion.modules.Usuarios.servicesinterfaces.IAuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
        String appCodeValue = loginDTO.getAppCode();
        boolean isAppLogin = appCodeValue != null && !appCodeValue.trim().isEmpty();
        Aplicacion aplicacion = null;
        UsuarioAplicacion usuarioAplicacion = null;
        
        // 1. Si es login desde aplicación externa, validar app PRIMERO (OBLIGATORIO)
        if (isAppLogin) {
            String codigoProducto = appCodeValue.trim();
            Optional<Aplicacion> aplicacionOpt = aplicacionRepository.findByCodigoProducto(codigoProducto);
            
            if (aplicacionOpt.isEmpty()) {
                throw new RuntimeException("La aplicación no está registrada en el sistema");
            }
            
            aplicacion = aplicacionOpt.get();
            
            if (aplicacion.getActivo() == null || !aplicacion.getActivo()) {
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
        
        // 5. Si es login desde aplicación externa, OBLIGATORIO validar vinculación y licencia
        // Esta validación es CRÍTICA y NO puede omitirse - DEBE ejecutarse ANTES de generar el token
        // IMPORTANTE: Si appCode está presente, esta validación DEBE ejecutarse sin excepciones
        if (isAppLogin) {
            // Asegurar que la aplicación fue encontrada (doble verificación)
            if (aplicacion == null) {
                throw new RuntimeException("Error crítico: La aplicación no fue encontrada. No se puede continuar.");
            }
            
            // VALIDACIÓN OBLIGATORIA Y CRÍTICA: Verificar que el usuario esté vinculado a la aplicación
            // Esta es la validación más importante - sin vínculo, NO HAY ACCESO
            // Usamos existsBy para verificar primero de forma más eficiente
            boolean existeVinculo = usuarioAplicacionRepository.existsByUsuario_IdUsuarioAndAplicacion_IdAplicacion(
                    usuario.getIdUsuario(), 
                    aplicacion.getIdAplicacion()
            );
            
            // Si no existe el vínculo, DENEGAR acceso inmediatamente - NO generar token bajo ninguna circunstancia
            if (!existeVinculo) {
                throw new RuntimeException("El usuario no tiene acceso a esta aplicación. Contacte al administrador para solicitar acceso.");
            }
            
            // Obtener el vínculo para validar licencia
            Optional<UsuarioAplicacion> usuarioAplicacionOpt = usuarioAplicacionRepository
                    .findByUsuario_IdUsuarioAndAplicacion_IdAplicacion(
                            usuario.getIdUsuario(), 
                            aplicacion.getIdAplicacion()
                    );
            
            // Esta verificación no debería fallar si existeVinculo es true, pero la hacemos por seguridad
            if (usuarioAplicacionOpt.isEmpty()) {
                throw new RuntimeException("Error: No se pudo obtener la información de vinculación del usuario.");
            }
            
            usuarioAplicacion = usuarioAplicacionOpt.get();
            
            // 6. Verificar que la licencia esté activa
            if (usuarioAplicacion.getLicenciaActiva() == null || !usuarioAplicacion.getLicenciaActiva()) {
                throw new RuntimeException("La licencia del usuario para esta aplicación está inactiva");
            }
            
            // 7. Verificar que la licencia no haya expirado
            // IMPORTANTE: La fecha en la base de datos está en UTC, debemos comparar en UTC
            // La licencia está vigente si la fecha de expiración es igual o posterior a AHORA (en UTC)
            LocalDateTime fechaExpiracion = usuarioAplicacion.getFechaExpiracionLicencia();
            if (fechaExpiracion != null) {
                // Convertir ambas fechas a UTC para comparar correctamente
                // La fecha de expiración viene de la BD en UTC (timestamp with time zone)
                ZonedDateTime fechaExpiracionUTC = fechaExpiracion.atZone(ZoneId.of("UTC"));
                // Obtener la fecha/hora actual en UTC
                ZonedDateTime ahoraUTC = ZonedDateTime.now(ZoneId.of("UTC"));
                
                // La licencia está expirada si la fecha de expiración es anterior a ahora (en UTC)
                // Si fechaExpiracionUTC.isBefore(ahoraUTC) = true → la licencia ha expirado
                // Si fechaExpiracionUTC.isBefore(ahoraUTC) = false → la licencia está vigente (es igual o posterior)
                if (fechaExpiracionUTC.isBefore(ahoraUTC)) {
                    throw new RuntimeException("La licencia del usuario para esta aplicación ha expirado");
                }
                // Si llegamos aquí, la fecha de expiración es igual o posterior a ahora (en UTC), la licencia está vigente
            }
            
            // Solo si todas las validaciones pasan, actualizar fecha de último acceso en UsuarioAplicacion
            usuarioAplicacion.setFechaUltimoAcceso(LocalDateTime.now());
            usuarioAplicacionRepository.save(usuarioAplicacion);
        }
        
        // 8. Actualizar fecha de último acceso del usuario (solo si llegamos aquí)
        usuario.setFechaUltimoAcceso(LocalDateTime.now());
        usuarioRepository.save(usuario);
        
        // 9. Generar token JWT (solo si todas las validaciones pasaron, incluyendo vinculación si es app login)
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

