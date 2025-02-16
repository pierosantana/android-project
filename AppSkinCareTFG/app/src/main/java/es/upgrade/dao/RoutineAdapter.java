package es.upgrade.dao;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import es.upgrade.R;
import es.upgrade.entidad.Routine;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.ViewHolder> {

    private List<Routine> routines;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Routine routine);
    }

    public RoutineAdapter(List<Routine> routines, OnItemClickListener listener) {
        this.routines = routines;
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_routine, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Routine routine = routines.get(position);
        holder.txtRutinaNombre.setText("Rutina " + (position + 1));
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(routine));
    }

    @Override
    public int getItemCount() {
        return routines.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtRutinaNombre;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtRutinaNombre = itemView.findViewById(R.id.txtRutinaNombre);
        }
    }
}
