package es.upgrade;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import es.upgrade.UI.DescripcionPiel;
import es.upgrade.UI.fragments.QuestionnaireFragment;
import es.upgrade.entidad.SkinType;
import es.upgrade.entidad.User;
import es.upgrade.entidad.Routine;

public class SkinTypeActivity extends AppCompatActivity implements QuestionnaireFragment.OnQuestionnaireCompletedListener {

    private TextView tvConfig, whichIsMySkin;
    private ProgressBar progressBar;
    private Button btnNext;
    private int selectedOption = -1;
    private int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin_type);

        tvConfig = findViewById(R.id.tv_configuracion);
        whichIsMySkin = findViewById(R.id.NoIdeaSkin);
        progressBar = findViewById(R.id.progressBar);
        btnNext = findViewById(R.id.btn_next);

        // Crear una instancia del fragmento
        QuestionnaireFragment questionnaireFragment = new QuestionnaireFragment();

        // Pasar argumentos al fragmento
        Bundle args = new Bundle();
        args.putInt("num_options", 3); // Número de opciones
        args.putStringArray("options_texts", new String[]{
                "Piel Seca", // Opción 1
                "Piel Grasa", // Opción 2
                "Piel Mixta"  // Opción 3
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

        // Click en el texto para la descripción de la piel
        whichIsMySkin.setOnClickListener(v -> startActivity(new Intent(this, DescripcionPiel.class)));
    }

    @Override
    public void onQuestionnaireCompleted(int selectedOption) {
        this.selectedOption = selectedOption; // Guarda la opción seleccionada

        // Habilitamos el botón de confirmación
        btnNext.setEnabled(true);

        // Puedes mostrar un Toast para feedback del usuario
        Toast.makeText(this, "Opción seleccionada: " + selectedOption, Toast.LENGTH_SHORT).show();
    }

    private void selectSkinType(SkinType type) {
        User user = User.getInstance();
        Routine routine = Routine.getInstance();
        routine.setSkinType(type);
        user.setSkinType(type);
        progress = 1; // Puedes ajustar el progreso según lo que necesites
        nextActivity();
    }

    private void nextActivity() {
        Intent intent = new Intent(this, HourActivity.class);
        intent.putExtra("progress", progress);
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
