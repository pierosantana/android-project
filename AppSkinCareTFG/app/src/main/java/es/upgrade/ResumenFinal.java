package es.upgrade;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.upgrade.entidad.Product;

public class ResumenFinal extends AppCompatActivity {

    private TextView txtResumen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen_final);

        txtResumen = findViewById(R.id.txtResumen);

        // Obtener los productos seleccionados desde el Intent
        Product selectedLimpieza = (Product) getIntent().getSerializableExtra("selectedLimpieza");
        Product selectedHidratacion = (Product) getIntent().getSerializableExtra("selectedHidratacion");
        Product selectedTonificacion = (Product) getIntent().getSerializableExtra("selectedTonificacion");
        Product selectedTratamiento = (Product) getIntent().getSerializableExtra("selectedTratamiento");
        Product selectedProtector = (Product) getIntent().getSerializableExtra("selectedProtector");

        // Crear el resumen con nombre y precio
        StringBuilder resumen = new StringBuilder("Resumen de productos seleccionados:\n\n");

        if (selectedLimpieza != null) {
            resumen.append("Producto de Limpieza: ").append(selectedLimpieza.getName())
                    .append(" - Precio: ").append(selectedLimpieza.getPrice()).append("\n");
        }
        if (selectedHidratacion != null) {
            resumen.append("Producto de Hidratación: ").append(selectedHidratacion.getName())
                    .append(" - Precio: ").append(selectedHidratacion.getPrice()).append("\n");
        }
        if (selectedTonificacion != null) {
            resumen.append("Producto de Tonificación: ").append(selectedTonificacion.getName())
                    .append(" - Precio: ").append(selectedTonificacion.getPrice()).append("\n");
        }
        if (selectedTratamiento != null) {
            resumen.append("Producto de Tratamiento: ").append(selectedTratamiento.getName())
                    .append(" - Precio: ").append(selectedTratamiento.getPrice()).append("\n");
        }
        if (selectedProtector != null) {
            resumen.append("Producto de Protector Solar: ").append(selectedProtector.getName())
                    .append(" - Precio: ").append(selectedProtector.getPrice()).append("\n");
        }

        // Mostrar el resumen en la pantalla
        txtResumen.setText(resumen.toString());

        // Si algún producto no fue seleccionado, mostrar un mensaje
        if (selectedLimpieza == null || selectedHidratacion == null || selectedTonificacion == null
                || selectedTratamiento == null || selectedProtector == null) {
            Toast.makeText(this, "Faltan productos por seleccionar.", Toast.LENGTH_SHORT).show();
        }
    }
}
