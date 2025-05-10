package es.upgrade;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import es.upgrade.UI.fragments.SettingsFragment;

public class SettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Cargamos el fragmento de configuración
        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}