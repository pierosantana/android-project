package es.upgrade;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import es.upgrade.dao.ProductDao;
import es.upgrade.dao.UserDao;
import es.upgrade.dao.api.ProductAdapter;
import es.upgrade.dao.api.RetrofitClient;
import es.upgrade.entidad.Budget;
import es.upgrade.entidad.CategoryProduct;
import es.upgrade.entidad.Product;
import es.upgrade.entidad.Routine;
import es.upgrade.entidad.RoutineType;
import es.upgrade.entidad.Schedule;
import es.upgrade.entidad.SkinType;
import es.upgrade.entidad.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompleteCustomizedActivity extends AppCompatActivity {

    private RecyclerView recyclerViewLimpieza, recyclerViewHidratacion,recyclerViewTonificacion,
            recyclerViewTratamiento,recyclerViewProtectorSolar;
    private TextView emptyView,txtProtector;
    private Button btnContinuar;
    private ProductAdapter limpiezaAdapter, hidratacionAdapter,tonificacionAdapter,
                            tratamientoAdapter,protectorAdapter;

    private Product selectedLimpiezaProduct,selectedHidratacionProduct,selectedTonificacionProduct,
            selectedTratamientoProduct,selectedProtectorProduct;
    private Routine routine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_complete_customized);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.complete_customized), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        routine = (Routine) getIntent().getSerializableExtra("routine");

        recyclerViewLimpieza = findViewById(R.id.rvLimpieza);
        recyclerViewHidratacion = findViewById(R.id.rvHidratacion);
        recyclerViewTonificacion = findViewById(R.id.rvTonificacion);
        recyclerViewTratamiento = findViewById(R.id.rvTratamiento);
        recyclerViewProtectorSolar = findViewById(R.id.rvProtectorSolar);

        //Vista del reciclerView
        recyclerViewLimpieza.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewHidratacion.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTonificacion.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTratamiento.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewProtectorSolar.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recyclerViewLimpieza.setNestedScrollingEnabled(false);
        recyclerViewHidratacion.setNestedScrollingEnabled(false);
        recyclerViewTonificacion.setNestedScrollingEnabled(false);
        recyclerViewTratamiento.setNestedScrollingEnabled(false);
        recyclerViewProtectorSolar.setNestedScrollingEnabled(false);

        emptyView = findViewById(R.id.emptyView);
        txtProtector = findViewById(R.id.sectionTitle5);
        btnContinuar = findViewById(R.id.btnContinuar);

        //Carrusel
        LinearSnapHelper snapLimpieza = new LinearSnapHelper();
        snapLimpieza.attachToRecyclerView(recyclerViewLimpieza);
        LinearSnapHelper snapHidratacion = new LinearSnapHelper();
        snapHidratacion.attachToRecyclerView(recyclerViewHidratacion);
        LinearSnapHelper snapTonico = new LinearSnapHelper();
        snapTonico.attachToRecyclerView(recyclerViewTonificacion);
        LinearSnapHelper snapTratamiento = new LinearSnapHelper();
        snapTratamiento.attachToRecyclerView(recyclerViewTratamiento);
        LinearSnapHelper snapProtector = new LinearSnapHelper();
        snapProtector.attachToRecyclerView(recyclerViewProtectorSolar);

        obtenerProductosDesdeApi();

        btnContinuar.setOnClickListener(v -> {
            if (selectedLimpiezaProduct == null || selectedHidratacionProduct == null || selectedTonificacionProduct == null
                    || selectedTratamientoProduct == null && selectedProtectorProduct == null) {
                Toast.makeText(this, "You must select a product from each category.", Toast.LENGTH_SHORT).show();
            } else {

                routine.addProduct(selectedLimpiezaProduct);
                routine.addProduct(selectedHidratacionProduct);
                routine.addProduct(selectedTonificacionProduct);
                routine.addProduct(selectedTratamientoProduct);
                routine.addProduct(selectedProtectorProduct);

                // Recibir datos de la Intent anterior
                String skinType = getIntent().getStringExtra("skinType");
                String schedule = getIntent().getStringExtra("schedule");
                String routineType = getIntent().getStringExtra("routineType");
                String budget = getIntent().getStringExtra("budget");

                routine.setSkinType(SkinType.valueOf(skinType));
                routine.setSchedule(Schedule.valueOf(schedule));
                routine.setRoutineType(RoutineType.valueOf(routineType));
                routine.setBudget(Budget.valueOf(budget));

                // Crear Intent para pasar los datos a ResumenFinal
                Intent intent = new Intent(this, ResumenFinal.class);
                intent.putExtra("selectedLimpieza", selectedLimpiezaProduct);
                intent.putExtra("selectedHidratacion", selectedHidratacionProduct);
                intent.putExtra("selectedTonificacion", selectedTonificacionProduct);
                intent.putExtra("selectedTratamiento", selectedTratamientoProduct);
                intent.putExtra("selectedProtector", selectedProtectorProduct);

                // Pasar también los valores de skinType, schedule, routineType y budget
                intent.putExtra("skinType", skinType);
                intent.putExtra("schedule", schedule);
                intent.putExtra("routineType", routineType);
                intent.putExtra("budget", budget);

                // Iniciar ResumenFinal
                startActivity(intent);

            }
        });

    }
    private void obtenerProductosDesdeApi(){
        Call<List<Product>> call = RetrofitClient.getApiService().getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> productosApi = response.body();
                    ProductDao.getInstance().setProductos(productosApi); // Guardamos los productos en ProductDao
                    cargarProductos();
                } else {
                    Toast.makeText(CompleteCustomizedActivity.this, "Error retrieving products", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable throwable) {
                Toast.makeText(CompleteCustomizedActivity.this, "Connection failure", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void cargarProductos() {
        List<Product> products = ProductDao.getInstance().getProductos();

        if (products == null || products.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            recyclerViewLimpieza.setVisibility(View.GONE);
            recyclerViewHidratacion.setVisibility(View.GONE);
            recyclerViewTonificacion.setVisibility(View.GONE);
            recyclerViewTratamiento.setVisibility(View.GONE);
            recyclerViewProtectorSolar.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);

            List<Product> productosLimpieza = obtenerTodosLosProductos(CategoryProduct.CLEANER,routine);
            List<Product> productosHidratacion = obtenerTodosLosProductos(CategoryProduct.MOISTURIZER,routine);
            List<Product> productosTonificacion = obtenerTodosLosProductos(CategoryProduct.TONIC,routine);
            List<Product> productosTratamiento = obtenerTodosLosProductos(CategoryProduct.CREAM_TREATMENT,routine);
            List<Product> productosProtector = obtenerTodosLosProductos(CategoryProduct.SUNSCREEN,routine);

            // Verificamos el tipo de rutina (si es noche completa, no mostramos el protector solar)
            boolean esRutinaDeNoche= routine.getSchedule() == Schedule.NIGHT;

            if (!productosLimpieza.isEmpty()) {
                limpiezaAdapter = new ProductAdapter( productosLimpieza,this,false, product -> selectedLimpiezaProduct = product,routine);
                recyclerViewLimpieza.setAdapter(limpiezaAdapter);
                recyclerViewLimpieza.setVisibility(View.VISIBLE);
            } else {
                recyclerViewLimpieza.setVisibility(View.GONE);
            }

            if (!productosHidratacion.isEmpty()) {
                hidratacionAdapter = new ProductAdapter(productosHidratacion,this,false, product -> selectedHidratacionProduct = product,routine);
                recyclerViewHidratacion.setAdapter(hidratacionAdapter);
                recyclerViewHidratacion.setVisibility(View.VISIBLE);
            } else {
                recyclerViewHidratacion.setVisibility(View.GONE);
            }
            if (!productosTonificacion.isEmpty()) {
                tonificacionAdapter = new ProductAdapter(productosTonificacion,this,false, product -> selectedTonificacionProduct = product,routine);
                recyclerViewTonificacion.setAdapter(tonificacionAdapter);
                recyclerViewTonificacion.setVisibility(View.VISIBLE);
            } else {
                recyclerViewTonificacion.setVisibility(View.GONE);
            }
            if (!productosTratamiento.isEmpty()) {
                tratamientoAdapter = new ProductAdapter(productosTratamiento,this,false, product -> selectedTratamientoProduct = product,routine);
                recyclerViewTratamiento.setAdapter(tratamientoAdapter);
                recyclerViewTratamiento.setVisibility(View.VISIBLE);
            } else {
                recyclerViewTratamiento.setVisibility(View.GONE);
            }
            if (!productosProtector.isEmpty() && !esRutinaDeNoche) {
                protectorAdapter = new ProductAdapter(productosProtector,this,false, product -> selectedProtectorProduct = product,routine);
                recyclerViewProtectorSolar.setAdapter(protectorAdapter);
                recyclerViewProtectorSolar.setVisibility(View.VISIBLE);
                txtProtector.setVisibility(View.VISIBLE);
            } else {
                recyclerViewProtectorSolar.setVisibility(View.GONE);
                txtProtector.setVisibility(View.GONE);
            }
        }


    }

    private List<Product> obtenerTodosLosProductos(CategoryProduct category,Routine routine) {
        List<Product> productos = ProductDao.getInstance().getProductos();
        SkinType tipoPiel = routine.getSkinType();
        return productos.stream()
                .filter(product ->product.getSkinType() == tipoPiel // Filtro por tipo de piel
                        && product.getCategoryProduct() == category) // Filtro por categoría
                .collect(Collectors.toList());
    }
}