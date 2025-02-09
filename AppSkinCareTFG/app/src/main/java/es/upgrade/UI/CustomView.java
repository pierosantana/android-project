package es.upgrade.UI;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import es.upgrade.R;

public class CustomView extends LinearLayout {
    private ImageView buttonIcon;
    private TextView buttonText;
    private ImageButton btnCustom;  // ImageButton que vamos a hacer funcional

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.custom_button, this, true);

        buttonIcon = findViewById(R.id.buttonIcon);
        buttonText = findViewById(R.id.buttonText);
        btnCustom = findViewById(R.id.btnCustom);  // Referencia al ImageButton

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomButtonView);

            String text = typedArray.getString(R.styleable.CustomButtonView_buttonText);
            int iconResId = typedArray.getResourceId(R.styleable.CustomButtonView_buttonIcon, -1);

            if (text != null) {
                buttonText.setText(text);
            }
            if (iconResId != -1) {
                buttonIcon.setImageResource(iconResId);
            }

            typedArray.recycle();
        }
    }

    // Métodos públicos para cambiar texto e icono desde el código
    public void setButtonText(String text) {
        buttonText.setText(text);
    }

    public void setButtonIcon(int resId) {
        buttonIcon.setImageResource(resId);
    }

    public void setOnImageButtonClickListener(OnClickListener listener) {
        btnCustom.setOnClickListener(listener);  // Método para asignar la acción al ImageButton
    }
}
