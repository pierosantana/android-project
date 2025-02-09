package es.upgrade.dao.api;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.upgrade.R;
import es.upgrade.entidad.Product;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EjemploParaObtenerProductosAPI extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_de_api);

        // Configuramos el RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        obtenerProductos();
    }

    private void obtenerProductos() {
        // Realizamos la llamada a la API utilizando Retrofit
        RetrofitClient.getInstance().getProductos().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    productos = response.body();
                    if (productos != null) {
                        // Mostramos los productos en Logcat
                        for (Product producto : productos) {
                            Log.d("API", "Producto: " + producto.getName() + " - Precio: " + producto.getPrice());
                        }

                        // Creamos el adapter con la lista de productos y lo asignamos al RecyclerView
                        productAdapter = new ProductAdapter(productos);
                        recyclerView.setAdapter(productAdapter);

                        // Mostramos un Toast si los productos fueron obtenidos
                        Toast.makeText(EjemploParaObtenerProductosAPI.this, "Productos obtenidos correctamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EjemploParaObtenerProductosAPI.this, "No se encontraron productos", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Si la respuesta no es exitosa
                    Log.e("API", "Error en la respuesta: " + response.code());
                    Toast.makeText(EjemploParaObtenerProductosAPI.this, "Error en la respuesta: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("API", "Error de conexión: " + t.getMessage());
                Toast.makeText(EjemploParaObtenerProductosAPI.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
