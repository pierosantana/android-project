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
        if (user.getRoutineList() == null) {
            user.setRoutineList(new ArrayList<>()); // Inicializar la lista de rutinas vacía
        }
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
            Log.w("UserDao_updateUser", "La lista de rutinas es null, se ha inicializado como una lista vacía.");
        } else {
            Log.d("UserDao_updateUser", "La lista de rutinas contiene " + routines.size() + " rutinas.");
        }

        // Convertir lista de rutinas a un formato compatible con Firebase
        List<Map<String, Object>> routineList = new ArrayList<>();
        for (Routine r : routines) {
            if (r == null) {
                Log.e("UserDao_updateUser", "Una rutina es null.");
                continue; // Salir si la rutina es null
            }
            Map<String, Object> routineMap = new HashMap<>();
            Log.d("UserDao_updateUser", "Procesando rutina: " + r.getRoutineType());

            // Verifica si los campos de la rutina son null o válidos
            Log.d("UserDao_updateUser", "Schedule: " + (r.getSchedule() != null ? r.getSchedule() : "null"));
            routineMap.put("schedule", r.getSchedule() != null ? r.getSchedule().toString() : "null");

            Log.d("UserDao_updateUser", "RoutineType: " + (r.getRoutineType() != null ? r.getRoutineType() : "null"));
            routineMap.put("routineType", r.getRoutineType() != null ? r.getRoutineType().toString() : "null");

            Log.d("UserDao_updateUser", "Budget: " + (r.getBudget() != null ? r.getBudget() : "null"));
            routineMap.put("budget", r.getBudget() != null ? r.getBudget().toString() : "null");

            Log.d("UserDao_updateUser", "SkinType: " + (r.getSkinType() != null ? r.getSkinType() : "null"));
            routineMap.put("skinType", r.getSkinType() != null ? r.getSkinType().toString() : "null");

            // Convertir lista de productos a una estructura compatible con Firebase
            List<Map<String, Object>> productList = new ArrayList<>();
            for (Product p : r.getProductList()) {
                if (p == null) {
                    Log.e("UserDao_updateUser", "Producto es null en la rutina: " + r.getRoutineType());
                    continue; // Saltar este producto si es null
                }

                Map<String, Object> productMap = new HashMap<>();
                Log.d("UserDao_updateUser", "Procesando producto: " + p.getName());

                if (p.getCategoryProduct() == null) {
                    Log.e("UserDao_updateUser", "CategoryProduct es null para el producto: " + p.getName());
                    productMap.put("category", "null"); // O cualquier valor por defecto
                } else {
                    Log.d("UserDao_updateUser", "CategoryProduct: " + p.getCategoryProduct());
                    productMap.put("category", p.getCategoryProduct().toString());
                }

                productMap.put("id", p.getId());
                productMap.put("name", p.getName());
                productMap.put("price", p.getPrice());

                productList.add(productMap);
            }

            routineMap.put("productList", productList); // Agregar lista de productos a la rutina
            routineList.add(routineMap);
        }

        // Guardar lista de rutinas en Firebase
        Map<String, Object> updates = new HashMap<>();
        updates.put("email", user.getEmail());
        updates.put("name", user.getName());
        updates.put("password", user.getPassword());
        updates.put("skinType", user.getSkinType());
        updates.put("routineList", routineList);

        // Verificar los datos que se van a guardar
        Log.d("UserDao_updateUser", "Datos que se van a guardar en Firebase: " + updates.toString());

        userRef.updateChildren(updates).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("UserDao_updateUser", "El usuario se ha actualizado en la base de datos.");
            } else {
                Log.e("UserDao_updateUser", "El usuario NO se ha actualizado en la base de datos. ERROR: " + task.getException().getMessage());
            }
        });
    }

}


