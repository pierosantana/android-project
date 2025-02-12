package es.upgrade;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import es.upgrade.dao.ProductDao;
import es.upgrade.dao.api.ProductAdapter;
import es.upgrade.dao.api.RetrofitClient;
import es.upgrade.entidad.CategoryProduct;
import es.upgrade.entidad.Product;
import es.upgrade.entidad.Routine;
import es.upgrade.entidad.Schedule;
import es.upgrade.entidad.SkinType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompleteEconomicActivity extends AppCompatActivity {

    private ProductAdapter adapter;
    private Button btnContinuar;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_complete_economic);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.complete_economic), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Configurar RecyclerView
        //recyclerViewProductos.setLayoutManager(new LinearLayoutManager(this));

        // Llamada al método para obtener productos desde la API al cargar la actividad
        obtenerProductosDesdeApi();


        btnContinuar.setOnClickListener(v->cargarProductos());


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
                    Toast.makeText(CompleteEconomicActivity.this, "Error al obtener productos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(CompleteEconomicActivity.this, "Fallo en la conexión", Toast.LENGTH_SHORT).show();
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
        List<Product> productosFiltrados = obtenerProductosFilradosPorCategoriaYPrecioBajo();

        if (!productosFiltrados.isEmpty()) {
            // Si hay productos filtrados, los pasamos al adapter
            //adapter = new ProductAdapter(this, productosFiltrados);
            //recyclerViewProductos.setAdapter(adapter);

        } else {
            // Si no hay productos filtrados, mostramos un mensaje
            Toast.makeText(this, "No hay productos disponibles", Toast.LENGTH_SHORT).show();
        }
    }

    public List<Product> obtenerProductosFilradosPorCategoriaYPrecioBajo(){
        List<Product>productosGuardados = ProductDao.getInstance().getProductos();
        // Establecemos el precio maximo bajo
        double precioMaximo = 15.0;
        SkinType tipoPiel = Routine.getInstance().getSkinType();

        // Filtramos por categoría(Limpieza e Hidratacion) y precio bajo
        // Luego mas tarde habra que filtrar el producto por el tipo de piel

        return productosGuardados.stream().filter
                (product -> product.getPrice()<precioMaximo && product.getSkinType()==tipoPiel).collect(Collectors.toList());
    }
}