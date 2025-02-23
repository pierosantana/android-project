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

    private TextView tvRoutineTitle, tvRoutineType, tvStepCounter;
    private RecyclerView recyclerViewSteps;
    private Routine routine;
    private LinearLayout dotsLayout; // Contenedor de los dots
    private List<Step> stepsApi = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_explain);

        // Initialize views
        tvRoutineTitle = findViewById(R.id.tvRoutineTitleDetail);
        tvRoutineType = findViewById(R.id.tvRoutineTypeDetail);
        dotsLayout = findViewById(R.id.dotsLayout); 
        recyclerViewSteps = findViewById(R.id.rvSteps);

        
        routine = (Routine) getIntent().getSerializableExtra("routine");

        if (routine != null) {
            // Show the routine information
            tvRoutineTitle.setText("Routine: " + routine.getRoutineType());
            tvRoutineType.setText(routine.isNightRoutine() ? "Nocturnal Routine”" : "Complete Routine");
        } else {
            Toast.makeText(this, "Routine information not found", Toast.LENGTH_SHORT).show();
        }

        
        cargarStepsFromApi();


    }

    /**
    * Loads the routine steps from the API and sets them to the RecyclerView.
    * This method makes an API call to get the routine steps, limits the number of steps
    * to the number defined in the routine, and saves the obtained steps to an object of type StepDao.
    * If the API call is successful, the steps are loaded into the activity's RecyclerView,
    * otherwise, an error message is displayed to the user.
    *
    * @see RetrofitClient
    * @see StepDao
    * @see RoutineExplainActivity
    */
    private void cargarStepsFromApi() {
        Call<List<Step>> call = RetrofitClient.getApiService().getSteps(); 
        call.enqueue(new Callback<List<Step>>() {
            @Override
            public void onResponse(Call<List<Step>> call, Response<List<Step>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    
                    stepsApi = response.body();
                    
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

        
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSteps.setLayoutManager(layoutManager);

        //Configure the RecyclerView to snap to the nearest item
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerViewSteps);

        StepAdapter stepAdapter = new StepAdapter(stepList, selectedProducts);
        recyclerViewSteps.setAdapter(stepAdapter);

        
        initializeDots(stepList.size());

        recyclerViewSteps.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /**
             * This method is called when the RecyclerView has been scrolled.
             *
             * @param recyclerView The RecyclerView which scrolled.
             * @param dx The amount of horizontal scroll.
             * @param dy The amount of vertical scroll.
             */
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

    
    /**
     * Initializes the dots indicator for a view pager.
     *
     * This method clears any existing dots and creates a new set of dots based on the provided count.
     * Each dot is represented by a TextView with a dot symbol ("●"), a specified text size, and an initial color.
     * The dots are spaced evenly with padding.
     * The first dot is activated by default.
     *
     * @param count the number of dots to initialize
     */
    private void initializeDots(int count) {
        dotsLayout.removeAllViews(); // Clear any existing dots
        for (int i = 0; i < count; i++) {
            TextView dot = new TextView(this);
            dot.setText("●"); // Dot symbol
            dot.setTextSize(16);
            dot.setTextColor(getResources().getColor(android.R.color.darker_gray)); 
            dot.setPadding(8, 0, 8, 0); // Spacing between dots
            dotsLayout.addView(dot);
        }
        
        updateDots(0);
    }

    
    /**
     * Updates the color of the dots in the dotsLayout to indicate the current position.
     *
     * @param currentPosition The index of the currently active dot.
     */
    private void updateDots(int currentPosition) {
        int count = dotsLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            TextView dot = (TextView) dotsLayout.getChildAt(i);
            if (i == currentPosition) {
                dot.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
            } else {
                dot.setTextColor(getResources().getColor(android.R.color.darker_gray));
            }
        }
    }}
