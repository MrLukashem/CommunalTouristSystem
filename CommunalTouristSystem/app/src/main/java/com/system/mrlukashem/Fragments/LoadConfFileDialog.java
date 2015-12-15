package com.system.mrlukashem.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.system.mrlukashem.Interfaces.LoadConfFileCallback;
import com.system.mrlukashem.communaltouristsystem.R;

import java.io.File;

/**
 * Created by mrlukashem on 06.12.15.
 */
public class LoadConfFileDialog extends DialogFragment {


    private final String DIALOG_MSG = "Proszę podać ścieżkę do pliku konfiguracyjnego, który"
            + "powinien być umieszczony na karcie pamięci urządzenia np. xml_files/content.xml";

    private final String DIALOG_TITLE = "Komunikat";

    private static final String ERROR_MSG = "Taki plik nie istnieje!";

    private final String EMPTY_STRING = "";

    public static LoadConfFileDialog newInstance() {
        return new LoadConfFileDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LoadConfFileCallback cb = (LoadConfFileCallback)getActivity();

        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder
                .setTitle(DIALOG_TITLE)
                .setPositiveButton(R.string.ok, null)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setMessage(DIALOG_MSG);

        final AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface d) {
                Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String filePath = Environment.getExternalStorageDirectory().getPath()
                                + "/" + input.getText().toString();
                        File file = new File(filePath);
                        if(!input.getText().toString().equals(EMPTY_STRING) && file.exists()) {
                            cb.load(filePath);
                            dialog.dismiss();
                        } else {
                            input.setError(ERROR_MSG);
                        }
                    }
                });
            }
        });

        return dialog;
    }
}
