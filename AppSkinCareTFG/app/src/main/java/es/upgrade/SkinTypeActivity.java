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

import es.upgrade.UI.DescripcionPiel;
import es.upgrade.UI.UserLogin;
import es.upgrade.UI.UserRegistration;
import es.upgrade.dao.UserDao;
import es.upgrade.entidad.Routine;
import es.upgrade.entidad.SkinType;
import es.upgrade.entidad.User;

public class SkinTypeActivity extends AppCompatActivity {

    private Button btnNormal;
    private Button btnSeca;
    private Button btnMixta;
    private Button btnNoSabes;
    private TextView tv_bienvenido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_skin_type);

        tv_bienvenido = findViewById(R.id.tv_bienvenido);

        btnNormal = findViewById(R.id.btn_normal);
        btnSeca = findViewById(R.id.btn_seca);
        btnMixta = findViewById(R.id.btn_mixta);
        User user = User.getInstance();
        Routine routine = Routine.getInstance();

        btnNormal.setOnClickListener(v ->{
            routine.setSkinType(SkinType.NORMAL);
            user.setSkynType(SkinType.NORMAL);
            nextActivity();
        }) ;
        btnSeca.setOnClickListener(v ->{
            routine.setSkinType(SkinType.DRY);
            user.setSkynType(SkinType.DRY);
                    nextActivity();
        });
        btnMixta.setOnClickListener(v -> {
            routine.setSkinType(SkinType.COMBINATION);
            user.setSkynType(SkinType.COMBINATION);
            nextActivity();
        });

        btnNoSabes = findViewById(R.id.btn_no_sabes);

        btnNoSabes.setOnClickListener(v -> {
            startActivity(new Intent(SkinTypeActivity.this, DescripcionPiel.class));
        });
        tv_bienvenido.setText(User.getInstance().getName());

    }

    public void nextActivity(){
        startActivity(new Intent(SkinTypeActivity.this, HourActivity.class));
    }
}