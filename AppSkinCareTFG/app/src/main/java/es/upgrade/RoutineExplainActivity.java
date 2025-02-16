package es.upgrade;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import es.upgrade.dao.StepAdapter;
import es.upgrade.entidad.Routine;
import es.upgrade.entidad.Step;

public class RoutineExplainActivity extends AppCompatActivity {

    private TextView tvRoutineTitle, tvRoutineType, tvStepCounter, backToRoutines;
    private RecyclerView recyclerViewSteps;
    private List<Step> stepList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_explain);

        // Inicialización de vistas
        backToRoutines = findViewById(R.id.tvBackToRoutines);
        tvRoutineTitle = findViewById(R.id.tvRoutineTitleDetail);
        tvRoutineType = findViewById(R.id.tvRoutineTypeDetail);
        tvStepCounter = findViewById(R.id.tvStepCounter); // Asegúrate que este ID esté en el XML
        recyclerViewSteps = findViewById(R.id.rvSteps);

        // Obtener la rutina desde el Singleton
        Routine routine = Routine.getInstance();

        // Mostrar información de la rutina
        tvRoutineTitle.setText("Rutina: " + routine.getRoutineType());
        tvRoutineType.setText(routine.isNightRoutine() ? "Rutina Nocturna" : "Rutina Diurna");

        // Generar los pasos para la rutina
        stepList = generateSteps(routine);

        // Crear el adaptador para los pasos
        StepAdapter stepAdapter = new StepAdapter(stepList);
        recyclerViewSteps.setAdapter(stepAdapter);
        recyclerViewSteps.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Actualizar el contador de pasos
        tvStepCounter.setText("Paso 1 de " + stepList.size()); // Contador inicial

        // Manejar el evento de regreso a las rutinas
        backToRoutines.setOnClickListener(v -> {
            // Navegar de regreso a la actividad de rutinas
            Intent intent = new Intent(RoutineExplainActivity.this, MyRoutinesActivity.class);
            startActivity(intent);
        });
    }

    // Método para generar los pasos de la rutina
    private List<Step> generateSteps(Routine routine) {
        List<Step> steps = new ArrayList<>();
        int stepCount = routine.getStepCount();

        for (int i = 1; i <= stepCount; i++) {
            steps.add(new Step(
                    "Paso " + i,
                    "Descripción del paso " + i,
                    (i * 10) + " segundos", // Duración del paso
                    "Consejo útil para el paso " + i
            ));
        }
        return steps;
    }
}
