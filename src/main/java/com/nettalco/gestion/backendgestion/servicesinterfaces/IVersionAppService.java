package com.nettalco.gestion.backendgestion.servicesinterfaces;

import com.nettalco.gestion.backendgestion.dtos.VersionAppDTO;

public interface IVersionAppService {
    
    /**
     * Obtiene la versión actual (última publicada) de una aplicación por su código de producto
     * @param codigoProducto Código único del producto (ej: FLUTTER_APP_SERVICIOS)
     * @return DTO con la información de la versión
     */
    VersionAppDTO obtenerVersionActual(String codigoProducto);
}

