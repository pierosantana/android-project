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

   // The `CustomViewOptionsRoutine` constructor `public CustomViewOptionsRoutine(Context context,
   // AttributeSet attrs)` is initializing the custom view by calling the superclass constructor
   // `super(context, attrs)` and then calling the `init(context)` method to set up the view by
   // inflating the layout and initializing the view components like `optionIcon`, `optionText`, and
   // `optionDescription`.
    public CustomViewOptionsRoutine(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * The `init` function inflates a custom view layout and initializes its components.
     * 
     * @param context The `context` parameter in the `init` method is typically the context in which
     * the view is being created or used. It provides access to resources, system services,
     * preferences, etc., and is necessary for inflating the layout and finding view elements within
     * that layout.
     */
    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.custom_view_option_routine, this, true);

        optionIcon = findViewById(R.id.optionIcon);
        optionText = findViewById(R.id.optionText);
        optionDescription = findViewById(R.id.optionDescription);
    }

    
   /**
    * The setOptionContent function sets the text and icon of an option.
    * 
    * @param text The `text` parameter is a string that represents the content or text that will be set
    * in a particular view or component. In the provided code snippet, the `text` parameter is used to
    * set the text content of a view named `optionText`.
    * @param iconResId The `iconResId` parameter is an integer value that represents the resource ID of
    * an icon image that you want to set for a particular option. This resource ID is typically used to
    * retrieve the icon image from the app's resources and display it in the user interface.
    */
    public void setOptionContent(String text, int iconResId) {
        optionText.setText(text);
        optionIcon.setImageResource(iconResId);
    }
    
    /**
     * The function sets the text of a UI element to the provided description.
     * 
     * @param description The `description` parameter in the `setAnswerText` method is a string that
     * represents the text or description of the answer selected by the user. This method is used to
     * set and display this answer text in a user interface component, such as a text view or label.
     */
    public void setAnswerText(String description) {
        optionDescription.setText(description);  
    }
}

