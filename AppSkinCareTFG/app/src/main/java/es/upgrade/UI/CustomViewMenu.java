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

public class CustomViewMenu extends LinearLayout {
    private ImageView buttonIcon;
    private TextView buttonText;
    private ImageButton btnCustom;  // ImageButton que vamos a hacer funcional

    // The `CustomViewMenu` constructor is initializing a new instance of the `CustomViewMenu` class
    // with the provided `Context` and `AttributeSet` parameters. It calls the superclass constructor
    // `super(context, attrs)` to ensure that the parent class (`LinearLayout` in this case) is
    // properly initialized.
    public CustomViewMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * The `init` function inflates a custom button layout, retrieves attributes from XML, and sets the
     * button text and icon based on the attributes if present.
     * 
     * @param context The `context` parameter in the `init` method is typically the context in which
     * the view is created or used. It provides access to resources, system services, preferences, etc.
     * It's important for inflating layouts, accessing resources, and other context-related operations
     * within the custom view.
     * @param attrs The `attrs` parameter in the `init` method is used to retrieve the custom
     * attributes that are set in the XML layout file where the `CustomButtonView` is used. These
     * attributes are defined in a styleable resource array (in this case,
     * `R.styleable.CustomButtonView`) and can be
     */
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


    /**
     * The function `setButtonText` sets the text of a button to the specified text.
     * 
     * @param text The `text` parameter is a `String` type that represents the text that you want to
     * set on a button.
     */
    public void setButtonText(String text) {
        buttonText.setText(text);
    }

    /**
     * The function `setButtonIcon` sets the image resource of a button to the specified resource ID.
     * 
     * @param resId The `resId` parameter in the `setButtonIcon` method is an integer value that
     * represents the resource ID of the icon that you want to set for a button. This resource ID is
     * typically obtained from the `R.drawable` class in Android, which contains references to all
     * drawable resources in your
     */
    public void setButtonIcon(int resId) {
        buttonIcon.setImageResource(resId);
    }

    /**
     * The function sets a click listener for an ImageButton in Java.
     * 
     * @param listener The `listener` parameter in the `setOnImageButtonClickListener` method is an
     * `OnClickListener` object that defines the action to be taken when the ImageButton is clicked. It
     * typically contains the implementation of the `onClick` method, which specifies what should
     * happen when the ImageButton is clicked.
     */
    public void setOnImageButtonClickListener(OnClickListener listener) {
        btnCustom.setOnClickListener(listener);  // Método para asignar la acción al ImageButton
    }
}
