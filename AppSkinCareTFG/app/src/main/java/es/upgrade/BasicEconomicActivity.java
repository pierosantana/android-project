package es.upgrade;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.stream.Collectors;

import es.upgrade.dao.UserDao;
import es.upgrade.dao.api.ProductAdapter;
import es.upgrade.dao.ProductDao;
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

public class BasicEconomicActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_basic_economic);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.basic_economic), (v, insets) -> {
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

        //Carrusel
        LinearSnapHelper snapLimpieza = new LinearSnapHelper();
        snapLimpieza.attachToRecyclerView(recyclerViewLimpieza);
        LinearSnapHelper snapHidratacion = new LinearSnapHelper();
        snapHidratacion.attachToRecyclerView(recyclerViewHidratacion);

        obtenerProductosDesdeApi();

        btnContinuar.setOnClickListener(v -> {
            if (selectedLimpiezaProduct == null || selectedHidratacionProduct == null) {
                Toast.makeText(this, "You must select a product from each category", Toast.LENGTH_SHORT).show();
            } else {

                routine.addProduct(selectedLimpiezaProduct);
                routine.addProduct(selectedHidratacionProduct);

                
                String skinType = getIntent().getStringExtra("skinType");
                String schedule = getIntent().getStringExtra("schedule");
                String routineType = getIntent().getStringExtra("routineType");
                String budget = getIntent().getStringExtra("budget");

                routine.setSkinType(SkinType.valueOf(skinType));
                routine.setSchedule(Schedule.valueOf(schedule));
                routine.setRoutineType(RoutineType.valueOf(routineType));
                routine.setBudget(Budget.valueOf(budget));
                
                Intent intent = new Intent(BasicEconomicActivity.this, ResumenFinal.class);
                intent.putExtra("routine", routine);
                intent.putExtra("selectedLimpieza", selectedLimpiezaProduct);
                intent.putExtra("selectedHidratacion", selectedHidratacionProduct);

                // Pasar también los valores de skinType, schedule, routineType y budget
                intent.putExtra("skinType", skinType);
                intent.putExtra("schedule", schedule);
                intent.putExtra("routineType", routineType);
                intent.putExtra("budget", budget);

                startActivity(intent);
                Toast.makeText(this, "Cleansing Product: " + selectedLimpiezaProduct.getName() + "\nMoisturizing Product: " + selectedHidratacionProduct.getName(), Toast.LENGTH_SHORT).show();

            }

        });
    }
     /**
    * Fetches the products from the API using Retrofit and updates the ProductDao.
    * If the API call is successful, it stores the products in the ProductDao and calls cargarProductos() to update the UI.
    * If the API call fails, it shows a Toast message indicating the error.
    */
    private void obtenerProductosDesdeApi() {
        Call<List<Product>> call = RetrofitClient.getApiService().getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProductDao.getInstance().setProductos(response.body());
                    cargarProductos();
                } else {
                    Toast.makeText(BasicEconomicActivity.this, "Error retrieving products", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(BasicEconomicActivity.this, "Connection failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Loads the products from the database and updates the UI accordingly.
     * 
     * This method retrieves the list of products from the ProductDao. If the list is empty or null,
     * it displays an empty view and hides the RecyclerViews for cleaning and moisturizing products.
     * Otherwise, it categorizes the products into cleaning and moisturizing categories, sets up the
     * adapters for the respective RecyclerViews, and updates their visibility based on the presence
     * of products in each category.
     */
    private void cargarProductos() {
        List<Product> products = ProductDao.getInstance().getProductos();

        if (products == null || products.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            recyclerViewLimpieza.setVisibility(View.GONE);
            recyclerViewHidratacion.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);

            List<Product> productosLimpieza = obtenerProductosPorCategoria(CategoryProduct.CLEANER,routine);
            List<Product> productosHidratacion = obtenerProductosPorCategoria(CategoryProduct.MOISTURIZER,routine);

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
        }
    }

    /**
     * Obtains a list of products filtered by category, routine's skin type, and a maximum price.
     *
     * @param category the category of the products to filter
     * @param routine the routine containing the skin type to filter
     * @return a list of products that match the specified category, skin type, and are below the maximum price
     */
    private List<Product> obtenerProductosPorCategoria(CategoryProduct category, Routine routine) {
        List<Product> productos = ProductDao.getInstance().getProductos();
        
        double precioMaximo = 7.0;

        
        SkinType tipoPiel = routine.getSkinType();

        return productos.stream()
                .filter(product -> product.getPrice() < precioMaximo // Filtro por precio
                        && product.getSkinType() == tipoPiel // Filtro por tipo de piel
                        && product.getCategoryProduct() == category) // Filtro por categoría
                .collect(Collectors.toList());

    }
}
