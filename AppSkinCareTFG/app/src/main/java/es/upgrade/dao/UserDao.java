package es.upgrade.dao;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import es.upgrade.UI.Launcher;
import es.upgrade.UI.MainActivity;
import es.upgrade.UI.UserMenu;
import es.upgrade.entidad.User;


public class UserDao {
    //FirebaseAuth permite la autenticación y verificación de usuarios
    //a través de Firebase.
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    User user;
    private final DatabaseReference userReference;

    // El constructor `UserDao()` en la clase `UserDao` está inicializando una instancia `FirebaseDatabase`
    // utilizando el método `FirebaseDatabase.getInstance()`, que recupera la instancia `FirebaseApp`
    // predeterminada.
    public UserDao(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        userReference = database.getReference("Users"); // NODE "Users" in Firebase
    }
    /**
     * El método `saveUser` guarda un 'User' en una base de datos de Firebase usando
     * el correo electrónico del usuario como clave después de reemplazar '.' con '_',
     * debido a las restricciones de Firebase.
     *
     * @param user El parámetro `user` es un objeto de tipo `User` que contiene
     * información sobre un usuario.
     * @param onCompleteListener El parámetro `onCompleteListener` es una interfaz
     * que le permite especificar una función de devolución de llamada que se
     * ejecutará cuando se complete la operación de guardar el usuario en la base de datos.
     */
    public void saveUser(User user, OnCompleteListener<Void> onCompleteListener){
        String userId = user.getEmail().replace(".","_");// Reemplazar '.' porque Firebase no los permite
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

}

