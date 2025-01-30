package es.upgrade;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import es.upgrade.dao.UserDao;
import es.upgrade.entidad.Routine;
import es.upgrade.entidad.User;

public class ReviewAndEditActivity extends AppCompatActivity {
    private TextView resumen;
    private Button continuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_review_and_edit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.RW), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        resumen = findViewById(R.id.tv_resumen);
        continuar = findViewById(R.id.btn_continuar);


        User user = User.getInstance();
        resumen.setText(user.toString());
        continuar.setOnClickListener(view -> {
            UserDao userDao = UserDao.getInstance();

            Routine routine = Routine.getInstance();
            user.addRoutine(routine);

            userDao.updateUser();


        });

    }
}