package com.nettalco.gestion.backendgestion.modules.Lanzamientos.servicesimplements;

import com.nettalco.gestion.backendgestion.modules.Lanzamientos.dtos.UsuarioLanzamientoDisponibleDTO;
import com.nettalco.gestion.backendgestion.modules.Lanzamientos.servicesinterfaces.IUsuarioLanzamientoDisponibleService;

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
public class UsuarioLanzamientoDisponibleServiceImpl implements IUsuarioLanzamientoDisponibleService {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public List<UsuarioLanzamientoDisponibleDTO> listarTodos() {
        String sql = "SELECT id_usuario, username, email, id_aplicacion, nombre_aplicacion, " +
                    "id_lanzamiento, version, estado, fecha_lanzamiento, notas_version, " +
                    "url_descarga, tamano_archivo, es_critico, id_grupo, nombre_grupo, " +
                    "fecha_disponibilidad, fecha_fin_disponibilidad " +
                    "FROM v_usuario_lanzamientos_disponibles ORDER BY id_usuario, nombre_aplicacion, version";
        Query query = entityManager.createNativeQuery(sql);
        return convertirResultados(query.getResultList());
    }
    
    @Override
    public List<UsuarioLanzamientoDisponibleDTO> listarPorUsuario(Integer idUsuario) {
        String sql = "SELECT id_usuario, username, email, id_aplicacion, nombre_aplicacion, " +
                    "id_lanzamiento, version, estado, fecha_lanzamiento, notas_version, " +
                    "url_descarga, tamano_archivo, es_critico, id_grupo, nombre_grupo, " +
                    "fecha_disponibilidad, fecha_fin_disponibilidad " +
                    "FROM v_usuario_lanzamientos_disponibles WHERE id_usuario = :idUsuario " +
                    "ORDER BY nombre_aplicacion, version";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("idUsuario", idUsuario);
        return convertirResultados(query.getResultList());
    }
    
    @Override
    public List<UsuarioLanzamientoDisponibleDTO> listarPorAplicacion(Integer idAplicacion) {
        String sql = "SELECT id_usuario, username, email, id_aplicacion, nombre_aplicacion, " +
                    "id_lanzamiento, version, estado, fecha_lanzamiento, notas_version, " +
                    "url_descarga, tamano_archivo, es_critico, id_grupo, nombre_grupo, " +
                    "fecha_disponibilidad, fecha_fin_disponibilidad " +
                    "FROM v_usuario_lanzamientos_disponibles WHERE id_aplicacion = :idAplicacion " +
                    "ORDER BY id_usuario, version";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("idAplicacion", idAplicacion);
        return convertirResultados(query.getResultList());
    }
    
    @Override
    public List<UsuarioLanzamientoDisponibleDTO> listarPorGrupo(Integer idGrupo) {
        String sql = "SELECT id_usuario, username, email, id_aplicacion, nombre_aplicacion, " +
                    "id_lanzamiento, version, estado, fecha_lanzamiento, notas_version, " +
                    "url_descarga, tamano_archivo, es_critico, id_grupo, nombre_grupo, " +
                    "fecha_disponibilidad, fecha_fin_disponibilidad " +
                    "FROM v_usuario_lanzamientos_disponibles WHERE id_grupo = :idGrupo " +
                    "ORDER BY id_usuario, nombre_aplicacion, version";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("idGrupo", idGrupo);
        return convertirResultados(query.getResultList());
    }
    
    @SuppressWarnings("unchecked")
    private List<UsuarioLanzamientoDisponibleDTO> convertirResultados(List<Object[]> resultados) {
        List<UsuarioLanzamientoDisponibleDTO> lista = new ArrayList<>();
        
        for (Object[] row : resultados) {
            UsuarioLanzamientoDisponibleDTO dto = new UsuarioLanzamientoDisponibleDTO();
            int index = 0;
            
            dto.setIdUsuario(convertirAInteger(row[index++]));
            dto.setUsername(convertirAString(row[index++]));
            dto.setEmail(convertirAString(row[index++]));
            dto.setIdAplicacion(convertirAInteger(row[index++]));
            dto.setNombreAplicacion(convertirAString(row[index++]));
            dto.setIdLanzamiento(convertirAInteger(row[index++]));
            dto.setVersion(convertirAString(row[index++]));
            dto.setEstado(convertirAString(row[index++]));
            dto.setFechaLanzamiento(convertirALocalDateTime(row[index++]));
            dto.setNotasVersion(convertirAString(row[index++]));
            dto.setUrlDescarga(convertirAString(row[index++]));
            dto.setTamanoArchivo(convertirALong(row[index++]));
            dto.setEsCritico(convertirABoolean(row[index++]));
            dto.setIdGrupo(convertirAInteger(row[index++]));
            dto.setNombreGrupo(convertirAString(row[index++]));
            dto.setFechaDisponibilidad(convertirALocalDateTime(row[index++]));
            dto.setFechaFinDisponibilidad(convertirALocalDateTime(row[index++]));
            
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

