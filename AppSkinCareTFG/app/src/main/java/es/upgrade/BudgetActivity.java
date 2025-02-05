package es.upgrade;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import es.upgrade.entidad.Budget;
import es.upgrade.entidad.Routine;

public class BudgetActivity extends AppCompatActivity {
    private Button customized;
    private Button economic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_budget);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.budget_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        customized = findViewById(R.id.btn_personalizada);
        economic = findViewById(R.id.btn_economico);
        Routine routine = Routine.getInstance();

        customized.setOnClickListener(v ->{
            startActivity(new Intent(BudgetActivity.this, ReviewAndEditActivity.class));
            routine.setBudget(Budget.CUSTOMIZED);
        });

        economic.setOnClickListener(v->{
            startActivity(new Intent(BudgetActivity.this, ReviewAndEditActivity.class));
            routine.setBudget(Budget.ECONOMIC);
        });
    }
}