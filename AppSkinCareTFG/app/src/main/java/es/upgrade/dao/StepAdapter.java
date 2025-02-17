package es.upgrade.dao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.upgrade.R;
import es.upgrade.entidad.Product;
import es.upgrade.entidad.Step;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {
    private List<Step> stepList;
    Context context;


    public StepAdapter(List<Step> stepList, List<Product> selectedProducts) {
        this.stepList = stepList;

    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_step, parent, false);
        context = parent.getContext();
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        Step step = stepList.get(position);
        holder.stepTitle.setText(step.getTitle());
        holder.stepDescription.setText(step.getDescription());
        holder.stepDuration.setText(step.getDuration());
        holder.stepProTip.setText(String.join("\n", step.getProTips()));

        // Actualiza el contador de paso
        holder.tvStepCounter.setText("Paso " + (position + 1));



    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    public static class StepViewHolder extends RecyclerView.ViewHolder {
        TextView stepTitle, stepDescription, stepDuration, stepProTip,tvStepCounter;

        public StepViewHolder(@NonNull View itemView) {
            super(itemView);
            stepTitle = itemView.findViewById(R.id.tvStepTitle);
            stepDescription = itemView.findViewById(R.id.tvStepDescription);
            stepDuration = itemView.findViewById(R.id.tvStepDuration);
            stepProTip = itemView.findViewById(R.id.tvStepProTips);
            tvStepCounter = itemView.findViewById(R.id.tvStepCounter);
        }
    }



}
