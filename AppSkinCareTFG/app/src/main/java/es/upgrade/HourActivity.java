package es.upgrade;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import es.upgrade.entidad.Routine;
import es.upgrade.entidad.Schedule;

public class HourActivity extends AppCompatActivity {

    private Button btnMananaNoche;
    private Button btnNoche;
    private TextView tvDontKnow;
    private ProgressBar progressBar;
    private int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hour);

        progressBar = findViewById(R.id.progressBar);
        btnMananaNoche = findViewById(R.id.btn_manana_noche);
        btnNoche = findViewById(R.id.btn_noche);
        Routine routine = Routine.getInstance();

        btnMananaNoche.setOnClickListener(v->{
            startActivity(new Intent(HourActivity.this,SkinCareTypeActivity.class));
            routine.setSchedule(Schedule.COMPLETE);
        });
        btnNoche.setOnClickListener(v->{
            startActivity(new Intent(HourActivity.this,SkinCareTypeActivity.class));

            routine.setSchedule(Schedule.NIGHT);
        });

        tvDontKnow = findViewById(R.id.NoIdeaChoose);

        // Tomamos el progreso de la actividad anterior
        progress = getIntent().getIntExtra("progress", 0);
        updateProgressBar(progress);

        btnMananaNoche.setOnClickListener(v -> nextActivity(progress));
        btnNoche.setOnClickListener(v -> nextActivity(progress));
        tvDontKnow.setOnClickListener(v -> startActivity(new Intent(this, HourDescriptionActivity.class)));
    }

    private void nextActivity(int progress) {
        Intent intent = new Intent(this, SkinCareTypeActivity.class);
        // Pasamos el progreso a la siguiente actividad
        intent.putExtra("progress", progress + 33);
        startActivity(intent);
    }

    private void updateProgressBar(int progress) {
        progressBar.setProgress(progress);
        updateStepCircles(progress);
    }

    private void updateStepCircles(int progress) {
        findViewById(R.id.step1).setBackgroundResource(progress >= 1 ? R.drawable.circle_filled : R.drawable.circle_empty);
        findViewById(R.id.step2).setBackgroundResource(progress > 33 ? R.drawable.circle_filled : R.drawable.circle_empty);
        findViewById(R.id.step3).setBackgroundResource(progress >= 66 ? R.drawable.circle_filled : R.drawable.circle_empty);
        findViewById(R.id.step4).setBackgroundResource(progress >= 100 ? R.drawable.circle_filled : R.drawable.circle_empty);
    }

}
