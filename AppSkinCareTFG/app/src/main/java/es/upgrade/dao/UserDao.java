package es.upgrade.dao;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import es.upgrade.entidad.User;


public class UserDao {
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

}

