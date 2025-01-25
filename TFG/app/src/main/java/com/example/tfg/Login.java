package com.example.tfg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText MailLogin,PassLogin;
    Button Btn_Log;
    TextView UsuarioNuevoTxt;


    FirebaseAuth firebaseAuth;

    //Validar los datos
    String mail = "", pass = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MailLogin = findViewById(R.id.MailLogin);
        PassLogin = findViewById(R.id.PassLogin);
        Btn_Log = findViewById(R.id.Btn_Log);
        UsuarioNuevoTxt = findViewById(R.id.UsuarioNuevoTxt);

        firebaseAuth = FirebaseAuth.getInstance();



        Btn_Log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidaDatos();
            }
        });

        UsuarioNuevoTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Registro.class));
            }
        });
    }

    private void ValidaDatos() {
        mail = MailLogin.getText().toString();
        pass = PassLogin.getText().toString();

        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            Toast.makeText(this, "Correo Invalido", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Ingrese password", Toast.LENGTH_SHORT).show();
        }
        else{
            LoginUsuario();
        }
    }

    private void LoginUsuario() {
        firebaseAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    Log.d("Login", "Inicio de sesión exitoso: " + user.getEmail());
                    startActivity(new Intent(Login.this,MenuPrincipal.class));
                    Toast.makeText(Login.this, "Bienvenido: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(Login.this, "Verifique si el mail y password son correctos", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}