package com.chris.mtgdecksapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import com.chris.mtgdecksapp.database.GameEntity;
import com.chris.mtgdecksapp.database.MTGAppRepository;
import com.chris.mtgdecksapp.databinding.ActivityRecordGameBinding;
import com.chris.mtgdecksapp.databinding.ActivityRecordsBinding;
import com.chris.mtgdecksapp.databinding.ToolbarBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import static com.chris.mtgdecksapp.utility.Constants.DECK_ID_KEY;

public class RecordGameActivity extends AppCompatActivity {
    private ActivityRecordGameBinding binding;
    private ToolbarBinding toolbarBinding;
    private MTGAppRepository repository;
    private TextInputEditText opponentField, opponentDeckField;
    private RadioButton radioButtonWin, radioButtonLose, radioButtonDraw;
    private FloatingActionButton fab;
    private int deckId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = MTGAppRepository.getInstance(this.getApplicationContext());
        binding = ActivityRecordGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbarBinding = binding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        getSupportActionBar().setTitle("Record Game");
        Bundle extras = getIntent().getExtras();
        deckId = extras.getInt(DECK_ID_KEY);
        opponentField = binding.editTextOpponent;
        opponentDeckField = binding.editTextOpponentDeck;
        radioButtonDraw = binding.radioButtonDraw;
        radioButtonWin = binding.radioButtonWin;
        radioButtonLose = binding.radioButtonLose;
        fab = binding.floatingActionButton;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String opponent="", opponenetDeck="";
                if(opponentField != null)
                    opponent = opponentField.getText().toString().trim();
                if(opponentDeckField != null)
                    opponenetDeck = opponentDeckField.getText().toString().trim();
                if(radioButtonWin.isChecked())
                    repository.insertGameEntity(new GameEntity(deckId,opponent,opponenetDeck,"Win"));
                else if(radioButtonLose.isChecked())
                    repository.insertGameEntity(new GameEntity(deckId,opponent,opponenetDeck,"Lose"));
                else if(radioButtonDraw.isChecked())
                    repository.insertGameEntity(new GameEntity(deckId,opponent,opponenetDeck,"Draw"));
                else{
                    // todo error message
                }
            }
        });
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
                Intent intent= new Intent(RecordGameActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }
}
