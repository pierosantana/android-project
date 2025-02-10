package es.upgrade.UI;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import es.upgrade.R;

public class AlertDialogCustom {
    public static void showCustomAlertDialog(
            Context context,
            String title,
            String message,
            String positiveButtonText,
            DialogInterface.OnClickListener positiveAction,
            String negativeButtonText,
            DialogInterface.OnClickListener negativeAction
    ) {
        // Inflar el layout personalizado
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.custom_alert_dialog, null);

        // Referenciar los elementos del layout
        TextView tvTitle = dialogView.findViewById(R.id.tv_title);
        TextView tvMessage = dialogView.findViewById(R.id.tv_message);
        Button btnPositive = dialogView.findViewById(R.id.btn_positive);
        Button btnNegative = dialogView.findViewById(R.id.btn_negative);

        // Asignar textos
        tvTitle.setText(title);
        tvMessage.setText(message);
        btnPositive.setText(positiveButtonText);
        btnNegative.setText(negativeButtonText);

        // Crear el diálogo
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(dialogView)
                .create();

        // Asignar acciones a los botones
        btnPositive.setOnClickListener(v -> {
            positiveAction.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
            dialog.dismiss();
        });

        btnNegative.setOnClickListener(v -> {
            negativeAction.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
            dialog.dismiss();
        });

        dialog.show();
    }
}