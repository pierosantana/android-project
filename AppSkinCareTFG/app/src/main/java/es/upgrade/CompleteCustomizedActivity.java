package es.upgrade;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import es.upgrade.dao.ProductDao;
import es.upgrade.dao.api.ProductAdapter;
import es.upgrade.dao.api.RetrofitClient;
import es.upgrade.entidad.CategoryProduct;
import es.upgrade.entidad.Product;
import es.upgrade.entidad.Routine;
import es.upgrade.entidad.SkinType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompleteCustomizedActivity extends AppCompatActivity {
    private ProductAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_complete_customized);


        //Configurar RecyclerView
        //recyclerViewProductos.setLayoutManager(new LinearLayoutManager(this));

        obtenerProductosDesdeApi();

        //btnContinuar.setOnClickListener(v -> cargarProductos());


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
                    Toast.makeText(CompleteCustomizedActivity.this, "Error al obtener productos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable throwable) {
                Toast.makeText(CompleteCustomizedActivity.this, "Fallo en la conexión", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void cargarProductos() {
        List<Product> productos = ProductDao.getInstance().getProductos();

        if (productos == null || productos.isEmpty()) {
            Toast.makeText(this, "⚠ No hay productos en ProductDao", Toast.LENGTH_LONG).show();
            return; // Salimos del método para evitar errores
        } else {
            Toast.makeText(this, "✅ Productos cargados correctamente: " + productos.size(), Toast.LENGTH_SHORT).show();
        }

        // Filtrar los productos por categoría y precio
        List<Product> productosFiltrados = obtenerTodosLosProductos();

        if (!productosFiltrados.isEmpty()) {
            // Si hay productos filtrados, los pasamos al adapter
            //adapter = new ProductAdapter(this, productosFiltrados);
            //recyclerViewProductos.setAdapter(adapter);

        } else {
            // Si no hay productos filtrados, mostramos un mensaje
            Toast.makeText(this, "No hay productos disponibles", Toast.LENGTH_SHORT).show();
        }
    }


    public List<Product> obtenerTodosLosProductos() {
        List<Product>productosGuardados = ProductDao.getInstance().getProductos();
        SkinType tipoPiel = Routine.getInstance().getSkinType();

        return productosGuardados.stream().filter(product -> (product.getSkinType() ==tipoPiel)).collect(Collectors.toList());
    }
}