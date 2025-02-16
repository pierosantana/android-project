package es.upgrade;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.upgrade.UI.LobbyActivity;
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

        // Obtener los datos de la rutina
        String skinType = getIntent().getStringExtra("skinType");
        String schedule = getIntent().getStringExtra("schedule");
        String routineType = getIntent().getStringExtra("routineType");
        String budget = getIntent().getStringExtra("budget");

        // Obtener los productos seleccionados desde el Intent
        Product selectedLimpieza = (Product) getIntent().getSerializableExtra("selectedLimpieza");
        Product selectedHidratacion = (Product) getIntent().getSerializableExtra("selectedHidratacion");
        Product selectedTonificacion = (Product) getIntent().getSerializableExtra("selectedTonificacion");
        Product selectedTratamiento = (Product) getIntent().getSerializableExtra("selectedTratamiento");
        Product selectedProtector = (Product) getIntent().getSerializableExtra("selectedProtector");

        // Crear el resumen de la rutina
        StringBuilder resumen = new StringBuilder("Resumen de tu rutina:");
        resumen.append("\n\nTipo de piel: ").append(skinType);
        resumen.append("\nMomento del día: ").append(schedule);
        resumen.append("\nTipo de rutina: ").append(routineType);
        resumen.append("\nPresupuesto: ").append(budget);
        resumen.append("\n\nProductos seleccionados:\n\n");

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
            Intent intent = new Intent(ResumenFinal.this, LobbyActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
