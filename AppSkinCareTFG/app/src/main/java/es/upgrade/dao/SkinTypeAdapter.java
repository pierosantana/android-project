package es.upgrade.dao;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import es.upgrade.R;
import es.upgrade.entidad.SkinType;

public class SkinTypeAdapter extends RecyclerView.Adapter<SkinTypeAdapter.SkinTypeViewHolder> {
    private final SkinType[] skinTypes = SkinType.values();

    @NonNull
    @Override
    public SkinTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_skin_type, parent, false);
        return new SkinTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SkinTypeViewHolder holder, int position) {
        SkinType skinType = skinTypes[position];
        holder.bind(skinType);
    }

    @Override
    public int getItemCount() {
        return skinTypes.length;
    }
    static class SkinTypeViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iconView;
        private final ImageView backgroundIconView;
        private final TextView titleText;
        private final TextView descriptionText;

        public SkinTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            iconView = itemView.findViewById(R.id.iconView);
            backgroundIconView = itemView.findViewById(R.id.backgroundIconView);
            titleText = itemView.findViewById(R.id.titleText);
            descriptionText = itemView.findViewById(R.id.descriptionText);
        }

        public void bind(SkinType skinType) {
            iconView.setImageResource(skinType.getImageResId());
            backgroundIconView.setImageResource(skinType.getImageResId());
            titleText.setText(skinType.name());
            descriptionText.setText(skinType.getDescription());
        }
    }
}
