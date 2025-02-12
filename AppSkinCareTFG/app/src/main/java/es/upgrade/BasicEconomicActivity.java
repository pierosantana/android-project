package es.upgrade;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.util.Log;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import es.upgrade.dao.api.RetrofitClient;
import es.upgrade.dao.ProductDao;
import es.upgrade.dao.api.ProductAdapter;
import es.upgrade.entidad.CategoryProduct;
import es.upgrade.entidad.Product;
import es.upgrade.entidad.Routine;
import es.upgrade.entidad.SkinType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class BasicEconomicActivity extends AppCompatActivity {
    private RadioGroup radioGroup1, radioGroup2;
    private Button btnContinuar;
    private RecyclerView recyclerViewLimpieza, recyclerViewHidratacion;
    private ProductAdapter adapterLimpieza, adapterHidratacion;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_basic_economic);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.basic_economic), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnContinuar = findViewById(R.id.btnContinuar);
        recyclerViewLimpieza = findViewById(R.id.rvLimpieza);
        recyclerViewHidratacion = findViewById(R.id.rvHidratacion);

        // Configurar RecyclerView
        recyclerViewLimpieza.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false)); //false se pone para no invertir el orden
        recyclerViewHidratacion.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        recyclerViewLimpieza.setNestedScrollingEnabled(false);
        recyclerViewHidratacion.setNestedScrollingEnabled(false);
        //Efecto carrusel
        LinearSnapHelper snapLimpieza = new LinearSnapHelper();
        snapLimpieza.attachToRecyclerView(recyclerViewLimpieza);
        LinearSnapHelper snapHidratacion = new LinearSnapHelper();
        snapHidratacion.attachToRecyclerView(recyclerViewHidratacion);



        // Llamada al método para obtener productos desde la API al cargar la actividad
        obtenerProductosDesdeApi();

        btnContinuar.setOnClickListener(v -> cargarProductos());
    }

    private void obtenerProductosDesdeApi() {
        // Llamamos a Retrofit para obtener productos desde la API
        Call<List<Product>> call = RetrofitClient.getApiService().getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> productosApi = response.body();
                    ProductDao.getInstance().setProductos(productosApi); // Guardamos los productos en ProductDao
                    cargarProductos();
                } else {
                    Toast.makeText(BasicEconomicActivity.this, "Error al obtener productos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(BasicEconomicActivity.this, "Fallo en la conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarProductos() {
        List<Product> productos = ProductDao.getInstance().getProductos();

        if (productos == null || productos.isEmpty()) {
            findViewById(R.id.emptyView).setVisibility(View.VISIBLE);
            recyclerViewLimpieza.setVisibility(View.GONE);
            recyclerViewHidratacion.setVisibility(View.GONE);
            return;
        }

        // Filtrar productos por categoría
        List<Product> productosLimpieza = productos.stream()
                .filter(product -> product.getCategoryProduct() == CategoryProduct.CLEANER)
                .collect(Collectors.toList());

        List<Product> productosHidratacion = productos.stream()
                .filter(product -> product.getCategoryProduct() == CategoryProduct.MOISTURIZER)
                .collect(Collectors.toList());

        // Verificar y mostrar u ocultar la vista vacía según los productos disponibles
        if (productosLimpieza.isEmpty() && productosHidratacion.isEmpty()) {
            findViewById(R.id.emptyView).setVisibility(View.VISIBLE);
            recyclerViewLimpieza.setVisibility(View.GONE);
            recyclerViewHidratacion.setVisibility(View.GONE);
        } else {
            findViewById(R.id.emptyView).setVisibility(View.GONE);
            recyclerViewLimpieza.setVisibility(View.VISIBLE);
            recyclerViewHidratacion.setVisibility(View.VISIBLE);

            // Configurar adaptadores solo si no están ya configurados o si los datos han cambiado
            if (adapterLimpieza == null) {
                adapterLimpieza = new ProductAdapter(this, productosLimpieza);
                recyclerViewLimpieza.setAdapter(adapterLimpieza);
            } else {
                adapterLimpieza.updateData(productosLimpieza);
            }

            if (adapterHidratacion == null) {
                adapterHidratacion = new ProductAdapter(this, productosHidratacion);
                recyclerViewHidratacion.setAdapter(adapterHidratacion);
            } else {
                adapterHidratacion.updateData(productosHidratacion);
            }
        }
    }

    public List<Product> obtenerProductosFiltradosPorCategoriaYPrecioBajo() {
        List<Product> productosGuardados = ProductDao.getInstance().getProductos();
        // Establecemos el precio máximo bajo
        double precioMaximo = 7.5;
        SkinType tipoPiel = Routine.getInstance().getSkinType();

        // Filtramos los productos por categoría (Limpieza e Hidratación), precio bajo y tipo de piel
        return productosGuardados.stream().filter(product -> (product.getCategoryProduct() == CategoryProduct.CLEANER
                        || product.getCategoryProduct() == CategoryProduct.MOISTURIZER)
                        && product.getPrice() < precioMaximo
                        && product.getSkinType() == tipoPiel)
                .collect(Collectors.toList());
    }

}