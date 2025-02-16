package es.upgrade;

import android.content.Intent;
import android.os.Bundle;
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


        User user = User.getInstance();
        Routine routine = Routine.getInstance();


        titulo.setText("Has elegido las siguientes cosas: ¿Deseas modificar algo?");


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
                intent.putExtra("skinType", formatSkinType(routine.getSkinType()));
                intent.putExtra("schedule", formatSchedule(routine.getSchedule()));
                intent.putExtra("routineType", formatRoutineType(routine.getRoutineType()));
                intent.putExtra("budget", formatBudget(routine.getBudget()));

                startActivity(intent);
            }
        });


        edit.setOnClickListener(v -> {
            AlertDialogCustom.showCustomAlertDialog(
                    this,
                    "Confirmación",
                    "¿Está seguro que quiere editar la rutina?",
                    "Sí",
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
            case DRY: return "Piel seca";
            case NORMAL: return "Piel normal";
            case COMBINATION: return "Piel mixta";
            default: return "Tipo de piel desconocido";
        }
    }

    private String formatSchedule(Schedule schedule) {
        return (schedule == Schedule.NIGHT) ? "Noche" : "Mañana y Noche";
    }

    private String formatRoutineType(RoutineType routineType) {
        return (routineType == RoutineType.BASIC) ? "Básica" : "Completa";
    }

    private String formatBudget(Budget budget) {
        return (budget == Budget.ECONOMIC) ? "Económico" : "Personalizado";
    }

    private int getIconForOption(String title, String description) {
        switch (title) {
            case "Tipo de piel":
                if (description.equals("Piel seca")) return R.drawable.ic_piel_seca;
                if (description.equals("Piel normal")) return R.drawable.ic_piel_normal;
                if (description.equals("Piel mixta")) return R.drawable.ic_piel_mixta;
                return R.drawable.ic_piel_normal; // Icono por defecto en caso de error

            case "Momento del día":
                return description.equals("Noche") ? R.drawable.ic_rutina_noche : R.drawable.ic_dia_y_noche;

            case "Tipo de rutina":
                return description.equals("Básica") ? R.drawable.ic_routine_basic_complete : R.drawable.ic_routine_basic_complete;

            case "Presupuesto":
                return description.equals("Económico") ? R.drawable.ic_economic : R.drawable.ic_budget_rich;

            default:
                return R.drawable.ic_settings; // Icono genérico en caso de error
        }
    }

    protected void onResume() {
        super.onResume();
        updateCustomOptions();
    }
    private void updateCustomOptions() {
        Routine routine = Routine.getInstance();

        String skinTypeDesc = formatSkinType(routine.getSkinType());
        String scheduleDesc = formatSchedule(routine.getSchedule());
        String routineTypeDesc = formatRoutineType(routine.getRoutineType());
        String budgetDesc = formatBudget(routine.getBudget());

        // Limpia el contenedor y vuelve a agregar las vistas actualizadas
        optionsContainer.removeAllViews();

        addCustomOptionView("Tipo de piel:", skinTypeDesc, getIconForOption("Tipo de piel", skinTypeDesc));
        addCustomOptionView("Momento del día:", scheduleDesc, getIconForOption("Momento del día", scheduleDesc));
        addCustomOptionView("Tipo de rutina:", routineTypeDesc, getIconForOption("Tipo de rutina", routineTypeDesc));
        addCustomOptionView("Presupuesto:", budgetDesc, getIconForOption("Presupuesto", budgetDesc));
    }
}