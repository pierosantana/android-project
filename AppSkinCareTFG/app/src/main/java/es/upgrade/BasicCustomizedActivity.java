package es.upgrade;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.util.List;
import java.util.stream.Collectors;

import es.upgrade.dao.ProductDao;
import es.upgrade.dao.api.ProductAdapter;
import es.upgrade.dao.api.RetrofitClient;
import es.upgrade.entidad.Budget;
import es.upgrade.entidad.CategoryProduct;
import es.upgrade.entidad.Product;
import es.upgrade.entidad.Routine;
import es.upgrade.entidad.RoutineType;
import es.upgrade.entidad.Schedule;
import es.upgrade.entidad.SkinType;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BasicCustomizedActivity extends AppCompatActivity {
    private RecyclerView recyclerViewLimpieza, recyclerViewHidratacion;
    private TextView emptyView;
    private Button btnContinuar;
    private ProductAdapter limpiezaAdapter, hidratacionAdapter;

    private Product selectedLimpiezaProduct;
    private Product selectedHidratacionProduct;

    private Routine routine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_basic_customized);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.basic_customized), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerViewLimpieza = findViewById(R.id.rvLimpieza);
        recyclerViewHidratacion = findViewById(R.id.rvHidratacion);
        emptyView = findViewById(R.id.emptyView);
        btnContinuar = findViewById(R.id.btnContinuar);

        // Recuperar la instancia de Routine pasada desde la actividad anterior
        routine = (Routine) getIntent().getSerializableExtra("routine");

        //Vista del RecyclerView
        recyclerViewLimpieza.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewHidratacion.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewLimpieza.setNestedScrollingEnabled(false);
        recyclerViewHidratacion.setNestedScrollingEnabled(false);

        // Carrusel
        LinearSnapHelper snapLimpieza = new LinearSnapHelper();
        snapLimpieza.attachToRecyclerView(recyclerViewLimpieza);
        LinearSnapHelper snapHidratacion = new LinearSnapHelper();
        snapHidratacion.attachToRecyclerView(recyclerViewHidratacion);

        // Aquí llamamos al método para obtener productos desde la API al cargar la actividad
        obtenerProductDesdeApi();

        btnContinuar.setOnClickListener(v -> {
            if (selectedLimpiezaProduct == null || selectedHidratacionProduct == null) {
                Toast.makeText(this, "Debes seleccionar un producto de cada categoría", Toast.LENGTH_SHORT).show();
            } else {
                // Agregar los productos seleccionados a la rutina
                routine.addProduct(selectedLimpiezaProduct);
                routine.addProduct(selectedHidratacionProduct);

                // Recibir datos de la Intent anterior
                String skinType = getIntent().getStringExtra("skinType");
                String schedule = getIntent().getStringExtra("schedule");
                String routineType = getIntent().getStringExtra("routineType");
                String budget = getIntent().getStringExtra("budget");

                routine.setSkinType(SkinType.valueOf(skinType));
                routine.setSchedule(Schedule.valueOf(schedule));
                routine.setRoutineType(RoutineType.valueOf(routineType));
                routine.setBudget(Budget.valueOf(budget));

                // Crear un Intent para abrir la ResumenFinal Activity
                Intent intent = new Intent(BasicCustomizedActivity.this, ResumenFinal.class);
                // Pasar los productos seleccionados al Intent
                intent.putExtra("selectedLimpieza", selectedLimpiezaProduct);
                intent.putExtra("selectedHidratacion", selectedHidratacionProduct);

                // Pasar también los valores de skinType, schedule, routineType y budget
                intent.putExtra("skinType", skinType);
                intent.putExtra("schedule", schedule);
                intent.putExtra("routineType", routineType);
                intent.putExtra("budget", budget);

                startActivity(intent);
                Toast.makeText(this, "Producto de Limpieza: " + selectedLimpiezaProduct.getName() + "\nProducto de Hidratación: " + selectedHidratacionProduct.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void obtenerProductDesdeApi(){
        // Llamamos al Retrofit para obtener los productos desde la API
        Call<List<Product>> call = RetrofitClient.getApiService().getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<Product> productosApi = response.body();
                    // Aquí guardamos los productos de la API dentro del productDao,
                    // el cual al ser singleton podemos acceder a él desde cualquier punto
                    ProductDao.getInstance().setProductos(productosApi);
                    cargarProductos();
                } else {
                    Toast.makeText(BasicCustomizedActivity.this, "Error retrieving products", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable throwable) {
                Toast.makeText(BasicCustomizedActivity.this, "Connection failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarProductos() {
        List<Product> products = ProductDao.getInstance().getProductos();

        if (products == null || products.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            recyclerViewLimpieza.setVisibility(View.GONE);
            recyclerViewHidratacion.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);

            List<Product> productosLimpieza = obtenerProductosPorCategoria(CategoryProduct.CLEANER, routine);
            List<Product> productosHidratacion = obtenerProductosPorCategoria(CategoryProduct.MOISTURIZER, routine);

            if (!productosLimpieza.isEmpty()) {
                limpiezaAdapter = new ProductAdapter(this, productosLimpieza, product -> selectedLimpiezaProduct = product, routine);
                recyclerViewLimpieza.setAdapter(limpiezaAdapter);
                recyclerViewLimpieza.setVisibility(View.VISIBLE);
            } else {
                recyclerViewLimpieza.setVisibility(View.GONE);
            }

            if (!productosHidratacion.isEmpty()) {
                hidratacionAdapter = new ProductAdapter(this, productosHidratacion, product -> selectedHidratacionProduct = product, routine);
                recyclerViewHidratacion.setAdapter(hidratacionAdapter);
                recyclerViewHidratacion.setVisibility(View.VISIBLE);
            } else {
                recyclerViewHidratacion.setVisibility(View.GONE);
            }
        }
    }

    private List<Product> obtenerProductosPorCategoria(CategoryProduct category, Routine routine) {
        List<Product> productos = ProductDao.getInstance().getProductos();
        SkinType tipoPiel = routine.getSkinType();
        return productos.stream()
                .filter(product -> product.getSkinType() == tipoPiel // Filtro por tipo de piel
                        && product.getCategoryProduct() == category) // Filtro por categoría
                .collect(Collectors.toList());
    }
}
