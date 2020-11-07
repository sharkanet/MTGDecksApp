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
import android.widget.Toast;

import com.chris.mtgdecksapp.UI.DecksAdapter;
import com.chris.mtgdecksapp.ViewModel.DecksViewModel;
import com.chris.mtgdecksapp.ViewModel.SelectDeckViewModel;
import com.chris.mtgdecksapp.database.DeckEntity;
import com.chris.mtgdecksapp.databinding.ActivitySelectDeckBinding;
import com.chris.mtgdecksapp.databinding.ToolbarBinding;

import java.util.ArrayList;
import java.util.List;

import static com.chris.mtgdecksapp.utility.Constants.DECK_ID_KEY;
import static com.chris.mtgdecksapp.utility.Constants.DECK_NAME_KEY;

public class SelectDeckActivity extends AppCompatActivity {
    private ActivitySelectDeckBinding binding;
    private ToolbarBinding toolbarBinding;
    private RecyclerView recyclerView;
    private DecksAdapter adapter;
    private SelectDeckViewModel viewModel;
    private List<DeckEntity> decks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectDeckBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbarBinding = binding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        getSupportActionBar().setTitle("Select Deck For Game");

        recyclerView = binding.recyclerView;
        adapter = new DecksAdapter(decks, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final Observer<List<DeckEntity>> deckObserver = newDecks -> {
            decks.clear();
            decks.addAll(newDecks);
            if(adapter == null){
                adapter = new DecksAdapter(decks, SelectDeckActivity.this);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();;
            }
        };
        viewModel = new ViewModelProvider(this).get(SelectDeckViewModel.class);
        viewModel.getAllDecks().observe(this, deckObserver);
        adapter.setOnDeckClickListener(deck ->{
            Intent intent = new Intent(SelectDeckActivity.this, PlayGameActivity.class);
            intent.putExtra(DECK_ID_KEY, deck.getDeckId());
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
            default:
                return false;
        }
    }
}
