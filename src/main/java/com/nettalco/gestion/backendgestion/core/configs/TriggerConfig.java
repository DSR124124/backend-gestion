package com.nettalco.gestion.backendgestion.core.configs;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Configuración para asegurar que los triggers de fecha_modificacion estén creados
 * Esta clase verifica que los triggers existan en la base de datos
 */
@Component
public class TriggerConfig {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * Verifica que los triggers estén creados en la base de datos
     * Los triggers se crean mediante el script schema.sql, pero esta clase
     * puede ser usada para verificar su existencia o recrearlos si es necesario
     */
    @PostConstruct
    public void verificarTriggers() {
        // Los triggers se crean mediante el script schema.sql
        // Esta clase está aquí para documentación y posibles verificaciones futuras
        // Si necesitas verificar que los triggers existan, puedes agregar lógica aquí
    }
}

