package es.upgrade.UI;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

import es.upgrade.R;
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

        btnLogin.setOnClickListener(v -> validateAndLogin());
        registerText.setOnClickListener(v -> startActivity(new Intent(UserLogin.this,UserRegistration.class)));

    }

    private void validateAndLogin() {
        String mail = etMail.getText().toString();
        String password = etPassword.getText().toString();

        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            showToast("Correo inválido");
        } else if (TextUtils.isEmpty(password)) {
            showToast("Ingrese contraseña");
        } else {
            loginUser(mail, password);
        }
    }

    private void loginUser(String mail, String password) {
        authenticatorManager.login(mail,password,task -> {
            if(task.isSuccessful()){
                FirebaseUser user = authenticatorManager.getCurrentUser();
                showToast("Welcome " + user.getEmail());
                startActivity(new Intent(UserLogin.this,UserMenu.class));
                finish();
            }else{
                showToast("Error al iniciar session");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}

