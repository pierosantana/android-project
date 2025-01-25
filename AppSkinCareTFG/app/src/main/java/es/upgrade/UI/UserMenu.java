package es.upgrade.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

import es.upgrade.R;

public class UserMenu extends AppCompatActivity {
    private Button btnLogOut;
    
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
        btnLogOut.setOnClickListener(v -> logOut()) ;
    }

    private void logOut() {
        // Cerrar sesión en Firebase
        FirebaseAuth.getInstance().signOut();

        // Redirigir a Launcher (para que se verifique si está logueado o no)
        startActivity(new Intent(UserMenu.this, Launcher.class));
        finish();  // Finalizamos la actividad actual
    }
}