package com.chris.mtgdecksapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.chris.mtgdecksapp.UI.DecksAdapter;
import com.chris.mtgdecksapp.UI.GamesAdapter;
import com.chris.mtgdecksapp.ViewModel.DecksViewModel;
import com.chris.mtgdecksapp.ViewModel.RecordsViewModel;
import com.chris.mtgdecksapp.database.GameEntity;
import com.chris.mtgdecksapp.databinding.ActivityRecordsBinding;
import com.chris.mtgdecksapp.databinding.ToolbarBinding;

import java.util.ArrayList;
import java.util.List;

import static com.chris.mtgdecksapp.utility.Constants.*;

public class RecordsActivity extends AppCompatActivity {
    private ActivityRecordsBinding binding;
    private RecyclerView recyclerView;
    private ToolbarBinding toolbarBinding;
    private RecordsViewModel viewModel;
    private GamesAdapter adapter;
    private List<GameEntity> games = new ArrayList<>();
    private int deckId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityRecordsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbarBinding = binding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        getSupportActionBar().setTitle("Records");

        initRecyclerView();
        initViewModel();

    }

    private void initRecyclerView() {
        recyclerView = binding.recyclerView;
        adapter = new GamesAdapter(games, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }

    private void initViewModel() {
        final Observer<List<GameEntity>> gamesObserver = newGames->{
            games.clear();
            games.addAll(newGames);
            if(adapter == null){
                adapter = new GamesAdapter(games, RecordsActivity.this);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        };
        viewModel = new ViewModelProvider(this).get(RecordsViewModel.class);
        Bundle extras = getIntent().getExtras();
        deckId = extras.getInt(DECK_ID_KEY);
        viewModel.loadGames(deckId);
        viewModel.getGames().observe(this, gamesObserver);
        adapter.setOnItemClickListener(new GamesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(GameEntity game) {
                Intent intent = new Intent(RecordsActivity.this, EditGameActivity.class);
                intent.putExtra(GAME_ID_KEY, game.getGameId());
                intent.putExtra(DECK_ID_KEY, game.getDeckId_FK());
                intent.putExtra(OPPONENT_NAME_KEY, game.getOpponent());
                intent.putExtra(OPPONENT_DECK_KEY, game.getOpponentDeckName());
                intent.putExtra(RESULT_KEY, game.getResult());
                startActivity(intent);
            }

            @Override
            public void onLongClick(GameEntity game) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RecordsActivity.this);
                builder.setMessage("Delete Record?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                viewModel.delete(game);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                builder.create().show();
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
                Intent intent1 = new Intent(RecordsActivity.this, AboutActivity.class);
                startActivity(intent1);
                return true;
            default:
                return false;
        }
    }


}
