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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.upgrade.entidad.CategoryProduct;
import es.upgrade.entidad.Product;
import es.upgrade.entidad.Routine;
import es.upgrade.entidad.RoutineType;
import es.upgrade.entidad.Schedule;

public class CompleteEconomicActivity extends AppCompatActivity {

    private RadioGroup radioGroup1;
    private RadioGroup radioGroup2;
    private RadioGroup radioGroup3;
    private RadioGroup radioGroup4;
    private RadioGroup radioGroup5;
    private Button btnContinuar;
    private TextView protectorSolar;


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
        // Creamos un hasmap para guardar la lista de productos por cada radio group
        Map<Integer, List<Product>> productMap =new HashMap<>();
        radioGroup1 = findViewById(R.id.radioGroup1);
        radioGroup2 = findViewById(R.id.radioGroup2);
        radioGroup3 = findViewById(R.id.radioGroup3);
        radioGroup4 = findViewById(R.id.radioGroup4);
        radioGroup5 = findViewById(R.id.radioGroup5);
        protectorSolar = findViewById(R.id.title5);
        btnContinuar = findViewById(R.id.button);
        Routine routine = Routine.getInstance();
        Log.d("Routine", "El Schedule actual es: " + routine.getSchedule());
        Log.d("RoutineCheck", "Schedule actual en CompleteEconomicActivity: " + routine.getSchedule());
        if(routine.getSchedule() == Schedule.NIGHT){
            radioGroup5.setVisibility(View.GONE);
            protectorSolar.setVisibility(View.GONE);
        }


        productMap.put(R.id.radioGroup1,ProductRepository.getLimpiezaProducts());
        productMap.put(R.id.radioGroup2,ProductRepository.getHidratarProducts());
        productMap.put(R.id.radioGroup3,ProductRepository.getTonificarProducts());
        productMap.put(R.id.radioGroup4,ProductRepository.getTratamientoProducts());
        productMap.put(R.id.radioGroup5,ProductRepository.getProtectorSolarProducts());

        // Inicalizamos los productos en cada radio group
        for (Map.Entry<Integer, List<Product>> entry : productMap.entrySet()) {
            int radioGroupId = entry.getKey();
            List<Product> products = entry.getValue();

            RadioGroup radioGroup = findViewById(radioGroupId);
            if (radioGroup != null) {
                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
                    if (radioButton != null && i < products.size()) {
                        radioButton.setTag(products.get(i));  // Asignar producto con setTag()
                    }
                }
            }
        }
        // Aqui hacemos un listener por cada RadioGroup asi quitamos codigo
        for (int radioGroupId : productMap.keySet()) {
            RadioGroup radioGroup = findViewById(radioGroupId);

            if (radioGroup != null) {
                radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                    RadioButton selectedRadioButton = findViewById(checkedId);
                    if (selectedRadioButton != null) {
                        // Obtener el producto correspondiente al RadioButton seleccionado
                        Product selectedProduct = (Product) selectedRadioButton.getTag();

                        // Asegurarnos de que la lista de productos en la rutina esté inicializada

                        if (routine.getProductList() == null) {
                            routine.setProductList(new ArrayList<>());
                        }

                        // Obtener la categoría del producto seleccionado (esto se asume por el grupo)
                        CategoryProduct productCategory = selectedProduct.getCategoryProduct();

                        // Buscar si ya existe un producto de esta categoría en la rutina
                        boolean productExists = false;
                        for (Product product : routine.getProductList()) {
                            if (product.getCategoryProduct() == productCategory) {
                                // Si ya existe un producto de esta categoría, reemplazarlo
                                routine.getProductList().remove(product);
                                routine.getProductList().add(selectedProduct);
                                productExists = true;
                                break;
                            }
                        }

                        // Si no existe un producto de esta categoría, añadir el producto seleccionado
                        if (!productExists) {
                            routine.getProductList().add(selectedProduct);
                        }

                        // Mostrar mensaje de confirmación
                        Toast.makeText(this, "Producto agregado: " + selectedProduct.getName(), Toast.LENGTH_SHORT).show();

                        // Esto es solo a nivel interno de que los productos de la rutina se guardan de manera correcta
                        // Opcionalmente, puedes imprimir la lista de productos en el Log para ver si se agregan correctamente
                        StringBuilder productListStr = new StringBuilder("Productos en la rutina: ");
                        for (Product product : routine.getProductList()) {
                            productListStr.append(product.getName()).append(", ");
                        }

                        // Eliminar la última coma
                        if (productListStr.length() > 0) {
                            productListStr.setLength(productListStr.length() - 2);
                        }

                        Log.d("Routine", productListStr.toString());

                    }

                });
            }
        }


        btnContinuar.setOnClickListener(v->{

        });


    }
}