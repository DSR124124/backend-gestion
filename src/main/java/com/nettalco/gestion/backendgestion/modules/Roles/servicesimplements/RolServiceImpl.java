package com.nettalco.gestion.backendgestion.modules.Roles.servicesimplements;

import com.nettalco.gestion.backendgestion.modules.Roles.dtos.RolDTO;
import com.nettalco.gestion.backendgestion.modules.Roles.dtos.RolResponseDTO;
import com.nettalco.gestion.backendgestion.modules.Roles.entities.Rol;
import com.nettalco.gestion.backendgestion.modules.Roles.repositories.RolRepository;
import com.nettalco.gestion.backendgestion.modules.Roles.servicesinterfaces.IRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RolServiceImpl implements IRolService {
    
    @Autowired
    private RolRepository rolRepository;
    
    @Override
    public RolResponseDTO crear(RolDTO rolDTO) {
        // Validar que el nombre del rol no exista
        if (rolRepository.existsByNombreRol(rolDTO.getNombreRol())) {
            throw new RuntimeException("Ya existe un rol con el nombre: " + rolDTO.getNombreRol());
        }
        
        Rol rol = new Rol();
        rol.setNombreRol(rolDTO.getNombreRol());
        rol.setDescripcion(rolDTO.getDescripcion());
        rol.setPermisos(rolDTO.getPermisos());
        rol.setActivo(rolDTO.getActivo() != null ? rolDTO.getActivo() : true);
        
        Rol rolGuardado = rolRepository.save(rol);
        return convertirAResponseDTO(rolGuardado);
    }
    
    @Override
    public RolResponseDTO actualizar(Integer id, RolDTO rolDTO) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + id));
        
        // Validar que el nombre del rol no exista en otro rol
        if (rolRepository.existsByNombreRolAndIdRolNot(rolDTO.getNombreRol(), id)) {
            throw new RuntimeException("Ya existe otro rol con el nombre: " + rolDTO.getNombreRol());
        }
        
        rol.setNombreRol(rolDTO.getNombreRol());
        rol.setDescripcion(rolDTO.getDescripcion());
        rol.setPermisos(rolDTO.getPermisos());
        if (rolDTO.getActivo() != null) {
            rol.setActivo(rolDTO.getActivo());
        }
        
        Rol rolActualizado = rolRepository.save(rol);
        return convertirAResponseDTO(rolActualizado);
    }
    
    @Override
    public void eliminar(Integer id) {
        if (!rolRepository.existsById(id)) {
            throw new RuntimeException("Rol no encontrado con id: " + id);
        }
        rolRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<RolResponseDTO> listar() {
        return rolRepository.findAll().stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public RolResponseDTO listarPorId(Integer id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + id));
        return convertirAResponseDTO(rol);
    }
    
    private RolResponseDTO convertirAResponseDTO(Rol rol) {
        return new RolResponseDTO(
                rol.getIdRol(),
                rol.getNombreRol(),
                rol.getDescripcion(),
                rol.getPermisos(),
                rol.getActivo(),
                rol.getFechaCreacion(),
                rol.getFechaModificacion()
        );
    }
}

