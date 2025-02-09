package es.upgrade;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import es.upgrade.dao.UserDao;
import es.upgrade.entidad.Budget;
import es.upgrade.entidad.Routine;
import es.upgrade.entidad.RoutineType;
import es.upgrade.entidad.SkinType;
import es.upgrade.entidad.User;

public class ReviewAndEditActivity extends AppCompatActivity {
    private TextView resumen;
    private Button continuar;
    private Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_review_and_edit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.RW), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        resumen = findViewById(R.id.tv_resumen);
        continuar = findViewById(R.id.btn_continuar);
        edit = findViewById(R.id.btn_editar);


        User user = User.getInstance();
        Routine routine = Routine.getInstance();

        Log.d("ReviewAndEdit",routine.toString());
        Log.d("Routine", "El Schedule actual es: " + routine.getSchedule());
        resumen.setText("Routine \n" +
                "Schedule: " + routine.getSchedule() + "\n" +
                "Skin Type: " + routine.getSkinType() + "\n" +
                "Routine Type: " + routine.getRoutineType() + "\n" +
                "Budget: " + routine.getBudget());
        continuar.setOnClickListener(view -> {
            UserDao userDao = UserDao.getInstance();
            user.addRoutine(routine);
            userDao.updateUser();

            if(routine.getRoutineType() == RoutineType.BASIC
                    && routine.getBudget() == Budget.ECONOMIC){
                startActivity(new Intent(ReviewAndEditActivity.this, BasicEconomicActivity.class));
            }

            if(routine.getRoutineType() == RoutineType.BASIC
                    && routine.getBudget() == Budget.CUSTOMIZED){
                startActivity(new Intent(ReviewAndEditActivity.this, BasicCustomizedActivity.class));
            }

            if(routine.getRoutineType() == RoutineType.COMPLETE
                    && routine.getBudget() == Budget.ECONOMIC){
                startActivity(new Intent(ReviewAndEditActivity.this, CompleteEconomicActivity.class));
            }

            if(routine.getRoutineType() == RoutineType.COMPLETE
                    && routine.getBudget() == Budget.CUSTOMIZED){
                startActivity(new Intent(ReviewAndEditActivity.this, CompleteCustomizedActivity.class));
            }



//            Routine routine = Routine.getInstance();

            //user.addRoutine(routine);

//            userDao.updateUser();



        });

        edit.setOnClickListener(v -> {


        });

    }
}