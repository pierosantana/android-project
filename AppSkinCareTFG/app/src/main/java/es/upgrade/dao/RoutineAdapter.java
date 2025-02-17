package es.upgrade.dao;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import es.upgrade.R;
import es.upgrade.RoutineExplainActivity;
import es.upgrade.entidad.Routine;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder> {

    private Context context;
    private List<Routine> routineList;
    private Routine currentRoutine;

    public RoutineAdapter(Context context, List<Routine> routineList) {
        this.context = context;
        this.routineList = routineList;
    }

    @NonNull
    @Override
    public RoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_routine, parent, false);
        return new RoutineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineViewHolder holder, int position) {
        Routine routine = routineList.get(position);

        holder.tvTitle.setText(routine.getRoutineType().toString());
        holder.tvType.setText(routine.getSchedule().toString());
        holder.tvBudget.setText("Precio: $" + routine.getBudget());

        // Limpiar los círculos anteriores
        holder.stepsContainer.removeAllViews();

        // Crear los círculos según el número de pasos de la rutina
        int stepCount = routine.getStepCount();
        for (int i = 0; i < stepCount; i++) {
            TextView stepCircle = new TextView(holder.itemView.getContext());
            stepCircle.setWidth(60);
            stepCircle.setHeight(60);
            stepCircle.setText("");
            stepCircle.setBackgroundResource(R.drawable.rounded_image); // Usamos el drawable circle.xml

            // Agregar márgenes entre los círculos
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(8, 0, 8, 0);
            stepCircle.setLayoutParams(layoutParams);

            // Agregar el círculo al contenedor
            holder.stepsContainer.addView(stepCircle);
        }


        holder.itemView.setOnClickListener(v -> {
            currentRoutine = routine;
            Intent intent = new Intent(context, RoutineExplainActivity.class);
            intent.putExtra("routine",currentRoutine);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return routineList.size();
    }

    public static class RoutineViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvType, tvBudget;
        LinearLayout stepsContainer;

        public RoutineViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvRoutineTitle);
            tvType = itemView.findViewById(R.id.tvRoutineType);
            tvBudget = itemView.findViewById(R.id.tvBudget);
            stepsContainer = itemView.findViewById(R.id.stepsContainer);
        }
    }
}