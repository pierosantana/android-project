package es.upgrade.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import es.upgrade.HourActivity;
import es.upgrade.R;
import es.upgrade.SkinTypeActivity;
import es.upgrade.dao.UserDao;
import es.upgrade.entidad.SkinType;
import es.upgrade.entidad.User;
import es.upgrade.manager.AuthenticatorManager;


public class UserMenu extends AppCompatActivity {

    private LinearLayout layoutExit, layoutNewRoutine;
    AuthenticatorManager authenticatorManager = new AuthenticatorManager();

    // Recuperar el usuario
    User user = User.getInstance();
    
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
        // Enlazar los LinearLayouts a sus respectivos IDs
        layoutExit = findViewById(R.id.layoutExit);
        layoutNewRoutine = findViewById(R.id.layoutNewRoutine);


        // Establecer listeners de clic en los LinearLayouts
        layoutExit.setOnClickListener(v -> logOut());

        layoutNewRoutine.setOnClickListener(v -> {
            Toast.makeText(this, "Haz elegido Nueva Rutina "  + user.getName(), Toast.LENGTH_SHORT).show();
            if (user.getSkynType() == null) {
                startActivity(new Intent(UserMenu.this, SkinTypeActivity.class));
            } else {
                startActivity(new Intent(UserMenu.this, HourActivity.class));
            }
        });
    }


    /**
     * El método `logOut` cierra la sesión del usuario en Firebase, lo redirecciona a la
     * actividad del Launcher y finaliza la actividad actual.
     */
    private void logOut() {
        // Cerrar sesión en Firebase
        Toast.makeText(this, "Bye " + user.getName(), Toast.LENGTH_SHORT).show();
        authenticatorManager.logout();

        // Redirige a Launcher (para que se verifique si el usuario está logueado o no)
        startActivity(new Intent(UserMenu.this, UserLogin.class));
    }

}