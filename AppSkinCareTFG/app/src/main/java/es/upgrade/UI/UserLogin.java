package es.upgrade.UI;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import es.upgrade.R;
import es.upgrade.dao.UserDao;
import es.upgrade.entidad.User;
import es.upgrade.manager.AuthenticatorManager;

public class UserLogin extends AppCompatActivity {
    private  EditText etMail, etPassword;
    private  Button btnLogin;
    private TextView registerText;
    private final AuthenticatorManager authenticatorManager = new AuthenticatorManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etMail = findViewById(R.id.MailLogin);
        etPassword = findViewById(R.id.PassLogin);
        btnLogin = findViewById(R.id.Btn_Log);
        registerText = findViewById(R.id.NewUserTxt);

        User user = User.getInstance();

        // Recibir email y password desde el registro
        Intent intent = getIntent();
        String receivedEmail = intent.getStringExtra("email");
        String receivedPassword = intent.getStringExtra("password");

        // Agregar logs para verificar que los datos se están recibiendo
        Log.d("UserLogin_onCreate", "Received Email: " + receivedEmail);
        Log.d("UserLogin_onCreate", "Received Password: " + receivedPassword);
        Log.d("UserLogin_onCreate", "DATOS: " + user.getName() + " " + user.getEmail());

        // Completar los campos automáticamente si los datos existen
        if (receivedEmail != null) {
            etMail.setText(receivedEmail);
        }
        if (receivedPassword != null) {
            etPassword.setText(receivedPassword);
        }

        btnLogin.setOnClickListener(v -> validateAndLogin());
        registerText.setOnClickListener(v -> startActivity(new Intent(UserLogin.this,UserRegistration.class)));

    }
    /**
     * El método `validateAndLogin` verifica si el correo electrónico es válido y
     * si la contraseña no está vacía. Si ambas se cumple, se inicia sesión como usuario.
     */
    private void validateAndLogin() {
        String mail = etMail.getText().toString();
        String password = etPassword.getText().toString();

        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            showToast("Invalid email.");
        } else if (TextUtils.isEmpty(password)) {
            showToast("Enter password.");
        } else {
            loginUser(mail, password);
        }
    }
    /**
     * El método `loginUser` intenta autenticar a un usuario con el correo electrónico y
     * la contraseña proporcionados, mostrando un mensaje de bienvenida y
     * navegando al menú de usuario tras iniciar sesión exitosamente.
     *
     * @param mail representa la dirección de correo electrónico del usuario que intenta
     * iniciar sesión.
     * @param password representa la contraseña ingresada por el usuario cuando intenta
     * iniciar sesión.
     */
    private void loginUser(String mail, String password) {
        authenticatorManager.login(mail,password,task -> {
            if(task.isSuccessful()){
                FirebaseUser userAuth = authenticatorManager.getCurrentUser();
                if(userAuth != null) {
                    UserDao userDao = UserDao.getInstance();
                    userDao.recoveryUser(userR -> {
                        Log.d("UserLogin_loginUser", "Usuario recuperado: " + userR);
                        showToast("Welcome " +  userR.getName());
                    });

                    startActivity(new Intent(UserLogin.this, LobbyActivity.class));
                    finish();
                }
            }else{
                showToast("Login error");
            }
        });
    }
    /**
     * El método `showToast` muestra un mensaje de notificación de corta duración en una
     * aplicación de Android.
     *
     * @param message representa el mensaje de texto que desea mostrar.
     */
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}

