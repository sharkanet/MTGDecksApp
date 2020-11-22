package com.chris.mtgdecksapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.chris.mtgdecksapp.UI.DecksAdapter;
import com.chris.mtgdecksapp.ViewModel.DecksViewModel;
import com.chris.mtgdecksapp.database.DeckEntity;
import com.chris.mtgdecksapp.databinding.ActivityDecksBinding;
import com.chris.mtgdecksapp.databinding.ToolbarBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.chris.mtgdecksapp.utility.Constants.DECK_ID_KEY;
import static com.chris.mtgdecksapp.utility.Constants.DECK_NAME_KEY;
import static com.chris.mtgdecksapp.utility.Constants.IS_COMMANDER_KEY;

public class DecksActivity extends AppCompatActivity {
    private ActivityDecksBinding binding;
    private ToolbarBinding toolbarBinding;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private DecksAdapter adapter;
    private List<DeckEntity> decks = new ArrayList<>();
    private DecksViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDecksBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //setup toolbar
        toolbarBinding = binding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        getSupportActionBar().setTitle("Decks");

        //setup recyclerview
        initRecyclerView();
        //setup viewmodel
        initViewModel();
        //setup floating action button
        fab = binding.floatingActionButton;
        fab.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(DecksActivity.this, DeckAddActivity.class);
                    startActivity(intent);
        }
        });

    }



    private void initRecyclerView() {
        //setup recyclerview
        recyclerView = binding.recyclerView;
        adapter = new DecksAdapter(decks, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }

    private void initViewModel() {
        //setup viewmodel
        final Observer<List<DeckEntity>> deckObserver = newDecks -> {
            decks.clear();
            decks.addAll(newDecks);
            if(adapter == null){
                adapter = new DecksAdapter(decks, DecksActivity.this);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();;
            }
        };
        viewModel = new ViewModelProvider(this).get(DecksViewModel.class);
        viewModel.getAllDecks().observe(this, deckObserver);
        adapter.setOnDeckClickListener(deck -> {
                Intent intent = new Intent(DecksActivity.this, DeckDetailActivity.class);
                intent.putExtra(DECK_ID_KEY, deck.getDeckId());
                intent.putExtra(DECK_NAME_KEY, deck.getName());
                intent.putExtra(IS_COMMANDER_KEY, deck.isCommanderDeck());
                startActivity(intent);
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
                Intent intent= new Intent(DecksActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                 return false;
        }
    }

}
