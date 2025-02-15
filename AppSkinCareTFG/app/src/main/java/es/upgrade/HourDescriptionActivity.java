package es.upgrade;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.Arrays;
import java.util.List;

import es.upgrade.dao.ScheduleAdapter;
import es.upgrade.dao.SkinTypeAdapter;
import es.upgrade.entidad.SkinType;

public class HourDescriptionActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private ScheduleAdapter adapter;
    private WormDotsIndicator dotsIndicator;

    private Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hour_selection);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.scheduleSelection), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        viewPager = findViewById(R.id.viewPager);
        btnBack = findViewById(R.id.btnBack);
        // Lista de tipos de piel con imagen y descripción
        dotsIndicator = findViewById(R.id.dotsIndicator);

        // Configurar el adaptador y ViewPager
        adapter = new ScheduleAdapter();
        viewPager.setAdapter(adapter);

        // Conectar el indicador de páginas
        dotsIndicator.attachTo(viewPager);


        // Botón de Confirmación
        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(HourDescriptionActivity.this, HourActivity.class);
            startActivity(intent);
        });
    }
}