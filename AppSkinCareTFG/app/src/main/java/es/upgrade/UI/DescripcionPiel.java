package es.upgrade.UI;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.Arrays;
import java.util.List;

import es.upgrade.R;
import es.upgrade.dao.Adaptador;
import es.upgrade.entidad.SkinType;

public class DescripcionPiel extends AppCompatActivity {
    private ViewPager2 viewPager;
    private Adaptador adapter;
    private List<SkinType> tiposDePiel;
    //private Button btnConfirmar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_descripcion_piel);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.descripcionPiel), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        viewPager = findViewById(R.id.viewPager);
        //btnConfirmar = findViewById(R.id.btnConfirmar);
        // Lista de tipos de piel con imagen y descripción
        tiposDePiel = Arrays.asList(SkinType.values());

        // Configurar el adaptador y ViewPager
        adapter = new Adaptador(this, tiposDePiel);
        viewPager.setAdapter(adapter);

        // Conectar el indicador de páginas
        WormDotsIndicator dotsIndicator = findViewById(R.id.dotsIndicator);
        dotsIndicator.attachTo(viewPager);

        // Botón de Confirmación
        /*btnConfirmar.setOnClickListener(view -> {
            int posicion = viewPager.getCurrentItem();
            SkinType pielSeleccionada = tiposDePiel.get(posicion);
            Toast.makeText(this, "Seleccionaste: " + pielSeleccionada.getDescription(), Toast.LENGTH_SHORT).show();
        });*/
    }
}