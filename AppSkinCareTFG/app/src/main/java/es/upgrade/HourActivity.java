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
import es.upgrade.entidad.Schedule;

public class HourActivity extends AppCompatActivity {

    private Button btnMananaNoche;
    private Button  btnNoche;
    private Button btnNosabes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hour);

        btnMananaNoche = findViewById(R.id.btn_manana_noche);
        btnNoche = findViewById(R.id.btn_noche);
        btnNosabes = findViewById(R.id.btn_no_sabes);
        Routine routine = Routine.getInstance();

        btnMananaNoche.setOnClickListener(v->{
            startActivity(new Intent(HourActivity.this,SkinCareTypeActivity.class));
            routine.setSchedule(Schedule.COMPLETE);
        });
        btnNoche.setOnClickListener(v->{
            startActivity(new Intent(HourActivity.this,SkinCareTypeActivity.class));

            routine.setSchedule(Schedule.NIGHT);
        });
        btnNosabes.setOnClickListener(v->{
            startActivity(new Intent(HourActivity.this,HourDescriptionActivity.class));
        });

    }

    public void nextActivity(){
        //startActivity(new Intent(HourActivity.class, ));
    }
}