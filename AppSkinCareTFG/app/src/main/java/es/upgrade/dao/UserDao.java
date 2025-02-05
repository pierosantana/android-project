package es.upgrade.dao;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import es.upgrade.UI.Launcher;
import es.upgrade.UI.MainActivity;
import es.upgrade.UI.UserMenu;
import es.upgrade.entidad.User;


public class UserDao {
    private static UserDao instance = null;

    public static UserDao getInstance() {
        if (instance == null) {
            instance = new UserDao();
        }
        return instance;
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
     * el correo electrónico del usuario como clave después de reemplazar '.' con '_',
     * debido a las restricciones de Firebase.
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
        Log.d("PantallaCarga", "Usuario autenticado: " + (firebaseUser != null));


        // Si no hay un usuario autenticado, lo redirigimos al MainActivity
        if (firebaseUser == null) {
            Log.d("PantallaCarga", "EMAIL: null");
            return false;
        } else {
            Log.d("PantallaCarga", "EMAIL: " + firebaseUser.getEmail());
            return true;
        }
    }

    public void recoveryUser() {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users");
        String userId = firebaseAuth.getUid();
        userRef.child(userId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                User userInfo = task.getResult().getValue(User.class);
                if (userInfo != null) {
                    User user = User.getInstance();
                    user.setName(userInfo.getName());
                    user.setEmail(userInfo.getEmail());
                    user.setPassword(userInfo.getPassword());
                    Log.d("UserDao", "Usuario encontrado: " + userInfo.getName());
                } else {
                    Log.e("UserDao", "Error: No se encontró el usuario en Firebase");
                }
            }else{
                Log.e("UserDao", "Error al recuperar usuario", task.getException());
            }
        });
    }
    public void updateUser() {
        String userId = firebaseAuth.getUid();
        User user = User.getInstance();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference userRef = firebaseDatabase.getReference("Users").child(userId);
        Map<String, Object> updates = new HashMap<>();
        updates.put("email",user.getEmail());
        updates.put("name",user.getName());
        updates.put("password",user.getPassword());
        updates.put("skinType",user.getSkynType());



        userRef.updateChildren(updates).addOnCompleteListener(task -> {
           if(task.isSuccessful()){
               Log.d("UserDao", "Hemos tenido un exito rotundo");
           }else{
               Log.d("UserDao", "Piero se ha equivocado " + task.getException().getMessage());
           }
        });

    }
}


