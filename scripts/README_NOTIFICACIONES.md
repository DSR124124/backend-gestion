# Sistema de Notificaciones Multi-Aplicativo

## 📋 Descripción

Sistema de notificaciones centralizado que permite a cada área/aplicación crear y gestionar notificaciones para usuarios individuales o grupos. Los usuarios pueden ver, leer y marcar como leídas las notificaciones en sus aplicaciones respectivas.

## 🗄️ Base de Datos

**Base de datos:** `backend-gestion` (sistema central)

**Razón:** Como es un sistema multi-aplicativo, todas las notificaciones deben estar centralizadas en la base de datos del sistema de gestión para mantener consistencia y permitir que todas las aplicaciones accedan a las mismas notificaciones.

## 📊 Estructura de Tablas

### 1. `notificaciones`
Almacena las notificaciones creadas por cada área/aplicación.

**Campos principales:**
- `id_notificacion`: Identificador único
- `titulo`: Título de la notificación
- `mensaje`: Contenido de la notificación
- `tipo_notificacion`: info, warning, error, success, critical
- `prioridad`: baja, normal, alta, urgente
- `id_aplicacion`: Aplicación/área que crea la notificación
- `creado_por`: Usuario que crea la notificación
- `fecha_creacion`: Fecha de creación
- `fecha_expiracion`: Fecha de expiración (NULL = no expira)
- `fecha_envio`: Fecha programada de envío (NULL = inmediato)
- `requiere_confirmacion`: Si requiere confirmación explícita
- `mostrar_como_recordatorio`: Si aparece como recordatorio hasta ser leída
- `activo`: Si la notificación está activa
- `datos_adicionales`: JSON para información adicional (URLs, acciones, etc.)

### 2. `notificacion_destinatarios`
Almacena los destinatarios individuales de cada notificación.

**Campos principales:**
- `id_notificacion_destinatario`: Identificador único
- `id_notificacion`: Referencia a la notificación
- `id_usuario`: Usuario destinatario
- `leida`: Si el usuario ha leído la notificación
- `fecha_lectura`: Fecha en que se leyó
- `confirmada`: Si el usuario ha confirmado (si requiere confirmación)
- `fecha_confirmacion`: Fecha de confirmación

### 3. `notificacion_grupos`
Almacena los grupos destinatarios de cada notificación.

**Campos principales:**
- `id_notificacion_grupo`: Identificador único
- `id_notificacion`: Referencia a la notificación
- `id_grupo`: Grupo de despliegue destinatario

**Nota:** Cuando se asigna una notificación a un grupo, se crean automáticamente registros en `notificacion_destinatarios` para cada usuario activo del grupo.

## 🔧 Funciones SQL

### `asignar_notificacion_a_grupo(p_id_notificacion, p_id_grupo)`
Asigna automáticamente una notificación a todos los usuarios activos de un grupo.

**Uso:**
```sql
SELECT asignar_notificacion_a_grupo(1, 5); -- Asigna notificación 1 al grupo 5
```

### `obtener_notificaciones_no_leidas(p_id_usuario, p_id_aplicacion)`
Obtiene las notificaciones no leídas de un usuario, opcionalmente filtradas por aplicación.

**Uso:**
```sql
-- Todas las notificaciones no leídas del usuario 10
SELECT * FROM obtener_notificaciones_no_leidas(10);

-- Solo notificaciones de la aplicación 2
SELECT * FROM obtener_notificaciones_no_leidas(10, 2);
```

## 🔄 Triggers Automáticos

1. **Actualización de fecha_modificacion**: Se actualiza automáticamente cuando se modifica una notificación.

2. **Asignación automática a nuevos usuarios de grupo**: Si un usuario se agrega a un grupo que ya tiene notificaciones asignadas, se le asignan automáticamente esas notificaciones.

## 📱 Flujo de Uso

### 1. Crear Notificación (Backend/Sistema Web)

```sql
-- Insertar notificación
INSERT INTO notificaciones (
    titulo, 
    mensaje, 
    tipo_notificacion, 
    prioridad,
    id_aplicacion, 
    creado_por,
    requiere_confirmacion,
    mostrar_como_recordatorio,
    datos_adicionales
) VALUES (
    'Actualización Disponible',
    'Hay una nueva versión disponible para descargar',
    'info',
    'normal',
    1, -- ID de la aplicación
    5, -- ID del usuario creador
    false,
    true,
    '{"url": "https://ejemplo.com/descargar", "accion": "descargar"}'::jsonb
) RETURNING id_notificacion;
```

### 2. Asignar a Usuarios Individuales

```sql
-- Asignar a un usuario específico
INSERT INTO notificacion_destinatarios (id_notificacion, id_usuario)
VALUES (1, 10);

-- Asignar a múltiples usuarios
INSERT INTO notificacion_destinatarios (id_notificacion, id_usuario)
VALUES 
    (1, 10),
    (1, 11),
    (1, 12);
```

### 3. Asignar a Grupos

```sql
-- Asignar a un grupo (automáticamente asigna a todos los usuarios del grupo)
SELECT asignar_notificacion_a_grupo(1, 5); -- Notificación 1, Grupo 5
```

### 4. Obtener Notificaciones No Leídas (App/Web)

```sql
-- Obtener todas las notificaciones no leídas del usuario
SELECT * FROM obtener_notificaciones_no_leidas(10);

-- Obtener solo las de una aplicación específica
SELECT * FROM obtener_notificaciones_no_leidas(10, 2);
```

### 5. Marcar como Leída (App/Web)

```sql
-- Marcar notificación como leída
UPDATE notificacion_destinatarios
SET 
    leida = true,
    fecha_lectura = CURRENT_TIMESTAMP
WHERE id_notificacion = 1 
  AND id_usuario = 10;
```

### 6. Confirmar Notificación (Si requiere confirmación)

```sql
-- Confirmar notificación
UPDATE notificacion_destinatarios
SET 
    confirmada = true,
    fecha_confirmacion = CURRENT_TIMESTAMP
WHERE id_notificacion = 1 
  AND id_usuario = 10;
```

## 🎯 Casos de Uso

### Caso 1: Notificación para un Usuario Específico
```sql
-- 1. Crear notificación
INSERT INTO notificaciones (titulo, mensaje, tipo_notificacion, id_aplicacion, creado_por)
VALUES ('Mensaje Personal', 'Tienes un mensaje nuevo', 'info', 1, 5)
RETURNING id_notificacion;

-- 2. Asignar al usuario
INSERT INTO notificacion_destinatarios (id_notificacion, id_usuario)
VALUES (1, 10);
```

### Caso 2: Notificación para un Grupo Completo
```sql
-- 1. Crear notificación
INSERT INTO notificaciones (titulo, mensaje, tipo_notificacion, id_aplicacion, creado_por)
VALUES ('Mantenimiento Programado', 'El sistema estará en mantenimiento mañana', 'warning', 1, 5)
RETURNING id_notificacion;

-- 2. Asignar al grupo (automáticamente asigna a todos los usuarios)
SELECT asignar_notificacion_a_grupo(1, 5);
```

### Caso 3: Notificación con Expiración
```sql
-- Crear notificación que expira en 7 días
INSERT INTO notificaciones (
    titulo, 
    mensaje, 
    tipo_notificacion, 
    id_aplicacion, 
    creado_por,
    fecha_expiracion
) VALUES (
    'Oferta Especial',
    'Oferta válida por tiempo limitado',
    'success',
    1,
    5,
    CURRENT_TIMESTAMP + INTERVAL '7 days'
)
RETURNING id_notificacion;
```

### Caso 4: Notificación Programada
```sql
-- Crear notificación que se enviará mañana
INSERT INTO notificaciones (
    titulo, 
    mensaje, 
    tipo_notificacion, 
    id_aplicacion, 
    creado_por,
    fecha_envio
) VALUES (
    'Recordatorio',
    'No olvides completar tu perfil',
    'info',
    1,
    5,
    CURRENT_TIMESTAMP + INTERVAL '1 day'
)
RETURNING id_notificacion;
```

## 📊 Consultas Útiles

### Ver todas las notificaciones con estadísticas
```sql
SELECT * FROM vista_notificaciones_completa
ORDER BY fecha_creacion DESC;
```

### Ver notificaciones no leídas de un usuario
```sql
SELECT * FROM obtener_notificaciones_no_leidas(10);
```

### Contar notificaciones no leídas por usuario
```sql
SELECT 
    u.id_usuario,
    u.nombre_completo,
    COUNT(*) AS notificaciones_no_leidas
FROM usuarios u
INNER JOIN notificacion_destinatarios nd ON u.id_usuario = nd.id_usuario
INNER JOIN notificaciones n ON nd.id_notificacion = n.id_notificacion
WHERE nd.leida = false
  AND n.activo = true
  AND (n.fecha_expiracion IS NULL OR n.fecha_expiracion > CURRENT_TIMESTAMP)
GROUP BY u.id_usuario, u.nombre_completo
ORDER BY notificaciones_no_leidas DESC;
```

### Ver notificaciones por aplicación
```sql
SELECT 
    a.nombre_aplicacion,
    COUNT(*) AS total_notificaciones,
    COUNT(CASE WHEN n.activo = true THEN 1 END) AS activas,
    COUNT(CASE WHEN n.fecha_expiracion > CURRENT_TIMESTAMP OR n.fecha_expiracion IS NULL THEN 1 END) AS vigentes
FROM aplicaciones a
LEFT JOIN notificaciones n ON a.id_aplicacion = n.id_aplicacion
GROUP BY a.id_aplicacion, a.nombre_aplicacion
ORDER BY total_notificaciones DESC;
```

## 🔐 Consideraciones de Seguridad

1. **Permisos**: Solo usuarios con permisos adecuados pueden crear notificaciones.
2. **Validación**: Validar que el usuario creador tenga acceso a la aplicación.
3. **Expiración**: Las notificaciones expiradas no se muestran automáticamente.
4. **Auditoría**: Se registra quién crea cada notificación y cuándo.

## 🚀 Próximos Pasos

1. **Backend (Spring Boot)**: Crear entidades, repositorios, servicios y controladores.
2. **Frontend (Angular)**: Crear componentes para gestionar y ver notificaciones.
3. **App (Flutter)**: Integrar API para recibir y mostrar notificaciones.
4. **Notificaciones Push**: Integrar con servicios de notificaciones push (FCM, APNS).

## 📝 Notas Importantes

- Las notificaciones asignadas a grupos se distribuyen automáticamente a todos los usuarios activos del grupo.
- Si un usuario se agrega a un grupo después de que se creó una notificación, NO recibirá esa notificación (solo las futuras).
- Una notificación marcada como leída NO aparecerá como recordatorio si `mostrar_como_recordatorio = true`.
- Las notificaciones expiradas no se muestran, pero se mantienen en la base de datos para auditoría.

