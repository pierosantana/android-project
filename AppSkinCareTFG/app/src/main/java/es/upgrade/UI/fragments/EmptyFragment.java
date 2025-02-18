package es.upgrade.UI.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.upgrade.R;

public class EmptyFragment extends Fragment {
    /**
     * The `onCreateView` function inflates a layout file to create the fragment's view.
     * 
     * @param inflater The `inflater` parameter in the `onCreateView` method is used to inflate a
     * layout resource file into a View object that represents the layout of the fragment. This
     * inflater is responsible for instantiating the layout XML file into its corresponding View
     * objects.
     * @param container The `container` parameter in the `onCreateView` method refers to the parent
     * view that the fragment's UI should be attached to. It is the ViewGroup in which the fragment's
     * layout will be placed. This parameter is typically used when inflating the layout for the
     * fragment to determine the layout's
     * @param savedInstanceState The `savedInstanceState` parameter in the `onCreateView` method is a
     * Bundle object that provides data about the previous state of the fragment. It allows you to
     * restore the fragment to its previous state if needed, such as after a configuration change like
     * screen rotation. You can use this parameter to retrieve
     * @return The method `onCreateView` is returning a View object that is inflated from the layout
     * file `fragment_empty`.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Use a layout file to inflate the fragment
        return inflater.inflate(R.layout.fragment_empty, container, false);  
    }
}