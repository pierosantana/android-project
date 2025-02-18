package es.upgrade.UI;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import es.upgrade.R;

public class FinalActivity extends AppCompatActivity {

   /**
    * The `onCreate` method in the Java code snippet enables edge-to-edge display and adjusts padding
    * based on system bars for the `activity_final` layout.
    * 
    * @param savedInstanceState The `savedInstanceState` parameter in the `onCreate` method of an
    * Android activity is a Bundle object that provides the activity with previously saved state
    * information, if available. This bundle contains key-value pairs that represent the state of the
    * activity when it was last stopped or destroyed. It can be used to
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_final);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_final), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}