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

    public static OptionFragment newInstance(SkinType tipoPiel) {
        OptionFragment fragment = new OptionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_IMAGEN, tipoPiel.getImageResId());
        args.putString(ARG_DESC, tipoPiel.getDescription());
        fragment.setArguments(args);
        return fragment;
    }

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