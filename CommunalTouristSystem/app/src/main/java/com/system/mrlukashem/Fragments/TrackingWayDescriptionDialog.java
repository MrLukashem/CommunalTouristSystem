package com.system.mrlukashem.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.system.mrlukashem.Interfaces.StartWayTracingCallback;
import com.system.mrlukashem.communaltouristsystem.R;
import com.system.mrlukashem.refbases.PlaceRefBase;
import com.system.mrlukashem.refbases.TrackingWayRefBase;

/**
 * Created by mrlukashem on 19.11.15.
 */
public class TrackingWayDescriptionDialog extends DialogFragment {

    private final String DIALOG_TITLE = "Wprowadź informacje o nowej trasie";

    private TrackingWayRefBase mWay;

    private StartWayTracingCallback mStartWayTracingCB;

    private final String OK = "ok";

    private final String NO = "NO";

    public static TrackingWayDescriptionDialog newInstance(TrackingWayRefBase way) {
        if(way.isNill()) {
            throw new IllegalArgumentException();
        }

        TrackingWayDescriptionDialog dialog = new TrackingWayDescriptionDialog();
        dialog.mWay = way;

        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mStartWayTracingCB = (StartWayTracingCallback)getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder
                .setTitle(DIALOG_TITLE)
                .setPositiveButton(OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editDesc = (EditText)getDialog().findViewById(R.id.description);
                        EditText editTitle = (EditText)getDialog().findViewById(R.id.title);

                        mWay.setDescription(editDesc.getText().toString());
                        mWay.setTitle(editTitle.getText().toString());
                        mWay.setTag(mWay.getTitle());

                        mStartWayTracingCB.start(mWay);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(NO, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.setView(R.layout.dialog_layout);

        return builder.create();
    }
}
