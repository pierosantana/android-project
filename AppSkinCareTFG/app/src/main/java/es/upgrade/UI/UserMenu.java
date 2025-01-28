package es.upgrade.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import es.upgrade.R;
import es.upgrade.SkinTypeActivity;
import es.upgrade.manager.AuthenticatorManager;

public class UserMenu extends AppCompatActivity {

    private Button btnLogOut,btnNewRoutine;
    AuthenticatorManager authenticatorManager = new AuthenticatorManager();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnLogOut = findViewById(R.id.Btn_LogOut);
        btnNewRoutine = findViewById(R.id.Btn_NewRoutine);


        btnLogOut.setOnClickListener(v -> logOut()) ;
        btnNewRoutine.setOnClickListener(v -> startActivity(new Intent(UserMenu.this, SkinTypeActivity.class)));
<<<<<<< HEAD

=======
       
>>>>>>> carlos
    }
    /**
     * El método `logOut` cierra la sesión del usuario en Firebase, lo redirecciona a la
     * actividad del Launcher y finaliza la actividad actual.
     */
    private void logOut() {
        // Cerrar sesión en Firebase
        authenticatorManager.logout();

        // Redirige a Launcher (para que se verifique si el usuario está logueado o no)
        startActivity(new Intent(UserMenu.this, Launcher.class));
        finish();
    }
}