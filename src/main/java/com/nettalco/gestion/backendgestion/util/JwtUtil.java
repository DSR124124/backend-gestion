package com.nettalco.gestion.backendgestion.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    
    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.expiration}")
    private Long expiration;
    
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            logger.warn("Token JWT expirado al extraer claims: {}", e.getMessage());
            throw e;
        } catch (SignatureException e) {
            logger.error("Error de firma al extraer claims del token JWT. Posible causa: Secret key diferente. Error: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error al extraer claims del token JWT. Tipo: {}, Mensaje: {}", e.getClass().getSimpleName(), e.getMessage());
            throw e;
        }
    }
    
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }
    
    public String generateToken(String username, Integer idUsuario, Integer idRol, String nombreRol) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("idUsuario", idUsuario);
        claims.put("idRol", idRol);
        claims.put("nombreRol", nombreRol);
        
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }
    
    public Boolean validateToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            Date expiration = claims.getExpiration();
            Date now = new Date();
            boolean isValid = expiration.after(now);
            
            if (!isValid) {
                logger.warn("Token JWT expirado. Fecha de expiración: {}, Fecha actual: {}", expiration, now);
            }
            
            return isValid;
        } catch (ExpiredJwtException e) {
            logger.warn("Token JWT expirado: {}", e.getMessage());
            return false;
        } catch (SignatureException e) {
            logger.error("Error de firma en token JWT. Posible causa: Secret key diferente. Error: {}", e.getMessage());
            return false;
        } catch (io.jsonwebtoken.security.InvalidKeyException e) {
            logger.error("Clave inválida para validar token JWT: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            logger.error("Error al validar token JWT. Tipo: {}, Mensaje: {}", e.getClass().getSimpleName(), e.getMessage());
            return false;
        }
    }
    
    public Integer extractIdRol(String token) {
        return extractAllClaims(token).get("idRol", Integer.class);
    }
}

