package es.upgrade.dao;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.upgrade.R;
import es.upgrade.entidad.Step;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {
    private List<Step> stepList;

    public StepAdapter(List<Step> stepList) {
        this.stepList = stepList;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_step, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        Step step = stepList.get(position);
        holder.stepTitle.setText(step.getTitle());
        holder.stepDescription.setText(step.getDescription());
        holder.stepProTip.setText(step.getProTip());
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    public static class StepViewHolder extends RecyclerView.ViewHolder {
        TextView stepTitle, stepDescription, stepDuration, stepProTip;

        public StepViewHolder(@NonNull View itemView) {
            super(itemView);
            stepTitle = itemView.findViewById(R.id.tvStepTitle);
            stepDescription = itemView.findViewById(R.id.tvStepDescription);
            stepProTip = itemView.findViewById(R.id.tvProTips);
        }
    }
}
