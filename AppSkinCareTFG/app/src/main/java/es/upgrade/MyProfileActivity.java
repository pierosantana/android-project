package es.upgrade;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import de.hdodenhof.circleimageview.CircleImageView;
import es.upgrade.dao.UserDao;
import es.upgrade.entidad.User;

public class MyProfileActivity extends AppCompatActivity {

    private EditText nameEditText, emailEditText;
    private TextView skinTypeText;
    private FloatingActionButton editProfileButton;
    private CircleImageView profileImage;
    private ImageButton editImageButton;

    private ActivityResultLauncher<Intent> galleryLauncher;

    private boolean isEditing = false;
    private User user;

    private String previousName;
    private String previousEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        // Views
        nameEditText = findViewById(R.id.profile_name);
        emailEditText = findViewById(R.id.profile_email);
        skinTypeText = findViewById(R.id.profile_skin_type);
        editProfileButton = findViewById(R.id.edit_profile_button);
        profileImage = findViewById(R.id.profileImage);
        editImageButton = findViewById(R.id.editImagebutton);

        user = User.getInstance();

        // Populate initial values
        populateUserData();

        // Disable fields at start
        setFieldsEditable(false);

        // Toggle editing profile info
        editProfileButton.setOnClickListener(v -> {
            if (!isEditing) {
                previousName = nameEditText.getText().toString();
                previousEmail = emailEditText.getText().toString();

                setFieldsEditable(true);
                isEditing = true;
                editProfileButton.setImageResource(R.drawable.ic_check);
            } else {
                String newName = nameEditText.getText().toString().trim();
                String newEmail = emailEditText.getText().toString().trim();

                if (newName.isEmpty() || newEmail.isEmpty()) {
                    Snackbar.make(findViewById(R.id.profile_container), "Name and email cannot be empty", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                user.setName(newName);
                user.setEmail(newEmail);
                UserDao.getInstance().updateUser();

                setFieldsEditable(false);
                isEditing = false;
                editProfileButton.setImageResource(R.drawable.ic_edit);

                Snackbar.make(findViewById(R.id.profile_container), "Changes saved", Snackbar.LENGTH_LONG)
                        .setAction("Undo", undoView -> {
                            nameEditText.setText(previousName);
                            emailEditText.setText(previousEmail);
                            user.setName(previousName);
                            user.setEmail(previousEmail);
                            UserDao.getInstance().updateUser();

                            Snackbar.make(findViewById(R.id.profile_container), "Changes reverted", Snackbar.LENGTH_SHORT).show();
                        }).show();
            }
        });

        // Configure the selector image
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();
                        if (selectedImageUri != null) {
                            profileImage.setImageURI(selectedImageUri);
                            // TODO: puedes guardar la URI en la base de datos si lo necesitas
                        }
                    }
                });


        editImageButton.setOnClickListener(v -> openGallery());
    }

    private void populateUserData() {
        nameEditText.setText(user.getName() != null ? user.getName() : "");
        emailEditText.setText(user.getEmail() != null ? user.getEmail() : "");
        skinTypeText.setText(user.getSkinType() != null ? user.getSkinType().toString() : "Undefined");

    }

    private void setFieldsEditable(boolean editable) {
        nameEditText.setEnabled(editable);
        emailEditText.setEnabled(editable);

        nameEditText.setInputType(editable ? InputType.TYPE_CLASS_TEXT : InputType.TYPE_NULL);
        emailEditText.setInputType(editable ? InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS : InputType.TYPE_NULL);

        nameEditText.setBackgroundResource(editable ? R.drawable.edit_text_background : android.R.color.transparent);
        emailEditText.setBackgroundResource(editable ? R.drawable.edit_text_background : android.R.color.transparent);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
    }
}