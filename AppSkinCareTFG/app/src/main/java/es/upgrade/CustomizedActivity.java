package es.upgrade;

import android.annotation.SuppressLint;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomizedActivity extends AppCompatActivity {

    private ExpandableListView expandableListView;
    private List<String> listGroupTitles;
    private HashMap<String,List<String>> listChild;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customized);

        expandableListView = findViewById(R.id.lista_expandible);
        listGroupTitles = new ArrayList<>();
        listChild = new HashMap<>();
        listGroupTitles.add("Limpieza");
        listGroupTitles.add("Hidratacion");
        listGroupTitles.add("Tonico");
        listGroupTitles.add("Tratamiento");
        listGroupTitles.add("Proteccion solar");

        List<String> limpieza = new ArrayList<>();
        limpieza.add("Producto 1");
        limpieza.add("Producto 2");
        limpieza.add("Producto 3");

        List<String> hidratacion = new ArrayList<>();
        hidratacion.add("Producto A");
        hidratacion.add("Producto B");
        hidratacion.add("Producto C");

        List<String> tonico = new ArrayList<>();
        tonico.add("Producto A");
        tonico.add("Producto B");
        tonico.add("Producto C");

        List<String> tratamiento = new ArrayList<>();
        tratamiento.add("Producto A");
        tratamiento.add("Producto B");
        tratamiento.add("Producto C");

        List<String> proteccionSolar = new ArrayList<>();
        proteccionSolar.add("Producto A");
        proteccionSolar.add("Producto B");
        proteccionSolar.add("Producto C");

        listChild.put(listGroupTitles.get(0), limpieza);
        listChild.put(listGroupTitles.get(1), hidratacion);
        listChild.put(listGroupTitles.get(2), tonico);
        listChild.put(listGroupTitles.get(3), tratamiento);
        listChild.put(listGroupTitles.get(4), proteccionSolar);

        ExpandableListAdapter adapter1 = new ExpandableListAdapter() {
            @Override
            public void registerDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public int getGroupCount() {
                return listGroupTitles.size();

            }

            @Override
            public int getChildrenCount(int groupPosition) {
                return listChild.get(listGroupTitles.get(groupPosition)).size();

            }

            @Override
            public Object getGroup(int groupPosition) {
                return listGroupTitles.get(groupPosition);



            }

            @Override
            public Object getChild(int groupPosition, int childPosition) {
                return listChild.get(listGroupTitles.get(groupPosition)).get(childPosition);
            }

            @Override
            public long getGroupId(int groupPosition) {
                return groupPosition;
            }

            @Override
            public long getChildId(int groupPosition, int childPosition) {
                return childPosition;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
                String groupTitle = (String) getGroup(groupPosition);
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext())
                            .inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
                }
                TextView textView = convertView.findViewById(android.R.id.text1);
                textView.setText(groupTitle);
                return convertView;
            }

            @Override
            public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
                String childText = (String) getChild(groupPosition, childPosition);
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext())
                            .inflate(android.R.layout.simple_list_item_1, parent, false);
                }
                TextView textView = convertView.findViewById(android.R.id.text1);
                textView.setText(childText);
                return convertView;
            }

            @Override
            public boolean isChildSelectable(int groupPosition, int childPosition) {
                return true;
            }

            @Override
            public boolean areAllItemsEnabled() {
                return false;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public void onGroupExpanded(int groupPosition) {

            }

            @Override
            public void onGroupCollapsed(int groupPosition) {

            }

            @Override
            public long getCombinedChildId(long groupId, long childId) {
                return 0;
            }

            @Override
            public long getCombinedGroupId(long groupId) {
                return 0;
            }
        };
        expandableListView.setAdapter(adapter1);
        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            String grupo = listGroupTitles.get(groupPosition);
            String opcionSeleccionada = listChild.get(grupo).get(childPosition);
            Toast.makeText(this, "Seleccionaste: " + opcionSeleccionada + " en " + grupo, Toast.LENGTH_SHORT).show();
            return true;
        });



    }
}