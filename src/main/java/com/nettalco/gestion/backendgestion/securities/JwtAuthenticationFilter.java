package com.nettalco.gestion.backendgestion.securities;

import com.nettalco.gestion.backendgestion.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        if (path == null) {
            return false;
        }
        
        // No aplicar el filtro a peticiones OPTIONS (preflight CORS)
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        
        // No aplicar el filtro a endpoints públicos
        // El path puede incluir o no el context-path dependiendo de la configuración
        return path.startsWith("/gestion/api/auth/") || path.startsWith("/api/auth/");
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        
        // Permitir peticiones OPTIONS sin autenticación
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            chain.doFilter(request, response);
            return;
        }
        
        String authHeader = request.getHeader("Authorization");
        
        logger.info("Procesando petición: {} {} - Authorization header presente: {}", 
            request.getMethod(), request.getRequestURI(), authHeader != null);
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            
            // Log parcial del token para debugging (solo primeros y últimos caracteres)
            String tokenPreview = token.length() > 20 
                ? token.substring(0, 10) + "..." + token.substring(token.length() - 10)
                : "***";
            logger.info("Token recibido (preview): {}", tokenPreview);
            
            try {
                if (jwtUtil.validateToken(token)) {
                    String username = jwtUtil.extractUsername(token);
                    Integer idRol = jwtUtil.extractIdRol(token);
                    
                    logger.info("Token válido para usuario: {} con rol: {}", username, idRol);
                    
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + (idRol != null ? idRol : "USER")))
                    );
                    
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    
                    // Verificar que la autenticación se estableció correctamente
                    var context = SecurityContextHolder.getContext();
                    var auth = context.getAuthentication();
                    if (auth != null && auth.isAuthenticated()) {
                        logger.info("Autenticación establecida correctamente en SecurityContext para usuario: {} con autoridades: {}", 
                            username, auth.getAuthorities());
                    } else {
                        logger.error("ERROR: La autenticación NO se estableció correctamente en SecurityContext para usuario: {}", username);
                    }
                } else {
                    // Token inválido - limpiar contexto y continuar (Spring Security manejará el 403)
                    SecurityContextHolder.clearContext();
                    logger.warn("Token JWT inválido o expirado para la petición: {} {} - Token preview: {}", 
                        request.getMethod(), request.getRequestURI(), tokenPreview);
                }
            } catch (Exception e) {
                // Error al validar el token - limpiar contexto
                SecurityContextHolder.clearContext();
                logger.error("Error validando token JWT para {} {} - Token preview: {} - Error: {}", 
                    request.getMethod(), request.getRequestURI(), tokenPreview, e.getMessage(), e);
            }
        } else {
            // No hay token - limpiar contexto (Spring Security manejará el 403)
            SecurityContextHolder.clearContext();
            if (authHeader != null) {
                logger.warn("Header Authorization presente pero no tiene formato Bearer para la petición: {} {} - Header: {}", 
                    request.getMethod(), request.getRequestURI(), authHeader.length() > 50 ? authHeader.substring(0, 50) + "..." : authHeader);
            } else {
                logger.warn("No se encontró header Authorization en la petición: {} {}", 
                    request.getMethod(), request.getRequestURI());
            }
        }
        
        chain.doFilter(request, response);
    }
}

