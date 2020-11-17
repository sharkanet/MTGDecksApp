package com.chris.mtgdecksapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CmdQuantityAlertDialogFragment extends DialogFragment {

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Commander deck should only have one of each nonbasic card.")
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onCmdQuantityDialogPositiveClick(CmdQuantityAlertDialogFragment.this);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onCmdQuantityDialogNegativeClick(CmdQuantityAlertDialogFragment.this);
                    }
                });
        return builder.create();
    }

    public interface CmdQuantityDialogListener {
        public void onCmdQuantityDialogPositiveClick(DialogFragment dialog);
        public void onCmdQuantityDialogNegativeClick(DialogFragment dialog);
    }

    CmdQuantityDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (CmdQuantityDialogListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException("class cast exception");
        }
    }
}
