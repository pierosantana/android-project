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

/**
 * The `SkinTypeAdapter` class is a RecyclerView adapter that displays skin types with corresponding
 * icons, titles, and descriptions.
 */
public class SkinTypeAdapter extends RecyclerView.Adapter<SkinTypeAdapter.SkinTypeViewHolder> {
    private final SkinType[] skinTypes = SkinType.values();

    /**
     * The function onCreateViewHolder creates and returns a SkinTypeViewHolder object based on the
     * specified layout.
     * 
     * @param parent The `parent` parameter in the `onCreateViewHolder` method of a RecyclerView
     * adapter represents the ViewGroup into which the created View will be placed after it is bound to
     * the data. It is typically the RecyclerView itself.
     * @param viewType The `viewType` parameter in the `onCreateViewHolder` method of a RecyclerView
     * adapter is used to determine the type of view to be created based on the view type provided.
     * This can be useful when dealing with multiple view types in a RecyclerView. The `viewType`
     * parameter is typically used when
     * @return A new instance of the `SkinTypeViewHolder` class is being returned.
     */
    @NonNull
    @Override
    public SkinTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_skin_type, parent, false);
        return new SkinTypeViewHolder(view);
    }

    /**
     * The onBindViewHolder method binds a SkinType object to a SkinTypeViewHolder at a specific
     * position.
     * 
     * @param holder The `holder` parameter in the `onBindViewHolder` method is an instance of the
     * `SkinTypeViewHolder` class. It is responsible for holding the views that represent an item in
     * the RecyclerView. In this method, you typically bind data to the views held by the `holder`
     * based on the
     * @param position The `position` parameter in the `onBindViewHolder` method represents the
     * position of the item within the RecyclerView. It indicates the position of the item within the
     * adapter's data set that needs to be bound to the view holder. This parameter is typically used
     * to retrieve the corresponding data object from the data
     */
    @Override
    public void onBindViewHolder(@NonNull SkinTypeViewHolder holder, int position) {
        SkinType skinType = skinTypes[position];
        holder.bind(skinType);
    }

    /**
     * The function returns the number of elements in the array "skinTypes".
     * 
     * @return The method `getItemCount()` is returning the length of the `skinTypes` array.
     */
    @Override
    public int getItemCount() {
        return skinTypes.length;
    }
    /**
     * The `SkinTypeViewHolder` class is a ViewHolder implementation for a RecyclerView that binds data
     * of type `SkinType` to its views.
     */
    static class SkinTypeViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iconView;
        private final ImageView backgroundIconView;
        private final TextView titleText;
        private final TextView descriptionText;

        // This code snippet is defining a constructor for the `SkinTypeViewHolder` class. When a new
        // instance of `SkinTypeViewHolder` is created, this constructor is called with a `View`
        // parameter `itemView`.
        public SkinTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            iconView = itemView.findViewById(R.id.iconView);
            backgroundIconView = itemView.findViewById(R.id.backgroundIconView);
            titleText = itemView.findViewById(R.id.titleText);
            descriptionText = itemView.findViewById(R.id.descriptionText);
        }

       /**
        * The `bind` function sets the image resources, title, and description based on the provided
        * `SkinType`.
        * 
        * @param skinType The `skinType` parameter is an enum type that represents different types of
        * skins. It contains information such as the image resource ID for the icon and background, the
        * name of the skin type, and a description of the skin type. The `bind` method is responsible
        * for setting these values to the
        */
        public void bind(SkinType skinType) {
            iconView.setImageResource(skinType.getImageResId());
            backgroundIconView.setImageResource(skinType.getImageResId());
            titleText.setText(skinType.name());
            descriptionText.setText(skinType.getDescription());
        }
    }
}
