package com.chris.mtgdecksapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.chris.mtgdecksapp.UI.DeckDetailAdapter;
import com.chris.mtgdecksapp.ViewModel.DeckDetailViewModel;
import com.chris.mtgdecksapp.database.CardEntity;
import com.chris.mtgdecksapp.databinding.ActivityDeckDetailBinding;
import com.chris.mtgdecksapp.databinding.ToolbarBinding;
import com.chris.mtgdecksapp.model.CardInDeck;

import java.util.ArrayList;
import java.util.List;

import static com.chris.mtgdecksapp.utility.Constants.DECK_ID_KEY;
import static com.chris.mtgdecksapp.utility.Constants.DECK_NAME_KEY;


public class DeckDetailActivity extends AppCompatActivity {
    private ActivityDeckDetailBinding binding;
    private ToolbarBinding toolbarBinding;
    private RecyclerView recyclerView;
    private DeckDetailAdapter adapter;
    private TextView winsText, losesText;
    private List<CardInDeck> cards = new ArrayList<>();
    private DeckDetailViewModel viewModel;
    private int deckId, wins, loses;
    private String deckName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeckDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        winsText = binding.winsText;
        losesText = binding.losesText;
        recyclerView = binding.recyclerView;


        initRecyclerView();
        initViewModel();
//setup toolbar
        toolbarBinding = binding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        getSupportActionBar().setTitle(deckName);

        //load wins and loses
        setTextWinsLoses();



    }

    private void setTextWinsLoses() {
        //TODO
        //Set win/lose fields
        //search games, count wins, count loses
    }

    private void initRecyclerView() {
        //setup recyclerview
        adapter = new DeckDetailAdapter(cards,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }

    private void initViewModel(){
        //setup viewmodel
        final Observer<List<CardInDeck>> cardObserver = newCards->{
            cards.clear();
            cards.addAll(newCards);
            if(adapter==null){
                adapter = new DeckDetailAdapter(cards,this);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }

        };
        viewModel = new ViewModelProvider(this).get(DeckDetailViewModel.class);
        Bundle extras = getIntent().getExtras();
        if(extras == null){
            System.out.println("something went wrong - should never be called - DeckDetailActivity.InitViewModel()");
        } else {
            deckId = extras.getInt(DECK_ID_KEY);
            deckName = extras.getString(DECK_NAME_KEY);
            viewModel.loadDeck(deckId);
        }
        viewModel.getCardsInDeck().observe(this, cardObserver);
        adapter.setOnCardClickListener(card -> {
            //TODO
            Toast toast=Toast.makeText(getApplicationContext(), card + " button", Toast.LENGTH_SHORT );
            toast.show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        Toolbar toolbar = toolbarBinding.toolbar;
        toolbar.inflateMenu(R.menu.detail_menu);
        return true;
    }

//    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
//    public void loadDeck(){
//        viewModel.loadDeck(deckId);
//    }
}
