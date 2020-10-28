package com.chris.mtgdecksapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chris.mtgdecksapp.UI.DeckDetailAdapter;
import com.chris.mtgdecksapp.ViewModel.DeckDetailViewModel;
import com.chris.mtgdecksapp.database.CardEntity;
import com.chris.mtgdecksapp.database.GameEntity;
import com.chris.mtgdecksapp.databinding.ActivityDeckDetailBinding;
import com.chris.mtgdecksapp.databinding.DeckDetailListItemBinding;
import com.chris.mtgdecksapp.databinding.ToolbarBinding;
import com.chris.mtgdecksapp.model.CardInDeck;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.chris.mtgdecksapp.utility.Constants.CARD_ID_KEY;
import static com.chris.mtgdecksapp.utility.Constants.CARD_LOYALTY_KEY;
import static com.chris.mtgdecksapp.utility.Constants.CARD_MANA_KEY;
import static com.chris.mtgdecksapp.utility.Constants.CARD_NAME_KEY;
import static com.chris.mtgdecksapp.utility.Constants.CARD_POWER_KEY;
import static com.chris.mtgdecksapp.utility.Constants.CARD_QUANTITY_KEY;
import static com.chris.mtgdecksapp.utility.Constants.CARD_READY_KEY;
import static com.chris.mtgdecksapp.utility.Constants.CARD_TEXT_KEY;
import static com.chris.mtgdecksapp.utility.Constants.CARD_TOUGHNESS_KEY;
import static com.chris.mtgdecksapp.utility.Constants.DECK_ID_KEY;
import static com.chris.mtgdecksapp.utility.Constants.DECK_NAME_KEY;


public class DeckDetailActivity extends AppCompatActivity {
    private ActivityDeckDetailBinding binding;
    private DeckDetailListItemBinding listItemBinding;
    private ToolbarBinding toolbarBinding;
    private RecyclerView recyclerView;
    private DeckDetailAdapter adapter;
    private TextView winsText, losesText, drawsText;
    private List<CardInDeck> cards = new ArrayList<>();
    private List<GameEntity> games, wins, loses = new ArrayList<>();
    private DeckDetailViewModel viewModel;
    private int deckId;
    private String deckName;
    private Executor executor = Executors.newSingleThreadExecutor();
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeckDetailBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        winsText = binding.winsText;
        losesText = binding.losesText;
        recyclerView = binding.recyclerView;
        fab = binding.floatingActionButton;


        initRecyclerView();
        initViewModel();
//setup toolbar
        toolbarBinding = binding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        getSupportActionBar().setTitle(deckName);

        //load wins and loses
        setTextWinsLoses();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeckDetailActivity.this, AddCardToDeckActivity.class);
                intent.putExtra(DECK_ID_KEY, deckId);
                startActivity(intent);
            }
        });



    }

    private void setTextWinsLoses() {
        final Observer<List<GameEntity>> recordsObserver = new Observer<List<GameEntity>>() {
            @Override
            public void onChanged(List<GameEntity> games) {
                int wins = 0;
                int loses = 0;
                int draws = 0;
                for (GameEntity gameEntity : games) {
                    if (gameEntity.getResult().equalsIgnoreCase("Win"))
                        wins++;
                    else if(gameEntity.getResult().equalsIgnoreCase("Lose"))
                        loses++;
                    else if(gameEntity.getResult().equalsIgnoreCase("Draw"))
                        draws++;
                }
                winsText.setText(String.valueOf(wins));
                losesText.setText(String.valueOf(loses));
                drawsText.setText(String.valueOf(draws));

            }
        };
        viewModel.getGameRecords().observe(this, recordsObserver);


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
        adapter.setOnCardClickListener(new DeckDetailAdapter.OnCardClickListener() {
            @Override
            public void onCardClick(CardInDeck card) {
                Intent intent = new Intent(DeckDetailActivity.this, EditCardInDeckActivity.class);
                intent.putExtra(DECK_ID_KEY, deckId);
                intent.putExtra(CARD_ID_KEY, card.getCardId());
                intent.putExtra(CARD_NAME_KEY, card.getName());
                intent.putExtra(CARD_TEXT_KEY, card.getText());
                intent.putExtra(CARD_QUANTITY_KEY, card.getQuantity());
                intent.putExtra(CARD_READY_KEY, card.isCurrentlyInDeck());
                startActivity(intent);
            }

            @Override
            public void onLongClick(CardInDeck card) {
                Intent intent = new Intent(DeckDetailActivity.this, CardEditActivity.class);
                intent.putExtra(CARD_ID_KEY, card.getCardId());
                intent.putExtra(CARD_NAME_KEY, card.getName());
                intent.putExtra(CARD_MANA_KEY, card.getManaCost());
                intent.putExtra(CARD_TEXT_KEY, card.getText());
                intent.putExtra(CARD_POWER_KEY, card.getPower());
                intent.putExtra(CARD_TOUGHNESS_KEY, card.getToughness());
                intent.putExtra(CARD_LOYALTY_KEY, card.getLoyalty());
                startActivity(intent);
            }

        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        Toolbar toolbar = toolbarBinding.toolbar;
        toolbar.inflateMenu(R.menu.deck_detail_menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.view_records:
                Intent intent = new Intent(DeckDetailActivity.this, RecordsActivity.class);
                intent.putExtra(DECK_ID_KEY, deckId);
                startActivity(intent);
                return true;
            case R.id.delete:
                viewModel.deleteDeck(deckId);
                // todo
                // confirmation
                finish();
                return  true;
            case R.id.about:
                Intent intent1 = new Intent(DeckDetailActivity.this, AboutActivity.class);
                startActivity(intent1);
                return true;
            default:
                return false;
        }
    }
}
