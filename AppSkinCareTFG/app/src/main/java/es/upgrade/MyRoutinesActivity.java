package es.upgrade;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.upgrade.dao.RoutineAdapter;
import es.upgrade.entidad.Routine;
import es.upgrade.entidad.User;

public class MyRoutinesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RoutineAdapter routineAdapter;
    private List<Routine> routineList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_routines);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerViewMisRutinas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        routineList = User.getInstance().getRoutineList();

        if (routineList == null || routineList.isEmpty()) {
            Toast.makeText(this, "No routines created yet", Toast.LENGTH_SHORT).show();
        } else {
            routineAdapter = new RoutineAdapter(this,routineList);
            recyclerView.setAdapter(routineAdapter);
        }
    }

    /**
     * Opens the RoutineExplainActivity to display the details of the specified routine.
     *
     * @param routine The Routine object containing the details to be displayed.
     */
    private void openRoutineDetail(Routine routine) {
        Intent intent = new Intent(this, RoutineExplainActivity.class);
        intent.putExtra("routine", routine);
        startActivity(intent);
    }
}