package es.upgrade.UI.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.upgrade.R;

public class EmptyFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Usamos un layout vacío, o simplemente un layout básico para mostrar el Lobby
        return inflater.inflate(R.layout.fragment_empty, container, false);  // Asegúrate de tener este layout creado
    }
}