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

    // The `OnProductClickListener` interface in the `ProductAdapter` class is defining a contract for
    // classes that want to listen for click events on products within the RecyclerView.
    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    public ProductAdapter(Context context, List<Product> productList, OnProductClickListener listener, Routine routine) {
        this.context = context;
        this.productList = productList;
        this.onProductClickListener = listener;
        this.routine = routine;
    }

    /**
     * The function onCreateViewHolder creates and returns a ProductViewHolder object for displaying
     * product items in a RecyclerView.
     *
     * @param parent   The `parent` parameter in the `onCreateViewHolder` method represents the ViewGroup
     *                 into which the new View will be added after it is bound to an adapter position. In the context of
     *                 a RecyclerView, the `parent` parameter typically refers to the RecyclerView itself or one of its
     *                 direct parent layouts.
     * @param viewType The `viewType` parameter in the `onCreateViewHolder` method is used to determine
     *                 the type of view that needs to be created based on the position in the RecyclerView. It can be
     *                 useful when dealing with multiple view types in a single RecyclerView.
     * @return A ProductViewHolder object is being returned.
     */
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    /**
     * The onBindViewHolder method in a RecyclerView adapter sets up the views for each product item,
     * including displaying product information, loading images with Glide, handling item selection,
     * and setting click listeners.
     *
     * @param holder   The `holder` parameter in the `onBindViewHolder` method refers to the ViewHolder
     *                 object that represents the views of an item in the RecyclerView. It holds references to the
     *                 views that need to be updated for a particular item at a given position in the list.
     * @param position The `position` parameter in the `onBindViewHolder` method represents the
     *                 position of the item within the RecyclerView. It is the position of the item in the data set
     *                 that the adapter is currently binding to the view holder.
     */
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Product product = productList.get(position);

        // We assign the name and price of the product
        holder.productName.setText(product.getBrand());
        holder.productPrice.setText(String.format("%s€", product.getPrice()));

        // Charge the image of the product with Glide
        Glide.with(context)
                .load(product.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.productImage);

        //  Check if the current product is selected
        holder.radioButton.setChecked(position == selectedPosition);

        //  Set the click listener for the item view
        holder.itemView.setOnClickListener(v -> {
            //  Check if the current product is selected
            if (selectedPosition != position) {
                int previousPosition = selectedPosition;
                selectedPosition = position;

                // Notify the adapter that the item has changed
                notifyItemChanged(previousPosition);
                notifyItemChanged(selectedPosition);

                // Notify the listener that the product has been clicked
                onProductClickListener.onProductClick(product);
                routine.addProduct(product);
            }
        });

        //  Set the click listener for the radio button
        holder.radioButton.setOnClickListener(v -> holder.itemView.performClick());
    }

    // The code snippet you provided is part of a Java class named `ProductAdapter` which extends
    // `RecyclerView.Adapter<ProductAdapter.ProductViewHolder>`. Here's a breakdown of the code:
    @Override
    public int getItemCount() {
        return productList.size(); // Return the number of products in the list
    }
        // The ProductViewHolder class is a subclass of RecyclerView.ViewHolder and is used to display
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