package es.upgrade;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;


import es.upgrade.UI.fragments.QuestionnaireFragment;
import es.upgrade.entidad.Budget;
import es.upgrade.entidad.Routine;

public class BudgetActivity extends AppCompatActivity implements
        QuestionnaireFragment.OnQuestionnaireCompletedListener {

    private Button btnNext;
    private int selectedOption = -1;
    private ProgressBar progressBar;
    private int progress;
    private Routine routine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_budget);


        progressBar = findViewById(R.id.progressBar);
        btnNext = findViewById(R.id.btn_next);
        routine = (Routine) getIntent().getSerializableExtra("routine");

        progress = getIntent().getIntExtra("progress", 100);
        updateProgressBar(progress);

        // Crear una instancia del fragmento
        QuestionnaireFragment questionnaireFragment = new QuestionnaireFragment();

        // Pasar argumentos al fragmento (opcional)
        Bundle args = new Bundle();
        args.putInt("num_options", 2); // Número de opciones que quieres mostrar
        args.putStringArray("options_texts", new String[]{
                "Economic Budget", // Opción 1
                "Customized Budget" // Opción 2
        });
        questionnaireFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, questionnaireFragment);
        transaction.commit();

        // Configurar el botón de avanzar (inicialmente deshabilitado)
        btnNext.setEnabled(false);

        // Al hacer clic en el botón de avanzar, guarda la opción y pasa a la siguiente actividad
        btnNext.setOnClickListener(v -> {
            if (selectedOption != -1) {
                Budget selectedBudget = (selectedOption == 0) ? Budget.ECONOMIC : Budget.CUSTOMIZED;
                routine.setBudget(selectedBudget);
                Log.d("Budget", "El presupuesto actual es: " + selectedBudget);
                nextActivity();
            }
        });
    }

    // Método para cambiar de actividad
    private void nextActivity() {
        Intent intent = new Intent(this, ReviewAndEditActivity.class);
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
        findViewById(R.id.step2).setBackgroundResource(progress >= 33 ? R.drawable.circle_filled : R.drawable.circle_empty);
        findViewById(R.id.step3).setBackgroundResource(progress >= 66 ? R.drawable.circle_filled : R.drawable.circle_empty);
        findViewById(R.id.step4).setBackgroundResource(progress >= 100 ? R.drawable.circle_filled : R.drawable.circle_empty);
    }
    @Override
    public void onQuestionnaireCompleted(int selectedOption) {
        this.selectedOption = selectedOption; // Guarda la opción seleccionada

        // Habilitar el botón de "Avanzar"
        btnNext.setEnabled(true);

        // Mostrar un mensaje de feedback (opcional)
        Toast.makeText(this, "Selected option: " + (selectedOption == 0 ? "Economic Budget" : "Customized Budget"), Toast.LENGTH_SHORT).show();
    }
}