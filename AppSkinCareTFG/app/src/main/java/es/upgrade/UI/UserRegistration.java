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

        // Configure the progress dialog
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
     * Validates the user input data for registration.
     * <p>
     * This method checks the following conditions:
     * <ul>
     *   <li>If the name field is empty, it shows a message to add a name.</li>
     *   <li>If the email does not match the standard email pattern, it shows a message to enter a valid email.</li>
     *   <li>If the password field is empty, it shows a message to add a password.</li>
     *   <li>If the password is less than 6 characters long, it shows a message that the password must be at least 6 characters long.</li>
     *   <li>If the confirm password field is empty, it shows a message to confirm the password.</li>
     *   <li>If the password and confirm password do not match, it shows a message that the passwords must match.</li>
     * </ul>
     * If all conditions are met, it proceeds to create an account with the provided email, password, and name.
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
    * The `createAccount` method registers a user account with the provided email,
    * password, and name, handling exceptions.
    *
    * @param mail the email address to use to create the account.
    * @param password the password chosen by the user for their account.
    * @param name the real name of the user for whom the account is being created.
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
     * The `saveInformation` method saves the user's information to the database.
     *
     * @param mail the email address entered by the user during the registration process.
     * @param password the user's password entered during the registration process.
     * @param name the user's name entered during the registration process.
     */
    private void saveInformation(String mail, String password, String name) {
        progressDialog.setMessage("Saving information...");

        // Generar el hash de la contraseña
        String passwordHash = authenticatorManager.hashPassword(password);
        if (passwordHash == null) {
            progressDialog.dismiss();
            showMessage("Error generating password hash");
            return;
        }
        // Reinicialize the user instance
        User.resetInstance();

        User user = User.getInstance();
        user.setName(name);
        user.setEmail(mail);
        user.setPassword(passwordHash);

       
        user.setRoutineList(new ArrayList<>());

        // Save the user information to the database
        userDao.saveUser(user, task -> {
            progressDialog.dismiss();
            if (task.isSuccessful()) {
                showMessage("Account successfully created");
                startSession(mail, password);
            } else {
                showMessage("Error saving user information");
            }
        });
    }
    /**
    * The `startSession` method takes the email and password values,
    * sends them to the UserMenu activity, and redirects to it.
    *
    * @param mail the email address entered by the user during
    * the registration process.
    * @param password the password the user entered during the registration process.
    */
    private void startSession(String mail, String password) {
        // Show the user's email and password in the log
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
    * The `showToast` method displays a short-duration notification message in an
    * Android application.
    *
    * @param message represents the text message you want to display.
    */
    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
