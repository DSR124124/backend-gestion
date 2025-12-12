package com.nettalco.gestion.backendgestion.modules.Lanzamientos.servicesimplements;

import com.nettalco.gestion.backendgestion.modules.Lanzamientos.dtos.VerificarActualizacionDTO;
import com.nettalco.gestion.backendgestion.modules.Lanzamientos.servicesinterfaces.IVerificarActualizacionService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementación del servicio para verificar actualizaciones de apps móviles
 */
@Service
@Transactional(readOnly = true)
public class VerificarActualizacionServiceImpl implements IVerificarActualizacionService {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public VerificarActualizacionDTO verificarActualizacion(Integer idUsuario, String codigoProducto, String versionActual) {
        // Query para obtener la última versión disponible para el usuario y aplicación
        String sql = """
            SELECT 
                l.id_lanzamiento,
                a.id_aplicacion,
                a.nombre_aplicacion,
                l.version,
                l.estado,
                l.fecha_lanzamiento,
                l.notas_version,
                l.url_descarga,
                l.tamano_archivo,
                l.es_critico,
                g.nombre_grupo,
                lg.fecha_disponibilidad,
                lg.fecha_fin_disponibilidad
            FROM lanzamientos l
            INNER JOIN aplicaciones a ON l.id_aplicacion = a.id_aplicacion
            INNER JOIN lanzamiento_grupo lg ON l.id_lanzamiento = lg.id_lanzamiento
            INNER JOIN grupos_despliegue g ON lg.id_grupo = g.id_grupo
            INNER JOIN usuario_grupo ug ON g.id_grupo = ug.id_grupo
            WHERE ug.id_usuario = :idUsuario
              AND a.codigo_producto = :codigoProducto
              AND a.activo = true
              AND lg.activo = true
              AND ug.activo = true
              AND l.estado IN ('activo', 'publicado')
              AND (lg.fecha_disponibilidad IS NULL OR lg.fecha_disponibilidad <= NOW())
              AND (lg.fecha_fin_disponibilidad IS NULL OR lg.fecha_fin_disponibilidad >= NOW())
            ORDER BY 
                CAST(SPLIT_PART(l.version, '.', 1) AS INTEGER) DESC,
                CAST(SPLIT_PART(l.version, '.', 2) AS INTEGER) DESC,
                CAST(SPLIT_PART(l.version, '.', 3) AS INTEGER) DESC
            LIMIT 1
            """;
        
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("idUsuario", idUsuario);
        query.setParameter("codigoProducto", codigoProducto);
        
        @SuppressWarnings("unchecked")
        List<Object[]> resultados = query.getResultList();
        
        if (resultados.isEmpty()) {
            return VerificarActualizacionDTO.sinActualizacion();
        }
        
        Object[] row = resultados.get(0);
        String versionDisponible = convertirAString(row[3]);
        
        // Comparar versiones
        if (!esVersionMasNueva(versionDisponible, versionActual)) {
            return VerificarActualizacionDTO.sinActualizacion();
        }
        
        // Hay actualización disponible
        VerificarActualizacionDTO dto = new VerificarActualizacionDTO();
        dto.setHayActualizacion(true);
        dto.setIdUsuario(idUsuario);
        dto.setCodigoProducto(codigoProducto);
        dto.setVersionActual(versionActual);
        
        int index = 0;
        dto.setIdLanzamiento(convertirAInteger(row[index++]));
        dto.setIdAplicacion(convertirAInteger(row[index++]));
        dto.setNombreAplicacion(convertirAString(row[index++]));
        dto.setVersionNueva(convertirAString(row[index++]));
        dto.setEstado(convertirAString(row[index++]));
        dto.setFechaLanzamiento(convertirALocalDateTime(row[index++]));
        dto.setNotasVersion(convertirAString(row[index++]));
        dto.setUrlDescarga(convertirAString(row[index++]));
        dto.setTamanoArchivo(convertirALong(row[index++]));
        dto.setEsCritico(convertirABoolean(row[index++]));
        dto.setNombreGrupo(convertirAString(row[index++]));
        dto.setFechaDisponibilidad(convertirALocalDateTime(row[index++]));
        dto.setFechaFinDisponibilidad(convertirALocalDateTime(row[index++]));
        
        return dto;
    }
    
    /**
     * Compara dos versiones semánticas (X.Y.Z)
     * @return true si versionNueva > versionActual
     */
    private boolean esVersionMasNueva(String versionNueva, String versionActual) {
        if (versionNueva == null || versionActual == null) {
            return false;
        }
        
        try {
            String[] partesNueva = versionNueva.split("\\.");
            String[] partesActual = versionActual.split("\\.");
            
            // Asegurar que ambas tengan al menos 3 partes
            int[] nueva = new int[3];
            int[] actual = new int[3];
            
            for (int i = 0; i < 3; i++) {
                nueva[i] = i < partesNueva.length ? Integer.parseInt(partesNueva[i].replaceAll("[^0-9]", "")) : 0;
                actual[i] = i < partesActual.length ? Integer.parseInt(partesActual[i].replaceAll("[^0-9]", "")) : 0;
            }
            
            // Comparar major.minor.patch
            for (int i = 0; i < 3; i++) {
                if (nueva[i] > actual[i]) return true;
                if (nueva[i] < actual[i]) return false;
            }
            
            return false; // Son iguales
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private Integer convertirAInteger(Object obj) {
        if (obj == null) return null;
        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        }
        return null;
    }
    
    private String convertirAString(Object obj) {
        return obj != null ? obj.toString() : null;
    }
    
    private Long convertirALong(Object obj) {
        if (obj == null) return null;
        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        }
        return null;
    }
    
    private Boolean convertirABoolean(Object obj) {
        if (obj == null) return null;
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        }
        if (obj instanceof Number) {
            return ((Number) obj).intValue() != 0;
        }
        return null;
    }
    
    private LocalDateTime convertirALocalDateTime(Object obj) {
        if (obj == null) return null;
        if (obj instanceof Timestamp) {
            return ((Timestamp) obj).toLocalDateTime();
        }
        if (obj instanceof java.sql.Date) {
            return ((java.sql.Date) obj).toLocalDate().atStartOfDay();
        }
        return null;
    }
}

