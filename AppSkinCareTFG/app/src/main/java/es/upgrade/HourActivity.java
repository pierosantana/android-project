package es.upgrade;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import es.upgrade.UI.fragments.HourDescriptionFragment;
import es.upgrade.UI.fragments.QuestionnaireFragment;
import es.upgrade.UI.fragments.SkinDescriptionFragment;
import es.upgrade.entidad.Routine;
import es.upgrade.entidad.Schedule;
import es.upgrade.entidad.User;

public class HourActivity extends AppCompatActivity implements
        QuestionnaireFragment.OnQuestionnaireCompletedListener {

    private Button btnNext;
    private int selectedOption = -1;
    private Routine routine;
    private TextView tvDontKnow,tvTittle;
    private ProgressBar progressBar;
    private int progress = 0;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hour);

        btnNext = findViewById(R.id.btnNext);
        progressBar = findViewById(R.id.progressBar);
        tvTittle = findViewById(R.id.tvTittle);
        routine = (Routine) getIntent().getSerializableExtra("routine");
        tvDontKnow = findViewById(R.id.NoIdeaChoose);


        if (routine == null) {
            routine = new Routine();  
        }
        // Create a new instance of the QuestionnaireFragment
        QuestionnaireFragment questionnaireFragment = new QuestionnaireFragment();

        // Set the arguments
        Bundle args = new Bundle();
        args.putInt("num_options", 2);// Numbre of options
        args.putStringArray("options_texts", new String[]{
                "Complete", // Option 1
                "Night" // Option 2
        });
        questionnaireFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_Container, questionnaireFragment);
        transaction.commit();

        btnNext.setEnabled(false);

        
        btnNext.setOnClickListener(v -> {
            if (selectedOption != -1) {
                
                if (selectedOption == 0) {
                    routine.setSchedule(Schedule.COMPLETE);
                } else if (selectedOption == 1) {
                    routine.setSchedule(Schedule.NIGHT);
                }
                nextActivity(33);
            }
        });

        // Get the progress from the previous activity
        progress = getIntent().getIntExtra("progress", 0);
        updateProgressBar(progress);

        tvDontKnow.setOnClickListener(v -> {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.hour_description_fragment, new HourDescriptionFragment())
                    .addToBackStack(null)
                    .commit();

            
            findViewById(R.id.tvTittle).setVisibility(View.GONE);
            findViewById(R.id.btnNext).setVisibility(View.GONE);
            findViewById(R.id.NoIdeaChoose).setVisibility(View.GONE);
            findViewById(R.id.include_progress).setVisibility(View.GONE);
            findViewById(R.id.fragment_Container).setVisibility(View.GONE);

            
            findViewById(R.id.hour_description_fragment).setVisibility(View.VISIBLE);
        });


    }

    /**
     * Starts the SkinCareTypeActivity and passes the current progress and routine to it.
     *
     * @param progress The current progress to be passed to the next activity. 
     *                 This value will be incremented by 33 before being passed.
     */
    private void nextActivity(int progress) {
        Intent intent = new Intent(this, SkinCareTypeActivity.class);
        intent.putExtra("progress", progress + 33);
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
     *                 - If progress is greater than or equal to 1, step1 circle is filled.
     *                 - If progress is greater than 33, step2 circle is filled.
     *                 - If progress is greater than or equal to 66, step3 circle is filled.
     *                 - If progress is greater than or equal to 100, step4 circle is filled.
     *                 Otherwise, the circles are set to empty.
     */
    private void updateStepCircles(int progress) {
        findViewById(R.id.step1).setBackgroundResource(progress >= 1 ? R.drawable.circle_filled : R.drawable.circle_empty);
        findViewById(R.id.step2).setBackgroundResource(progress > 33 ? R.drawable.circle_filled : R.drawable.circle_empty);
        findViewById(R.id.step3).setBackgroundResource(progress >= 66 ? R.drawable.circle_filled : R.drawable.circle_empty);
        findViewById(R.id.step4).setBackgroundResource(progress >= 100 ? R.drawable.circle_filled : R.drawable.circle_empty);
    }
    /**
     * Callback method invoked when the questionnaire is completed.
     *
     * @param selectedOption The option selected by the user. 
     *                       0 indicates "Complete" and any other value indicates "Night".
     */
    @Override
    public void onQuestionnaireCompleted(int selectedOption) {
        this.selectedOption = selectedOption;
        btnNext.setEnabled(true);
        Toast.makeText(this, "Selected option: " + (selectedOption == 0 ? "Complete" : "Night"), Toast.LENGTH_SHORT).show();
    }
}