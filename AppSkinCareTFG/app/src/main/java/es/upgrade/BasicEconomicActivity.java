package es.upgrade;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
    private RecyclerView recyclerViewProductos;
    private ProductAdapter adapter;

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

        radioGroup1 = findViewById(R.id.radioGroup1);
        radioGroup2 = findViewById(R.id.radioGroup2);
        btnContinuar = findViewById(R.id.button);
        recyclerViewProductos = findViewById(R.id.recyclerViewProductos);

        // Listener para el primer grupo de opciones de nuestro radio group.
        radioGroup1.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedRadioButton = findViewById(checkedId);
            if (selectedRadioButton != null) {
                Toast.makeText(this, "Seleccionaste: " + selectedRadioButton.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        // Listener para el segundo grupo de opciones de nuestro radio grupo
        radioGroup2.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedRadioButton = findViewById(checkedId);
            if (selectedRadioButton != null) {
                Toast.makeText(this, "Seleccionaste: " + selectedRadioButton.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        // Configurar RecyclerView
        recyclerViewProductos.setLayoutManager(new LinearLayoutManager(this));

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
        // Obtener los productos guardados en el ProductDao
        List<Product> productos = ProductDao.getInstance().getProductos();

        if (productos == null || productos.isEmpty()) {
            Toast.makeText(this, "⚠ No hay productos en ProductDao", Toast.LENGTH_LONG).show();
            return; // Salimos del método para evitar errores
        } else {
            Toast.makeText(this, "✅ Productos cargados correctamente: " + productos.size(), Toast.LENGTH_SHORT).show();
        }

        // Filtrar los productos por categoría y precio
        List<Product> productosFiltrados = obtenerProductosFiltradosPorCategoriaYPrecioBajo();

        if (!productosFiltrados.isEmpty()) {
            // Si hay productos filtrados, los pasamos al adapter
            adapter = new ProductAdapter(this, productosFiltrados);
            recyclerViewProductos.setAdapter(adapter);

        } else {
            // Si no hay productos filtrados, mostramos un mensaje
            Toast.makeText(this, "No hay productos disponibles", Toast.LENGTH_SHORT).show();
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
