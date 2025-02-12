package es.upgrade.dao.api;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import es.upgrade.R;
import es.upgrade.entidad.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private final Context context;
    private List<Product> productList;
    private int selectedPosition = -1;  // Almacena la posición del producto seleccionado

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.productName.setText(product.getBrand());
        holder.productPrice.setText("$" + product.getPrice());

        // Cargar imagen con Glide, incluyendo un placeholder y una imagen de error
        Glide.with(context)
                .load(product.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.productImage);

        // Actualizar el estado del CheckBox según la posición seleccionada
        holder.checkBox.setChecked(position == selectedPosition);

        // Manejar clics en el CheckBox
        holder.checkBox.setOnClickListener(v -> {
            int currentPosition = holder.getBindingAdapterPosition();
            if (selectedPosition != currentPosition) {
                notifyItemChanged(selectedPosition);  // Desmarcar el producto anterior
                selectedPosition = currentPosition;   // Actualizar la posición seleccionada
                notifyItemChanged(selectedPosition);  // Marcar el nuevo producto
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    // Método para actualizar los datos del adaptador
    public void updateData(List<Product> newProducts) {
        this.productList = newProducts;
        notifyDataSetChanged();  // Notificar al adaptador que los datos han cambiado
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice;
        ImageView productImage;
        CheckBox checkBox;

        public ProductViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productImage = itemView.findViewById(R.id.productImage);
            checkBox = itemView.findViewById(R.id.checkProducto);
        }
    }
}
