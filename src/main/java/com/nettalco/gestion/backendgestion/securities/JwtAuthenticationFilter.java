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

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        if (path == null) {
            return false;
        }
        
        // Normalizar el path (remover /gestion si existe)
        String normalizedPath = path;
        if (path.startsWith("/gestion")) {
            normalizedPath = path.substring("/gestion".length());
            if (normalizedPath.isEmpty()) {
                normalizedPath = "/";
            }
        }
        
        // No aplicar el filtro a endpoints públicos
        return normalizedPath.equals("/") ||
               normalizedPath.equals("/api") ||
               normalizedPath.equals("/api/health") ||
               normalizedPath.startsWith("/api/auth/") ||
               normalizedPath.startsWith("/actuator/") ||
               normalizedPath.startsWith("/error") ||
               path.equals("/gestion") ||
               path.equals("/gestion/") ||
               path.contains("/api/health") ||
               path.contains("/api/auth/") ||
               path.contains("/error");
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        
        String authHeader = request.getHeader("Authorization");
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            
            if (jwtUtil.validateToken(token)) {
                String username = jwtUtil.extractUsername(token);
                Integer idRol = jwtUtil.extractIdRol(token);
                
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + (idRol != null ? idRol : "USER")))
                );
                
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        
        chain.doFilter(request, response);
    }
}

