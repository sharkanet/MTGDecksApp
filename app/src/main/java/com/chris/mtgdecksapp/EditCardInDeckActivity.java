package com.chris.mtgdecksapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chris.mtgdecksapp.ViewModel.EditCardInDeckViewModel;
import com.chris.mtgdecksapp.databinding.ActivityEditCardInDeckBinding;
import com.chris.mtgdecksapp.databinding.ToolbarBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    private FloatingActionButton fab;

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

        fab = binding.floatingActionButton;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonIn.isChecked()) {
                    viewModel.save(cardId, deckId, Integer.valueOf(cardQuantityField.getText().toString().trim()), true);
                    finish();
                } else if(buttonOut.isChecked()){
                    viewModel.save(cardId, deckId, Integer.valueOf(cardQuantityField.getText().toString().trim()), false);
                    finish();
                } else {
                  //  todo
                  //  throw Exception
                }
            }
        });

        toolbarBinding = binding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        getSupportActionBar().setTitle("Edit Card in Deck");

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
            inDeck = extras.getBoolean(CARD_READY_KEY);
            cardNameField.setText(cardName);
            cardTextField.setText(cardText);
            cardQuantityField.setText(String.valueOf(quantity));
            if(inDeck){
                buttonIn.setChecked(true);
            } else {
                buttonOut.setChecked(true);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        Toolbar toolbar = toolbarBinding.toolbar;
        toolbar.inflateMenu(R.menu.detail_menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.delete:
                //todo
                //confirmation
                viewModel.delete(cardId, deckId);
                finish();
                return true;
            case R.id.about:
                Intent intent= new Intent(EditCardInDeckActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }
}
