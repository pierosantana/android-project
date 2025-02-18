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

    /**
     * The function onCreateViewHolder creates and returns a RoutineViewHolder object for displaying
     * items in a RecyclerView.
     * 
     * @param parent The `parent` parameter in the `onCreateViewHolder` method represents the ViewGroup
     * into which the new View will be added after it is bound to an adapter position. In the context
     * of a RecyclerView, the `parent` is typically the RecyclerView itself or one of its direct parent
     * layouts.
     * @param viewType The `viewType` parameter in the `onCreateViewHolder` method of a
     * RecyclerView.Adapter is used to identify the type of view that needs to be created based on the
     * position in the RecyclerView. This can be useful when you have different types of views in your
     * RecyclerView and need to create different view holders
     * @return A new instance of RoutineViewHolder is being returned.
     */
    @NonNull
    @Override
    public RoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_routine, parent, false);
        return new RoutineViewHolder(view);
    }

   /**
    * This function populates a RecyclerView item with data from a Routine object and dynamically
    * creates circles based on the number of steps in the routine.
    * 
    * @param holder The `holder` parameter in the `onBindViewHolder` method is a reference to the
    * `ViewHolder` object which holds the views for a single item in the RecyclerView. It is used to
    * access and update the views within that item.
    * @param position The `position` parameter in the `onBindViewHolder` method represents the position
    * of the item within the RecyclerView. It indicates the position of the item within the data set
    * that the RecyclerView Adapter is currently binding to the ViewHolder.
    */
    @Override
    public void onBindViewHolder(@NonNull RoutineViewHolder holder, int position) {
        Routine routine = routineList.get(position);

        holder.tvTitle.setText(routine.getRoutineType().toString());
        holder.tvType.setText(routine.getSchedule().toString());
        holder.tvBudget.setText("Precio: $" + routine.getBudget());
        holder.stepsContainer.removeAllViews();

        //Create circles for each step in the routine
        int stepCount = routine.getStepCount();
        for (int i = 0; i < stepCount; i++) {
            TextView stepCircle = new TextView(holder.itemView.getContext());
            stepCircle.setWidth(60);
            stepCircle.setHeight(60);
            stepCircle.setText("");
            stepCircle.setBackgroundResource(R.drawable.rounded_image); 

            // Set the margins for the circle
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(8, 0, 8, 0);
            stepCircle.setLayoutParams(layoutParams);
            holder.stepsContainer.addView(stepCircle);
        }


        holder.itemView.setOnClickListener(v -> {
            currentRoutine = routine;
            Intent intent = new Intent(context, RoutineExplainActivity.class);
            intent.putExtra("routine",currentRoutine);
            context.startActivity(intent);
        });
    }

    /**
     * This function returns the number of items in a list called routineList.
     * 
     * @return The method `getItemCount()` is returning the size of the `routineList`, which represents
     * the number of items in the list.
     */
    @Override
    public int getItemCount() {
        return routineList.size();
    }

    /**
     * The RoutineViewHolder class extends RecyclerView.ViewHolder and holds references to various
     * views for displaying routine information.
     */
    public static class RoutineViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvType, tvBudget;
        LinearLayout stepsContainer;

        // In the `RoutineViewHolder` constructor, the code is initializing the views for displaying
        // routine information. Here's a breakdown of what each line is doing:
        public RoutineViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvRoutineTitle);
            tvType = itemView.findViewById(R.id.tvRoutineType);
            tvBudget = itemView.findViewById(R.id.tvBudget);
            stepsContainer = itemView.findViewById(R.id.stepsContainer);
        }
    }
}