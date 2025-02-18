package es.upgrade;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import es.upgrade.UI.MainActivity;
import es.upgrade.dao.SkinTypeAdapter;
import es.upgrade.entidad.SkinType;

public class SkinDescriptionActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private SkinTypeAdapter adapter;
    private WormDotsIndicator dotsIndicator;

    private Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_skin_description);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.skinDescription), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        viewPager = findViewById(R.id.viewPager);
        btnBack = findViewById(R.id.btnBack);
        // List of skin types with image and description
        dotsIndicator = findViewById(R.id.dotsIndicator);

        //Configure the adapter and the viewpager
        adapter = new SkinTypeAdapter();
        viewPager.setAdapter(adapter);

        // “Connect the page indicator”
        dotsIndicator.attachTo(viewPager);


        // Confirmation button
        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(SkinDescriptionActivity.this, SkinTypeActivity.class);
            startActivity(intent);
        });
    }
}