package es.upgrade.UI.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import es.upgrade.R;
import es.upgrade.dao.SkinTypeAdapter;


public class SkinDescriptionFragment extends Fragment {
    private ViewPager2 viewPager;
    private SkinTypeAdapter adapter;
    private WormDotsIndicator dotsIndicator;
    private Button btnBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skin_type, container, false);

        viewPager = view.findViewById(R.id.viewPager);
        dotsIndicator = view.findViewById(R.id.dotsIndicator);
        btnBack = view.findViewById(R.id.btnBack);

        // Configurar el adaptador y el ViewPager
        adapter = new SkinTypeAdapter();
        viewPager.setAdapter(adapter);

        // Conectar el indicador de página
        dotsIndicator.attachTo(viewPager);

        // Botón para cerrar el fragmento y volver
        btnBack.setOnClickListener(v -> {
            // Restaurar la visibilidad de los elementos
            requireActivity().findViewById(R.id.tv_configuracion).setVisibility(View.VISIBLE);
            requireActivity().findViewById(R.id.btn_next).setVisibility(View.VISIBLE);
            requireActivity().findViewById(R.id.NoIdeaSkin).setVisibility(View.VISIBLE);
            requireActivity().findViewById(R.id.include_progress).setVisibility(View.VISIBLE);
            requireActivity().findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);

            // Ocultar el fragmento de descripción
            requireActivity().findViewById(R.id.skin_description_fragment_container).setVisibility(View.GONE);
        });

        return view;
    }
}
