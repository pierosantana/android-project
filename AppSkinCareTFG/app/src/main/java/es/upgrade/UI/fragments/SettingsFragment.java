package es.upgrade.UI.fragments;

import android.os.Bundle;
import android.widget.Toast;
import android.widget.LinearLayout;
import android.widget.EditText;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import es.upgrade.R;
import es.upgrade.UI.UserLogin;
import es.upgrade.manager.AuthenticatorManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class SettingsFragment extends PreferenceFragmentCompat {

    private final AuthenticatorManager authenticatorManager = new AuthenticatorManager();

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        // Cambiar contraseña
        Preference changePassword = findPreference("change_password");
        if (changePassword != null) {
            changePassword.setOnPreferenceClickListener(preference -> {
                // Crear un LinearLayout para contener ambos campos de texto
                LinearLayout layout = new LinearLayout(requireContext());
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText currentPasswordInput = new EditText(requireContext());
                currentPasswordInput.setHint("Current password");
                layout.addView(currentPasswordInput);

                final EditText newPasswordInput = new EditText(requireContext());
                newPasswordInput.setHint("New password");
                newPasswordInput.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
                layout.addView(newPasswordInput);

                new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                        .setTitle("Change Password")
                        .setMessage("Please enter your current password and new password.")
                        .setView(layout)
                        .setPositiveButton("Change", (dialog, which) -> {
                            String currentPassword = currentPasswordInput.getText().toString();
                            String newPassword = newPasswordInput.getText().toString();

                            if (currentPassword.isEmpty() || newPassword.isEmpty()) {
                                Toast.makeText(getContext(), "Please fill in both fields", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            // Reautenticación utilizando el correo y contraseña actuales
                            FirebaseAuth auth = FirebaseAuth.getInstance();
                            String email = auth.getCurrentUser().getEmail(); // Recuperamos el correo del usuario actual
                            if (email != null) {
                                auth.getCurrentUser().reauthenticate(EmailAuthProvider.getCredential(email, currentPassword))
                                        .addOnCompleteListener(task -> {
                                            if (task.isSuccessful()) {
                                                auth.getCurrentUser().updatePassword(newPassword)
                                                        .addOnCompleteListener(task1 -> {
                                                            if (task1.isSuccessful()) {
                                                                Toast.makeText(getContext(), "Password changed successfully!", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Toast.makeText(getContext(), "Error changing password: " + task1.getException(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                            } else {
                                                Toast.makeText(getContext(), "Current password is incorrect", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                Toast.makeText(getContext(), "Error: Unable to retrieve user email", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
                return true;
            });
        }

        // Eliminar cuenta
        Preference deleteAccount = findPreference("delete_account");
        if (deleteAccount != null) {
            deleteAccount.setOnPreferenceClickListener(preference -> {
                // Pide la contraseña actual del usuario antes de eliminar la cuenta
                final EditText currentPasswordInput = new EditText(requireContext());
                currentPasswordInput.setHint("Current password");
                currentPasswordInput.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);

                new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                        .setTitle("Delete Account")
                        .setMessage("Please enter your current password to confirm account deletion.")
                        .setView(currentPasswordInput)
                        .setPositiveButton("Delete", (dialog, which) -> {
                            String currentPassword = currentPasswordInput.getText().toString();

                            FirebaseAuth auth = FirebaseAuth.getInstance();
                            String email = auth.getCurrentUser().getEmail();
                            String uid = auth.getCurrentUser().getUid(); // Obtenemos el UID para borrar datos del usuario

                            if (email != null) {
                                auth.getCurrentUser().reauthenticate(EmailAuthProvider.getCredential(email, currentPassword))
                                        .addOnCompleteListener(task -> {
                                            if (task.isSuccessful()) {
                                                // Primero borramos el usuario de la auth
                                                auth.getCurrentUser().delete()
                                                        .addOnCompleteListener(task1 -> {
                                                            if (task1.isSuccessful()) {
                                                                // Ahora borramos los datos del Realtime Database
                                                                FirebaseDatabase.getInstance().getReference("Users")
                                                                        .child(uid)
                                                                        .removeValue()
                                                                        .addOnSuccessListener(aVoid -> {
                                                                            Toast.makeText(getContext(), "Account and data deleted", Toast.LENGTH_SHORT).show();
                                                                            authenticatorManager.logout();
                                                                            startActivity(new android.content.Intent(requireContext(), UserLogin.class));
                                                                            requireActivity().finish();
                                                                        })
                                                                        .addOnFailureListener((Exception e) -> {
                                                                            Toast.makeText(getContext(), "Error deleting user data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                        });
                                                            } else {
                                                                Toast.makeText(getContext(), "Error deleting account: " + task1.getException(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                            } else {
                                                Toast.makeText(getContext(), "Current password is incorrect", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                Toast.makeText(getContext(), "Error: Unable to retrieve user email", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
                return true;
            });
        }
    }
}
