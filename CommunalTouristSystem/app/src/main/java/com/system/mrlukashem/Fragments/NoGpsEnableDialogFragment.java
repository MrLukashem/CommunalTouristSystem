package com.system.mrlukashem.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import com.system.mrlukashem.communaltouristsystem.R;

/**
 * Created by mrlukashem on 05.12.15.
 */
public class NoGpsEnableDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle __saved_instance_state) {
        AlertDialog.Builder _builder = new AlertDialog.Builder(getActivity());
        _builder.setTitle(getResources().getString(R.string.NoGpsTitle))
                .setMessage(getResources().getString(R.string.NoGpsMsg))
                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface __dialog, int __which) {
                        Intent _intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        getActivity().startActivity(_intent);
                        dismiss();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface __dialog, int __which) {
                        dismiss();
                    }
                });

        return _builder.create();
    }
}
