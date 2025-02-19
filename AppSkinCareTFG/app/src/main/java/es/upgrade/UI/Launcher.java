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

    /**
     * The `onCreate` method in this Java code snippet sets up the layout for an activity, adjusts
     * window insets, simulates a loading time, and schedules a task to verify the user after a delay.
     * 
     * @param savedInstanceState The `savedInstanceState` parameter in the `onCreate` method of an
     * Android activity is a Bundle object that provides the activity with previously saved state
     * information, if available. This bundle is used to restore the activity to its previous state if
     * it was destroyed and recreated by the system, such as during a
     */
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

        // Simulate a loading time
        int TimeLoading = 5000; // 

        //postDelayed method schedules a task to verify the user after a delay
        new Handler().postDelayed(this::verifyUser, TimeLoading);


    }

    /**
     * The `verifyUser` method checks if a user is authenticated and redirects them to the appropriate
     * activity based on the authentication status.
     */
    private void verifyUser() {
        UserDao uDao = UserDao.getInstance();

        boolean UserExist = uDao.verifyFirebaseUser();
        // If the user is not authenticated, redirect to MainActivity
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
                // If the user is authenticated, redirect to LobbyActivity
                Log.d("Launcher_verifyUser", "Usuario autenticado. Redirigiendo a LobbyActivity.");
                startActivity(new Intent(Launcher.this, LobbyActivity.class));
                finish();
            });
        }
    }

}