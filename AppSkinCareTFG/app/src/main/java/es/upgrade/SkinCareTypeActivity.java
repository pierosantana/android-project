package es.upgrade;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import es.upgrade.entidad.Routine;
import es.upgrade.entidad.RoutineType;

public class SkinCareTypeActivity extends AppCompatActivity {

    private Button complete, basic;
    private ProgressBar progressBar;
    private int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_skin_care_type);

        progressBar = findViewById(R.id.progressBar);
        complete = findViewById(R.id.btn_completa);
        basic = findViewById(R.id.btn_basica);

        progress = getIntent().getIntExtra("progress", 66); // Tomamos el progreso de la actividad anterior
        updateProgressBar(progress);

        Routine routine = Routine.getInstance();

        complete.setOnClickListener(v -> selectRoutineType(RoutineType.COMPLETE, routine));
        basic.setOnClickListener(v -> selectRoutineType(RoutineType.BASIC, routine));
    }

    private void selectRoutineType(RoutineType type, Routine routine) {
        routine.setRoutineType(type);
        nextActivity(progress + 33);
    }

    private void nextActivity(int progress) {
        Intent intent = new Intent(this, BudgetActivity.class);
        // Pasamos el progreso a la siguiente actividad
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
