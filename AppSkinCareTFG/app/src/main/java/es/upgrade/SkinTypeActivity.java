package es.upgrade;

import static android.app.ProgressDialog.show;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import es.upgrade.UI.fragments.QuestionnaireFragment;
import es.upgrade.UI.fragments.SkinDescriptionFragment;
import es.upgrade.entidad.SkinType;
import es.upgrade.entidad.User;
import es.upgrade.entidad.Routine;

public class SkinTypeActivity extends AppCompatActivity implements QuestionnaireFragment.OnQuestionnaireCompletedListener {

    private TextView tvConfig, whichIsMySkin;
    private ProgressBar progressBar;
    private Button btnNext;
    private int selectedOption = -1;
    private int progress = 0;
    private Routine routine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin_type);

        tvConfig = findViewById(R.id.tv_configuracion);
        whichIsMySkin = findViewById(R.id.NoIdeaSkin);
        progressBar = findViewById(R.id.progressBar);
        btnNext = findViewById(R.id.btn_next);
        routine = new Routine();

        // Crear una instancia del fragmento
        QuestionnaireFragment questionnaireFragment = new QuestionnaireFragment();

        // Pasar argumentos al fragmento
        Bundle args = new Bundle();
        args.putInt("num_options", 3); // Número de opciones
        args.putStringArray("options_texts", new String[]{
                "Dry skin", // Option 1
                "Normal skin", // Option 2
                "Combination skin" // Option 3
        });
        questionnaireFragment.setArguments(args);

        // Agregar el fragmento al contenedor
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, questionnaireFragment);
        transaction.commit();

        // Configurar el botón de avanzar (inicialmente deshabilitado)
        btnNext.setEnabled(false);

        // Al hacer clic en el botón de avanzar, pasa a la siguiente actividad
        btnNext.setOnClickListener(v -> {
            if (selectedOption != -1) {
                SkinType selectedSkinType = SkinType.values()[selectedOption]; // Suponiendo que SkinType es un enum
                selectSkinType(selectedSkinType);
            }
        });

        whichIsMySkin.setOnClickListener(v -> {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.skin_description_fragment_container, new SkinDescriptionFragment())
                    .addToBackStack(null)
                    .commit();

            // Ocultar los elementos que no queremos ver
            findViewById(R.id.tv_configuracion).setVisibility(View.GONE);
            findViewById(R.id.btn_next).setVisibility(View.GONE);
            findViewById(R.id.NoIdeaSkin).setVisibility(View.GONE);
            findViewById(R.id.include_progress).setVisibility(View.GONE);
            findViewById(R.id.fragment_container).setVisibility(View.GONE);

            // Mostrar el contenedor del fragmento de descripción
            findViewById(R.id.skin_description_fragment_container).setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void onQuestionnaireCompleted(int selectedOption) {
        this.selectedOption = selectedOption; // Guarda la opción seleccionada

        // Habilitamos el botón de confirmación
        btnNext.setEnabled(true);

        // Puedes mostrar un Toast para feedback del usuario
        Toast.makeText(this, "Selected option: " + selectedOption, Toast.LENGTH_SHORT).show();
    }

    private void selectSkinType(SkinType type) {
        User user = User.getInstance();
        routine.setSkinType(type);
        user.setSkinType(type);
        progress = 1; // Puedes ajustar el progreso según lo que necesites
        nextActivity();
    }

    private void nextActivity() {
        Log.d("TAG", "nextActivity: "  + routine);
        Intent intent = new Intent(this, HourActivity.class);
        intent.putExtra("progress", progress);
        intent.putExtra("routine", routine); // Pasar la rutina a la siguiente actividad
        startActivity(intent);
    }

    private void updateProgressBar(int progress) {
        progressBar.setProgress(progress);
        updateStepCircles(progress);
    }

    private void updateStepCircles(int progress) {
        findViewById(R.id.step1).setBackgroundResource(progress >= 1 ? R.drawable.circle_filled : R.drawable.circle_empty);
        findViewById(R.id.step2).setBackgroundResource(progress >= 33 ? R.drawable.circle_filled : R.drawable.circle_empty);
        findViewById(R.id.step3).setBackgroundResource(progress >= 66 ? R.drawable.circle_filled : R.drawable.circle_empty);
        findViewById(R.id.step4).setBackgroundResource(progress >= 100 ? R.drawable.circle_filled : R.drawable.circle_empty);
    }

}
