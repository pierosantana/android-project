package es.upgrade.dao;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.upgrade.entidad.Product;
import es.upgrade.entidad.Routine;
import es.upgrade.entidad.SkinType;
import es.upgrade.entidad.User;


public class UserDao {
    private static UserDao instance = null;


    /**
     * The getInstance method returns a single instance of the UserDao class, creating it if it doesn't
     * already exist.
     * 
     * @return An instance of the UserDao class is being returned.
     */
    public static UserDao getInstance() {
        if (instance == null) {
            instance = new UserDao();
        }
        return instance;
    }

    
    // The `OnUserRecoveredListener` interface in the `UserDao` class defines a contract for a callback
    // listener that is used to notify when a user has been successfully recovered from Firebase
    // database. It contains a single method `onUserRecovered(User user)` that should be implemented by
    // any class that wants to listen for user recovery events.
    public interface OnUserRecoveredListener {
        void onUserRecovered(User user);
    }

    
    // The line `FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();` is initializing an instance
    // of the `FirebaseAuth` class by calling the static method `getInstance()` from the `FirebaseAuth`
    // class. This instance is used to interact with Firebase Authentication services in the Android
    // application. It allows the app to perform actions such as user authentication, user creation,
    // password reset, and more using Firebase Authentication functionalities.
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    private final DatabaseReference userReference;

    
    // The `private UserDao()` constructor in the `UserDao` class is a private constructor that
    // initializes a `FirebaseDatabase` instance and sets the `userReference` field to point to the
    // "Users" node in the Firebase Realtime Database.
    private UserDao() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        userReference = database.getReference("Users"); // NODE "Users" in Firebase
    }

    /**
     * El método `saveUser` guarda un 'User' en una base de datos de Firebase usando
     * el Uid del usuario como clave .
     *
     * @param user               El parámetro `user` es un objeto de tipo `User` que contiene
     *                           información sobre un usuario.
     * @param onCompleteListener El parámetro `onCompleteListener` es una interfaz
     *                           que le permite especificar una función de devolución de llamada que se
     *                           ejecutará cuando se complete la operación de guardar el usuario en la base de datos.
     */
    public void saveUser(User user, OnCompleteListener<Void> onCompleteListener) {
        String userId = firebaseAuth.getUid();
        if (user.getRoutineList() == null) {
            user.setRoutineList(new ArrayList<>()); 
        }
        userReference.child(userId).setValue(user).addOnCompleteListener(onCompleteListener);
    }

    
    /**
     * The `verifyFirebaseUser` function checks if a Firebase user is authenticated and logs the user's
     * email if available.
     * 
     * @return The method `verifyFirebaseUser` returns a boolean value. If there is a Firebase user
     * authenticated, it returns `true`. If there is no authenticated user, it returns `false`.
     */
    public boolean verifyFirebaseUser() {

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        Log.d("UserDao_verifyFirebaseUser", "Usuario autenticado: " + (firebaseUser != null));


        
       // The code snippet you provided is from the `verifyFirebaseUser` method in the `UserDao` class.
        if (firebaseUser == null) {
            Log.d("UserDao_verifyFirebaseUser", "EMAIL: null");
            return false;
        } else {
            Log.d("UserDao_UserDao_verifyFirebaseUser", "EMAIL: " + firebaseUser.getEmail());
            return true;
        }
    }

   /**
    * The `recoveryUser` method retrieves user information from Firebase and notifies a listener with
    * the retrieved user data or null if the user is not found.
    * 
    * @param listener The `listener` parameter in the `recoveryUser` method is an instance of the
    * `OnUserRecoveredListener` interface. This listener is used to notify the caller when the user
    * data has been successfully recovered or if an error occurs during the recovery process.
    */
    public void recoveryUser(OnUserRecoveredListener listener) {
        User user = User.getInstance();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users");
        String userId = firebaseAuth.getUid();
    
        userRef.child(userId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                User userInfo = task.getResult().getValue(User.class);
                if (userInfo != null) {
                    user.setName(userInfo.getName());
                    user.setEmail(userInfo.getEmail());
                    user.setPassword(userInfo.getPassword());
                    user.setSkinType((userInfo.getSkinType()));
                    Log.d("UserDao_recoveryUser", "Usuario encontrado: " + user);
    
                    // Recuperar las rutinas del usuario
                    List<Routine> routines = userInfo.getRoutineList();
                    if (routines != null) {
                        user.setRoutineList(routines);
                    } else {
                        user.setRoutineList(new ArrayList<>()); // Inicializar la lista de rutinas vacía si es null
                    }
    
                    // Notificamos que los datos están listos
                    if (listener != null) {
                        listener.onUserRecovered(user);
                    }
                } else {
                    Log.e("UserDao_recoveryUser", "Error: No se encontró el usuario en Firebase");
                    if (listener != null) {
                        listener.onUserRecovered(null);
                    }
                }
            } else {
                Log.e("UserDao_recoveryUser", "Error al recuperar usuario", task.getException());
                if (listener != null) {
                    listener.onUserRecovered(null);
                }
            }
        });
    }

    /**
     * The `updateUser()` function in Java updates user information and routines in Firebase database,
     * handling null values and logging relevant information along the way.
     */
    public void updateUser() {
        String userId = firebaseAuth.getUid();
        
        if (userId == null) {
            Log.e("UserDao_updateUser", "Error: Usuario no autenticado.");
            return;
        }

        User user = User.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference userRef = firebaseDatabase.getReference("Users").child(userId);

        
        List<Routine> routines = user.getRoutineList();
        if (routines == null) {
            routines = new ArrayList<>(); // Evitar que sea null
            Log.w("UserDao_updateUser", "La lista de rutinas es null, se ha inicializado como una lista vacía.");
        } else {
            Log.d("UserDao_updateUser", "La lista de rutinas contiene " + routines.size() + " rutinas.");
        }

        //Create a list of routines to store in Firebase
        List<Map<String, Object>> routineList = new ArrayList<>();
        for (Routine r : routines) {
            if (r == null) {
                Log.e("UserDao_updateUser", "Una rutina es null.");
                continue; 
            }
            Map<String, Object> routineMap = new HashMap<>();
            
            routineMap.put("schedule", r.getSchedule() != null ? r.getSchedule().toString() : "null");
            routineMap.put("routineType", r.getRoutineType() != null ? r.getRoutineType().toString() : "null");         
            routineMap.put("budget", r.getBudget() != null ? r.getBudget().toString() : "null");
            routineMap.put("skinType", r.getSkinType() != null ? r.getSkinType().toString() : "null");

            
            List<Map<String, Object>> productList = new ArrayList<>();
            for (Product p : r.getProductList()) {
                if (p == null) {
                    continue;
                }

                Map<String, Object> productMap = new HashMap<>();
                

                if (p.getCategoryProduct() == null) {
                    productMap.put("category", "null"); 
                } else {
                    productMap.put("category", p.getCategoryProduct().toString());
                }
                productMap.put("id", p.getId());
                productMap.put("name", p.getName());
                productMap.put("price", p.getPrice());
                productMap.put("url", p.getUrl());
                productMap.put("brand", p.getBrand());

                productList.add(productMap);
            }

            routineMap.put("productList", productList); // Agregar lista de productos a la rutina
            routineList.add(routineMap);
        }

        //Save user data to Firebase
        Map<String, Object> updates = new HashMap<>();
        updates.put("email", user.getEmail());
        updates.put("name", user.getName());
        updates.put("password", user.getPassword());
        updates.put("skinType", user.getSkinType());
        updates.put("routineList", routineList);

    
       // The code snippet `userRef.updateChildren(updates).addOnCompleteListener(task -> { ... });` in
       // the `updateUser()` method of the `UserDao` class is updating the user data in the Firebase
       // Realtime Database. Here's a breakdown of what it does:
        userRef.updateChildren(updates).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("UserDao_updateUser", "El usuario se ha actualizado en la base de datos.");
            } else {
                Log.e("UserDao_updateUser", "El usuario NO se ha actualizado en la base de datos. ERROR: " + task.getException().getMessage());
            }
        });
    }

}


