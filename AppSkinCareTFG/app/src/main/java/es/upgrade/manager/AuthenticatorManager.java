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
    * The `register` function creates a new user account using the provided email and password in
    * Firebase Authentication and invokes the `onCompleteListener` when the operation is complete.
    * 
    * @param email The `email` parameter in the `register` method is a `String` that represents the
    * email address of the user who is registering for an account.
    * @param password The `password` parameter in the `register` method is a string that represents the
    * password that the user wants to use for their account. This password will be used during the
    * registration process to create a new user account with Firebase Authentication.
    * @param onCompleteListener The `onCompleteListener` parameter is an instance of the
    * `OnCompleteListener` interface that defines a callback method `onComplete` which will be called
    * when the task is complete. In this case, it is used to handle the result of creating a user with
    * the provided email and password in Firebase authentication.
    */
    public void register(String email, String password, OnCompleteListener<AuthResult> onCompleteListener) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                });
    }
    
    /**
     * The `login` function in Java signs in a user with the provided email and password using Firebase
     * authentication and invokes the `onCompleteListener` upon completion.
     * 
     * @param email The `email` parameter in the `login` method represents the email address entered by
     * the user for authentication. This email will be used along with the password to sign in to the
     * Firebase authentication system.
     * @param password The `password` parameter in the `login` method is a `String` type variable that
     * represents the password input provided by the user for authentication. It is used along with the
     * `email` parameter to sign in a user using Firebase Authentication.
     * @param onCompleteListener The `onCompleteListener` parameter is an instance of the
     * `OnCompleteListener` interface that defines a callback method `onComplete` which will be called
     * when the login operation is complete. This callback method will receive an `AuthResult` object
     * as a parameter, which represents the result of the authentication process.
     */
    public void login(String email, String password, OnCompleteListener<AuthResult> onCompleteListener) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                });;
    }
    
    /**
     * The `logout()` function signs the user out of Firebase authentication.
     */
    public void logout() {
        firebaseAuth.signOut();
    }


   
   /**
    * The hashPassword function takes a password as input, hashes it using SHA-256 algorithm, and
    * returns the hashed password encoded in Base64 format.
    * 
    * @param password The `hashPassword` method you provided takes a password as input, hashes it using
    * the SHA-256 algorithm, and returns the hashed password encoded in Base64 format.
    * @return The method `hashPassword` is returning the hashed password as a Base64 encoded string
    * with no line breaks.
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
