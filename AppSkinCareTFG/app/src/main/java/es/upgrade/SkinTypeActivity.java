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

        // Create a new instance of QuestionnaireFragment
        QuestionnaireFragment questionnaireFragment = new QuestionnaireFragment();

        
        Bundle args = new Bundle();
        args.putInt("num_options", 3); // Número de opciones
        args.putStringArray("options_texts", new String[]{
                "Dry skin", // Option 1
                "Normal skin", // Option 2
                "Combination skin" // Option 3
        });
        questionnaireFragment.setArguments(args);

        
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, questionnaireFragment);
        transaction.commit();

        
        btnNext.setEnabled(false);

        
        btnNext.setOnClickListener(v -> {
            if (selectedOption != -1) {
                SkinType selectedSkinType = SkinType.values()[selectedOption]; 
                selectSkinType(selectedSkinType);
            }
        });

        whichIsMySkin.setOnClickListener(v -> {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.skin_description_fragment_container, new SkinDescriptionFragment())
                    .addToBackStack(null)
                    .commit();

            
            findViewById(R.id.tv_configuracion).setVisibility(View.GONE);
            findViewById(R.id.btn_next).setVisibility(View.GONE);
            findViewById(R.id.NoIdeaSkin).setVisibility(View.GONE);
            findViewById(R.id.include_progress).setVisibility(View.GONE);
            findViewById(R.id.fragment_container).setVisibility(View.GONE);

            
            findViewById(R.id.skin_description_fragment_container).setVisibility(View.VISIBLE);
        });
    }

    /**
     * Callback method invoked when the questionnaire is completed.
     *
     * @param selectedOption The option selected by the user in the questionnaire.
     */
    @Override
    public void onQuestionnaireCompleted(int selectedOption) {
        this.selectedOption = selectedOption; 

        
        btnNext.setEnabled(true);

        
        Toast.makeText(this, "Selected option: " + selectedOption, Toast.LENGTH_SHORT).show();
    }

    /**
     * Selects the skin type for the user and updates the routine accordingly.
     *
     * @param type the skin type to be selected
     */
    private void selectSkinType(SkinType type) {
        User user = User.getInstance();
        routine.setSkinType(type);
        user.setSkinType(type);
        progress = 1; 
        nextActivity();
    }

    /**
     * Navigates to the HourActivity, passing the current progress and routine as extras in the intent.
     * Logs the routine for debugging purposes.
     */
    private void nextActivity() {
        Log.d("TAG", "nextActivity: "  + routine);
        Intent intent = new Intent(this, HourActivity.class);
        intent.putExtra("progress", progress);
        intent.putExtra("routine", routine); // Pasar la rutina a la siguiente actividad
        startActivity(intent);
    }

    /**
     * Updates the progress bar and step circles based on the given progress value.
     *
     * @param progress the current progress value to set on the progress bar
     */
    private void updateProgressBar(int progress) {
        progressBar.setProgress(progress);
        updateStepCircles(progress);
    }

    /**
     * Updates the background resource of step circles based on the given progress.
     * 
     * This method sets the background resource of four step circles (step1, step2, step3, step4)
     * to either a filled circle or an empty circle depending on the progress value.
     * 
     * @param progress an integer representing the current progress, where:
     *                 - step1 is filled if progress is greater than or equal to 1
     *                 - step2 is filled if progress is greater than or equal to 33
     *                 - step3 is filled if progress is greater than or equal to 66
     *                 - step4 is filled if progress is greater than or equal to 100
     */
    private void updateStepCircles(int progress) {
        findViewById(R.id.step1).setBackgroundResource(progress >= 1 ? R.drawable.circle_filled : R.drawable.circle_empty);
        findViewById(R.id.step2).setBackgroundResource(progress >= 33 ? R.drawable.circle_filled : R.drawable.circle_empty);
        findViewById(R.id.step3).setBackgroundResource(progress >= 66 ? R.drawable.circle_filled : R.drawable.circle_empty);
        findViewById(R.id.step4).setBackgroundResource(progress >= 100 ? R.drawable.circle_filled : R.drawable.circle_empty);
    }

}
