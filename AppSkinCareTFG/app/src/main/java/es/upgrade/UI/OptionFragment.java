package es.upgrade.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import es.upgrade.R;
import es.upgrade.entidad.SkinType;

public class OptionFragment extends Fragment {
    private static final String ARG_IMAGEN = "imagen";
    private static final String ARG_DESC = "descripcion";

    /**
     * The `newInstance` function creates a new instance of `OptionFragment` with arguments based on
     * the provided `SkinType`.
     * 
     * @param tipoPiel `tipoPiel` is an enum type variable representing the type of skin.
     * @return An instance of the OptionFragment class with arguments set based on the SkinType
     * provided.
     */
    public static OptionFragment newInstance(SkinType tipoPiel) {
        OptionFragment fragment = new OptionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_IMAGEN, tipoPiel.getImageResId());
        args.putString(ARG_DESC, tipoPiel.getDescription());
        fragment.setArguments(args);
        return fragment;
    }

   /**
    * The onCreateView function inflates the layout for the OptionFragment.
    * 
    * @param inflater The `inflater` parameter in the `onCreateView` method is used to inflate a layout
    * resource file into a View object that represents the layout. It is responsible for converting the
    * XML layout file into corresponding View objects that can be displayed on the screen.
    * @param container The `container` parameter in the `onCreateView` method represents the parent
    * view that the fragment's UI will be attached to. It is the ViewGroup in which the fragment's
    * layout will be inflated and displayed. If the fragment is not being attached to any parent view,
    * this parameter will be null
    * @param savedInstanceState The `savedInstanceState` parameter in the `onCreateView` method is a
    * Bundle object that provides data about the previous state of the fragment. It allows you to
    * restore the fragment to its previous state if needed. You can use this parameter to retrieve any
    * previously saved data or configuration information.
    * @return A View object is being returned.
    */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_option_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textView = view.findViewById(R.id.textView);

        imageView.setImageResource(getArguments().getInt(ARG_IMAGEN));
        textView.setText(getArguments().getString(ARG_DESC));
    }
}