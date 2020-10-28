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

import com.chris.mtgdecksapp.UI.PlayGameAdapter;
import com.chris.mtgdecksapp.ViewModel.PlayGameViewModel;
import com.chris.mtgdecksapp.databinding.ActivityPlayGameBinding;
import com.chris.mtgdecksapp.databinding.ToolbarBinding;
import com.chris.mtgdecksapp.model.CardInDeck;

import java.util.ArrayList;
import java.util.List;

import static com.chris.mtgdecksapp.utility.Constants.DECK_ID_KEY;


public class PlayGameActivity extends AppCompatActivity {
    private RecyclerView recyclerView1, recyclerView2;
    private ToolbarBinding toolbarBinding;
    private ActivityPlayGameBinding binding;
    private PlayGameViewModel viewModel;
    private PlayGameAdapter adapter1, adapter2;
    //private List<CardInDeck> cardsUnseen = new ArrayList<>(), cardsSeen = new ArrayList<>();
    private int deckId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViewModel();
        initRecyclerView();


        //setup toolbar
        toolbarBinding = binding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        getSupportActionBar().setTitle("Play Game");
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(PlayGameViewModel.class);
        Bundle extras = getIntent().getExtras();
        deckId = extras.getInt(DECK_ID_KEY);
        viewModel.loadDeck(deckId);
//        cardsUnseen = viewModel.getCardsUnseen();
//        cardsSeen = viewModel.getCardsSeen();
        final Observer<List<CardInDeck>> cardObserver = newCards ->{
            if(viewModel.fullDeck.size() == 0 && viewModel.cardsSeen.size() == 0) {
                viewModel.fullDeck.addAll(newCards);
                viewModel.cardsUnseen.clear();
                viewModel.fullDeck.forEach( cardInDeck -> {
                    for (int i = 0; i < cardInDeck.getQuantity(); i++){
                        viewModel.cardsUnseen.add(cardInDeck.getName());
                    }
                });
            }

            if(adapter1 == null){
                adapter1 = new PlayGameAdapter(viewModel.cardsUnseen, this);
                recyclerView1.setAdapter(adapter1);
            } else {
                adapter1.notifyDataSetChanged();
            }
            if(adapter2 == null){
                adapter2 = new PlayGameAdapter(viewModel.cardsSeen, this);
                recyclerView2.setAdapter(adapter2);
            } else {
                adapter2.notifyDataSetChanged();
            }
        };
        viewModel.getCards().observe(this, cardObserver);

    }

    private void initRecyclerView() {
        recyclerView1 = binding.recyclerView;
        recyclerView2 = binding.recyclerView2;
// todo
        // recycler view sizes
        if(adapter1 == null){
            adapter1 = new PlayGameAdapter(viewModel.cardsUnseen, this);
            recyclerView1.setAdapter(adapter1);
        }
        if(adapter2 == null){
            adapter2 = new PlayGameAdapter(viewModel.cardsSeen, this);
            recyclerView2.setAdapter(adapter2);
        }
        adapter1 = new PlayGameAdapter(viewModel.cardsUnseen, this);
        recyclerView1.setAdapter(adapter1);
        adapter2 = new PlayGameAdapter(viewModel.cardsSeen, this);
        recyclerView2.setAdapter(adapter2);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        adapter1.setOnCardClickListener(card -> {
            viewModel.cardsSeen.add(card);
            viewModel.cardsUnseen.remove(card);
            adapter1.notifyDataSetChanged();
            adapter2.notifyDataSetChanged();
        });
        adapter2.setOnCardClickListener(card -> {
            viewModel.cardsUnseen.add(card);
            viewModel.cardsSeen.remove(card);
            adapter1.notifyDataSetChanged();
            adapter2.notifyDataSetChanged();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        Toolbar toolbar = toolbarBinding.toolbar;
        toolbar.inflateMenu(R.menu.play_menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.record_game:
                Intent intent = new Intent(PlayGameActivity.this, RecordGameActivity.class);
                intent.putExtra(DECK_ID_KEY, deckId);
                startActivity(intent);
                return true;
            case R.id.about:
                Intent intent1 = new Intent(PlayGameActivity.this, AboutActivity.class);
                startActivity(intent1);
                return true;
            default:
                return false;
        }
    }
}
