package com.nettalco.gestion.backendgestion.modules.Lanzamientos.servicesinterfaces;

import com.nettalco.gestion.backendgestion.modules.Lanzamientos.dtos.VerificarActualizacionDTO;

/**
 * Servicio para verificar actualizaciones disponibles para aplicaciones móviles
 */
public interface IVerificarActualizacionService {
    
    /**
     * Verifica si hay una actualización disponible para un usuario y aplicación específica
     * 
     * @param idUsuario ID del usuario logueado
     * @param codigoProducto Código único de la aplicación (ej: FLUTTER_APP_SERVICIOS)
     * @param versionActual Versión actual instalada en el dispositivo (ej: 1.0.0)
     * @return DTO con información de la actualización si existe, o indicando que no hay
     */
    VerificarActualizacionDTO verificarActualizacion(Integer idUsuario, String codigoProducto, String versionActual);
}

