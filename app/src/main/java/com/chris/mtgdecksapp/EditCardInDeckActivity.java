package com.chris.mtgdecksapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chris.mtgdecksapp.ViewModel.EditCardInDeckViewModel;
import com.chris.mtgdecksapp.databinding.ActivityEditCardInDeckBinding;
import com.chris.mtgdecksapp.databinding.ToolbarBinding;
import com.google.android.material.textfield.TextInputEditText;

import static com.chris.mtgdecksapp.utility.Constants.*;

public class EditCardInDeckActivity extends AppCompatActivity {
    private ActivityEditCardInDeckBinding binding;
    private ToolbarBinding toolbarBinding;
    private String cardName, cardText;
    private int deckId, cardId, quantity;
    private boolean inDeck;
    private TextView cardNameField, cardTextField;
    private TextInputEditText cardQuantityField;
    private RadioButton buttonIn, buttonOut;
    private RadioGroup radioGroup;
    private EditCardInDeckViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditCardInDeckBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cardNameField = binding.textViewNameField;
        cardTextField = binding.textViewTextField;
        cardQuantityField = binding.editTextQuantity;
        buttonIn = binding.radioButtonIn;
        buttonOut = binding.radioButtonOut;
        radioGroup = binding.radioGroup;

        initViewModel();

    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(EditCardInDeckViewModel.class);
        Bundle extras = getIntent().getExtras();
        if(extras == null){
            System.out.println("something went wrong - should never be called - DeckDetailActivity.InitViewModel()");
        } else {
            cardName = extras.getString(CARD_NAME_KEY);
            cardText = extras.getString(CARD_TEXT_KEY);
            cardId = extras.getInt(CARD_ID_KEY);
            deckId = extras.getInt(DECK_ID_KEY);
            quantity = extras.getInt(CARD_QUANTITY_KEY);
            inDeck = extras.getBoolean(CARD_IN_DECK_KEY);
            cardNameField.setText(cardName);
            cardTextField.setText(cardText);
            cardQuantityField.setText(Integer.valueOf(quantity));
            if(inDeck){
                buttonIn.setChecked(true);
            } else {
                buttonOut.setChecked(true);
            }
        }
    }
}
