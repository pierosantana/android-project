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

        
        routine = (Routine) getIntent().getSerializableExtra("routine");

        //View configuration
        recyclerViewLimpieza.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewHidratacion.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewLimpieza.setNestedScrollingEnabled(false);
        recyclerViewHidratacion.setNestedScrollingEnabled(false);

        // Carrusel
        LinearSnapHelper snapLimpieza = new LinearSnapHelper();
        snapLimpieza.attachToRecyclerView(recyclerViewLimpieza);
        LinearSnapHelper snapHidratacion = new LinearSnapHelper();
        snapHidratacion.attachToRecyclerView(recyclerViewHidratacion);

        obtenerProductDesdeApi();

        btnContinuar.setOnClickListener(v -> {
            if (selectedLimpiezaProduct == null || selectedHidratacionProduct == null) {
                Toast.makeText(this, "Debes seleccionar un producto de cada categoría", Toast.LENGTH_SHORT).show();
            } else {
                // Add the selected products to the routine
                routine.addProduct(selectedLimpiezaProduct);
                routine.addProduct(selectedHidratacionProduct);

                // Recive the values of skinType, schedule, routineType and budget
                String skinType = getIntent().getStringExtra("skinType");
                String schedule = getIntent().getStringExtra("schedule");
                String routineType = getIntent().getStringExtra("routineType");
                String budget = getIntent().getStringExtra("budget");

                routine.setSkinType(SkinType.valueOf(skinType));
                routine.setSchedule(Schedule.valueOf(schedule));
                routine.setRoutineType(RoutineType.valueOf(routineType));
                routine.setBudget(Budget.valueOf(budget));

                // Create the intent to navigate to the next activity
                Intent intent = new Intent(BasicCustomizedActivity.this, ResumenFinal.class);
                intent.putExtra("routine", routine);
                intent.putExtra("selectedLimpieza", selectedLimpiezaProduct);
                intent.putExtra("selectedHidratacion", selectedHidratacionProduct);

                
                intent.putExtra("skinType", skinType);
                intent.putExtra("schedule", schedule);
                intent.putExtra("routineType", routineType);
                intent.putExtra("budget", budget);

                startActivity(intent);
                Toast.makeText(this, "Producto de Limpieza: " + selectedLimpiezaProduct.getName() + "\nProducto de Hidratación: " + selectedHidratacionProduct.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
    * Fetches the products from the API using Retrofit and updates the ProductDao.
    * If the API call is successful, it stores the products in the ProductDao and calls cargarProductos() to update the UI.
    * If the API call fails, it shows a Toast message indicating the error.
    */
    private void obtenerProductDesdeApi(){
        // Call the API to get the products
        Call<List<Product>> call = RetrofitClient.getApiService().getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<Product> productosApi = response.body();
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
    /**
    * Loads the products from the ProductDao and updates the UI accordingly.
    * If there are no products, it shows an empty view.
    * Otherwise, it filters the products by category and sets the adapters for the RecyclerViews.
    */
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

    /**
    * Obtains a list of products filtered by category and skin type.
    *
    * @param category the category of the products to filter.
    * @param routine the routine containing the skin type to filter by.
    * @return a list of products that match the specified category and skin type.
    */
    private List<Product> obtenerProductosPorCategoria(CategoryProduct category, Routine routine) {
        List<Product> productos = ProductDao.getInstance().getProductos();
        SkinType tipoPiel = routine.getSkinType();
        return productos.stream()
                .filter(product -> product.getSkinType() == tipoPiel // Filter by skin type
                        && product.getCategoryProduct() == category) // Filter by category
                .collect(Collectors.toList());
    }
}
