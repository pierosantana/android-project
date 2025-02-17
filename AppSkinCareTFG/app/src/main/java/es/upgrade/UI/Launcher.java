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


import es.upgrade.R;
import es.upgrade.dao.UserDao;
import es.upgrade.entidad.User;

public class Launcher extends AppCompatActivity {

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

        // Simulamos el tiempo de carga, pero la lógica de verificación se ejecuta de inmediato
        int TimeLoading = 5000; // 

        //Programa una tarea para que se ejecute después del retraso especificado en 'TimeLoading'
        new Handler().postDelayed(this::verifyUser, TimeLoading);


    }

    private void verifyUser() {
        UserDao uDao = UserDao.getInstance();

        boolean UserExist = uDao.verifyFirebaseUser();
        // Si no hay un usuario autenticado, lo redirigimos al MainActivity
        if (!UserExist) {
            Log.d("Launcher_verifyUser", "Usuario no autenticado. Redirigiendo a MainActivity.");
            startActivity(new Intent(Launcher.this, MainActivity.class));
            finish();
        } else {
            uDao.recoveryUser(userR -> {
                Log.d("Launcher_verifyUser", "Usuario recuperado: " + userR);
                if (userR != null) {
                    User user = User.getInstance();
                    user.setName(userR.getName());
                    user.setEmail(userR.getEmail());
                    user.setPassword(userR.getPassword());
                    user.setSkinType(userR.getSkinType());
                    user.setRoutineList(userR.getRoutineList());
                }
                // Si el usuario está autenticado, lo redirigimos al LobbyActivity
                Log.d("Launcher_verifyUser", "Usuario autenticado. Redirigiendo a LobbyActivity.");
                startActivity(new Intent(Launcher.this, LobbyActivity.class));
                finish();
            });
        }
    }

}