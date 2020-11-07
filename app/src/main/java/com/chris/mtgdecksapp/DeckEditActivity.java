package com.chris.mtgdecksapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.PrimaryKey;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import com.chris.mtgdecksapp.UI.DeckDetailAdapter;
import com.chris.mtgdecksapp.ViewModel.DeckEditViewModel;
import com.chris.mtgdecksapp.databinding.ActivityDeckAddBinding;
import com.chris.mtgdecksapp.databinding.ToolbarBinding;
import com.chris.mtgdecksapp.model.CardInDeck;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import static com.chris.mtgdecksapp.utility.Constants.DECK_ID_KEY;
import static com.chris.mtgdecksapp.utility.Constants.DECK_NAME_KEY;
import static com.chris.mtgdecksapp.utility.Constants.IS_COMMANDER_KEY;

public class DeckEditActivity extends AppCompatActivity implements CmdDeckAlertDialogFragment.CmdDeckDialogListener {
    private ActivityDeckAddBinding binding;
    private CheckBox checkBox;
    private TextInputEditText nameField;
    private DeckEditViewModel viewModel;
    private FloatingActionButton fab;
    private ToolbarBinding toolbarBinding;
    private String deckName;
    private int deckId;
    private boolean isCommanderDeck;
    private List<CardInDeck> cards = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDeckAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        nameField = binding.editTextName;

        toolbarBinding = binding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        getSupportActionBar().setTitle("Edit Deck");

        Bundle extras = getIntent().getExtras();
        deckId = extras.getInt(DECK_ID_KEY);
        deckName = extras.getString(DECK_NAME_KEY);
        isCommanderDeck = extras.getBoolean(IS_COMMANDER_KEY);

        nameField.setText(deckName);
        checkBox = binding.checkBox;
        if(isCommanderDeck){
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }

        initViewModel();



        //setup floating action button
        fab = binding.floatingActionButton;
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(nameField.getText().toString().trim().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DeckEditActivity.this);
                    builder.setMessage("Enter a Name.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    builder.create().show();
                } else if(checkBox.isChecked() && containsNonBasicDuplicates() ){
                    // check cards in deck for dupes
                    // error
//                    AlertDialog.Builder builder = new AlertDialog.Builder(DeckEditActivity.this);
//                    builder.setMessage("Commander deck can only have one of each nonbasic card. \nRemove duplicate nonbasic cards before registering as Commander deck.")
//                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int id) {
//                                }
//                            });
//
//                    builder.create().show();
                    DialogFragment dialogFragment = new CmdDeckAlertDialogFragment();
                    dialogFragment.show(getSupportFragmentManager(), "CmdDeckAlertDialogFragment");
                } else {
                    save();
                }
            }
        });

    }

    private void save() {
        viewModel.save(deckId, nameField.getText().toString().trim(), checkBox.isChecked());
        Intent intent = new Intent(DeckEditActivity.this, DecksActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void initViewModel() {
        viewModel= new ViewModelProvider(this).get(DeckEditViewModel.class);
        final Observer<List<CardInDeck>> cardObserver = newCards->{
            cards.clear();
            cards.addAll(newCards);
        };
        viewModel.loadDeck(deckId);
        viewModel.getCardsInDeck().observe(this, cardObserver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        Toolbar toolbar = toolbarBinding.toolbar;
        toolbar.inflateMenu(R.menu.basic_menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.about:
                Intent intent= new Intent(DeckEditActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }

    private boolean containsNonBasicDuplicates(){
        for(CardInDeck cardInDeck: cards){
            if(cardInDeck.getQuantity()>1 && !cardInDeck.isBasic()){
                return true;
            }
        }
        return false;
    };

    @Override
    public void onCmdDeckDialogPositiveClick(DialogFragment dialog) {
        save();
    }

    @Override
    public void onCmdDeckDialogNegativeClick(DialogFragment dialog) {
        //do nothing
    }


}
