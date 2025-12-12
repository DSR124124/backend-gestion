-- ============================================================================
-- SCRIPT DE CREACIÓN DE TABLAS PARA SISTEMA DE NOTIFICACIONES
-- ============================================================================
-- Este script crea las tablas necesarias para un sistema de notificaciones
-- multi-aplicativo donde cada área puede crear y asignar notificaciones
-- a usuarios individuales.
-- 
-- Base de datos: backend-gestion (sistema central)
-- ============================================================================

-- ============================================================================
-- 1. TABLA: notificaciones
-- ============================================================================
-- Almacena las notificaciones creadas por cada área/aplicación
-- ============================================================================
CREATE TABLE IF NOT EXISTS notificaciones (
    id_notificacion SERIAL PRIMARY KEY,
    
    -- Información básica
    titulo VARCHAR(255) NOT NULL,
    mensaje TEXT NOT NULL,
    tipo_notificacion VARCHAR(50) NOT NULL DEFAULT 'info', -- info, warning, error, success, critical
    prioridad VARCHAR(20) NOT NULL DEFAULT 'normal', -- baja, normal, alta, urgente
    
    -- Relación con aplicación/área que crea la notificación
    id_aplicacion INTEGER NOT NULL,
    CONSTRAINT fk_notificacion_aplicacion 
        FOREIGN KEY (id_aplicacion) 
        REFERENCES aplicaciones(id_aplicacion) 
        ON DELETE CASCADE,
    
    -- Usuario/área que crea la notificación
    creado_por INTEGER NOT NULL,
    CONSTRAINT fk_notificacion_creador 
        FOREIGN KEY (creado_por) 
        REFERENCES usuarios(id_usuario) 
        ON DELETE RESTRICT,
    
    -- Fechas
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_expiracion TIMESTAMP NULL, -- Si es NULL, no expira
    fecha_envio TIMESTAMP NULL, -- Fecha programada de envío (si es NULL, se envía inmediatamente)
    
    -- Configuración
    requiere_confirmacion BOOLEAN NOT NULL DEFAULT false, -- Si requiere que el usuario confirme lectura
    mostrar_como_recordatorio BOOLEAN NOT NULL DEFAULT true, -- Si aparece como recordatorio hasta ser leída
    activo BOOLEAN NOT NULL DEFAULT true,
    
    -- Datos adicionales (JSON para flexibilidad)
    datos_adicionales JSONB NULL, -- Para almacenar información adicional (URLs, acciones, etc.)
    
    -- Auditoría
    fecha_modificacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Índices para mejorar rendimiento
CREATE INDEX IF NOT EXISTS idx_notificaciones_aplicacion ON notificaciones(id_aplicacion);
CREATE INDEX IF NOT EXISTS idx_notificaciones_creador ON notificaciones(creado_por);
CREATE INDEX IF NOT EXISTS idx_notificaciones_fecha_creacion ON notificaciones(fecha_creacion DESC);
CREATE INDEX IF NOT EXISTS idx_notificaciones_fecha_expiracion ON notificaciones(fecha_expiracion);
CREATE INDEX IF NOT EXISTS idx_notificaciones_activo ON notificaciones(activo);
CREATE INDEX IF NOT EXISTS idx_notificaciones_tipo ON notificaciones(tipo_notificacion);

-- Comentarios en la tabla
COMMENT ON TABLE notificaciones IS 'Almacena las notificaciones creadas por cada área/aplicación';
COMMENT ON COLUMN notificaciones.tipo_notificacion IS 'Tipo: info, warning, error, success, critical';
COMMENT ON COLUMN notificaciones.prioridad IS 'Prioridad: baja, normal, alta, urgente';
COMMENT ON COLUMN notificaciones.fecha_expiracion IS 'Si es NULL, la notificación no expira';
COMMENT ON COLUMN notificaciones.fecha_envio IS 'Fecha programada de envío. Si es NULL, se envía inmediatamente';
COMMENT ON COLUMN notificaciones.requiere_confirmacion IS 'Si requiere confirmación explícita del usuario';
COMMENT ON COLUMN notificaciones.mostrar_como_recordatorio IS 'Si aparece como recordatorio hasta ser leída';

-- ============================================================================
-- 2. TABLA: notificacion_destinatarios
-- ============================================================================
-- Almacena los destinatarios individuales de cada notificación
-- ============================================================================
CREATE TABLE IF NOT EXISTS notificacion_destinatarios (
    id_notificacion_destinatario SERIAL PRIMARY KEY,
    
    -- Relación con notificación
    id_notificacion INTEGER NOT NULL,
    CONSTRAINT fk_nd_notificacion 
        FOREIGN KEY (id_notificacion) 
        REFERENCES notificaciones(id_notificacion) 
        ON DELETE CASCADE,
    
    -- Usuario destinatario
    id_usuario INTEGER NOT NULL,
    CONSTRAINT fk_nd_usuario 
        FOREIGN KEY (id_usuario) 
        REFERENCES usuarios(id_usuario) 
        ON DELETE CASCADE,
    
    -- Estado de la notificación para este usuario
    leida BOOLEAN NOT NULL DEFAULT false,
    fecha_lectura TIMESTAMP NULL,
    confirmada BOOLEAN NOT NULL DEFAULT false, -- Si requiere confirmación
    fecha_confirmacion TIMESTAMP NULL,
    
    -- Fecha de creación
    fecha_asignacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    -- Constraint único: un usuario solo puede recibir una notificación una vez
    CONSTRAINT uk_notificacion_usuario UNIQUE (id_notificacion, id_usuario)
);

-- Índices
CREATE INDEX IF NOT EXISTS idx_nd_notificacion ON notificacion_destinatarios(id_notificacion);
CREATE INDEX IF NOT EXISTS idx_nd_usuario ON notificacion_destinatarios(id_usuario);
CREATE INDEX IF NOT EXISTS idx_nd_leida ON notificacion_destinatarios(leida);
CREATE INDEX IF NOT EXISTS idx_nd_usuario_leida ON notificacion_destinatarios(id_usuario, leida);

COMMENT ON TABLE notificacion_destinatarios IS 'Destinatarios individuales de las notificaciones';
COMMENT ON COLUMN notificacion_destinatarios.leida IS 'Indica si el usuario ha leído la notificación';
COMMENT ON COLUMN notificacion_destinatarios.confirmada IS 'Indica si el usuario ha confirmado la notificación (si requiere confirmación)';

-- ============================================================================
-- 3. FUNCIÓN: Obtener notificaciones no leídas de un usuario
-- ============================================================================
-- Retorna las notificaciones no leídas y activas para un usuario específico
-- ============================================================================
CREATE OR REPLACE FUNCTION obtener_notificaciones_no_leidas(
    p_id_usuario INTEGER,
    p_id_aplicacion INTEGER DEFAULT NULL
) RETURNS TABLE (
    id_notificacion INTEGER,
    titulo VARCHAR(255),
    mensaje TEXT,
    tipo_notificacion VARCHAR(50),
    prioridad VARCHAR(20),
    fecha_creacion TIMESTAMP,
    fecha_expiracion TIMESTAMP,
    requiere_confirmacion BOOLEAN,
    datos_adicionales JSONB,
    nombre_aplicacion VARCHAR(255),
    leida BOOLEAN,
    fecha_lectura TIMESTAMP,
    confirmada BOOLEAN,
    fecha_confirmacion TIMESTAMP
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        n.id_notificacion,
        n.titulo,
        n.mensaje,
        n.tipo_notificacion,
        n.prioridad,
        n.fecha_creacion,
        n.fecha_expiracion,
        n.requiere_confirmacion,
        n.datos_adicionales,
        a.nombre_aplicacion,
        nd.leida,
        nd.fecha_lectura,
        nd.confirmada,
        nd.fecha_confirmacion
    FROM notificaciones n
    INNER JOIN notificacion_destinatarios nd ON n.id_notificacion = nd.id_notificacion
    INNER JOIN aplicaciones a ON n.id_aplicacion = a.id_aplicacion
    WHERE nd.id_usuario = p_id_usuario
      AND n.activo = true
      AND nd.leida = false
      AND (n.fecha_expiracion IS NULL OR n.fecha_expiracion > CURRENT_TIMESTAMP)
      AND (n.fecha_envio IS NULL OR n.fecha_envio <= CURRENT_TIMESTAMP)
      AND (p_id_aplicacion IS NULL OR n.id_aplicacion = p_id_aplicacion)
    ORDER BY 
        CASE n.prioridad
            WHEN 'urgente' THEN 1
            WHEN 'alta' THEN 2
            WHEN 'normal' THEN 3
            WHEN 'baja' THEN 4
        END,
        n.fecha_creacion DESC;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION obtener_notificaciones_no_leidas IS 'Obtiene las notificaciones no leídas de un usuario, opcionalmente filtradas por aplicación';

-- ============================================================================
-- 4. TRIGGER: Actualizar fecha_modificacion automáticamente
-- ============================================================================
CREATE OR REPLACE FUNCTION actualizar_fecha_modificacion_notificacion()
RETURNS TRIGGER AS $$
BEGIN
    NEW.fecha_modificacion = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_actualizar_fecha_modificacion_notificacion
    BEFORE UPDATE ON notificaciones
    FOR EACH ROW
    EXECUTE FUNCTION actualizar_fecha_modificacion_notificacion();

-- ============================================================================
-- 5. VISTA: Vista de notificaciones con información completa
-- ============================================================================
CREATE OR REPLACE VIEW vista_notificaciones_completa AS
SELECT 
    n.id_notificacion,
    n.titulo,
    n.mensaje,
    n.tipo_notificacion,
    n.prioridad,
    n.id_aplicacion,
    a.nombre_aplicacion,
    a.codigo_producto,
    n.creado_por,
    u_creador.nombre_completo AS creador_nombre,
    n.fecha_creacion,
    n.fecha_expiracion,
    n.fecha_envio,
    n.requiere_confirmacion,
    n.mostrar_como_recordatorio,
    n.activo,
    n.datos_adicionales,
    n.fecha_modificacion,
    -- Estadísticas
    COUNT(DISTINCT nd.id_usuario) AS total_destinatarios,
    COUNT(DISTINCT CASE WHEN nd.leida = true THEN nd.id_usuario END) AS total_leidas,
    COUNT(DISTINCT CASE WHEN nd.confirmada = true THEN nd.id_usuario END) AS total_confirmadas
FROM notificaciones n
LEFT JOIN aplicaciones a ON n.id_aplicacion = a.id_aplicacion
LEFT JOIN usuarios u_creador ON n.creado_por = u_creador.id_usuario
LEFT JOIN notificacion_destinatarios nd ON n.id_notificacion = nd.id_notificacion
GROUP BY 
    n.id_notificacion,
    n.titulo,
    n.mensaje,
    n.tipo_notificacion,
    n.prioridad,
    n.id_aplicacion,
    a.nombre_aplicacion,
    a.codigo_producto,
    n.creado_por,
    u_creador.nombre_completo,
    n.fecha_creacion,
    n.fecha_expiracion,
    n.fecha_envio,
    n.requiere_confirmacion,
    n.mostrar_como_recordatorio,
    n.activo,
    n.datos_adicionales,
    n.fecha_modificacion;

COMMENT ON VIEW vista_notificaciones_completa IS 'Vista que muestra las notificaciones con información completa y estadísticas';

-- ============================================================================
-- FIN DEL SCRIPT
-- ============================================================================
-- Para verificar la creación:
-- SELECT * FROM notificaciones;
-- SELECT * FROM notificacion_destinatarios;
-- SELECT * FROM vista_notificaciones_completa;
-- ============================================================================

