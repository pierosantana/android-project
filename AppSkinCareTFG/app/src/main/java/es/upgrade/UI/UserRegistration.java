package es.upgrade.UI;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import java.util.ArrayList;

import es.upgrade.R;
import es.upgrade.dao.UserDao;
import es.upgrade.entidad.Routine;
import es.upgrade.entidad.User;
import es.upgrade.manager.AuthenticatorManager;

public class UserRegistration extends AppCompatActivity {
    private EditText etName, etMail, etPassword, etConfirmPassword;
    private Button btnRegisterUser;
    private TextView alreadyAccount;

    private final AuthenticatorManager authenticatorManager = new AuthenticatorManager();
    private final UserDao userDao = UserDao.getInstance();
    //Se utiliza para mostrar una indicación visual al usuario de que alguna operación está en progreso,
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

    /**
     * El método `dataValidator` valida la entrada del usuario para los campos de nombre,
     * correo electrónico, contraseña y confirmación de contraseña antes de crear una cuenta.
     */
    private void dataValidator() {
        String name = etName.getText().toString().trim();
        String mail = etMail.getText().toString().trim();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        //Condiciones
        if (TextUtils.isEmpty(name)) {//Si el nombre está vacio
            showMessage("Add name");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {//Si el email no sigue el patrón de un email
            showMessage("Enter a valid email");
        } else if (TextUtils.isEmpty(password)) {//Si la contraseña está vacía
            showMessage("Add password");
        } else if (password.length() < 6) {//Si la contraseña tiene menos de 6 letras
            showMessage("Password must be at least 6 characters long");
        } else if (TextUtils.isEmpty(confirmPassword)) {//Si la contraseña está vacía
            showMessage("Confirm your password");
        } else if (!password.equals(confirmPassword)) {// Si las contrseñas no coinciden
            showMessage("Password must be matched");
        } else {
            createAccount(mail, password, name);
        }
    }


    /**
     * El método `createAccount` registra una cuenta de usuario con el correo electrónico,
     * la contraseña y el nombre proporcionados,manejando las excepciones.
     *
     * @param mail la dirección de correo electrónico que se usará para crear la cuenta.
     * @param password la contraseña elegida por el usuario para su cuenta.
     * @param name el nombre real del usuario para quien se está creando la cuenta.
     */
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

    /**
     * El método `saveInformation` guarda la información del usuario mediante el hash de la contraseña,
     * creando un nuevo objeto Usuario y guardándolo en la base de datos.
     *
     * @param mail la dirección de correo electrónico del usuario.
     * @param password la contraseña del usuario que ingresó durante el proceso de registro.
     * @param name el nombre del usuario cuya información se está guardando.
     */
    private void saveInformation(String mail, String password, String name) {
        progressDialog.setMessage("Saving information...");

        // Generar el hash para la contraseña
        String passwordHash = authenticatorManager.hashPassword(password);
        if (passwordHash == null) {
            progressDialog.dismiss();
            showMessage("Error al generar el hash de la contraseña");
            return;
        }
        // Reinicializar las instancias de User y Routine
        User.resetInstance();

        User user = User.getInstance();
        user.setName(name);
        user.setEmail(mail);
        user.setPassword(passwordHash);

        // Asegurarse de que la lista de rutinas esté vacía
        user.setRoutineList(new ArrayList<>());

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
    /**
     * El método `startSession` registra los valores de correo electrónico y contraseña,
     * los envía a la activity UserMenu, y redirecciona a esta.
     *
     * @param mail la dirección de correo electrónico ingresada por el usuario durante
     * el proceso de registro.
     * @param password la contraseña del usuario que ingresó durante el proceso de registro.
     */
    private void startSession(String mail, String password) {
        // Mostrar los valores de correo y contraseña antes de enviar
        Log.d("UserRegistration", "Email: " + mail);
        Log.d("UserRegistration", "Password: " + password);

        Intent intent = new Intent(UserRegistration.this, UserLogin.class);
        intent.putExtra("email", mail);
        intent.putExtra("password", password);
        Log.d("UserRegistration", "Redirecting to UserLogin...");
        startActivity(intent);
        finish();
    }
    /**
     * El método `showToast` muestra un mensaje de notificación de corta duración en una
     * aplicación de Android.
     *
     * @param message representa el mensaje de texto que desea mostrar.
     */
    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
