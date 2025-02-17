package es.upgrade.UI.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import es.upgrade.R;

public class CalendarFragment extends Fragment {

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla el layout del fragmento
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        // Mostrar "Hola Mundo" en un TextView
        TextView textView = view.findViewById(R.id.textView);
        textView.setText("Hola Mundo");

        return view;
    }}
