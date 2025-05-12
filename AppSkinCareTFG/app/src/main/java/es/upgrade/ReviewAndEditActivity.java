package es.upgrade;

import java.math.RoundingMode;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import es.upgrade.UI.AlertDialogCustom;
import es.upgrade.UI.CustomViewOptionsRoutine;
import es.upgrade.dao.UserDao;
import es.upgrade.entidad.Budget;
import es.upgrade.entidad.Routine;
import es.upgrade.entidad.RoutineType;
import es.upgrade.entidad.Schedule;
import es.upgrade.entidad.SkinType;
import es.upgrade.entidad.User;

public class ReviewAndEditActivity extends AppCompatActivity {
    private TextView titulo;
    private Button continuar, edit;
    private LinearLayout optionsContainer; //Contiene los customView
    private Routine routine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_review_and_edit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.RW), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        titulo = findViewById(R.id.tvTitulo);
        continuar = findViewById(R.id.btn_continuar);
        edit = findViewById(R.id.btn_editar);
        optionsContainer = findViewById(R.id.optionsContainer);

        // Obtener la rutina desde el Intent anterior
        routine = (Routine) getIntent().getSerializableExtra("routine");

        if (routine == null) {
                return;

        }

        titulo.setText("Selected items: \n Do you want to modify anything?");

        continuar.setOnClickListener(view -> {
            Intent intent = null;

            if (routine.getRoutineType() == RoutineType.BASIC && routine.getBudget() == Budget.ECONOMIC) {
                intent = new Intent(ReviewAndEditActivity.this, BasicEconomicActivity.class);
            } else if (routine.getRoutineType() == RoutineType.BASIC && routine.getBudget() == Budget.CUSTOMIZED) {
                intent = new Intent(ReviewAndEditActivity.this, BasicCustomizedActivity.class);
            } else if (routine.getRoutineType() == RoutineType.COMPLETE && routine.getBudget() == Budget.ECONOMIC) {
                intent = new Intent(ReviewAndEditActivity.this, CompleteEconomicActivity.class);
            } else if (routine.getRoutineType() == RoutineType.COMPLETE && routine.getBudget() == Budget.CUSTOMIZED) {
                intent = new Intent(ReviewAndEditActivity.this, CompleteCustomizedActivity.class);
            }

            if (intent != null) {
                intent.putExtra("skinType", routine.getSkinType().name());
                intent.putExtra("schedule", routine.getSchedule().name());
                intent.putExtra("routineType", routine.getRoutineType().name());
                intent.putExtra("budget", routine.getBudget().name());
                intent.putExtra("routine", routine);
                startActivity(intent);
            }
        });

        edit.setOnClickListener(v -> {
            AlertDialogCustom.showCustomAlertDialog(
                    this,
                    "Confirmation",
                    "¿Are you sure you want to edit the routine?",
                    "Yes",
                    (dialog, which) -> {
                        startActivity(new Intent(ReviewAndEditActivity.this, SkinTypeActivity.class));
                        dialog.dismiss();
                    },
                    "No",
                    (dialog, which) -> dialog.dismiss()
            );
        });
    }

    
    /**
     * Adds a custom option view to the options container.
     *
     * @param title       The title of the custom option.
     * @param description The description or answer text chosen by the user.
     * @param iconResId   The resource ID of the icon to be displayed.
     */
    private void addCustomOptionView(String title, String description, int iconResId) {
        CustomViewOptionsRoutine customView = new CustomViewOptionsRoutine(this, null);

        // Stablish the title and icon
        customView.setOptionContent(title, iconResId);
        customView.setAnswerText(description);  // Set the description text
        optionsContainer.addView(customView);
    }

    
    /**
     * Formats the given SkinType into a user-friendly string representation.
     *
     * @param skinType the SkinType to format; can be null.
     * @return a string representing the skin type. If the skin type is null, 
     *         returns "Skin type not defined". If the skin type is not recognized,
     *         returns "Unknown skin type".
     */
    private String formatSkinType(SkinType skinType) {
        if (skinType == null) {
            return "Skin type not defined";  // O cualquier texto por defecto
        }
        switch (skinType) {
            case DRY: return "Dry skin";
            case NORMAL: return "Normal skin";
            case COMBINATION: return "Combination skin";
            case SENSITIVE: return "Sensitive skin";
            default: return "Unknown skin type";
        }
    }

    /**
     * Formats the given Schedule object into a string representation.
     *
     * @param schedule the Schedule object to be formatted
     * @return "Night" if the schedule is NIGHT, otherwise "Complete"
     */
    private String formatSchedule(Schedule schedule) {
        return (schedule == Schedule.NIGHT) ? "Night" : "Complete";
    }

    /**
     * Formats the given RoutineType into a string representation.
     *
     * @param routineType the RoutineType to format
     * @return "Basic" if the routineType is BASIC, otherwise "Complete"
     */
    private String formatRoutineType(RoutineType routineType) {
        return (routineType == RoutineType.BASIC) ? "Basic" : "Complete";
    }

    /**
     * Formats the given Budget object into a string representation.
     *
     * @param budget the Budget object to format
     * @return "Economic" if the budget is Budget.ECONOMIC, otherwise "Customized"
     */
    private String formatBudget(Budget budget) {
        return (budget == Budget.ECONOMIC) ? "Economic" : "Customized";
    }

    /**
     * Returns the appropriate icon resource ID based on the provided option title and description.
     *
     * @param title       The title of the option (e.g., "Skin type", "Time of day", "Routine type", "Budget").
     * @param description The description of the option, which further specifies the choice within the title category.
     * @return The resource ID of the drawable icon corresponding to the given title and description.
     *         If the title or description does not match any predefined cases, a default icon is returned.
     */
    private int getIconForOption(String title, String description) {
        switch (title) {
            case "Skin type":
                if (description.equals("Dry skin")) return R.drawable.ic_piel_seca;
                if (description.equals("Normal skin")) return R.drawable.ic_piel_normal;
                if (description.equals("Combination skin")) return R.drawable.ic_piel_mixta;
                if(description.equals("Sensitive skin")) return R.drawable.ic_piel_sensible;
                return R.drawable.ic_piel_normal; // Icono por defecto en caso de error

            case "Time of day":
                return description.equals("Night") ? R.drawable.ic_rutina_noche : R.drawable.ic_dia_y_noche;

            case "Routine type":
                return description.equals("Basic") ? R.drawable.ic_routine_basic_complete : R.drawable.ic_routine_basic_complete;

            case "Budget":
                return description.equals("Economic") ? R.drawable.ic_economic : R.drawable.ic_budget_rich;
            default:
                return R.drawable.ic_settings; // Icono genérico en caso de error
        }
    }

    /**
     * Called when the activity will start interacting with the user.
     * In this implementation, it calls the updateCustomOptions() method to refresh custom options.
     */
    @Override
    protected void onResume() {
        super.onResume();
        updateCustomOptions();
    }

    /**
     * Updates the custom options displayed in the UI by formatting and setting the descriptions
     * for skin type, schedule, routine type, and budget. It clears the current views in the 
     * options container and adds the updated views with corresponding icons.
     */
    private void updateCustomOptions() {
        String skinTypeDesc = formatSkinType(routine.getSkinType());
        String scheduleDesc = formatSchedule(routine.getSchedule());
        String routineTypeDesc = formatRoutineType(routine.getRoutineType());
        String budgetDesc = formatBudget(routine.getBudget());

        // Clean the current views in the options container
        optionsContainer.removeAllViews();

        addCustomOptionView("Skin type:", skinTypeDesc, getIconForOption("Skin type", skinTypeDesc));
        addCustomOptionView("Time of day:", scheduleDesc, getIconForOption("Time of day", scheduleDesc));
        addCustomOptionView("Routine type:", routineTypeDesc, getIconForOption("Routine type", routineTypeDesc));
        addCustomOptionView("Budget:", budgetDesc, getIconForOption("Budget", budgetDesc));
    }
}