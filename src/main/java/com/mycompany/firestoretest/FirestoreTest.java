/*
 * FirestoreTest - Clase principal para demostrar el uso de Firebase Firestore
 * 
 * Esta clase proporciona funcionalidades básicas para conectar, escribir y leer
 * datos desde Firebase Firestore usando el SDK de Firebase Admin.
 * 
 * @author locon
 * @version 1.0
 * @since 2024
 */

package com.mycompany.firestoretest;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase principal para demostrar operaciones básicas con Firebase Firestore.
 * Incluye funcionalidades para conectar, escribir y leer datos.
 */
public class FirestoreTest {
    
    private static final Logger logger = LoggerFactory.getLogger(FirestoreTest.class);
    private static final String CREDENTIALS_PATH = "src/main/resources/key.JSON";
    private static final String DATABASE_URL = "https://test2024-6eea1-default-rtdb.firebaseio.com";
    
    private static Firestore db;

    /**
     * Método principal que ejecuta las operaciones de demostración.
     * 
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        logger.info("Iniciando aplicación FirestoreTest...");
        
        try {
            // Conectar a la base de datos
            conectarDB();
            
            if (db == null) {
                logger.error("No se pudo conectar a Firestore. Saliendo...");
                return;
            }
            
            // Ejemplo de datos para insertar
            Map<String, Object> userData = new HashMap<>();
            userData.put("name", "Nestor");
            userData.put("email", "nestor@gmail.com");
            userData.put("age", 32);
            userData.put("createdAt", System.currentTimeMillis());
            
            // Ejecutar operaciones de demostración
            logger.info("Ejecutando operaciones de demostración...");
            
            // Escribir un documento (comentado para evitar duplicados)
            // write("users", "user1", userData);
            
            // Leer todos los documentos de la colección
            read("users");
            
            // Ejemplo de lectura de un documento específico
            readDocument("users", "user1");
            
            logger.info("Operaciones completadas exitosamente.");
            
        } catch (Exception e) {
            logger.error("Error durante la ejecución: ", e);
        }
    }

    /**
     * Establece la conexión con Firebase Firestore.
     * Lee las credenciales desde el archivo JSON y inicializa la aplicación.
     */
    private static void conectarDB() {
        try {
            logger.info("Iniciando conexión a Firebase Firestore...");
            
            // Verificar que el archivo de credenciales existe
            if (!new java.io.File(CREDENTIALS_PATH).exists()) {
                logger.error("Archivo de credenciales no encontrado en: {}", CREDENTIALS_PATH);
                logger.error("Por favor, coloca tu archivo key.JSON en src/main/resources/");
                return;
            }
            
            // Leer el archivo de credenciales
            FileInputStream serviceAccount = new FileInputStream(CREDENTIALS_PATH);

            // Configurar Firebase
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(DATABASE_URL)
                    .build();

            // Inicializar Firebase (evitar múltiples inicializaciones)
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                logger.info("Firebase inicializado correctamente");
            } else {
                logger.info("Firebase ya estaba inicializado");
            }

            // Obtener la instancia de Firestore
            db = FirestoreClient.getFirestore();
            logger.info("Conexión a Firestore establecida exitosamente");

        } catch (FileNotFoundException e) {
            logger.error("Archivo de credenciales no encontrado: {}", e.getMessage());
        } catch (IOException e) {
            logger.error("Error al leer el archivo de credenciales: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Error al conectar con Firestore: {}", e.getMessage(), e);
        }
    }

    /**
     * Crea o actualiza un documento en una colección específica.
     * 
     * @param collectionName Nombre de la colección
     * @param documentId ID del documento
     * @param data Datos a escribir en el documento
     */
    public static void write(String collectionName, String documentId, Map<String, Object> data) {
        if (db == null) {
            logger.error("No hay conexión a Firestore");
            return;
        }
        
        try {
            logger.info("Escribiendo documento {} en colección {}", documentId, collectionName);
            
            ApiFuture<WriteResult> result = db.collection(collectionName)
                    .document(documentId)
                    .set(data);
            
            WriteResult writeResult = result.get();
            logger.info("Documento creado/actualizado exitosamente. Tiempo de actualización: {}", 
                       writeResult.getUpdateTime());
            
        } catch (InterruptedException e) {
            logger.error("Operación interrumpida: {}", e.getMessage());
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            logger.error("Error al escribir documento: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Error inesperado al escribir documento: {}", e.getMessage(), e);
        }
    }

    /**
     * Lee todos los documentos de una colección específica.
     * 
     * @param collectionName Nombre de la colección a leer
     */
    public static void read(String collectionName) {
        if (db == null) {
            logger.error("No hay conexión a Firestore");
            return;
        }
        
        try {
            logger.info("Leyendo documentos de la colección: {}", collectionName);
            
            // Referencia a la colección
            CollectionReference collection = db.collection(collectionName);

            // Obtener documentos de la colección
            ApiFuture<QuerySnapshot> query = collection.get();
            QuerySnapshot querySnapshot = query.get();

            // Obtener una lista de documentos
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

            if (documents.isEmpty()) {
                logger.info("La colección '{}' está vacía", collectionName);
                return;
            }

            logger.info("Encontrados {} documentos en la colección '{}':", 
                       documents.size(), collectionName);
            
            for (QueryDocumentSnapshot document : documents) {
                logger.info("ID del documento: {}", document.getId());
                logger.info("Contenido: {}", document.getData());
                logger.info("---");
            }
            
        } catch (InterruptedException e) {
            logger.error("Operación interrumpida: {}", e.getMessage());
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            logger.error("Error al leer documentos: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Error inesperado al leer documentos: {}", e.getMessage(), e);
        }
    }
    
    /**
     * Lee un documento específico por su ID.
     * 
     * @param collectionName Nombre de la colección
     * @param documentId ID del documento
     */
    public static void readDocument(String collectionName, String documentId) {
        if (db == null) {
            logger.error("No hay conexión a Firestore");
            return;
        }
        
        try {
            logger.info("Leyendo documento {} de la colección {}", documentId, collectionName);
            
            DocumentReference docRef = db.collection(collectionName).document(documentId);
            ApiFuture<com.google.cloud.firestore.DocumentSnapshot> future = docRef.get();
            com.google.cloud.firestore.DocumentSnapshot document = future.get();
            
            if (document.exists()) {
                logger.info("Documento encontrado: {}", document.getData());
            } else {
                logger.info("Documento {} no encontrado en la colección {}", documentId, collectionName);
            }
            
        } catch (InterruptedException e) {
            logger.error("Operación interrumpida: {}", e.getMessage());
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            logger.error("Error al leer documento: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Error inesperado al leer documento: {}", e.getMessage(), e);
        }
    }
    
    /**
     * Elimina un documento específico.
     * 
     * @param collectionName Nombre de la colección
     * @param documentId ID del documento a eliminar
     */
    public static void deleteDocument(String collectionName, String documentId) {
        if (db == null) {
            logger.error("No hay conexión a Firestore");
            return;
        }
        
        try {
            logger.info("Eliminando documento {} de la colección {}", documentId, collectionName);
            
            ApiFuture<WriteResult> writeResult = db.collection(collectionName)
                    .document(documentId)
                    .delete();
            
            WriteResult result = writeResult.get();
            logger.info("Documento eliminado exitosamente. Tiempo de actualización: {}", 
                       result.getUpdateTime());
            
        } catch (InterruptedException e) {
            logger.error("Operación interrumpida: {}", e.getMessage());
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            logger.error("Error al eliminar documento: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Error inesperado al eliminar documento: {}", e.getMessage(), e);
        }
    }
    
    /**
     * Actualiza campos específicos de un documento existente.
     * 
     * @param collectionName Nombre de la colección
     * @param documentId ID del documento
     * @param updates Map con los campos a actualizar
     */
    public static void updateDocument(String collectionName, String documentId, Map<String, Object> updates) {
        if (db == null) {
            logger.error("No hay conexión a Firestore");
            return;
        }
        
        try {
            logger.info("Actualizando documento {} en colección {}", documentId, collectionName);
            
            ApiFuture<WriteResult> result = db.collection(collectionName)
                    .document(documentId)
                    .update(updates);
            
            WriteResult writeResult = result.get();
            logger.info("Documento actualizado exitosamente. Tiempo de actualización: {}", 
                       writeResult.getUpdateTime());
            
        } catch (InterruptedException e) {
            logger.error("Operación interrumpida: {}", e.getMessage());
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            logger.error("Error al actualizar documento: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Error inesperado al actualizar documento: {}", e.getMessage(), e);
        }
    }
}
