package com.nettalco.gestion.backendgestion.servicesimplements;

import com.nettalco.gestion.backendgestion.dtos.VerificarAccesoDTO;
import com.nettalco.gestion.backendgestion.servicesinterfaces.IVerificarAccesoService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class VerificarAccesoServiceImpl implements IVerificarAccesoService {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public VerificarAccesoDTO verificarAcceso(Integer idUsuario, Integer idLanzamiento) {
        if (idUsuario == null || idLanzamiento == null) {
            throw new IllegalArgumentException("El ID de usuario y el ID de lanzamiento son requeridos");
        }
        
        String sql = "SELECT fn_usuario_tiene_acceso_lanzamiento(:idUsuario, :idLanzamiento)";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("idUsuario", idUsuario);
        query.setParameter("idLanzamiento", idLanzamiento);
        
        Object result = query.getSingleResult();
        Boolean tieneAcceso = false;
        
        if (result != null) {
            if (result instanceof Boolean) {
                tieneAcceso = (Boolean) result;
            } else if (result instanceof Number) {
                tieneAcceso = ((Number) result).intValue() != 0;
            }
        }
        
        VerificarAccesoDTO dto = new VerificarAccesoDTO();
        dto.setIdUsuario(idUsuario);
        dto.setIdLanzamiento(idLanzamiento);
        dto.setTieneAcceso(tieneAcceso);
        
        return dto;
    }
}

