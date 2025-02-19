package es.upgrade.dao;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import es.upgrade.R;
import es.upgrade.entidad.Schedule;


/**
 * The `ScheduleAdapter` class is a RecyclerView adapter that displays a list of schedules with
 * corresponding icons, titles, and descriptions.
 */
public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {
    private final Schedule[] schedules = Schedule.values();

   /**
    * The function onCreateViewHolder creates and returns a ScheduleViewHolder object for a specific
    * view type.
    * 
    * @param parent The `parent` parameter in the `onCreateViewHolder` method is a reference to the
    * ViewGroup into which the created View will be added after it is bound to the adapter position. It
    * represents the parent ViewGroup that will contain the inflated view.
    * @param viewType The `viewType` parameter in the `onCreateViewHolder` method of a
    * RecyclerView.Adapter is used to identify the type of view that needs to be created based on the
    * view type provided by the `getItemViewType` method. This can be useful when dealing with multiple
    * view types in a single RecyclerView
    * @return A ScheduleViewHolder object is being returned.
    */
    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_schedule_type, parent, false);
        return new ScheduleViewHolder(view);
    }

    /**
     * The onBindViewHolder method binds a Schedule object to a ScheduleViewHolder at a specific
     * position.
     * 
     * @param holder The `holder` parameter in the `onBindViewHolder` method is a reference to the
     * `ScheduleViewHolder` object that represents a single item view in the RecyclerView. This
     * parameter is used to bind data from the `Schedule` object to the views within that particular
     * item view.
     * @param position The `position` parameter in the `onBindViewHolder` method represents the
     * position of the item within the adapter's data set that needs to be bound to the
     * `ScheduleViewHolder`. It is typically used to retrieve the corresponding data object from the
     * dataset (in this case, the `schedules` array
     */
    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        Schedule schedule = schedules[position];
        holder.bind(schedule);
    }

    /**
     * The function getItemCount() returns the length of the schedules array.
     * 
     * @return The method `getItemCount()` is returning the length of the `schedules` array.
     */
    @Override
    public int getItemCount() {
        return schedules.length;
    }
    /**
     * The ScheduleViewHolder class is a RecyclerView ViewHolder that binds data to views for
     * displaying schedule information.
     */
    static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iconView;
        private final ImageView backgroundIconView;
        private final TextView titleText;
        private final TextView descriptionText;

        // The `public ScheduleViewHolder(@NonNull View itemView)` constructor in the
        // `ScheduleViewHolder` class is initializing the views within the item view represented by the
        // `itemView` parameter. Here's a breakdown of what each line is doing:
        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            iconView = itemView.findViewById(R.id.iconView);
            backgroundIconView = itemView.findViewById(R.id.backgroundIconView);
            titleText = itemView.findViewById(R.id.titleText);
            descriptionText = itemView.findViewById(R.id.descriptionText);
        }

        /**
         * The `bind` function sets the image resources, title, and description based on the provided
         * `Schedule` object.
         * 
         * @param skinType The `skinType` parameter is of type `Schedule`, which seems to represent a
         * schedule related to skin types. The `bind` method is responsible for setting the image
         * resources, title, and description based on the properties of the `skinType` object.
         */
        public void bind(Schedule skinType) {
            iconView.setImageResource(skinType.getImageResId());
            backgroundIconView.setImageResource(skinType.getImageResId());
            titleText.setText(skinType.name());
            descriptionText.setText(skinType.getDescription());
        }
    }
}
