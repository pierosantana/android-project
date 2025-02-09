package es.upgrade.UI.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import es.upgrade.R;

public class QuestionnaireFragment extends Fragment {

    private LinearLayout[] optionsLayouts;
    private RadioButton[] radioButtons;
    private ImageView[] icons;
    private RadioGroup radioGroup;
    private OnQuestionnaireCompletedListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_questionnaire, container, false);

        radioGroup = view.findViewById(R.id.radioGroup);

        // Referencias a los layouts y radio buttons
        optionsLayouts = new LinearLayout[3];
        radioButtons = new RadioButton[3];
        icons = new ImageView[3];

        optionsLayouts[0] = view.findViewById(R.id.rd1);
        optionsLayouts[1] = view.findViewById(R.id.rd2);
        optionsLayouts[2] = view.findViewById(R.id.rd3);

        radioButtons[0] = view.findViewById(R.id.radio_option1);
        radioButtons[1] = view.findViewById(R.id.radio_option2);
        radioButtons[2] = view.findViewById(R.id.radio_option3);

        icons[0] = view.findViewById(R.id.radio_icon1);
        icons[1] = view.findViewById(R.id.radio_icon2);
        icons[2] = view.findViewById(R.id.radio_icon3);

        // Obtén el número de opciones a mostrar (por si lo pasas desde la actividad)
        int numOptions = 3; // Valor por defecto
        if (getArguments() != null) {
            numOptions = getArguments().getInt("num_options", 3);
            String[] optionTexts = getArguments().getStringArray("options_texts");

            // Asignar los textos a los RadioButtons
            for (int i = 0; i < numOptions; i++) {
                if (optionTexts != null && i < optionTexts.length) {
                    radioButtons[i].setText(optionTexts[i]);
                }
            }
        }

        // Ocultar las opciones que no sean necesarias
        for (int i = 0; i < optionsLayouts.length; i++) {
            if (i < numOptions) {
                optionsLayouts[i].setVisibility(View.VISIBLE);
            } else {
                optionsLayouts[i].setVisibility(View.GONE);
            }
        }

        // Escuchar cambios en la selección
        for (int i = 0; i < numOptions; i++) {
            int finalI = i;
            optionsLayouts[i].setOnClickListener(v -> updateSelection(finalI));
        }

        return view;
    }

    // Método para actualizar la selección de radio buttons
    private void updateSelection(int selectedOption) {
        for (int i = 0; i < optionsLayouts.length; i++) {
            if (i == selectedOption) {
                optionsLayouts[i].setSelected(true);
                icons[i].setImageResource(R.drawable.radio_button_circle_selected);
                radioButtons[i].setTextColor(ContextCompat.getColor(requireContext(), R.color.text_selected)); // Turquesa
                radioButtons[i].setChecked(true);
            } else {
                optionsLayouts[i].setSelected(false);
                icons[i].setImageResource(R.drawable.radio_button_circle_unselected);
                radioButtons[i].setTextColor(ContextCompat.getColor(requireContext(), R.color.text_unselected)); // Gris claro
                radioButtons[i].setChecked(false);
            }
        }
        // Notificar a la actividad que se completó el cuestionario
        if (listener != null) {
            listener.onQuestionnaireCompleted(selectedOption);
        }
    }

    // Interfaz para comunicar la selección a la actividad
    public interface OnQuestionnaireCompletedListener {
        void onQuestionnaireCompleted(int selectedOption);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnQuestionnaireCompletedListener) {
            listener = (OnQuestionnaireCompletedListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement OnQuestionnaireCompletedListener");
        }
    }
}
