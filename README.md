# FirestoreTest - Proyecto de Pruebas con Firebase Firestore

## 📋 Descripción

Este proyecto es una aplicación Java que demuestra cómo integrar y utilizar Firebase Firestore como base de datos NoSQL en el cloud. El proyecto incluye funcionalidades básicas para conectar, escribir y leer datos desde Firestore.

## 🚀 Características

- **Conexión a Firebase Firestore**: Configuración automática usando credenciales de servicio
- **Operaciones CRUD básicas**: 
  - Crear documentos en colecciones
  - Leer documentos de colecciones
- **Manejo de errores**: Gestión robusta de excepciones
- **Logging**: Uso de SLF4J para logging

## 🛠️ Tecnologías Utilizadas

- **Java 22**: Lenguaje de programación principal
- **Maven**: Gestión de dependencias y build
- **Firebase Admin SDK**: Para interactuar con Firestore
- **Google Cloud Firestore**: Base de datos NoSQL
- **SLF4J**: Framework de logging
- **Gson**: Serialización JSON (opcional)

## 📁 Estructura del Proyecto

```
FirestoreNetbeans/
├── pom.xml                          # Configuración de Maven
├── README.md                        # Este archivo
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── mycompany/
│       │           └── firestoretest/
│       │               └── FirestoreTest.java  # Clase principal
│       └── resources/
│           └── key.JSON             # Credenciales de Firebase (NO incluir en git)
└── target/                          # Archivos compilados
```

## ⚙️ Configuración Inicial

### Prerrequisitos

1. **Java 22** instalado en tu sistema
2. **Maven** instalado
3. **Cuenta de Firebase** con proyecto creado
4. **Archivo de credenciales** de Firebase

### Pasos de Configuración

1. **Clonar el repositorio**:
   ```bash
   git clone <url-del-repositorio>
   cd FirestoreNetbeans
   ```

2. **Configurar Firebase**:
   - Ve a la [Consola de Firebase](https://console.firebase.google.com/)
   - Crea un nuevo proyecto o selecciona uno existente
   - Ve a Configuración del proyecto > Cuentas de servicio
   - Genera una nueva clave privada
   - Descarga el archivo JSON de credenciales

3. **Configurar credenciales**:
   - Coloca el archivo JSON descargado en `src/main/resources/key.JSON`
   - **IMPORTANTE**: Nunca subas este archivo a control de versiones

4. **Actualizar configuración**:
   - En `FirestoreTest.java`, actualiza la URL de la base de datos:
   ```java
   .setDatabaseUrl("https://tu-proyecto.firebaseio.com")
   ```

## 🏃‍♂️ Ejecución

### Compilar el proyecto
```bash
mvn clean compile
```

### Ejecutar la aplicación
```bash
mvn exec:java
```

O directamente:
```bash
java -cp target/classes com.mycompany.firestoretest.FirestoreTest
```

## 📖 Uso

### Funcionalidades Principales

1. **Conexión a Firestore**:
   ```java
   ConectarDB();
   ```

2. **Crear un documento**:
   ```java
   Map<String, Object> data = new HashMap<>();
   data.put("name", "Nestor");
   data.put("email", "nestor@gmail.com");
   data.put("age", 32);
   
   write("users", "user1", data, db);
   ```

3. **Leer documentos de una colección**:
   ```java
   read("users");
   ```

## 🔧 Configuración de Dependencias

El proyecto utiliza las siguientes dependencias principales:

- **firebase-admin (9.1.1)**: SDK oficial de Firebase para Java
- **gson (2.10.1)**: Para serialización JSON
- **slf4j-simple (2.0.9)**: Implementación simple de logging

## 🚨 Consideraciones de Seguridad

- **Nunca subas el archivo `key.JSON` a control de versiones**
- Usa variables de entorno para credenciales en producción
- Configura reglas de seguridad apropiadas en Firestore
- Revisa y actualiza las dependencias regularmente

## 🐛 Solución de Problemas

### Error de conexión
- Verifica que el archivo `key.JSON` esté en la ubicación correcta
- Confirma que la URL de la base de datos sea correcta
- Asegúrate de que las credenciales tengan permisos adecuados

### Error de compilación
- Verifica que Java 22 esté instalado
- Ejecuta `mvn clean` antes de compilar
- Revisa que todas las dependencias se descarguen correctamente

## 📝 Notas de Desarrollo

- El proyecto está configurado para Java 22
- Usa Maven para gestión de dependencias
- Incluye logging básico con SLF4J
- Estructura preparada para expansión futura

## 🤝 Contribuciones

Para contribuir al proyecto:

1. Fork el repositorio
2. Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -am 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crea un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 👨‍💻 Autor

**locon** - Universidad Nacional de Colombia

---

## 🔄 Próximas Mejoras

- [ ] Agregar operaciones de actualización y eliminación
- [ ] Implementar consultas más complejas
- [ ] Agregar tests unitarios
- [ ] Mejorar el manejo de errores
- [ ] Agregar configuración por archivo de propiedades
- [ ] Implementar conexión pool para mejor rendimiento 