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
                Log.e("ReviewAndEditActivity", "La rutina es null");
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
                // Pasar los datos al siguiente Activity
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

    // Función para agregar una vista personalizada al contenedor
    private void addCustomOptionView(String title, String description, int iconResId) {
        // Crear el CustomViewOptionsRoutine
        CustomViewOptionsRoutine customView = new CustomViewOptionsRoutine(this, null);

        // Establecer el texto y el ícono según la elección del usuario
        customView.setOptionContent(title, iconResId);

        // Aquí, description será la respuesta que el usuario ha elegido
        customView.setAnswerText(description);  // Establecer la respuesta seleccionada

        // Agregar la vista al contenedor
        optionsContainer.addView(customView);
    }

    // Métodos de formateo de las respuestas
    private String formatSkinType(SkinType skinType) {
        if (skinType == null) {
            return "Tipo de piel no definido";  // O cualquier texto por defecto
        }
        switch (skinType) {
            case DRY: return "Dry skin";
            case NORMAL: return "Normal skin";
            case COMBINATION: return "Combination skin";
            default: return "Unknown skin type";
        }
    }

    private String formatSchedule(Schedule schedule) {
        return (schedule == Schedule.NIGHT) ? "Night" : "Complete";
    }

    private String formatRoutineType(RoutineType routineType) {
        return (routineType == RoutineType.BASIC) ? "Basic" : "Complete";
    }

    private String formatBudget(Budget budget) {
        return (budget == Budget.ECONOMIC) ? "Economic" : "Customized";
    }

    private int getIconForOption(String title, String description) {
        switch (title) {
            case "Skin type":
                if (description.equals("Dry skin")) return R.drawable.ic_piel_seca;
                if (description.equals("Normal skin")) return R.drawable.ic_piel_normal;
                if (description.equals("Combination skin")) return R.drawable.ic_piel_mixta;
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

    @Override
    protected void onResume() {
        super.onResume();
        updateCustomOptions();
    }

    private void updateCustomOptions() {
        String skinTypeDesc = formatSkinType(routine.getSkinType());
        String scheduleDesc = formatSchedule(routine.getSchedule());
        String routineTypeDesc = formatRoutineType(routine.getRoutineType());
        String budgetDesc = formatBudget(routine.getBudget());

        // Limpia el contenedor y vuelve a agregar las vistas actualizadas
        optionsContainer.removeAllViews();

        addCustomOptionView("Skin type:", skinTypeDesc, getIconForOption("Skin type", skinTypeDesc));
        addCustomOptionView("Time of day:", scheduleDesc, getIconForOption("Time of day", scheduleDesc));
        addCustomOptionView("Routine type:", routineTypeDesc, getIconForOption("Routine type", routineTypeDesc));
        addCustomOptionView("Budget:", budgetDesc, getIconForOption("Budget", budgetDesc));
    }
}