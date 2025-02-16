package es.upgrade.UI.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;
import es.upgrade.HourActivity;
import es.upgrade.MyRoutinesActivity;
import es.upgrade.R;
import es.upgrade.SkinTypeActivity;
import es.upgrade.UI.LobbyActivity;
import es.upgrade.UI.MainActivity;
import es.upgrade.entidad.User;
import es.upgrade.manager.AuthenticatorManager;

public class UserMenuFragment extends Fragment {

    private AuthenticatorManager authenticatorManager = new AuthenticatorManager();
    private User user = User.getInstance();
    private ActivityResultLauncher<Intent> galleryLauncher;

    public UserMenuFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_menu, container, false);

        CircleImageView profileImage = view.findViewById(R.id.profileImage);
        ImageButton editImageButton = view.findViewById(R.id.editImagebutton);
        TextView tvName = view.findViewById(R.id.userName);
        TextView tvSkin = view.findViewById(R.id.skinType);

        tvName.setText(user.getName());

        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();
                        if (selectedImageUri != null) {
                            profileImage.setImageURI(selectedImageUri);
                        } else {
                            showToast("Error al seleccionar la imagen.");
                        }
                    } else {
                        showToast("No se seleccionó ninguna imagen.");
                    }
                });

        editImageButton.setOnClickListener(v -> openGallery());

        view.findViewById(R.id.btnProfile).setOnClickListener(v -> showToast("Mi Perfil"));
        view.findViewById(R.id.btnNewRoutine).setOnClickListener(v -> {
            if (user.getSkinType() == null) {
                startActivity(new Intent(getActivity(), SkinTypeActivity.class));
            } else {
                startActivity(new Intent(getActivity(), HourActivity.class));
            }
        });
        view.findViewById(R.id.btnMyRoutines).setOnClickListener(v -> {
            if(user.getRoutineList() == null){
                showToast("No hay rutinas creadas aun");
            }
            Intent intent = new Intent(getActivity(), MyRoutinesActivity.class);
            startActivity(intent);
        });
        view.findViewById(R.id.btnLogout).setOnClickListener(v -> logOut());

        return view;
        // **🕒 Handler para navegar automáticamente a MainActivity después de 2 segundos**

    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
    }

    private void logOut() {
        Toast.makeText(getActivity(), "Bye " + user.getName(), Toast.LENGTH_SHORT).show();
        authenticatorManager.logout();
        getActivity().finish();
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}