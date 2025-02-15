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


public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {
    private final Schedule[] schedules = Schedule.values();

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_schedule_type, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        Schedule schedule = schedules[position];
        holder.bind(schedule);
    }

    @Override
    public int getItemCount() {
        return schedules.length;
    }
    static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iconView;
        private final ImageView backgroundIconView;
        private final TextView titleText;
        private final TextView descriptionText;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            iconView = itemView.findViewById(R.id.iconView);
            backgroundIconView = itemView.findViewById(R.id.backgroundIconView);
            titleText = itemView.findViewById(R.id.titleText);
            descriptionText = itemView.findViewById(R.id.descriptionText);
        }

        public void bind(Schedule skinType) {
            iconView.setImageResource(skinType.getImageResId());
            backgroundIconView.setImageResource(skinType.getImageResId());
            titleText.setText(skinType.name());
            descriptionText.setText(skinType.getDescription());
        }
    }
}
