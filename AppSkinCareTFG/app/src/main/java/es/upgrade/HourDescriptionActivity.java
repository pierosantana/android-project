package es.upgrade;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import es.upgrade.entidad.SkinType;

public class HourDescriptionActivity extends AppCompatActivity {
    private RecyclerView recyclerView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recycler_view_hour);

        recyclerView = findViewById(R.id.recyclerViewHour);


        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));


        List<String> horas = Arrays.asList("Mañana, Mañana y Noche"); //Hay que ver esto porque tengo dudas


        HourAdapter adapter = new HourAdapter(horas);
        recyclerView.setAdapter(adapter);
    }
}