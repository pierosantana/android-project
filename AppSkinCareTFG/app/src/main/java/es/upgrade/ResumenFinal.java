package es.upgrade;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import java.util.List;

import es.upgrade.entidad.CategoryProduct;
import es.upgrade.entidad.Product;
import es.upgrade.entidad.Routine;

public class ResumenFinal extends AppCompatActivity {
    private ImageView imgLimpieza, imgHidratacion;
    private TextView txtLimpieza, txtHidratacion;
    private Button btnFinalizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen_final);

        imgLimpieza = findViewById(R.id.imgLimpieza);
        imgHidratacion = findViewById(R.id.imgHidratacion);
        txtLimpieza = findViewById(R.id.txtLimpieza);
        txtHidratacion = findViewById(R.id.txtHidratacion);
        btnFinalizar = findViewById(R.id.btnFinalizar);

        List<Product> productosSeleccionados = Routine.getInstance().getProductList();

        for (Product producto : productosSeleccionados) {
            if (producto.getCategoryProduct() == CategoryProduct.CLEANER) {
                txtLimpieza.setText(producto.getName());
                Glide.with(this)
                        .load(producto.getUrl())
                        .into(imgLimpieza);
            } else if (producto.getCategoryProduct() == CategoryProduct.MOISTURIZER) {
                txtHidratacion.setText(producto.getName());
                Glide.with(this)
                        .load(producto.getUrl())
                        .into(imgHidratacion);
            }
        }

        btnFinalizar.setOnClickListener(v -> finish());
    }
}