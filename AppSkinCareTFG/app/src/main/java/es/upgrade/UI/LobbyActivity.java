package es.upgrade.UI;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nafis.bottomnavigation.NafisBottomNavigation;

import es.upgrade.R;
import es.upgrade.UI.fragments.CalendarFragment;
import es.upgrade.UI.fragments.ProductsFragment;
import es.upgrade.UI.fragments.UserMenuFragment;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class LobbyActivity extends AppCompatActivity {
    private static final int ID_CALENDAR = 1;
    private static final int ID_HOME = 2;
    private static final int ID_PRODUCTS = 3;

    NafisBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lobby);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bottomNavigation = findViewById(R.id.bottomNavigation);

        // Set up bottom navigation
        bottomNavigation.add(new NafisBottomNavigation.Model(1, R.drawable.ic_calendar));
        bottomNavigation.add(new NafisBottomNavigation.Model(2, R.drawable.ic_home));
        bottomNavigation.add(new NafisBottomNavigation.Model(3, R.drawable.ic_beauty_products));

        // Cargar HomeFragment por defecto al iniciar la app
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.barCustomMenu, new UserMenuFragment())
                .commit();

        bottomNavigation.setOnClickMenuListener(new Function1<NafisBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(NafisBottomNavigation.Model model) {
                Fragment selectedFragment = null;

                if (model.getId() == ID_HOME) {
                    selectedFragment = new UserMenuFragment();
                } else if (model.getId() == ID_CALENDAR) {
                    selectedFragment = new CalendarFragment();
                } else if (model.getId() == ID_PRODUCTS) {
                    selectedFragment = new ProductsFragment();
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.barCustomMenu, selectedFragment)
                            .commit();
                }
                return null;
            }
        });
    }
}