package com.nettalco.gestion.backendgestion.core.securities;

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

import com.nettalco.gestion.backendgestion.core.util.JwtUtil;

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
        String requestPath = request.getRequestURI();
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7).trim();
            
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
                    logger.error("Token JWT inválido o expirado para path: {}", requestPath);
                }
            } catch (io.jsonwebtoken.ExpiredJwtException e) {
                // Token expirado - limpiar contexto
                SecurityContextHolder.clearContext();
                logger.error("Token JWT expirado para path: {} - Expiró: {}", requestPath, e.getClaims().getExpiration());
            } catch (io.jsonwebtoken.security.SignatureException | io.jsonwebtoken.security.InvalidKeyException e) {
                // Error de firma - posible problema con secret key
                SecurityContextHolder.clearContext();
                logger.error("Error de firma en token JWT para path: {} - {}", requestPath, e.getMessage());
            } catch (io.jsonwebtoken.JwtException e) {
                // Otro error JWT
                SecurityContextHolder.clearContext();
                logger.error("Error JWT al validar token para path {}: {}", requestPath, e.getMessage());
            } catch (Exception e) {
                // Error inesperado al validar el token - limpiar contexto
                SecurityContextHolder.clearContext();
                logger.error("Excepción inesperada al validar token JWT para path {}: {}", requestPath, e.getMessage(), e);
            }
        } else {
            // No hay token - limpiar contexto (Spring Security manejará el 403)
            // Solo loguear si no es un health check
            if (!requestPath.contains("/health")) {
                SecurityContextHolder.clearContext();
            } else {
                SecurityContextHolder.clearContext();
            }
        }
        
        chain.doFilter(request, response);
    }
}

