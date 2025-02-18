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


    /**
     * The `onCreate` method in this Java code initializes the activity layout, sets up system bar
     * margins, checks user authentication, configures a bottom navigation menu, and loads different
     * fragments based on user selection.
     * 
     * @param savedInstanceState The `savedInstanceState` parameter in the `onCreate` method is a
     * Bundle object that provides data about the previous state of the activity if it was previously
     * destroyed and recreated. This bundle allows you to restore the activity to its previous state,
     * such as restoring user interface data or other information. You can
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);  // Cargar layout de Lobby

        // Configurate the system bar margins
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        authenticatorManager = new AuthenticatorManager();


        // Verify if the user is authenticated
        if (authenticatorManager.getCurrentUser() == null) {
            startActivity(new Intent(this, LobbyActivity.class));
            finish();
            return;
        }


        // Initialize the bottom navigation menu
        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.add(new NafisBottomNavigation.Model(1, R.drawable.ic_calendar));
        bottomNavigation.add(new NafisBottomNavigation.Model(2, R.drawable.ic_home));
        bottomNavigation.add(new NafisBottomNavigation.Model(3, R.drawable.ic_beauty_products));

        bottomNavigation.setOnClickMenuListener(new Function1<NafisBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(NafisBottomNavigation.Model model) {
                switch (model.getId()) {
                    case ID_HOME:
                        // If Home is selected, load the HomeFragment
                        loadHomeFragment();
                        break;

                    case ID_CALENDAR:
                        // If Calendar is selected, load the CalendarFragment
                        loadFragment(new CalendarFragment());
                        break;

                    case ID_PRODUCTS:
                        // If Products is selected, load the ProductsFragment
                        loadFragment(new ProductsFragment());
                        break;
                }
                return null;
            }
        });
        // Load the HomeFragment
        loadHomeFragment();

        // Configure the user menu
        configureUserMenu();
    }
    
   /**
    * The function `loadHomeFragment` sets the visibility of a view and replaces a fragment with an
    * empty fragment to display the Lobby.
    */
    private void loadHomeFragment() {
        findViewById(R.id.mainUser).setVisibility(View.VISIBLE);
        // Aquí no se necesita ningún fragmento, ya que solo se debe mostrar el Lobby sin fragmentos
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.barCustomMenu, new EmptyFragment()) // Un fragmento vacío que muestra el Lobby
                .commit();
    }

    
    /**
     * The `loadFragment` function hides a specific view and replaces its content with a given fragment
     * in an Android application.
     * 
     * @param fragment The `fragment` parameter in the `loadFragment` method is an instance of the
     * `Fragment` class that you want to load and display within the specified container in your
     * Android application. When this method is called, the content of the container specified by the
     * `R.id.barCustomMenu` resource ID
     */
    private void loadFragment(Fragment fragment) {
        findViewById(R.id.mainUser).setVisibility(View.GONE);
        // Reemplazar el contenido del contenedor con el fragmento adecuado
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.barCustomMenu, fragment)
                .commit();
    }

    /**
     * The `configureUserMenu` function sets up the user menu interface, including displaying user
     * information, handling image selection, and configuring action buttons for profile, routine
     * creation, viewing routines, and logging out.
     */
    private void configureUserMenu() {
        // Configurar elementos del menú de usuario
        CircleImageView profileImage = findViewById(R.id.profileImage);
        ImageButton editImageButton = findViewById(R.id.editImagebutton);
        TextView tvName = findViewById(R.id.userName);
        TextView tvSkin = findViewById(R.id.skinType);

        User user = User.getInstance();

        
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

        // Confirgure the editImageButton to open the gallery
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
                intent.putExtra("routine", routine); 
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

    /**
     * The `openGallery()` function launches an intent to pick an image from the device's external
     * storage using the system's gallery application.
     */
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
    }

    /**
     * The `logOut()` function logs out the current user, displays a farewell message using a Toast,
     * and finishes the current activity.
     */
    private void logOut() {
        Toast.makeText(this, "Logging out, bye " + User.getInstance().getName(), Toast.LENGTH_SHORT).show();
        authenticatorManager.logout();
        finish();
    }

    /**
     * The function `showToast` displays a short-duration toast message in an Android application.
     * 
     * @param message The `message` parameter in the `showToast` method is a string that represents the
     * text message you want to display in the toast notification.
     */
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
