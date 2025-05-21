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

    /**
     * The `onCreateView` function inflates a layout for a questionnaire fragment, sets up radio
     * buttons based on provided options, and handles selection changes.
     * 
     * @param inflater The `LayoutInflater` parameter in the `onCreateView` method is used to inflate a
     * layout XML file into a corresponding View object that can be displayed on the screen. It takes
     * the XML file as input and builds the View hierarchy based on that XML structure.
     * @param container In the `onCreateView` method of a Fragment, the `container` parameter refers to
     * the parent ViewGroup that the fragment's UI will be attached to. This ViewGroup is provided by
     * the system when inflating the layout for the fragment.
     * @param savedInstanceState The `savedInstanceState` parameter in the `onCreateView` method is a
     * Bundle object that provides data about the previous state of the fragment. It allows you to
     * restore the fragment to its previous state if needed, such as after a configuration change like
     * screen rotation.
     * @return In the `onCreateView` method, a `View` object named `view` is being returned. This
     * `view` is inflated from the layout resource `R.layout.fragment_questionnaire` and contains
     * various UI elements such as `radioGroup`, `optionsLayouts`, `radioButtons`, and `icons`. The
     * method sets up the UI components based on the provided arguments and then returns the final
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_questionnaire, container, false);

        radioGroup = view.findViewById(R.id.radioGroup);

        // Reference to the layout elements
        optionsLayouts = new LinearLayout[4];
        radioButtons = new RadioButton[4];
        icons = new ImageView[4];

        optionsLayouts[0] = view.findViewById(R.id.rd1);
        optionsLayouts[1] = view.findViewById(R.id.rd2);
        optionsLayouts[2] = view.findViewById(R.id.rd3);
        optionsLayouts[3] = view.findViewById(R.id.rd4);

        radioButtons[0] = view.findViewById(R.id.radio_option1);
        radioButtons[1] = view.findViewById(R.id.radio_option2);
        radioButtons[2] = view.findViewById(R.id.radio_option3);
        radioButtons[3] = view.findViewById(R.id.radio_option4);

        icons[0] = view.findViewById(R.id.radio_icon1);
        icons[1] = view.findViewById(R.id.radio_icon2);
        icons[2] = view.findViewById(R.id.radio_icon3);
        icons[3] = view.findViewById(R.id.radio_icon4);

        
        int numOptions = 4;
        if (getArguments() != null) {
            numOptions = getArguments().getInt("num_options", 4);
            String[] optionTexts = getArguments().getStringArray("options_texts");

            // Set the text for each option
            for (int i = 0; i < numOptions; i++) {
                if (optionTexts != null && i < optionTexts.length) {
                    radioButtons[i].setText(optionTexts[i]);
                }
            }
        }

        // Set the visibility of the options based on the number of options
        for (int i = 0; i < optionsLayouts.length; i++) {
            if (i < numOptions) {
                optionsLayouts[i].setVisibility(View.VISIBLE);
            } else {
                optionsLayouts[i].setVisibility(View.GONE);
            }
        }

        // Set the initial selection
        for (int i = 0; i < numOptions; i++) {
            int finalI = i;
            optionsLayouts[i].setOnClickListener(v -> updateSelection(finalI));
        }

        return view;
    }

    
   /**
    * The `updateSelection` function updates the visual appearance of a set of options based on the
    * selected option and notifies a listener when a questionnaire is completed.
    * 
    * @param selectedOption The `updateSelection` method takes an integer parameter `selectedOption`
    * which represents the index of the option that should be selected in a list of options. The method
    * iterates through all the options and updates their visual representation based on whether they
    * are the selected option or not.
    */
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
        
        if (listener != null) {
            listener.onQuestionnaireCompleted(selectedOption);
        }
    }

    
    // The `public interface OnQuestionnaireCompletedListener` defines a listener interface within the
    // `QuestionnaireFragment` class. This interface specifies a method `onQuestionnaireCompleted(int
    // selectedOption)` that must be implemented by any class that wants to listen for questionnaire
    // completion events.
    public interface OnQuestionnaireCompletedListener {
        void onQuestionnaireCompleted(int selectedOption);
    }

   /**
    * The `onAttach` function in Java checks if the context implements a specific listener interface
    * and assigns it to a variable if true.
    * 
    * @param context The `context` parameter in the `onAttach` method represents the context in which
    * the fragment is attached. It provides access to information about the application environment and
    * allows the fragment to interact with the rest of the application.
    */
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
