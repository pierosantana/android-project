package es.upgrade;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;



public class HourAdapter extends RecyclerView.Adapter<HourAdapter.HourViewHolder> {
    private final List<String> opcionesHorario;

    public HourAdapter(List<String> opcionesHorario) {
        this.opcionesHorario = opcionesHorario;
    }

    @NonNull
    @Override
    public HourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_hour_selection, parent, false);
        return new HourViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HourViewHolder holder, int position) {
        holder.txtHour.setText(opcionesHorario.get(position));
    }

    @Override
    public int getItemCount() {
        return opcionesHorario.size();
    }

    static class HourViewHolder extends RecyclerView.ViewHolder {
        TextView txtHour;

        public HourViewHolder(@NonNull View itemView) {
            super(itemView);
            txtHour = itemView.findViewById(R.id.tv_descripcion);
        }
    }
}
