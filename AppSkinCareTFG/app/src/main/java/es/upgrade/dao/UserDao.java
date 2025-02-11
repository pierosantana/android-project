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
import es.upgrade.entidad.User;


public class UserDao {
    private static UserDao instance = null;

    public static UserDao getInstance() {
        if (instance == null) {
            instance = new UserDao();
        }
        return instance;
    }

    // Definimos la interfaz para el callback
    public interface OnUserRecoveredListener {
        void onUserRecovered(User user);
    }

    //FirebaseAuth permite la autenticación y verificación de usuarios
    //a través de Firebase.
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    private final DatabaseReference userReference;

    // El constructor `UserDao()` en la clase `UserDao` está inicializando una instancia `FirebaseDatabase`
    // utilizando el método `FirebaseDatabase.getInstance()`, que recupera la instancia `FirebaseApp`
    // predeterminada.
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
        userReference.child(userId).setValue(user).addOnCompleteListener(onCompleteListener);
    }

    /**
     * El método `verifyUser` verifica si un usuario está autenticado con Firebase y lo redirecciona a
     * la actividad adecuada según su estado de autenticación.
     */
    public boolean verifyFirebaseUser() {

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        Log.d("UserDao_verifyFirebaseUser", "Usuario autenticado: " + (firebaseUser != null));


        // Si no hay un usuario autenticado, lo redirigimos al MainActivity
        if (firebaseUser == null) {
            Log.d("UserDao_verifyFirebaseUser", "EMAIL: null");
            return false;
        } else {
            Log.d("UserDao_UserDao_verifyFirebaseUser", "EMAIL: " + firebaseUser.getEmail());
            return true;
        }
    }

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
                    Log.d("UserDao_recoveryUser", "Usuario encontrado: " + user);

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

    public void updateUser() {
        String userId = firebaseAuth.getUid();
        // Verificar si el usuario está autenticado
        if (userId == null) {
            Log.e("UserDao_updateUser", "Error: Usuario no autenticado.");
            return;
        }

        User user = User.getInstance();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference userRef = firebaseDatabase.getReference("Users").child(userId);

        // Verificar si la lista de rutinas no es null
        List<Routine> routines = user.getRoutineList();
        if (routines == null) {
            routines = new ArrayList<>(); // Evitar que sea null
        }

        // Convertir lista de rutinas a un formato compatible con Firebase
        List<Map<String, Object>> routineList = new ArrayList<>();
        for (Routine r : user.getRoutineList()) {
            Map<String, Object> routineMap = new HashMap<>();
            routineMap.put("schedule", r.getSchedule().toString()); // Convertir a String si es un Enum
            routineMap.put("routineType", r.getRoutineType().toString()); // Convertir a String si es un Enum
            routineMap.put("budget", r.getBudget().toString()); // Convertir a String si es un Enum
            routineMap.put("budgetProducts", r.getBudgetProducts()); // Double, no es necesario convertir
            routineMap.put("skinType", r.getSkinType().toString()); // Convertir a String si es un Enum

            // Convertir lista de productos a una estructura compatible con Firebase
            List<Map<String, Object>> productList = new ArrayList<>();
            for (Product p : r.getProductList()) {
                if(p != null) {
                    Map<String, Object> productMap = new HashMap<>();
                    productMap.put("id", p.getId());
                   // productMap.put("name", p.getName());
                    // productMap.put("price", p.getPrice());
                   // productMap.put("category", p.getCategoryProduct().toString()); // Si category es un Enum
                    productList.add(productMap);
                }
            }

            routineMap.put("productList", productList); // Agregar lista de productos a la rutina
            routineList.add(routineMap);
        }

    // Guardar lista de rutinas en Firebase


        Map<String, Object> updates = new HashMap<>();
        updates.put("email",user.getEmail());
        updates.put("name",user.getName());
        updates.put("password",user.getPassword());
        updates.put("skinType",user.getSkinType());
        updates.put("routineList", routineList);



        userRef.updateChildren(updates).addOnCompleteListener(task -> {
           if(task.isSuccessful()){
               Log.d("UserDao_updateUser", "El usuario se ha actualizado en la base de datos.");
           }else{
               Log.d("UserDao_updateUser", "El usuario NO se ha actualizado en la base de datos. ERROR: " + task.getException().getMessage());
           }
        });

    }
}


