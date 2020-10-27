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


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CardQuantityDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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



    //
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public CardQuantityDialogFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment CardQuantityDialogFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static CardQuantityDialogFragment newInstance(String param1, String param2) {
//        CardQuantityDialogFragment fragment = new CardQuantityDialogFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_card_quantity_dialog, container, false);
//    }
}
