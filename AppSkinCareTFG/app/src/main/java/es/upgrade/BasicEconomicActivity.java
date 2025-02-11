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

import java.util.List;
import java.util.stream.Collectors;

import es.upgrade.dao.ProductDao;
import es.upgrade.entidad.CategoryProduct;
import es.upgrade.entidad.Product;
import es.upgrade.entidad.Routine;
import es.upgrade.entidad.SkinType;

public class BasicEconomicActivity extends AppCompatActivity {
    private RadioGroup radioGroup1;
    private RadioGroup radioGroup2;
    private Button btnContinuar;

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

        // Listener para el primer grupo de opciones de nuestro radio group.
        radioGroup1.setOnCheckedChangeListener((group, checkedId)->{
            RadioButton selectedRadioButton = findViewById(checkedId);
            if(selectedRadioButton != null){
                Toast.makeText(this, "Seleccionaste: " + selectedRadioButton.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        // Listener para el segundo grupo de opciones de nuestro radio grupo
        radioGroup2.setOnCheckedChangeListener((group, checkedId)->{
            RadioButton selectedRadioButton = findViewById(checkedId);
            if(selectedRadioButton != null){
                Toast.makeText(this, "Seleccionaste: " + selectedRadioButton.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        btnContinuar.setOnClickListener(v->{

        });
    }

    public List<Product> obtenerProductosFilradosPorCategoriaYPrecioBajo(){
        List<Product>productosGuardados = ProductDao.getInstance().getProductos();
        // Establecemos el precio maximo bajo
        double precioMaximo =7.5;
        SkinType tipoPiel = Routine.getInstance().getSkinType();

        // Filtramos por categoría(Limpieza e Hidratacion) y precio bajo
        // Luego mas tarde habra que filtrar el producto por el tipo de piel

        return productosGuardados.stream().filter(product -> (product.getCategoryProduct() == CategoryProduct.CLEANER
        || product.getCategoryProduct() == CategoryProduct.MOISTURIZER)&& product.getPrice() < precioMaximo
        && product.getSkinType()== tipoPiel).collect(Collectors.toList());
    }
}