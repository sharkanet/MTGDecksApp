package com.chris.mtgdecksapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chris.mtgdecksapp.UI.DecksAdapter;
import com.chris.mtgdecksapp.ViewModel.DecksViewModel;
import com.chris.mtgdecksapp.ViewModel.PlayGameViewModel;
import com.chris.mtgdecksapp.ViewModel.ReportsViewModel;
import com.chris.mtgdecksapp.database.DeckEntity;
import com.chris.mtgdecksapp.databinding.ActivityReportsBinding;
import com.chris.mtgdecksapp.databinding.ToolbarBinding;
import com.chris.mtgdecksapp.model.Game;

import java.util.ArrayList;
import java.util.List;

public class ReportsActivity extends AppCompatActivity {
    private List<Game> gamesList = new ArrayList<>();
    private ReportsViewModel viewModel;
    private ActivityReportsBinding binding;
    private ToolbarBinding toolbarBinding;
    private TextView textViewReport;
    private Button buttonByDate, buttonByWin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReportsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        textViewReport = binding.textViewReport;
        //setup toolbar
        toolbarBinding = binding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        getSupportActionBar().setTitle("Reports");

        viewModel = new ViewModelProvider(this).get(ReportsViewModel.class);
        viewModel.getGames().observe(this, gameObserver);
        buttonByDate = binding.button1;
        buttonByWin = binding.button2;
        buttonByDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getAllGamesOrderedByDate();
                viewModel.getGames().observe(ReportsActivity.this, gameObserver);
            }
        });
        buttonByWin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getAllGamesOrderedByWin();
                viewModel.getGames().observe(ReportsActivity.this, gameObserver);
            }
        });
    }
    final Observer<List<Game>> gameObserver = newGames -> {
        gamesList.clear();
        gamesList.addAll(newGames);
        textViewReport.setText("");
        gamesList.forEach(game -> {
            textViewReport.append("Game with " + game.getOpponent() + " at " + game.getGameTime() + System.getProperty("line.separator"));
            textViewReport.append(game.getDeckName() +" VS " +game.getOpponentDeckName() + System.getProperty("line.separator"));
            textViewReport.append("Result: " + game.getResult() +System.getProperty("line.separator"));
            textViewReport.append("-----------------------------------" + System.getProperty("line.separator"));
        });
    };


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
                Intent intent= new Intent(ReportsActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }
}
