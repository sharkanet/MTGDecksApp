package com.chris.mtgdecksapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.chris.mtgdecksapp.databinding.FragmentCardQuantityDialogBinding;



public class CardQuantityDialogFragment extends DialogFragment {
    private EditText quantityField;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_card_quantity_dialog, null);
        builder.setView(view)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //add card
                        try{
                            int quantity;
                            quantity = Integer.valueOf(quantityField.getText().toString().trim());
                            quantityPasser.quantityPass(quantity);
                            listener.onDialogPositiveClick(CardQuantityDialogFragment.this);
                        } catch (NumberFormatException e){
                            //todo
                            // error message for non number values
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //cancel
                        listener.onDialogNegativeClick(CardQuantityDialogFragment.this);
                    }
                });
        quantityField = view.findViewById(R.id.dialogCardQuantityField);
        return builder.create();

    }
    public interface QuantityPass {
        public void quantityPass(int quantity);
    }
    public interface CardQuantityDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    CardQuantityDialogListener listener;
    QuantityPass quantityPasser;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        quantityPasser = (QuantityPass) context;
        try{
            listener = (CardQuantityDialogListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException("class cast exception");
        }
    }

}
