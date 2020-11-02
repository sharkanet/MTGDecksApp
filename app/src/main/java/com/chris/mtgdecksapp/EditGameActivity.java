package com.chris.mtgdecksapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import com.chris.mtgdecksapp.database.GameEntity;
import com.chris.mtgdecksapp.database.MTGAppRepository;
import com.chris.mtgdecksapp.databinding.ActivityRecordGameBinding;
import com.chris.mtgdecksapp.databinding.ToolbarBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import static com.chris.mtgdecksapp.utility.Constants.DECK_ID_KEY;
import static com.chris.mtgdecksapp.utility.Constants.GAME_ID_KEY;
import static com.chris.mtgdecksapp.utility.Constants.OPPONENT_DECK_KEY;
import static com.chris.mtgdecksapp.utility.Constants.OPPONENT_NAME_KEY;
import static com.chris.mtgdecksapp.utility.Constants.RESULT_KEY;

public class EditGameActivity extends AppCompatActivity {
    private ActivityRecordGameBinding binding;
    private ToolbarBinding toolbarBinding;
    private MTGAppRepository repository;
    private TextInputEditText opponentField, opponentDeckField;
    private RadioButton radioButtonWin, radioButtonLose, radioButtonDraw;
    private FloatingActionButton fab;
    private int deckId, gameId;
    private String opponentName, opponentDeck, result;

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
        gameId = extras.getInt(GAME_ID_KEY);
        deckId = extras.getInt(DECK_ID_KEY);
        opponentName = extras.getString(OPPONENT_NAME_KEY);
        opponentDeck = extras.getString(OPPONENT_DECK_KEY);
        result = extras.getString(RESULT_KEY);
        opponentField = binding.editTextOpponent;
        opponentField.setText(opponentName);
        opponentDeckField = binding.editTextOpponentDeck;
        opponentDeckField.setText(opponentDeck);
        radioButtonDraw = binding.radioButtonDraw;
        radioButtonWin = binding.radioButtonWin;
        radioButtonLose = binding.radioButtonLose;
        switch (result.toLowerCase()){
            case "win":
                radioButtonWin.setChecked(true);
                radioButtonLose.setChecked(false);
                radioButtonDraw.setChecked(false);
                break;
            case "lose":
                radioButtonLose.setChecked(true);
                radioButtonWin.setChecked(false);
                radioButtonDraw.setChecked(false);
                break;
            case "draw":
                radioButtonDraw.setChecked(true);
                radioButtonWin.setChecked(false);
                radioButtonLose.setChecked(false);
                break;
            default:
                radioButtonWin.setChecked(false);
                radioButtonLose.setChecked(false);
                radioButtonDraw.setChecked(false);
        }
        fab = binding.floatingActionButton;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String opponent="", opponentDeck="";
                if(opponentField != null)
                    opponent = opponentField.getText().toString().trim();
                if(opponentDeckField != null)
                    opponentDeck = opponentDeckField.getText().toString().trim();
                if(radioButtonWin.isChecked())
                    repository.updateGameEntity(new GameEntity(gameId, deckId,opponent,opponentDeck,"Win"));
                else if(radioButtonLose.isChecked())
                    repository.updateGameEntity(new GameEntity(gameId, deckId,opponent,opponentDeck,"Lose"));
                else if(radioButtonDraw.isChecked())
                    repository.updateGameEntity(new GameEntity(gameId, deckId,opponent,opponentDeck,"Draw"));
                else{
                    // todo error message
                }
                finish();
            }
        });
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
                Intent intent= new Intent(EditGameActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }

    private void confirmDelete(){
        AlertDialog.Builder builder = new AlertDialog.Builder(EditGameActivity.this);
        builder.setMessage("Delete Record?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        repository.deleteGameEntityById(gameId);
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
