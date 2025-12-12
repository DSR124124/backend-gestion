package com.nettalco.gestion.backendgestion.modules.Grupos.servicesimplements;

import com.nettalco.gestion.backendgestion.modules.Grupos.dtos.GrupoDespliegueDTO;
import com.nettalco.gestion.backendgestion.modules.Grupos.dtos.GrupoDespliegueResponseDTO;
import com.nettalco.gestion.backendgestion.modules.Grupos.entities.GrupoDespliegue;
import com.nettalco.gestion.backendgestion.modules.Grupos.repositories.GrupoDespliegueRepository;
import com.nettalco.gestion.backendgestion.modules.Grupos.servicesinterfaces.IGrupoDespliegueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GrupoDespliegueServiceImpl implements IGrupoDespliegueService {
    
    @Autowired
    private GrupoDespliegueRepository grupoDespliegueRepository;
    
    @Override
    public GrupoDespliegueResponseDTO crear(GrupoDespliegueDTO grupoDespliegueDTO) {
        // Validar que el nombre del grupo no exista
        if (grupoDespliegueRepository.existsByNombreGrupo(grupoDespliegueDTO.getNombreGrupo())) {
            throw new RuntimeException("Ya existe un grupo de despliegue con el nombre: " + grupoDespliegueDTO.getNombreGrupo());
        }
        
        // Validar porcentaje de usuarios
        if (grupoDespliegueDTO.getPorcentajeUsuarios() != null) {
            if (grupoDespliegueDTO.getPorcentajeUsuarios() < 0 || grupoDespliegueDTO.getPorcentajeUsuarios() > 100) {
                throw new RuntimeException("El porcentaje de usuarios debe estar entre 0 y 100");
            }
        }
        
        GrupoDespliegue grupoDespliegue = new GrupoDespliegue();
        grupoDespliegue.setNombreGrupo(grupoDespliegueDTO.getNombreGrupo());
        grupoDespliegue.setDescripcion(grupoDespliegueDTO.getDescripcion());
        grupoDespliegue.setOrdenPrioridad(grupoDespliegueDTO.getOrdenPrioridad());
        grupoDespliegue.setPorcentajeUsuarios(grupoDespliegueDTO.getPorcentajeUsuarios());
        grupoDespliegue.setActivo(grupoDespliegueDTO.getActivo() != null ? grupoDespliegueDTO.getActivo() : true);
        grupoDespliegue.setMetadata(grupoDespliegueDTO.getMetadata());
        
        GrupoDespliegue grupoDespliegueGuardado = grupoDespliegueRepository.save(grupoDespliegue);
        return convertirAResponseDTO(grupoDespliegueGuardado);
    }
    
    @Override
    public GrupoDespliegueResponseDTO actualizar(Integer id, GrupoDespliegueDTO grupoDespliegueDTO) {
        GrupoDespliegue grupoDespliegue = grupoDespliegueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grupo de despliegue no encontrado con id: " + id));
        
        // Validar que el nombre del grupo no exista en otro grupo
        if (grupoDespliegueRepository.existsByNombreGrupoAndIdGrupoNot(grupoDespliegueDTO.getNombreGrupo(), id)) {
            throw new RuntimeException("Ya existe otro grupo de despliegue con el nombre: " + grupoDespliegueDTO.getNombreGrupo());
        }
        
        // Validar porcentaje de usuarios
        if (grupoDespliegueDTO.getPorcentajeUsuarios() != null) {
            if (grupoDespliegueDTO.getPorcentajeUsuarios() < 0 || grupoDespliegueDTO.getPorcentajeUsuarios() > 100) {
                throw new RuntimeException("El porcentaje de usuarios debe estar entre 0 y 100");
            }
        }
        
        grupoDespliegue.setNombreGrupo(grupoDespliegueDTO.getNombreGrupo());
        grupoDespliegue.setDescripcion(grupoDespliegueDTO.getDescripcion());
        grupoDespliegue.setOrdenPrioridad(grupoDespliegueDTO.getOrdenPrioridad());
        grupoDespliegue.setPorcentajeUsuarios(grupoDespliegueDTO.getPorcentajeUsuarios());
        if (grupoDespliegueDTO.getActivo() != null) {
            grupoDespliegue.setActivo(grupoDespliegueDTO.getActivo());
        }
        grupoDespliegue.setMetadata(grupoDespliegueDTO.getMetadata());
        
        GrupoDespliegue grupoDespliegueActualizado = grupoDespliegueRepository.save(grupoDespliegue);
        return convertirAResponseDTO(grupoDespliegueActualizado);
    }
    
    @Override
    public void eliminar(Integer id) {
        if (!grupoDespliegueRepository.existsById(id)) {
            throw new RuntimeException("Grupo de despliegue no encontrado con id: " + id);
        }
        grupoDespliegueRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<GrupoDespliegueResponseDTO> listar() {
        return grupoDespliegueRepository.findAll().stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public GrupoDespliegueResponseDTO listarPorId(Integer id) {
        GrupoDespliegue grupoDespliegue = grupoDespliegueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grupo de despliegue no encontrado con id: " + id));
        return convertirAResponseDTO(grupoDespliegue);
    }
    
    private GrupoDespliegueResponseDTO convertirAResponseDTO(GrupoDespliegue grupoDespliegue) {
        return new GrupoDespliegueResponseDTO(
                grupoDespliegue.getIdGrupo(),
                grupoDespliegue.getNombreGrupo(),
                grupoDespliegue.getDescripcion(),
                grupoDespliegue.getOrdenPrioridad(),
                grupoDespliegue.getPorcentajeUsuarios(),
                grupoDespliegue.getActivo(),
                grupoDespliegue.getFechaCreacion(),
                grupoDespliegue.getMetadata()
        );
    }
}

