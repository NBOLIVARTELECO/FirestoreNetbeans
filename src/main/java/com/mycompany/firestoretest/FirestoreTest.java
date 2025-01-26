/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author locon
 */
public class FirestoreTest {

    static Firestore db;
    //FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static void main(String[] args) {
        ConectarDB();
        
        Map<String, Object> data = new HashMap<>();
        data.put("name", "Nestor");
        data.put("email", "Nestor@Gmail.coom");
        data.put("age", 32);

        //write("users", "user1", data, db);
        read("users");
    }

    private static void ConectarDB() {
        try {
            // Ruta al archivo JSON de credenciales
            FileInputStream serviceAccount = new FileInputStream("src\\main\\resources\\key.JSON");

            // Configurar Firebase
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://test2024-6eea1-default-rtdb.firebaseio.com") // Reemplaza <tu-proyecto>
                    .build();

            FirebaseApp.initializeApp(options);

            // Obtener la instancia de Firestore
            db = FirestoreClient.getFirestore();
            System.out.println("Firestore inicializado sin problema");

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (db == null) {
            System.err.println("Firestore no se inicializó adecuadamente. Verifica las credenciales.");
            
        }
    }

// Crear un documento en una colección
    public static void write(String collectionName, String documentId, Map<String, Object> data, Firestore db ) {
        try {
            ApiFuture<WriteResult> result = db.collection(collectionName).document(documentId).set(data);
            System.out.println("Documento creado con éxito: " + result.get().getUpdateTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
 public static void read(String collectionName) {
        try {
            // Referencia a la colección
            CollectionReference collection = db.collection(collectionName);

            // Obtener documentos de la colección
            ApiFuture<QuerySnapshot> query = collection.get();
            QuerySnapshot querySnapshot = query.get();

            // Obtener una lista de documentos
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

            for (QueryDocumentSnapshot document : documents) {
                System.out.println("ID del documento: " + document.getId());
                System.out.println("Contenido del documento: " + document.getData());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}
