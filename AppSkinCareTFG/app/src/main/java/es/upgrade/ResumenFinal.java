package es.upgrade;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import es.upgrade.UI.LobbyActivity;
import es.upgrade.dao.UserDao;
import es.upgrade.entidad.Budget;
import es.upgrade.entidad.Product;
import es.upgrade.entidad.Routine;
import es.upgrade.entidad.RoutineType;
import es.upgrade.entidad.Schedule;
import es.upgrade.entidad.SkinType;
import es.upgrade.entidad.User;

public class ResumenFinal extends AppCompatActivity {

    private TextView txtResumen, txtTotalPrecio;
    private Button btnContinuar;
    private Routine routine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen_final);

        routine = (Routine) getIntent().getSerializableExtra("routine");

        txtResumen = findViewById(R.id.txtResumen);
        txtTotalPrecio = findViewById(R.id.txtTotalPrecio);
        btnContinuar = findViewById(R.id.btn_continuar);

        // Get the routine data

        String skinType = getIntent().getStringExtra("skinType");
        String schedule = getIntent().getStringExtra("schedule");
        String routineType = getIntent().getStringExtra("routineType");
        String budget = getIntent().getStringExtra("budget");

        
        Product selectedLimpieza = (Product) getIntent().getSerializableExtra("selectedLimpieza");
        Product selectedHidratacion = (Product) getIntent().getSerializableExtra("selectedHidratacion");
        Product selectedTonificacion = (Product) getIntent().getSerializableExtra("selectedTonificacion");
        Product selectedTratamiento = (Product) getIntent().getSerializableExtra("selectedTratamiento");
        Product selectedProtector = (Product) getIntent().getSerializableExtra("selectedProtector");

        // Create the routine summary
        StringBuilder resumen = new StringBuilder("Your routine summary:");
        resumen.append("\n\nSkin type: ").append(skinType);
        resumen.append("\nTime of day: ").append(schedule);
        resumen.append("\nRoutine type: ").append(routineType);
        resumen.append("\nBudget: ").append(budget);
        resumen.append("\n\nSelected products:\n\n");

        double totalPrecio = 0.0;

        if (selectedLimpieza != null) {
            resumen.append("Cleaning product: ").append(selectedLimpieza.getName())
                    .append(" - Price: ").append(selectedLimpieza.getPrice()).append("€\n");
            totalPrecio += selectedLimpieza.getPrice();
        }
        if (selectedHidratacion != null) {
            resumen.append("Moisturizing product: ").append(selectedHidratacion.getName())
                    .append(" - Price: ").append(selectedHidratacion.getPrice()).append("€\n");
            totalPrecio += selectedHidratacion.getPrice();
        }
        if (selectedTonificacion != null) {
            resumen.append("Toning product: ").append(selectedTonificacion.getName())
                    .append(" - Price: ").append(selectedTonificacion.getPrice()).append("€\n");
            totalPrecio += selectedTonificacion.getPrice();
        }
        if (selectedTratamiento != null) {
            resumen.append("Treatment product: ").append(selectedTratamiento.getName())
                    .append(" - Price: ").append(selectedTratamiento.getPrice()).append("€\n");
            totalPrecio += selectedTratamiento.getPrice();
        }
        if (selectedProtector != null) {
            resumen.append("Sunscreen product: ").append(selectedProtector.getName())
                    .append(" - Price: ").append(selectedProtector.getPrice()).append("€\n");
            totalPrecio += selectedProtector.getPrice();
        }

        
        txtResumen.setText(resumen.toString());
        String totalFormatted = String.format("%.2f", totalPrecio);
        txtTotalPrecio.setText("Total: " + totalFormatted + "€");

       
        if (selectedLimpieza == null || selectedHidratacion == null || selectedTonificacion == null
                || selectedTratamiento == null || selectedProtector == null) {
            Toast.makeText(this, "Products still to be selected.", Toast.LENGTH_SHORT).show();
        }

        routine.setSkinType(SkinType.valueOf(skinType));
        routine.setSchedule(Schedule.valueOf(schedule));
        routine.setRoutineType(RoutineType.valueOf(routineType));
        routine.setBudget(Budget.valueOf(budget));

        routine.setProductList(new ArrayList<>());
        if (selectedLimpieza != null) routine.getProductList().add(selectedLimpieza);
        if (selectedHidratacion != null) routine.getProductList().add(selectedHidratacion);
        if (selectedTonificacion != null) routine.getProductList().add(selectedTonificacion);
        if (selectedTratamiento != null) routine.getProductList().add(selectedTratamiento);
        if (selectedProtector != null) routine.getProductList().add(selectedProtector);

        User user = User.getInstance();
        
        btnContinuar.setOnClickListener(v -> {
            //Persist the routine
            UserDao userDao = UserDao.getInstance();
            user.addRoutine(routine);
            userDao.updateUser();

            Intent intent = new Intent(ResumenFinal.this, LobbyActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
