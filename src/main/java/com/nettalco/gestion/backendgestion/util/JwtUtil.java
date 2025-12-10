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
            logger.error("❌ Token JWT EXPIRADO al extraer claims. Fecha expiración: {}, Fecha actual: {}", 
                e.getClaims().getExpiration(), new Date());
            throw e;
        } catch (SignatureException | io.jsonwebtoken.security.InvalidKeyException e) {
            logger.error("❌ ERROR DE FIRMA al extraer claims del token JWT. Posible causa: Secret key diferente.");
            logger.error("   Tipo de excepción: {}", e.getClass().getName());
            logger.error("   Mensaje: {}", e.getMessage());
            logger.error("   Secret key configurada (primeros 20 chars): {}", 
                secret != null && secret.length() > 20 ? secret.substring(0, 20) + "..." : "null o vacía");
            throw e;
        } catch (io.jsonwebtoken.JwtException e) {
            logger.error("❌ Error JWT al extraer claims. Tipo: {}, Mensaje: {}", e.getClass().getSimpleName(), e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("❌ Error inesperado al extraer claims del token JWT. Tipo: {}, Mensaje: {}", 
                e.getClass().getSimpleName(), e.getMessage(), e);
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
            logger.error("❌ Token JWT EXPIRADO. Fecha expiración: {}, Fecha actual: {}", 
                e.getClaims().getExpiration(), new Date());
            return false;
        } catch (SignatureException | io.jsonwebtoken.security.InvalidKeyException e) {
            logger.error("❌ ERROR DE FIRMA en token JWT. Posible causa: Secret key diferente.");
            logger.error("   Tipo: {}, Mensaje: {}", e.getClass().getSimpleName(), e.getMessage());
            return false;
        } catch (io.jsonwebtoken.JwtException e) {
            logger.error("❌ Error JWT al validar token. Tipo: {}, Mensaje: {}", e.getClass().getSimpleName(), e.getMessage());
            return false;
        } catch (Exception e) {
            logger.error("❌ Error inesperado al validar token JWT. Tipo: {}, Mensaje: {}", 
                e.getClass().getSimpleName(), e.getMessage(), e);
            return false;
        }
    }
    
    public Integer extractIdRol(String token) {
        return extractAllClaims(token).get("idRol", Integer.class);
    }
}

