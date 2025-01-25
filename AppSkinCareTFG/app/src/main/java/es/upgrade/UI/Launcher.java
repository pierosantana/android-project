package es.upgrade.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import es.upgrade.R;

public class Launcher extends AppCompatActivity {

    //FirebaseAuth permite la autenticación y verificación de usuarios
    //a través de Firebase.
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_launcher);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        firebaseAuth = FirebaseAuth.getInstance();

        // Simulamos el tiempo de carga, pero la lógica de verificación se ejecuta de inmediato
        int TimeLoading = 5000; // 

        //Programa una tarea para que se ejecute después del retraso especificado en 'TimeLoading'
        new Handler().postDelayed(this::verifyUser, TimeLoading);
    }

    /**
     * El método `verifyUser` verifica si un usuario está autenticado con Firebase y lo redirecciona a
     * la actividad adecuada según su estado de autenticación.
     */
    private void verifyUser() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        Log.d("PantallaCarga", "Usuario autenticado: " + (firebaseUser != null));

        // Si no hay un usuario autenticado, lo redirigimos al MainActivity
        if (firebaseUser == null) {
            Log.d("PantallaCarga", "Usuario no autenticado. Redirigiendo a MainActivity.");
            startActivity(new Intent(Launcher.this, MainActivity.class));
            finish();
        } else {
            // Si el usuario está autenticado, lo redirigimos al MenuPrincipal
            Log.d("PantallaCarga", "Usuario autenticado. Redirigiendo a MenuPrincipal.");
            startActivity(new Intent(Launcher.this, UserMenu.class));
            finish();
        }
    }

}