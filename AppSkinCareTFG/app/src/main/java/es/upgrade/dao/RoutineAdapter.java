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

        // Establecemos la rutina seleccionada en el Singleton para evitar la creación de una nueva instancia.
        Routine.getInstance().setRoutineType(routine.getRoutineType());
        Routine.getInstance().setSchedule(routine.getSchedule());
        Routine.getInstance().setBudget(routine.getBudget());
        Routine.getInstance().setSkinType(routine.getSkinType());
        Routine.getInstance().setNightRoutine(routine.isNightRoutine());
        Routine.getInstance().setProductList(routine.getProductList());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RoutineExplainActivity.class);
            // Aquí no necesitamos pasar la rutina con el Intent porque ya está en el Singleton
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