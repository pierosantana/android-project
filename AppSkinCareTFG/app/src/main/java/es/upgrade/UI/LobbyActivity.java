package es.upgrade.UI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nafis.bottomnavigation.NafisBottomNavigation;

import de.hdodenhof.circleimageview.CircleImageView;
import es.upgrade.HourActivity;
import es.upgrade.R;
import es.upgrade.SkinTypeActivity;
import es.upgrade.MyRoutinesActivity;
import es.upgrade.UI.fragments.CalendarFragment;
import es.upgrade.UI.fragments.EmptyFragment;
import es.upgrade.UI.fragments.ProductsFragment;
import es.upgrade.entidad.Routine;
import es.upgrade.entidad.User;
import es.upgrade.manager.AuthenticatorManager;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class LobbyActivity extends AppCompatActivity {
    private static final int ID_CALENDAR = 1;
    private static final int ID_HOME = 2;
    private static final int ID_PRODUCTS = 3;


    NafisBottomNavigation bottomNavigation;
    private AuthenticatorManager authenticatorManager;
    private ActivityResultLauncher<Intent> galleryLauncher;
    private Routine routine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);  // Cargar layout de Lobby

        // Configurar márgenes para las barras del sistema (si es necesario)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        authenticatorManager = new AuthenticatorManager();


        // Verificar si el usuario está logueado
        if (authenticatorManager.getCurrentUser() == null) {
            startActivity(new Intent(this, LobbyActivity.class));
            finish();
            return;
        }


        // Inicializar el BottomNavigation
        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.add(new NafisBottomNavigation.Model(1, R.drawable.ic_calendar));
        bottomNavigation.add(new NafisBottomNavigation.Model(2, R.drawable.ic_home));
        bottomNavigation.add(new NafisBottomNavigation.Model(3, R.drawable.ic_beauty_products));

        bottomNavigation.setOnClickMenuListener(new Function1<NafisBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(NafisBottomNavigation.Model model) {
                switch (model.getId()) {
                    case ID_HOME:
                        // Si selecciona Home, se mantiene en LobbyActivity sin fragmentos
                        loadHomeFragment();
                        break;

                    case ID_CALENDAR:
                        // Si selecciona Calendar, mostrar el fragmento CalendarFragment
                        loadFragment(new CalendarFragment());
                        break;

                    case ID_PRODUCTS:
                        // Si selecciona Products, mostrar el fragmento ProductsFragment
                        loadFragment(new ProductsFragment());
                        break;
                }
                return null;
            }
        });
        // Cargar el fragmento inicial si es necesario, por ejemplo, HomeFragment
        loadHomeFragment();

        // Configuración de los elementos del UserMenu directamente en la Activity
        configureUserMenu();
    }
    // Función para cargar el fragmento de Home (vacío o con la interfaz de Lobby)
    private void loadHomeFragment() {
        findViewById(R.id.mainUser).setVisibility(View.VISIBLE);
        // Aquí no se necesita ningún fragmento, ya que solo se debe mostrar el Lobby sin fragmentos
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.barCustomMenu, new EmptyFragment()) // Un fragmento vacío que muestra el Lobby
                .commit();
    }

    // Función para cargar los fragmentos (Calendar, Products)
    private void loadFragment(Fragment fragment) {
        findViewById(R.id.mainUser).setVisibility(View.GONE);
        // Reemplazar el contenido del contenedor con el fragmento adecuado
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.barCustomMenu, fragment)
                .commit();
    }

    private void configureUserMenu() {
        // Configurar elementos del menú de usuario
        CircleImageView profileImage = findViewById(R.id.profileImage);
        ImageButton editImageButton = findViewById(R.id.editImagebutton);
        TextView tvName = findViewById(R.id.userName);
        TextView tvSkin = findViewById(R.id.skinType);

        User user = User.getInstance();

        // Establecer nombre del usuario
        tvName.setText(user.getName());
        // Verificar y mostrar el tipo de piel

       // if (user.getSkinType() != null) {
         //   tvSkin.setText(user.getSkinType().toString());  // Muestra el tipo de piel en el TextView
        //}



        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();
                        if (selectedImageUri != null) {
                            profileImage.setImageURI(selectedImageUri);
                        } else {
                            showToast("Error selecting image.");
                        }
                    } else {
                        showToast("No image selected.");
                    }
                });

        // Establecer listener para cambiar la imagen de perfil
        editImageButton.setOnClickListener(v -> openGallery());

        // Configuración de los botones de acción del menú
        findViewById(R.id.btnProfile).setOnClickListener(v -> showToast("My Profile"));
        findViewById(R.id.btnNewRoutine).setOnClickListener(v -> {
            if (user.getSkinType() == null) {
                startActivity(new Intent(LobbyActivity.this, SkinTypeActivity.class));
            } else {
                if(user.getSkinType() != null){
                    routine = new Routine();
                    routine.setSkinType(user.getSkinType());
                    Log.e("LobbyActivity","SkinType es nulo");
                }
                Intent intent = new Intent(this, HourActivity.class);
                intent.putExtra("routine", routine); // Pasar la rutina a la siguiente actividad
                startActivity(intent);
            }
        });
        findViewById(R.id.btnMyRoutines).setOnClickListener(v -> {
            if (user.getRoutineList() == null) {
                showToast("No routines created yet");
            }
            Intent intent = new Intent(LobbyActivity.this, MyRoutinesActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.btnLogout).setOnClickListener(v -> logOut());
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
    }

    private void logOut() {
        Toast.makeText(this, "Logging out, bye " + User.getInstance().getName(), Toast.LENGTH_SHORT).show();
        authenticatorManager.logout();
        finish();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
