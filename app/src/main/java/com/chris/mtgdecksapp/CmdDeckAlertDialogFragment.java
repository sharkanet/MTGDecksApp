package com.chris.mtgdecksapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class CmdDeckAlertDialogFragment extends DialogFragment{
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Commander deck can only have one of each nonbasic card. \nRemove duplicate nonbasic cards before registering as Commander deck.")
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onCmdDeckDialogPositiveClick(CmdDeckAlertDialogFragment.this);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onCmdDeckDialogNegativeClick(CmdDeckAlertDialogFragment.this);
                    }
                });
        return builder.create();
    }

    public interface CmdDeckDialogListener {
        public void onCmdDeckDialogPositiveClick(DialogFragment dialog);
        public void onCmdDeckDialogNegativeClick(DialogFragment dialog);
    }

    CmdDeckDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (CmdDeckDialogListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException("class cast exception");
        }
    }
}

