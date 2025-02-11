package es.upgrade.UI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.widget.ImageButton;

import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;


import de.hdodenhof.circleimageview.CircleImageView;
import es.upgrade.HourActivity;
import es.upgrade.R;
import es.upgrade.SkinTypeActivity;
import es.upgrade.entidad.User;
import es.upgrade.manager.AuthenticatorManager;


public class UserMenu extends AppCompatActivity {
    AuthenticatorManager authenticatorManager = new AuthenticatorManager();

    // Recuperar el usuario
    User user = User.getInstance();
    private ActivityResultLauncher<Intent> galleryLauncher;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_menu);

        CircleImageView profileImage = findViewById(R.id.profileImage);
        ImageButton editImageButton = findViewById(R.id.editImagebutton);


        // Registrar el launcher para abrir la galería y manejar el resultado
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();
                        if (selectedImageUri != null) {
                            profileImage.setImageURI(selectedImageUri);
                        } else {
                            showToast("Error al seleccionar la imagen.");
                        }
                    } else {
                        showToast("No se seleccionó ninguna imagen.");
                    }
                }
                );
        


        // Configuración del botón "Mi Perfil"
        CustomViewMenu btnProfile = findViewById(R.id.btnProfile);
        btnProfile.setButtonText("Mi Perfil");
        btnProfile.setButtonIcon(R.drawable.ic_face);
        btnProfile.setOnClickListener(view ->
                showToast("Acción general: Mi Perfil")
        );

        // Configuración del botón "Nueva Rutina"
        CustomViewMenu btnNewRoutine = findViewById(R.id.btnNewRoutine);
        btnNewRoutine.setButtonText("Nueva Rutina");
        btnNewRoutine.setButtonIcon(R.drawable.ic_plus);
        btnNewRoutine.setOnClickListener(view -> {
            showToast("Has elegido Nueva Rutina ");
                    if (user.getSkinType() == null) {
                        startActivity(new Intent(UserMenu.this, SkinTypeActivity.class));
                    } else {
                        startActivity(new Intent(UserMenu.this, HourActivity.class));
                    }
                });
        // Abrir la galería cuando se presiona el botón de edición
        editImageButton.setOnClickListener(v -> openGallery());

        // Configuración del botón "Mis Rutinas"
        CustomViewMenu btnMyRoutines = findViewById(R.id.btnMyRoutines);
        btnMyRoutines.setButtonText("Mis Rutinas");
        btnMyRoutines.setButtonIcon(R.drawable.products_svgrepo_com);
        btnMyRoutines.setOnClickListener(view ->
                showToast("Acción general: Mis Rutinas")
        );

        // Configuración del botón "Calendario"
        CustomViewMenu btnCalendar = findViewById(R.id.btnCalendar);
        btnCalendar.setButtonText("Calendario");
        btnCalendar.setButtonIcon(R.drawable.ic_calendar);
        btnCalendar.setOnClickListener(view ->
                showToast("Acción general: Calendario")
        );

        // Botón de cierre de sesión (Button estándar)
        findViewById(R.id.btnLogout).setOnClickListener(view -> {
                    logOut();
                    Toast.makeText(this, "Cerrando sesión...", Toast.LENGTH_SHORT).show();
                }

        );
    }
    //Método para abrir la galeria
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        galleryLauncher.launch(intent);

    }


    /**
     * El método `logOut` cierra la sesión del usuario en Firebase, lo redirecciona a la
     * actividad del Launcher y finaliza la actividad actual.
     */
    private void logOut() {
        // Cerrar sesión en Firebase
        Toast.makeText(this, "Bye " + user.getName(), Toast.LENGTH_SHORT).show();
        authenticatorManager.logout();

        // Redirige a Launcher (para que se verifique si el usuario está logueado o no)
        startActivity(new Intent(UserMenu.this, UserLogin.class));
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
