package es.upgrade;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.stream.Collectors;

import es.upgrade.dao.api.ProductAdapter;
import es.upgrade.dao.ProductDao;
import es.upgrade.dao.api.RetrofitClient;
import es.upgrade.entidad.CategoryProduct;
import es.upgrade.entidad.Product;
import es.upgrade.entidad.Routine;
import es.upgrade.entidad.SkinType;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_economic);

        recyclerViewLimpieza = findViewById(R.id.rvLimpieza);
        recyclerViewHidratacion = findViewById(R.id.rvHidratacion);
        emptyView = findViewById(R.id.emptyView);
        btnContinuar = findViewById(R.id.btnContinuar);

        //Vista del reciclerView
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
                Toast.makeText(this, "Debes seleccionar un producto de cada categoría", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Producto de Limpieza: " + selectedLimpiezaProduct.getName() + "\nProducto de Hidratación: " + selectedHidratacionProduct.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void obtenerProductosDesdeApi() {
        Call<List<Product>> call = RetrofitClient.getApiService().getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProductDao.getInstance().setProductos(response.body());
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
        List<Product> products = ProductDao.getInstance().getProductos();

        if (products == null || products.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            recyclerViewLimpieza.setVisibility(View.GONE);
            recyclerViewHidratacion.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);

            List<Product> productosLimpieza = obtenerProductosPorCategoria(CategoryProduct.CLEANER);
            List<Product> productosHidratacion = obtenerProductosPorCategoria(CategoryProduct.MOISTURIZER);

            if (!productosLimpieza.isEmpty()) {
                limpiezaAdapter = new ProductAdapter(this, productosLimpieza, product -> selectedLimpiezaProduct = product);
                recyclerViewLimpieza.setAdapter(limpiezaAdapter);
                recyclerViewLimpieza.setVisibility(View.VISIBLE);
            } else {
                recyclerViewLimpieza.setVisibility(View.GONE);
            }

            if (!productosHidratacion.isEmpty()) {
                hidratacionAdapter = new ProductAdapter(this, productosHidratacion, product -> selectedHidratacionProduct = product);
                recyclerViewHidratacion.setAdapter(hidratacionAdapter);
                recyclerViewHidratacion.setVisibility(View.VISIBLE);
            } else {
                recyclerViewHidratacion.setVisibility(View.GONE);
            }
        }
    }

    private List<Product> obtenerProductosPorCategoria(CategoryProduct category) {
        List<Product> productos = ProductDao.getInstance().getProductos();
        SkinType tipoPiel = Routine.getInstance().getSkinType();
        return productos.stream()
                .filter(product -> product.getCategoryProduct() == category && product.getSkinType() == tipoPiel)
                .collect(Collectors.toList());
    }
}
