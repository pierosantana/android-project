package es.upgrade;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import es.upgrade.entidad.SkinType;

public class SkinAdapter extends RecyclerView.Adapter<SkinAdapter.SkinViewHolder> {
    private final SkinType[] skinTypes;

    // Constructor
    public SkinAdapter(SkinType[] skinTypes) {
        this.skinTypes = skinTypes;
    }

    // Método para crear una nueva vista de ítem
    @NonNull
    @Override
    public SkinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_skin_description, parent, false);
        return new SkinViewHolder(view);
    }

    // Método para llenar la vista con datos del enum
    @Override
    public void onBindViewHolder(@NonNull SkinViewHolder holder, int position) {
        SkinType skin = SkinType.values()[position]; // Obtenemos el tipo de piel correspondiente al índice

        holder.imageView.setImageResource(skin.getImageResId()); // Asignamos la imagen
        holder.textView.setText(skin.getDescription()); // Asignamos la descripción
    }

    // Método para contar cuántos tipos de piel tenemos en el enum
    @Override
    public int getItemCount() {
        return SkinType.values().length; // La cantidad de ítems es igual al tamaño del enum
    }

    // ViewHolder para cada ítem
    public static class SkinViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public SkinViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_foto);  // Referencia a la imagen
            textView = itemView.findViewById(R.id.tv_descripcion);  // Referencia al texto
        }
    }
}
