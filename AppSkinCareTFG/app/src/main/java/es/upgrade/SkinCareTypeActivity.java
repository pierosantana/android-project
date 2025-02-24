package es.upgrade;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import es.upgrade.UI.fragments.QuestionnaireFragment;
import es.upgrade.entidad.Routine;
import es.upgrade.entidad.RoutineType;

public class SkinCareTypeActivity extends AppCompatActivity implements
        QuestionnaireFragment.OnQuestionnaireCompletedListener {

    private Button btnNext;
    private int selectedOption = -1;
    private ProgressBar progressBar;
    private int progress;
    private Routine routine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_skin_care_type);

        progressBar = findViewById(R.id.progressBar);
        btnNext = findViewById(R.id.btn_next);
        progress = getIntent().getIntExtra("progress", 66); 
        updateProgressBar(progress);
        routine = (Routine) getIntent().getSerializableExtra("routine");

        
        QuestionnaireFragment questionnaireFragment = new QuestionnaireFragment();

        
        Bundle args = new Bundle();
        args.putInt("num_options", 2); 
        args.putStringArray("options_texts", new String[]{
                "Complete routine", 
                "Basic routine"    
        });
        questionnaireFragment.setArguments(args);

       
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, questionnaireFragment);
        transaction.commit();

        
        btnNext.setEnabled(false);

        
        btnNext.setOnClickListener(v -> {
            if (selectedOption != -1) {
                
                if (selectedOption == 0) {
                    routine.setRoutineType(RoutineType.COMPLETE);
                } else if (selectedOption == 1) {
                    routine.setRoutineType(RoutineType.BASIC);
                }

                Log.d("Routine", "El RoutineType actual es: " + routine.getRoutineType());
                nextActivity(progress + 33);
            }
        });
    }

    /**
     * Sets the routine type for the given routine and advances to the next activity.
     *
     * @param type The type of the routine to be set.
     * @param routine The routine object to be updated.
     */
    private void selectRoutineType(RoutineType type, Routine routine) {
        routine.setRoutineType(type);
        nextActivity(progress + 33);
    }

    /**
     * Starts the BudgetActivity and passes the current progress and routine to it.
     *
     * @param progress The current progress to be passed to the next activity.
     */
    private void nextActivity(int progress) {
        Intent intent = new Intent(this, BudgetActivity.class);
        intent.putExtra("progress", progress);
        intent.putExtra("routine", routine); 
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
     * @param progress an integer representing the current progress (0-100).
     *                 - If progress is greater than or equal to 1, step 1 circle is filled.
     *                 - If progress is greater than or equal to 33, step 2 circle is filled.
     *                 - If progress is greater than 66, step 3 circle is filled.
     *                 - If progress is greater than or equal to 100, step 4 circle is filled.
     */
    private void updateStepCircles(int progress) {
        findViewById(R.id.step1).setBackgroundResource(progress >= 1 ? R.drawable.circle_filled : R.drawable.circle_empty);
        findViewById(R.id.step2).setBackgroundResource(progress >= 33 ? R.drawable.circle_filled : R.drawable.circle_empty);
        findViewById(R.id.step3).setBackgroundResource(progress > 66 ? R.drawable.circle_filled : R.drawable.circle_empty);
        findViewById(R.id.step4).setBackgroundResource(progress >= 100 ? R.drawable.circle_filled : R.drawable.circle_empty);
    }
    /**
     * Called when the questionnaire is completed.
     *
     * @param selectedOption The option selected by the user. 
     *                       0 represents "Complete routine" and 1 represents "Basic routine".
     *                       This value is used to determine the user's choice and update the UI accordingly.
     */
    @Override
    public void onQuestionnaireCompleted(int selectedOption) {
        this.selectedOption = selectedOption; 
        btnNext.setEnabled(true);
        Toast.makeText(this, "Selected option: " + (selectedOption == 0 ? "Complete routine" : "Basic routine"), Toast.LENGTH_SHORT).show();
    }
}