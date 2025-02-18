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

/**
 * The StepAdapter class is a RecyclerView adapter used to display a list of Step objects in an Android
 * application.
 */
public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {
    private List<Step> stepList;
    Context context;


    // The `StepAdapter` constructor `public StepAdapter(List<Step> stepList, List<Product>
    // selectedProducts)` is initializing the `stepList` field of the `StepAdapter` class with the
    // `stepList` parameter that is passed to the constructor. The `selectedProducts` parameter is not
    // being used in this constructor.
    public StepAdapter(List<Step> stepList, List<Product> selectedProducts) {
        this.stepList = stepList;

    }

    /**
     * The function onCreateViewHolder creates a StepViewHolder object for a specific view type within
     * a RecyclerView.
     * 
     * @param parent The `parent` parameter in the `onCreateViewHolder` method represents the ViewGroup
     * into which the created View will be placed after it is bound to an adapter position. It is
     * typically the RecyclerView in which the ViewHolder will be displayed.
     * @param viewType The `viewType` parameter in the `onCreateViewHolder` method of a RecyclerView
     * adapter is used to identify the type of view that needs to be created based on the view type
     * provided. This can be useful when dealing with multiple view types within the same RecyclerView.
     * The `viewType` parameter is
     * @return A new StepViewHolder object is being returned.
     */
    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_step, parent, false);
        context = parent.getContext();
        return new StepViewHolder(view);
    }

    /**
     * The onBindViewHolder method updates the UI elements in a RecyclerView ViewHolder with data from
     * a Step object.
     * 
     * @param holder The `holder` parameter in the `onBindViewHolder` method refers to the ViewHolder
     * object which represents the views of an individual item in the RecyclerView. It holds references
     * to the views that need to be updated with the data for a particular item at a given position.
     * @param position The `position` parameter in the `onBindViewHolder` method represents the
     * position of the item within the RecyclerView. It indicates the position of the item in the data
     * set that the RecyclerView Adapter is currently binding to the ViewHolder.
     */
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

    /**
     * The function getItemCount() returns the size of the stepList.
     * 
     * @return The method `getItemCount()` is returning the size of the `stepList`, which represents
     * the number of items in the list.
     */
    @Override
    public int getItemCount() {
        return stepList.size();
    }

    /**
     * The StepViewHolder class extends RecyclerView.ViewHolder and holds references to various
     * TextViews used to display step information in a RecyclerView.
     */
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
