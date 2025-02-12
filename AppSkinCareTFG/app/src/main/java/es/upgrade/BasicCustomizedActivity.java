package es.upgrade;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

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

public class BasicCustomizedActivity extends AppCompatActivity {
   private ProductAdapter adapter;
   private Button btnContinuar;

    @SuppressLint("MissingInflatedId")
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

        // Configurar RecyclerView
        //recyclerViewProductos.setLayoutManager(new LinearLayoutManager(this));

        // Aqui llamamos al metodo para obtener productos desde la API al cargar la actividad
        obtenerProductDesdeApi();
        btnContinuar.setOnClickListener(v->cargarProductos());
    }
    private void obtenerProductDesdeApi(){
        // Llamamos al Retrofir para obetner los productos dede la API
        Call<List<Product>> call = RetrofitClient.getApiService().getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<Product> productosApi = response.body();
                    // Aqui guardamos los productos de la API dentro del productDao,
                    // el cual al ser singletone podemos acceder a el desde cualquier punto
                    ProductDao.getInstance().setProductos(productosApi);
                    cargarProductos();
                }else{
                    Toast.makeText(BasicCustomizedActivity.this, "Error al obtener productos", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable throwable) {
                Toast.makeText(BasicCustomizedActivity.this, "Fallo en la conexión", Toast.LENGTH_SHORT).show();


            }
        });
    }

    private void cargarProductos() {
        // Obtener los productos guardados en el ProductDao
        List<Product>products = ProductDao.getInstance().getProductos();

        if(products == null || products.isEmpty()){
            Toast.makeText(this, "⚠ No hay productos en ProductDao", Toast.LENGTH_LONG).show();
            return;// Salimos del método para evitar errores
        }else{
            Toast.makeText(this, "✅ Productos cargados correctamente: " + products.size(), Toast.LENGTH_SHORT).show();
        }

        // Filtramos los productos por categoría
        List<Product>productosFiltrados = obtenerProductosFilradosPorCategoria();

        if(!productosFiltrados.isEmpty()){
            // Si hay productos filtrados lo pasamos al adapter??
            /*
                adapter = new ProductAdapter(this, productosFiltrados);
                recyclerViewProductos.setAdapter(adapter);
             */
        }else{
            // Si no hay productos filtrados vamos a mostrar un mensaje
            Toast.makeText(this, "No hay productos disponibles", Toast.LENGTH_SHORT).show();

        }
    }

    public List<Product> obtenerProductosFilradosPorCategoria(){
        List<Product>productosGuardados = ProductDao.getInstance().getProductos();
        SkinType tipoPiel = Routine.getInstance().getSkinType();


        // Filtramos por categoría(Limpieza e Hidratacion)


        return productosGuardados.stream().filter(product -> (product.getCategoryProduct() == CategoryProduct.CLEANER
                || product.getCategoryProduct() == CategoryProduct.MOISTURIZER && product.getSkinType() == tipoPiel)).collect(Collectors.toList());
    }
}