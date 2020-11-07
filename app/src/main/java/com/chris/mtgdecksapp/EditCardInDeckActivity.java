package com.chris.mtgdecksapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
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
    private int deckId, cardId, quantityOriginal;
    private boolean inDeck, isBasic, isCommanderDeck;
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
                int quantity = quantityOriginal;
                // check quantity field
                try{
                    quantity = Integer.valueOf(cardQuantityField.getText().toString().trim());

                } catch (NumberFormatException e){
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditCardInDeckActivity.this);
                    builder.setMessage("Error in quantity field.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            });
                    builder.create().show();
                } catch (NullPointerException e){
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditCardInDeckActivity.this);
                    builder.setMessage("Error in quantity field.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            });
                    builder.create().show();
                }

                if(isCommanderDeck && !isBasic && quantity > 1){
                    //error
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditCardInDeckActivity.this);
                    builder.setMessage("Commander deck can only have one of each nonbasic card.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });

                    builder.create().show();
                }else

                try {
                    if(quantity == 0){
                        confirmDelete();
                    }else if(buttonIn.isChecked()) {
                        viewModel.save(cardId, deckId, quantity, true);
                        finish();
                    } else if(buttonOut.isChecked()){
                        viewModel.save(cardId, deckId, quantity, false);
                        finish();
                    } else {
                        //  todo
                        throw new Exception("Unknown Error");
                    }
                } catch (Exception e){
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditCardInDeckActivity.this);
                    builder.setMessage("Error!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                    builder.create().show();
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
            quantityOriginal = extras.getInt(CARD_QUANTITY_KEY);
            inDeck = extras.getBoolean(CARD_READY_KEY);
            isBasic = extras.getBoolean(CARD_IS_BASIC);
            isCommanderDeck = extras.getBoolean(IS_COMMANDER_KEY);
            cardNameField.setText(cardName);
            cardTextField.setText(cardText);
            cardQuantityField.setText(String.valueOf(quantityOriginal));
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
                confirmDelete();
                return  true;
            case R.id.about:
                Intent intent= new Intent(EditCardInDeckActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }

    private void confirmDelete(){
        AlertDialog.Builder builder = new AlertDialog.Builder(EditCardInDeckActivity.this);
        builder.setMessage("Remove card from deck?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        viewModel.delete(cardId,deckId);
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.create().show();
    }
}
