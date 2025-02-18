package es.upgrade.dao.api;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import es.upgrade.R;
import es.upgrade.entidad.Product;
import es.upgrade.entidad.Routine;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private Context context;
    private OnProductClickListener onProductClickListener;
    private int selectedPosition = -1;
    private Routine routine;// No producto seleccionado al inicio

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    public ProductAdapter(Context context, List<Product> productList, OnProductClickListener listener,Routine routine) {
        this.context = context;
        this.productList = productList;
        this.onProductClickListener = listener;
        this.routine = routine;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Product product = productList.get(position);

        // Asignamos el nombre y el precio del producto
        holder.productName.setText(product.getBrand());
        holder.productPrice.setText(String.format("%s€", product.getPrice()));

        // Cargar imagen con Glide
        Glide.with(context)
                .load(product.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.productImage);

        // Marcar el RadioButton si este producto está seleccionado
        holder.radioButton.setChecked(position == selectedPosition);

        // Manejar clic en el producto
        holder.itemView.setOnClickListener(v -> {
            // Verificamos si el producto clickeado es diferente al seleccionado
            if (selectedPosition != position) {
                int previousPosition = selectedPosition;
                selectedPosition = position;

                // Notificar solo los cambios de los elementos afectados
                notifyItemChanged(previousPosition);
                notifyItemChanged(selectedPosition);

                // Llamamos al listener con el producto seleccionado
                onProductClickListener.onProductClick(product);
                routine.addProduct(product);  // Añadimos el producto seleccionado a la lista de la rutina
            }
        });

        // Asegurar que el RadioButton también maneje el evento de clic
        holder.radioButton.setOnClickListener(v -> holder.itemView.performClick());
    }

    @Override
    public int getItemCount() {
        return productList.size(); // Devuelve el tamaño de la lista de productos
    }

    // ViewHolder para manejar la vista de cada producto
    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice;
        ImageView productImage;
        RadioButton radioButton;

        public ProductViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productImage = itemView.findViewById(R.id.productImage);
            radioButton = itemView.findViewById(R.id.radioProducto);
        }
    }
}
