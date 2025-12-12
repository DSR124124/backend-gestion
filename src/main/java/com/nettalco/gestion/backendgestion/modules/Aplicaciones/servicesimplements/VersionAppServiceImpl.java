package com.nettalco.gestion.backendgestion.modules.Aplicaciones.servicesimplements;

import com.nettalco.gestion.backendgestion.modules.Aplicaciones.dtos.VersionAppDTO;
import com.nettalco.gestion.backendgestion.modules.Aplicaciones.servicesinterfaces.IVersionAppService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Implementación del servicio para obtener la versión actual de una aplicación
 */
@Service
@Transactional(readOnly = true)
public class VersionAppServiceImpl implements IVersionAppService {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    @Override
    public VersionAppDTO obtenerVersionActual(String codigoProducto) {
        // Query para obtener la última versión publicada/activa de la aplicación
        String sql = """
            SELECT 
                a.codigo_producto,
                a.nombre_aplicacion,
                l.version,
                l.fecha_publicacion
            FROM aplicaciones a
            LEFT JOIN lanzamientos l ON a.id_aplicacion = l.id_aplicacion 
                AND l.estado IN ('activo', 'publicado')
            WHERE a.codigo_producto = :codigoProducto
              AND a.activo = true
            ORDER BY 
                CAST(SPLIT_PART(COALESCE(l.version, '0.0.0'), '.', 1) AS INTEGER) DESC,
                CAST(SPLIT_PART(COALESCE(l.version, '0.0.0'), '.', 2) AS INTEGER) DESC,
                CAST(SPLIT_PART(COALESCE(l.version, '0.0.0'), '.', 3) AS INTEGER) DESC
            LIMIT 1
            """;
        
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("codigoProducto", codigoProducto);
        
        @SuppressWarnings("unchecked")
        List<Object[]> resultados = query.getResultList();
        
        if (resultados.isEmpty()) {
            return VersionAppDTO.sinVersion(codigoProducto);
        }
        
        Object[] row = resultados.get(0);
        
        VersionAppDTO dto = new VersionAppDTO();
        dto.setCodigoProducto(convertirAString(row[0]));
        dto.setNombreAplicacion(convertirAString(row[1]));
        dto.setVersionActual(convertirAString(row[2]) != null ? convertirAString(row[2]) : "0.0.0");
        
        if (row[3] != null) {
            if (row[3] instanceof Timestamp) {
                dto.setFechaPublicacion(((Timestamp) row[3]).toLocalDateTime().format(FORMATTER));
            } else {
                dto.setFechaPublicacion(row[3].toString());
            }
        }
        
        return dto;
    }
    
    private String convertirAString(Object obj) {
        return obj != null ? obj.toString() : null;
    }
}

