package com.sdm.mgpica;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class NameInputDialogFragment extends DialogFragment{
    public static boolean IsShown = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        IsShown = true;
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Confirm Pause?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // START THE GAME!
                        GameSystem.Instance.SetIsPaused(!GameSystem.Instance.GetIsPaused());
                        IsShown = false;
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        IsShown = false;
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
