package com.system.mrlukashem.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.system.mrlukashem.communaltouristsystem.R;

/**
 * Created by mrlukashem on 05.12.15.
 */
public class NoConfFileLoadedDialog extends DialogFragment {

    private final String DIALOG_MSG = "Plik konfiguracyjny nie został wczytany!";

    private final String DIALOG_TITLE = "Komunikat";

    public static NoConfFileLoadedDialog newInstance() {
        NoConfFileLoadedDialog dialog = new NoConfFileLoadedDialog();

        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder
                .setTitle(DIALOG_TITLE)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setMessage(DIALOG_MSG);

        return builder.create();
    }
}
