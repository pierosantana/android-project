package es.upgrade;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import es.upgrade.entidad.Budget;
import es.upgrade.entidad.Routine;

public class BudgetActivity extends AppCompatActivity {

    private Button customized, economic;
    private ProgressBar progressBar;
    private int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_budget);

        progressBar = findViewById(R.id.progressBar);
        customized = findViewById(R.id.btn_personalizada);
        economic = findViewById(R.id.btn_economico);

        progress = getIntent().getIntExtra("progress", 100);
        updateProgressBar(progress);

        Routine routine = Routine.getInstance();

        customized.setOnClickListener(v -> selectBudget(Budget.CUSTOMIZED, routine));
        economic.setOnClickListener(v -> selectBudget(Budget.ECONOMIC, routine));
    }

    private void selectBudget(Budget budget, Routine routine) {
        routine.setBudget(budget);
        startActivity(new Intent(this, ReviewAndEditActivity.class));
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