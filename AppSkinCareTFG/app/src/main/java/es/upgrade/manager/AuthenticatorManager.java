package es.upgrade.manager;

import android.util.Base64;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class AuthenticatorManager {
    private final FirebaseAuth firebaseAuth;

    public AuthenticatorManager(){
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    public FirebaseUser getCurrentUser(){
        return firebaseAuth.getCurrentUser();
    }

    /**
     * El método `register` crea una nueva cuenta de usuario con el correo electrónico y la
     * contraseña proporcionados mediante la autenticación de Firebase.
     *
     * @param email es un `String` que representa la dirección de correo electrónico del usuario
     * que se está registrando para una cuenta.
     * @param password es un `String` que representa la contraseña que el usuario desea usar
     * para su cuenta. Estavse usará durante el proceso de registro para crear una nueva cuenta
     *vcon Firebase Authentication mediante el método `createUserWithEmailAndPassword`.
     * @param onCompleteListener es una instancia de la interfaz `OnCompleteListener` que define
     * un método de devolución de llamada `onComplete` que se llamará cuando se complete la tarea.
     * Este método de devolución de llamada recibirá el resultado de la operación de autenticación
     * (éxito o fracaso) como parámetro.
     */
    public void register(String email, String password, OnCompleteListener<AuthResult> onCompleteListener) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                });
    }
    /**
     * El método `login` inicia la sesión de un usuario con el correo electrónico y la contraseña
     * mediante la autenticación de Firebase e invoca `onCompleteListener` al finalizar.
     *
     * @param email representa la dirección de correo electrónico ingresada por el usuario para
     * la autenticación.Se usará junto con la contraseña para iniciar la sesión del usuario
     * mediante la autenticación de Firebase.
     * @param password representa la contraseña ingresada por el usuario para la autenticación.
     * Se usará junto con el email para iniciar la sesión del usuario mediante la autenticación
     * de Firebase.
     * @param onCompleteListener es una instancia de la interfaz `OnCompleteListener` que define
     * un método de devolución de llamada `onComplete` que se llamará cuando se complete la tarea.
     * Este método de devolución de llamada recibirá el resultado de la operación de autenticación
     * (éxito o fracaso) como parámetro.
     */
    public void login(String email, String password, OnCompleteListener<AuthResult> onCompleteListener) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                });;
    }
    /**
     * El método `logout` cierra la sesión del usuario de la autenticación de Firebase.
     */
    public void logout() {
        firebaseAuth.signOut();
    }


    /**
     * El método hashPassword toma una contraseña como entrada, la convierte en hash
     * utilizando el algoritmo SHA-256 y devuelve la contraseña en hash codificada
     * en formato Base64.
     *
     * @param password contraseña tomada por el metodo.
     * @return devuelve la contraseña en hash como una cadena codificada en Base64.
     */
    public String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeToString(hashBytes, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
