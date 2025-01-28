package es.upgrade;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.upgrade.entidad.SkinType;

public class SkinDescriptionActivity extends AppCompatActivity {
    private RecyclerView recyclerView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recycler_view_skin);

        recyclerView = findViewById(R.id.recyclerViewTypeSkin);


        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));


        SkinType[] skinArray = SkinType.values();  //Hay que ver esto porque tengo dudas


        SkinAdapter adapter = new SkinAdapter(skinArray);
        recyclerView.setAdapter(adapter);
    }
}