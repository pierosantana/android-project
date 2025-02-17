package es.upgrade;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import es.upgrade.UI.fragments.QuestionnaireFragment;
import es.upgrade.entidad.Routine;
import es.upgrade.entidad.Schedule;
import es.upgrade.entidad.User;

public class HourActivity extends AppCompatActivity implements
        QuestionnaireFragment.OnQuestionnaireCompletedListener {

    private Button btnNext;
    private int selectedOption = -1;
    private Routine routine;
    private TextView tvDontKnow;
    private ProgressBar progressBar;
    private int progress = 0;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hour);

        btnNext = findViewById(R.id.btn_next);
        progressBar = findViewById(R.id.progressBar);

        routine = (Routine) getIntent().getSerializableExtra("routine");


        if (routine == null) {
            routine = new Routine();  // Si es null, crea una nueva instancia
        }
        // Crear una instancia del fragmento
        QuestionnaireFragment questionnaireFragment = new QuestionnaireFragment();

        // Pasar argumentos al fragmento (opcional)
        Bundle args = new Bundle();
        args.putInt("num_options", 2);// Número de opciones que quieres mostrar
        args.putStringArray("options_texts", new String[]{
                "Mañana y Noche", // Opción 1
                "Noche"   // Opción 2
        });
        questionnaireFragment.setArguments(args);

        // Agregar el fragmento al contenedor
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, questionnaireFragment);
        transaction.commit();

        btnNext.setEnabled(false);

        // Al hacer clic en el botón de avanzar, pasar al siguiente paso
        btnNext.setOnClickListener(v -> {
            if (selectedOption != -1) {
                // Asignar el valor a routine según la opción seleccionada
                if (selectedOption == 0) {
                    routine.setSchedule(Schedule.COMPLETE);
                } else if (selectedOption == 1) {
                    routine.setSchedule(Schedule.NIGHT);
                }

                Log.d("Routine", "El Schedule actual es: " + routine.getSchedule());
                nextActivity(33);
            }
        });
        tvDontKnow = findViewById(R.id.NoIdeaChoose);

        // Tomamos el progreso de la actividad anterior
        progress = getIntent().getIntExtra("progress", 0);
        updateProgressBar(progress);


        tvDontKnow.setOnClickListener(v -> startActivity(new Intent(this, HourDescriptionActivity.class)));
    }

    private void nextActivity(int progress) {
        Intent intent = new Intent(this, SkinCareTypeActivity.class);
        // Pasamos el progreso a la siguiente actividad
        intent.putExtra("progress", progress + 33);
        // Pasar la rutina a la siguiente actividad
        intent.putExtra("routine", routine);
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
    @Override
    public void onQuestionnaireCompleted(int selectedOption) {
        this.selectedOption = selectedOption;

        // Habilitar el botón de "Avanzar"
        btnNext.setEnabled(true);

        // Mostrar la opción seleccionada (opcional)
        Toast.makeText(this, "Opción seleccionada: " + (selectedOption == 0 ? "Mañana" : "Noche"), Toast.LENGTH_SHORT).show();
    }
}