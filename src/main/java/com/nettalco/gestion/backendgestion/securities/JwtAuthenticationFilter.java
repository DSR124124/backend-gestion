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
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            
            try {
                if (jwtUtil.validateToken(token)) {
                    String username = jwtUtil.extractUsername(token);
                    Integer idRol = jwtUtil.extractIdRol(token);
                    
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + (idRol != null ? idRol : "USER")))
                    );
                    
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    // Token inválido - limpiar contexto y continuar (Spring Security manejará el 403)
                    SecurityContextHolder.clearContext();
                }
            } catch (Exception e) {
                // Error al validar el token - limpiar contexto
                SecurityContextHolder.clearContext();
                logger.error("Error validando token JWT: {}", e.getMessage(), e);
            }
        } else {
            // No hay token - limpiar contexto (Spring Security manejará el 403)
            SecurityContextHolder.clearContext();
        }
        
        chain.doFilter(request, response);
    }
}

