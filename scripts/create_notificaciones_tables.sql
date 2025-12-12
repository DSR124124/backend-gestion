-- ============================================================================
-- SCRIPT DE CREACIÓN DE TABLAS PARA SISTEMA DE NOTIFICACIONES
-- ============================================================================
-- Este script crea las tablas necesarias para un sistema de notificaciones
-- multi-aplicativo donde cada área puede crear y asignar notificaciones
-- a usuarios individuales o grupos.
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
-- 3. TABLA: notificacion_grupos
-- ============================================================================
-- Almacena los grupos destinatarios de cada notificación
-- Cuando se asigna a un grupo, se crean registros automáticos en 
-- notificacion_destinatarios para cada usuario del grupo
-- ============================================================================
CREATE TABLE IF NOT EXISTS notificacion_grupos (
    id_notificacion_grupo SERIAL PRIMARY KEY,
    
    -- Relación con notificación
    id_notificacion INTEGER NOT NULL,
    CONSTRAINT fk_ng_notificacion 
        FOREIGN KEY (id_notificacion) 
        REFERENCES notificaciones(id_notificacion) 
        ON DELETE CASCADE,
    
    -- Grupo destinatario
    id_grupo INTEGER NOT NULL,
    CONSTRAINT fk_ng_grupo 
        FOREIGN KEY (id_grupo) 
        REFERENCES grupos_despliegue(id_grupo) 
        ON DELETE CASCADE,
    
    -- Fecha de asignación
    fecha_asignacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    -- Constraint único: un grupo solo puede recibir una notificación una vez
    CONSTRAINT uk_notificacion_grupo UNIQUE (id_notificacion, id_grupo)
);

-- Índices
CREATE INDEX IF NOT EXISTS idx_ng_notificacion ON notificacion_grupos(id_notificacion);
CREATE INDEX IF NOT EXISTS idx_ng_grupo ON notificacion_grupos(id_grupo);

COMMENT ON TABLE notificacion_grupos IS 'Grupos destinatarios de las notificaciones';
COMMENT ON COLUMN notificacion_grupos.id_grupo IS 'Grupo de despliegue que recibirá la notificación';

-- ============================================================================
-- 4. FUNCIÓN: Asignar notificación a grupo
-- ============================================================================
-- Esta función asigna automáticamente una notificación a todos los usuarios
-- activos de un grupo, creando registros en notificacion_destinatarios
-- ============================================================================
CREATE OR REPLACE FUNCTION asignar_notificacion_a_grupo(
    p_id_notificacion INTEGER,
    p_id_grupo INTEGER
) RETURNS INTEGER AS $$
DECLARE
    v_usuarios_asignados INTEGER := 0;
    v_usuario_record RECORD;
BEGIN
    -- Verificar que la notificación existe y está activa
    IF NOT EXISTS (
        SELECT 1 FROM notificaciones 
        WHERE id_notificacion = p_id_notificacion AND activo = true
    ) THEN
        RAISE EXCEPTION 'La notificación no existe o no está activa';
    END IF;
    
    -- Verificar que el grupo existe y está activo
    IF NOT EXISTS (
        SELECT 1 FROM grupos_despliegue 
        WHERE id_grupo = p_id_grupo AND activo = true
    ) THEN
        RAISE EXCEPTION 'El grupo no existe o no está activo';
    END IF;
    
    -- Insertar relación notificación-grupo
    INSERT INTO notificacion_grupos (id_notificacion, id_grupo)
    VALUES (p_id_notificacion, p_id_grupo)
    ON CONFLICT (id_notificacion, id_grupo) DO NOTHING;
    
    -- Asignar la notificación a todos los usuarios activos del grupo
    FOR v_usuario_record IN
        SELECT DISTINCT ug.id_usuario
        FROM usuario_grupo ug
        WHERE ug.id_grupo = p_id_grupo
          AND ug.activo = true
          AND EXISTS (
              SELECT 1 FROM usuarios u 
              WHERE u.id_usuario = ug.id_usuario 
              AND u.activo = true
          )
    LOOP
        -- Insertar en notificacion_destinatarios si no existe
        INSERT INTO notificacion_destinatarios (id_notificacion, id_usuario)
        VALUES (p_id_notificacion, v_usuario_record.id_usuario)
        ON CONFLICT (id_notificacion, id_usuario) DO NOTHING;
        
        v_usuarios_asignados := v_usuarios_asignados + 1;
    END LOOP;
    
    RETURN v_usuarios_asignados;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION asignar_notificacion_a_grupo IS 'Asigna una notificación a todos los usuarios activos de un grupo';

-- ============================================================================
-- 5. FUNCIÓN: Obtener notificaciones no leídas de un usuario
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
-- 6. TRIGGER: Actualizar fecha_modificacion automáticamente
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
-- 7. TRIGGER: Actualizar notificacion_destinatarios cuando se agregan usuarios a un grupo
-- ============================================================================
-- Si un usuario se agrega a un grupo que ya tiene notificaciones asignadas,
-- se le asignan automáticamente esas notificaciones
-- ============================================================================
CREATE OR REPLACE FUNCTION asignar_notificaciones_a_nuevo_usuario_grupo()
RETURNS TRIGGER AS $$
BEGIN
    -- Si el usuario se agrega a un grupo activo
    IF NEW.activo = true THEN
        -- Asignar todas las notificaciones activas del grupo al nuevo usuario
        INSERT INTO notificacion_destinatarios (id_notificacion, id_usuario)
        SELECT ng.id_notificacion, NEW.id_usuario
        FROM notificacion_grupos ng
        INNER JOIN notificaciones n ON ng.id_notificacion = n.id_notificacion
        WHERE ng.id_grupo = NEW.id_grupo
          AND n.activo = true
          AND (n.fecha_expiracion IS NULL OR n.fecha_expiracion > CURRENT_TIMESTAMP)
        ON CONFLICT (id_notificacion, id_usuario) DO NOTHING;
    END IF;
    
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_asignar_notificaciones_a_nuevo_usuario_grupo
    AFTER INSERT OR UPDATE ON usuario_grupo
    FOR EACH ROW
    WHEN (NEW.activo = true)
    EXECUTE FUNCTION asignar_notificaciones_a_nuevo_usuario_grupo();

-- ============================================================================
-- 8. VISTA: Vista de notificaciones con información completa
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
    COUNT(DISTINCT CASE WHEN nd.confirmada = true THEN nd.id_usuario END) AS total_confirmadas,
    COUNT(DISTINCT ng.id_grupo) AS total_grupos
FROM notificaciones n
LEFT JOIN aplicaciones a ON n.id_aplicacion = a.id_aplicacion
LEFT JOIN usuarios u_creador ON n.creado_por = u_creador.id_usuario
LEFT JOIN notificacion_destinatarios nd ON n.id_notificacion = nd.id_notificacion
LEFT JOIN notificacion_grupos ng ON n.id_notificacion = ng.id_notificacion
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
-- SELECT * FROM notificacion_grupos;
-- SELECT * FROM vista_notificaciones_completa;
-- ============================================================================

