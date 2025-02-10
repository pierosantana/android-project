package es.upgrade.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import es.upgrade.R;

public class CustomViewOptionsRoutine extends LinearLayout {
    private ImageView optionIcon;
    private TextView optionText,optionDescription;

    public CustomViewOptionsRoutine(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.custom_view_option_routine, this, true);

        optionIcon = findViewById(R.id.optionIcon);
        optionText = findViewById(R.id.optionText);
        optionDescription = findViewById(R.id.optionDescription);
    }

    // Método para cambiar dinámicamente el texto y el icono
    public void setOptionContent(String text, int iconResId) {
        optionText.setText(text);
        optionIcon.setImageResource(iconResId);
    }
    // Método para establecer la respuesta del usuario
    public void setAnswerText(String description) {
        optionDescription.setText(description);  // Establecer la respuesta seleccionada por el usuario
    }
}

