package es.upgrade;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import es.upgrade.entidad.Routine;
import es.upgrade.entidad.RoutineType;

public class SkinCareTypeActivity extends AppCompatActivity {
    private Button complete;
    private Button basic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_skin_care_type);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.skin_care_type), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        complete = findViewById(R.id.btn_completa);
        basic = findViewById(R.id.btn_basica);
        Routine routine = Routine.getInstance();

        complete.setOnClickListener(v->{
            startActivity(new Intent(SkinCareTypeActivity.this,BudgetActivity.class));
            routine.setRoutineType(RoutineType.COMPLETE);
        });

        basic.setOnClickListener(v->{
            startActivity(new Intent(SkinCareTypeActivity.this, BudgetActivity.class));
            routine.setRoutineType(RoutineType.BASIC);
        });

    }
}