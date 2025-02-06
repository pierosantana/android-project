package es.upgrade;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import es.upgrade.UI.DescripcionPiel;
import es.upgrade.entidad.Routine;
import es.upgrade.entidad.SkinType;
import es.upgrade.entidad.User;

public class SkinTypeActivity extends AppCompatActivity {

    private Button btnNormal;
    private Button btnSeca;
    private Button btnMixta;
    private TextView tvBienvenido, whichIsMySkin;
    private ProgressBar progressBar;
    private int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_skin_type);

        tvBienvenido = findViewById(R.id.tv_bienvenido);
        btnNormal = findViewById(R.id.btn_normal);
        btnSeca = findViewById(R.id.btn_seca);
        btnMixta = findViewById(R.id.btn_mixta);
        whichIsMySkin = findViewById(R.id.NoIdeaSkin);
        progressBar = findViewById(R.id.progressBar);

        User user = User.getInstance();
        Routine routine = Routine.getInstance();

        updateProgressBar(progress);
        tvBienvenido.setText(user.getName());

        btnNormal.setOnClickListener(v -> selectSkinType(SkinType.NORMAL, user, routine));
        btnSeca.setOnClickListener(v -> selectSkinType(SkinType.DRY, user, routine));
        btnMixta.setOnClickListener(v -> selectSkinType(SkinType.COMBINATION, user, routine));
        whichIsMySkin.setOnClickListener(v -> startActivity(new Intent(this, DescripcionPiel.class)));
    }

    private void selectSkinType(SkinType type, User user, Routine routine) {
        routine.setSkinType(type);
        user.setSkynType(type);
        progress = 1;
        nextActivity();
    }

    private void nextActivity() {
        Intent intent = new Intent(this, HourActivity.class);
        intent.putExtra("progress", progress);
        startActivity(intent);
    }

    private void updateProgressBar(int progress) {
        progressBar.setProgress(progress);
        updateStepCircles(progress);
    }

    private void updateStepCircles(int progress) {
        findViewById(R.id.step1).setBackgroundResource(progress >= 1 ? R.drawable.circle_filled : R.drawable.circle_empty);
        findViewById(R.id.step2).setBackgroundResource(progress >= 33 ? R.drawable.circle_filled : R.drawable.circle_empty);
        findViewById(R.id.step3).setBackgroundResource(progress >= 66 ? R.drawable.circle_filled : R.drawable.circle_empty);
        findViewById(R.id.step4).setBackgroundResource(progress >= 100 ? R.drawable.circle_filled : R.drawable.circle_empty);
    }
}