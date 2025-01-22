package com.example.tfg;

import android.app.AlertDialog;
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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;

public class Registro extends AppCompatActivity {

    EditText EtNombre, EtMail, EtPassword, EtConfirmPassword;
    Button BtnRegisterUser;
    TextView AlreadyAccount;

    FirebaseAuth firebaseAuth;
    AlertDialog progressDialog;

    String nombre = " ", mail = " ", password = " ", confirmarPassword = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);

        EtNombre = findViewById(R.id.EtName);
        EtMail = findViewById(R.id.EtMail);
        EtPassword = findViewById(R.id.EtPassword);
        EtConfirmPassword = findViewById(R.id.EtConfirmPassword);
        BtnRegisterUser = findViewById(R.id.BtnRegisterUser);
        AlreadyAccount = findViewById(R.id.AlreadyAccount);

        firebaseAuth = FirebaseAuth.getInstance();

        // Configuración de AlertDialog para el progreso
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_progress_dialog, null);
        progressDialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        BtnRegisterUser.setOnClickListener(v -> validarDatos());

        AlreadyAccount.setOnClickListener(v -> startActivity(new Intent(Registro.this, Login.class)));
    }

    private void validarDatos() {
        nombre = EtNombre.getText().toString();
        mail = EtMail.getText().toString();
        password = EtPassword.getText().toString();
        confirmarPassword = EtConfirmPassword.getText().toString();

        if (TextUtils.isEmpty(nombre)) {
            Toast.makeText(this, "Ingrese un nombre", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            Toast.makeText(this, "Ingrese un email válido", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Ingrese una contraseña", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(confirmarPassword)) {
            Toast.makeText(this, "Confirme su contraseña", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(confirmarPassword)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        } else {
            CrearCuenta();
        }
    }

    private void CrearCuenta() {
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(mail, password)
                .addOnSuccessListener(authResult -> GuardarInformacion())
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(Registro.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void GuardarInformacion() {
        progressDialog.setMessage("Guardando su inforamcion");
        progressDialog.dismiss();

        // Obtener la identificación de usuario actual
        String uid = firebaseAuth.getUid();

        // Generar el hash de la contraseña
        String passwordHash = generarHashSHA256(password);

        if (passwordHash == null) {
            Toast.makeText(this, "Error al generar el hash de la contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, String> datos = new HashMap<>();
        datos.put("uid", uid);
        datos.put("email", mail);
        datos.put("nombre", nombre);
        datos.put("passwordHash",passwordHash);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios");
        databaseReference.child(uid).setValue(datos)
                .addOnSuccessListener(unused -> {
                    progressDialog.dismiss();
                    Toast.makeText(Registro.this, "Cuenta creada con éxito", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Registro.this, MenuPrincipal.class));
                    finish();
                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(Registro.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
    //Metooo para hashear las contraseñas
    private String generarHashSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Maneja la excepción si es necesario
        }
    }

}
