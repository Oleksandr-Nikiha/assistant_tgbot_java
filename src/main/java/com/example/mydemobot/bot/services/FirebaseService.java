package com.example.mydemobot.bot.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.mydemobot.bot.models.Accounting;
import com.example.mydemobot.bot.models.Users;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.Query.Direction;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import org.telegram.telegrambots.meta.api.objects.User;

@Service
public class FirebaseService {
    private Firestore firebaseApp;

    @Value("${firebase.filename}")
    private String fireFile;

    private FirebaseService() {
        try {
            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(this.loadFileInputStream()))
                .build();
            FirebaseApp.initializeApp(options);
            this.firebaseApp = FirestoreClient.getFirestore();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private FileInputStream loadFileInputStream() throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        return new FileInputStream(classLoader.getResource(fireFile).getFile());
    }

    public void registerUser(User user, Long chatId, String step) {
        Map<String, Object> session = new HashMap<>();
        Map<String, Object> users = new HashMap<>();
        if(this.getUser(user) == null) {
            session.put("currentStep", step);
            session.put("actionDate", FieldValue.serverTimestamp());
            
            users.put("userName", user.getUserName());
            users.put("id", user.getId());
            users.put("roles", Arrays.asList("User"));
            users.put("registerDate", FieldValue.serverTimestamp());
            users.put("chatId", chatId);
            users.put("session", session);

            firebaseApp.collection("users").document(String.valueOf(user.getId())).set(users);
        }else{
            users.put("chatId", chatId);
            firebaseApp.collection("users").document(String.valueOf(user.getId())).update(users);

            session.put("currentStep", step);
            session.put("actionDate", FieldValue.serverTimestamp());

            firebaseApp.collection("users").document(String.valueOf(user.getId())).update("session", session);
        }
    }

    public Users getUser(User user) {
        ApiFuture<DocumentSnapshot> userObject = firebaseApp.collection("users").document(String.valueOf(user.getId())).get();
        if (userObject != null) {
            try {
                DocumentSnapshot userDoc = userObject.get();
                return userDoc.toObject(Users.class);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return null;
            }
        }else{
            return null;
        }
    }

    public List<Users> getAdmin() {
        List<Users> adminList = new ArrayList<Users>();

        ApiFuture<QuerySnapshot> usersObject = firebaseApp.collection("users")
            .whereArrayContains("roles", "admin")
            .get();

        try {
            List<QueryDocumentSnapshot> usersDocs = usersObject.get().getDocuments();
            for (DocumentSnapshot user : usersDocs) {
                adminList.add(user.toObject(Users.class));
            }

            return adminList;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUserStep(User user, String step) {
        Map<String, Object> session = new HashMap<>();
        session.put("currentStep", step);
        session.put("actionDate", FieldValue.serverTimestamp());

        firebaseApp.collection("users").document(String.valueOf(user.getId())).update("session", session);
    }

    public void createdAccount(User user, String typeInc) {
        Map<String, Object> accounts = new HashMap<>();
        accounts.put("type", typeInc);
        accounts.put("accountDate", FieldValue.serverTimestamp());

        firebaseApp.collection(String.format("users/%s/Accounting", user.getId())).add(accounts);
    }

    public void setValueAccount(User user, String value) {        
        Map<String, Object> accounts = new HashMap<>();
        accounts.put("value",  Double.parseDouble(value));

        ApiFuture<QuerySnapshot> accountObject = firebaseApp.collection(String.format("users/%s/Accounting", user.getId())).orderBy("accountDate", Direction.ASCENDING).limitToLast(1).get();
        try {
            List<QueryDocumentSnapshot> accountList = accountObject.get().getDocuments();
            String path = accountList.get(0).getReference().getPath();

            firebaseApp.document(path).update(accounts);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void setAnnotAccount(User user, String annotation) {
        Map<String, Object> accounts = new HashMap<>();
        accounts.put("annotation",  annotation);

        ApiFuture<QuerySnapshot> accountObject = firebaseApp.collection(String.format("users/%s/Accounting", user.getId())).orderBy("accountDate", Direction.ASCENDING).limitToLast(1).get();
        try {
            List<QueryDocumentSnapshot> accountList = accountObject.get().getDocuments();
            String path = accountList.get(0).getReference().getPath();

            firebaseApp.document(path).update(accounts);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public Accounting getLatestAccount(User user) {
        ApiFuture<QuerySnapshot> accountObject = firebaseApp.collection(
            String.format("users/%s/Accounting", user.getId())
        ).orderBy(
            "accountDate", Direction.ASCENDING
        ).limitToLast(1).get();

        try {
            List<QueryDocumentSnapshot> accountList = accountObject.get().getDocuments();
            Accounting accounting = accountList.get(0).toObject(Accounting.class);
            
            return accounting;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteLastAccount(User user) {
        ApiFuture<QuerySnapshot> accountObject = firebaseApp.collection(
            String.format("users/%s/Accounting", user.getId())
        ).orderBy(
            "accountDate", Direction.ASCENDING
        ).limitToLast(1).get();

        try {
            List<QueryDocumentSnapshot> accountList = accountObject.get().getDocuments();
            QueryDocumentSnapshot accounting = accountList.get(0);
            accounting.getReference().delete();
        }catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
