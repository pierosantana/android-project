package es.upgrade;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HourActivity extends AppCompatActivity {

    private Button btnMananaNoche;
    private Button  btnNoche;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hour);

        btnMananaNoche = findViewById(R.id.btn_manana_noche);
        btnNoche = findViewById(R.id.btn_noche);

    }

    public void nextActivity(){
        //startActivity(new Intent(HourActivity.class, ));
    }
}