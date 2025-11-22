package com.nettalco.gestion.backendgestion.servicesimplements;

import com.nettalco.gestion.backendgestion.dtos.EstadisticaLanzamientoDTO;
import com.nettalco.gestion.backendgestion.servicesinterfaces.IEstadisticaLanzamientoService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class EstadisticaLanzamientoServiceImpl implements IEstadisticaLanzamientoService {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public List<EstadisticaLanzamientoDTO> listarTodas() {
        String sql = "SELECT id_aplicacion, nombre_aplicacion, id_lanzamiento, version, estado, " +
                    "fecha_lanzamiento, usuarios_con_acceso, grupos_asignados, es_critico " +
                    "FROM v_estadisticas_lanzamientos " +
                    "ORDER BY nombre_aplicacion, fecha_lanzamiento DESC";
        Query query = entityManager.createNativeQuery(sql);
        return convertirResultados(query.getResultList());
    }
    
    @Override
    public List<EstadisticaLanzamientoDTO> listarPorAplicacion(Integer idAplicacion) {
        String sql = "SELECT id_aplicacion, nombre_aplicacion, id_lanzamiento, version, estado, " +
                    "fecha_lanzamiento, usuarios_con_acceso, grupos_asignados, es_critico " +
                    "FROM v_estadisticas_lanzamientos " +
                    "WHERE id_aplicacion = :idAplicacion " +
                    "ORDER BY fecha_lanzamiento DESC";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("idAplicacion", idAplicacion);
        return convertirResultados(query.getResultList());
    }
    
    @SuppressWarnings("unchecked")
    private List<EstadisticaLanzamientoDTO> convertirResultados(List<Object[]> resultados) {
        List<EstadisticaLanzamientoDTO> lista = new ArrayList<>();
        
        for (Object[] row : resultados) {
            EstadisticaLanzamientoDTO dto = new EstadisticaLanzamientoDTO();
            int index = 0;
            
            dto.setIdAplicacion(convertirAInteger(row[index++]));
            dto.setNombreAplicacion(convertirAString(row[index++]));
            dto.setIdLanzamiento(convertirAInteger(row[index++]));
            dto.setVersion(convertirAString(row[index++]));
            dto.setEstado(convertirAString(row[index++]));
            dto.setFechaLanzamiento(convertirALocalDateTime(row[index++]));
            dto.setUsuariosConAcceso(convertirALong(row[index++]));
            dto.setGruposAsignados(convertirALong(row[index++]));
            dto.setEsCritico(convertirABoolean(row[index++]));
            
            lista.add(dto);
        }
        
        return lista;
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

