package es.upgrade;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.upgrade.entidad.Product;

public class ResumenFinal extends AppCompatActivity {

    private TextView txtResumen, txtTotalPrecio;
    private Button btnContinuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen_final);

        txtResumen = findViewById(R.id.txtResumen);
        txtTotalPrecio = findViewById(R.id.txtTotalPrecio);
        btnContinuar = findViewById(R.id.btn_continuar);

        // Obtener los productos seleccionados desde el Intent
        Product selectedLimpieza = (Product) getIntent().getSerializableExtra("selectedLimpieza");
        Product selectedHidratacion = (Product) getIntent().getSerializableExtra("selectedHidratacion");
        Product selectedTonificacion = (Product) getIntent().getSerializableExtra("selectedTonificacion");
        Product selectedTratamiento = (Product) getIntent().getSerializableExtra("selectedTratamiento");
        Product selectedProtector = (Product) getIntent().getSerializableExtra("selectedProtector");

        // Crear el resumen con nombre y precio
        StringBuilder resumen = new StringBuilder("Resumen de productos seleccionados:\n\n");
        double totalPrecio = 0.0;

        if (selectedLimpieza != null) {
            resumen.append("Producto de Limpieza: ").append(selectedLimpieza.getName())
                    .append(" - Precio: ").append(selectedLimpieza.getPrice()).append("€\n");
            totalPrecio += selectedLimpieza.getPrice();
        }
        if (selectedHidratacion != null) {
            resumen.append("Producto de Hidratación: ").append(selectedHidratacion.getName())
                    .append(" - Precio: ").append(selectedHidratacion.getPrice()).append("€\n");
            totalPrecio += selectedHidratacion.getPrice();
        }
        if (selectedTonificacion != null) {
            resumen.append("Producto de Tonificación: ").append(selectedTonificacion.getName())
                    .append(" - Precio: ").append(selectedTonificacion.getPrice()).append("€\n");
            totalPrecio += selectedTonificacion.getPrice();
        }
        if (selectedTratamiento != null) {
            resumen.append("Producto de Tratamiento: ").append(selectedTratamiento.getName())
                    .append(" - Precio: ").append(selectedTratamiento.getPrice()).append("€\n");
            totalPrecio += selectedTratamiento.getPrice();
        }
        if (selectedProtector != null) {
            resumen.append("Producto de Protector Solar: ").append(selectedProtector.getName())
                    .append(" - Precio: ").append(selectedProtector.getPrice()).append("€\n");
            totalPrecio += selectedProtector.getPrice();
        }

        // Mostrar el resumen y el total
        txtResumen.setText(resumen.toString());
        txtTotalPrecio.setText("Total: " + totalPrecio + "€");

        // Si algún producto no fue seleccionado, mostrar un mensaje
        if (selectedLimpieza == null || selectedHidratacion == null || selectedTonificacion == null
                || selectedTratamiento == null || selectedProtector == null) {
            Toast.makeText(this, "Faltan productos por seleccionar.", Toast.LENGTH_SHORT).show();
        }

        // Evento para el botón continuar
        btnContinuar.setOnClickListener(v -> {
            Toast.makeText(this, "Continuando con la compra...", Toast.LENGTH_SHORT).show();
            // Aquí podrías iniciar otra actividad o finalizar esta
        });
    }
}