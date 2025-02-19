package es.upgrade.UI.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import es.upgrade.R;
import es.upgrade.dao.api.ProductAdapter;
import es.upgrade.entidad.Product;
import es.upgrade.entidad.Routine;
import es.upgrade.entidad.User;


public class ProductsFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product>listaProductos;
    private User user;
    private TextView textView;


    public ProductsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewProductos);

        // Configuramos el Layout Manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        user = User.getInstance();
        textView = view.findViewById(R.id.textProducts);
        listaProductos = obtenerTodosLosProductos(user);

        // Si no hay productos, mostramos un mensaje
        if(listaProductos.isEmpty()){
            textView.setVisibility(View.VISIBLE);
            Log.d("Error", "No se cargan las imagenes de los productos");
            recyclerView.setVisibility(View.GONE);
        }else{
            textView.setVisibility(View.GONE);
            Log.d("Exito", "Si se cargan las imagenes de los productos");
            recyclerView.setVisibility(View.VISIBLE);
        }
        productAdapter = new ProductAdapter(listaProductos, getContext());

        recyclerView.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();



        return view;
    }
    // Metodo para obtener todos los productos de las diferentes rutinas que tiene el usuario
    private List<Product>obtenerTodosLosProductos(User user){

        // Vamos a usar un Set(es una coleccion de valores, pero la diferencia esque no
        // pueden duplicarse elementos, lo cual para mostrar la lista de productos de las
        // diferentes rutinas viene bien porque algunos productos podrian repetirse
        // y no lo veo necesario
        Set<Product> listaProductosUnicos = new HashSet<>();
        if (user.getRoutineList() == null) {
            Log.e("ProductsFragment", "El usuario no tiene rutinas.");

        }

        if(user != null && user.getRoutineList() != null){
            // Recorremos todas las rutinas del usuario y extraemos todos los productos
            for(Routine routine : user.getRoutineList()){
                if(routine != null && routine.getProductList() != null){
                    listaProductosUnicos.addAll(routine.getProductList());
                }

            }

        }


        return new ArrayList<>(listaProductosUnicos);
    }


}
