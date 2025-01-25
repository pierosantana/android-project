package es.upgrade.UI;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import es.upgrade.R;
import es.upgrade.dao.UserDao;
import es.upgrade.entidad.User;
import es.upgrade.manager.AuthenticatorManager;

public class UserRegistration extends AppCompatActivity {
    private EditText etName, etMail, etPassword, etConfirmPassword;
    private Button btnRegisterUser;
    private TextView alreadyAccount;

    private final AuthenticatorManager authenticatorManager = new AuthenticatorManager();
    private final UserDao userDao = new UserDao();
    private AlertDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etName = findViewById(R.id.EtName);
        etMail = findViewById(R.id.EtMail);
        etPassword = findViewById(R.id.EtPassword);
        etConfirmPassword = findViewById(R.id.EtConfirmPassword);
        btnRegisterUser = findViewById(R.id.BtnRegisterUser);
        alreadyAccount = findViewById(R.id.AlreadyAccount);

        // Configuración del diálogo de progreso
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_progress_dialog, null);
        progressDialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        btnRegisterUser.setOnClickListener(v -> dataValidator());
        alreadyAccount.setOnClickListener(v -> startActivity(new Intent(UserRegistration.this, UserLogin.class)));
    }

    private void dataValidator() {
        String name = etName.getText().toString().trim();
        String mail = etMail.getText().toString().trim();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        if (TextUtils.isEmpty(name)) {
            showMessage("Add name");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            showMessage("Enter a valid email");
        } else if (TextUtils.isEmpty(password)) {
            showMessage("Add password");
        } else if (password.length() < 6) {
            showMessage("Password must be at least 6 characters long");
        } else if (TextUtils.isEmpty(confirmPassword)) {
            showMessage("Confirm your password");
        } else if (!password.equals(confirmPassword)) {
            showMessage("Password must be matched");
        } else {
            createAccount(mail, password, name);
        }
    }

    private void createAccount(String mail, String password, String name) {
        progressDialog.show();
        authenticatorManager.register(mail, password, authResult -> {
           if(authResult.isSuccessful()){
           saveInformation(mail,password,name);
           }else{
               progressDialog.dismiss();
               Exception exception = authResult.getException();
               if(exception !=null){
                   showMessage("Error: " + authResult.getException().getMessage());
                   exception.printStackTrace();
               }
           }
        });
    }

    private void saveInformation(String mail, String password, String name) {
        progressDialog.setMessage("Saving information...");

        // Generar el hash para la contraseña
        String passwordHash = authenticatorManager.hashPassword(password);
        if (passwordHash == null) {
            progressDialog.dismiss();
            showMessage("Error al generar el hash de la contraseña");
            return;
        }
        User user = new User();
        user.setName(name);
        user.setEmail(mail);
        user.setPassword(passwordHash); // Almacena el hash en tu base de datos, si es necesario

        // Guardar usuario en la base de datos
        userDao.saveUser(user, task -> {
            progressDialog.dismiss();
            if (task.isSuccessful()) {
                showMessage("Cuenta creada con éxito");
                startSession(mail, password);
            } else {
                showMessage("Error al guardar la información del usuario");
            }
        });
    }

    private void startSession(String mail, String password) {
        authenticatorManager.login(mail, password, authResult -> {
            if (authResult.isSuccessful()) {  // Comprobar si fue exitoso
                Intent intent = new Intent(UserRegistration.this, UserLogin.class);
                intent.putExtra("email", mail);
                intent.putExtra("password", password);
                startActivity(intent);
                finish();
            }else {
                showMessage("Error to initialize session: " + authResult.getException().getMessage());
            }
        });
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
