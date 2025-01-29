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

import es.upgrade.UI.UserLogin;
import es.upgrade.UI.UserRegistration;

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

        btnNormal.setOnClickListener(v -> nextActivity());
        btnSeca.setOnClickListener(v -> nextActivity());
        btnMixta.setOnClickListener(v -> nextActivity());

        btnNoSabes = findViewById(R.id.btn_no_sabes);

        btnNoSabes.setOnClickListener(v -> {
            startActivity(new Intent(SkinTypeActivity.this, SkinDescriptionActivity.class));
        });

    }

    public void nextActivity(){
        startActivity(new Intent(SkinTypeActivity.this, HourActivity.class));
    }
}