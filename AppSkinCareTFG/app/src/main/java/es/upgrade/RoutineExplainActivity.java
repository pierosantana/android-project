package es.upgrade;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.upgrade.dao.StepAdapter;
import es.upgrade.dao.api.RetrofitClient;
import es.upgrade.dao.api.StepDao;
import es.upgrade.entidad.Product;
import es.upgrade.entidad.Routine;
import es.upgrade.entidad.Step;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoutineExplainActivity extends AppCompatActivity {

    private TextView tvRoutineTitle, tvRoutineType, tvStepCounter, backToRoutines;
    private RecyclerView recyclerViewSteps;
    private Routine routine;
    private LinearLayout dotsLayout; // Contenedor de los dots
    private List<Step> stepsApi = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_explain);

        // Inicialización de vistas
        backToRoutines = findViewById(R.id.tvBackToRoutines);
        tvRoutineTitle = findViewById(R.id.tvRoutineTitleDetail);
        tvRoutineType = findViewById(R.id.tvRoutineTypeDetail);
        dotsLayout = findViewById(R.id.dotsLayout); // Inicializa el contenedor de dots
        recyclerViewSteps = findViewById(R.id.rvSteps);

        // Obtener la rutina actual desde el Intent
        routine = (Routine) getIntent().getSerializableExtra("routine");

        if (routine != null) {
            // Mostrar información de la rutina
            tvRoutineTitle.setText("Routine: " + routine.getRoutineType());
            tvRoutineType.setText(routine.isNightRoutine() ? "Nocturnal Routine”" : "Complete Routine");
        } else {
            Toast.makeText(this, "Routine information not found", Toast.LENGTH_SHORT).show();
        }

        // Cargar los pasos desde la API
        cargarStepsFromApi();

        // Manejar el evento de regreso a las rutinas
        backToRoutines.setOnClickListener(v -> {
            Intent intent = new Intent(RoutineExplainActivity.this, MyRoutinesActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Carga los pasos de la rutina desde la API y los configura en el RecyclerView.
     * Este metodo realiza una llamada a la API para obtener los pasos de la rutina, limita la cantidad de pasos
     * según el número definido en la rutina y guarda los pasos obtenidos en un objeto de tipo StepDao.
     * Si la llamada a la API es exitosa, los pasos se cargan en el RecyclerView de la actividad,
     * de lo contrario, se muestra un mensaje de error al usuario.
     *
     * @see RetrofitClient
     * @see StepDao
     * @see RoutineExplainActivity
     */
    private void cargarStepsFromApi() {
        Call<List<Step>> call = RetrofitClient.getApiService().getSteps(); // Llamada al endpoint
        call.enqueue(new Callback<List<Step>>() {
            @Override
            public void onResponse(Call<List<Step>> call, Response<List<Step>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Obtener la lista de pasos desde la API
                    stepsApi = response.body();
                    // Limitamos los pasos según el método getStepCount() de la rutina
                    int stepCount = routine.getStepCount();
                    if (stepCount != -1 && stepsApi.size() > stepCount) {
                        stepsApi = stepsApi.subList(0, stepCount);
                    }

                    StepDao.getInstance().setSteps(stepsApi); // Guardamos los pasos en StepDao
                    setupRecyclerView(stepsApi, routine.getProductList());
                } else {
                    Toast.makeText(RoutineExplainActivity.this, "Error retrieving steps", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Step>> call, Throwable throwable) {
                Log.e("API_ERROR", "Fallo en la conexión", throwable);
                Toast.makeText(RoutineExplainActivity.this, "Connection failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRecyclerView(List<Step> stepList, List<Product> selectedProducts) {

        // Configura RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSteps.setLayoutManager(layoutManager);

        // Configurar SnapHelper para deslizar una tarjeta a la vez
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerViewSteps);

        StepAdapter stepAdapter = new StepAdapter(stepList, selectedProducts);
        recyclerViewSteps.setAdapter(stepAdapter);

        // Inicializa los dots
        initializeDots(stepList.size());

        recyclerViewSteps.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int currentPosition = layoutManager.findFirstVisibleItemPosition();
                    updateDots(currentPosition);
                }
            }
        });
    }

    // Método para inicializar los dots
    private void initializeDots(int count) {
        dotsLayout.removeAllViews(); // Limpia cualquier punto previo
        for (int i = 0; i < count; i++) {
            TextView dot = new TextView(this);
            dot.setText("●"); // Símbolo del dot
            dot.setTextSize(16);
            dot.setTextColor(getResources().getColor(android.R.color.darker_gray)); // Color inicial (gris)
            dot.setPadding(8, 0, 8, 0); // Espaciado entre los dots
            dotsLayout.addView(dot);
        }
        // Activa el primer dot
        updateDots(0);
    }

    // Método para actualizar los dots
    private void updateDots(int currentPosition) {
        int count = dotsLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            TextView dot = (TextView) dotsLayout.getChildAt(i);
            if (i == currentPosition) {
                dot.setTextColor(getResources().getColor(android.R.color.holo_blue_dark)); // Color activo
            } else {
                dot.setTextColor(getResources().getColor(android.R.color.darker_gray)); // Color inactivo
            }
        }
    }}
