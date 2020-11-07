package com.chris.mtgdecksapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.chris.mtgdecksapp.UI.AddCardToDeckAdapter;
import com.chris.mtgdecksapp.UI.CardsAdapter;
import com.chris.mtgdecksapp.ViewModel.AddCardToDeckViewModel;
import com.chris.mtgdecksapp.ViewModel.CardsViewModel;
import com.chris.mtgdecksapp.database.CardEntity;
import com.chris.mtgdecksapp.database.CardInDeckEntity;
import com.chris.mtgdecksapp.databinding.ActivityAddCardToDeckBinding;
import com.chris.mtgdecksapp.databinding.ToolbarBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.chris.mtgdecksapp.utility.Constants.DECK_ID_KEY;
import static com.chris.mtgdecksapp.utility.Constants.IS_COMMANDER_KEY;

public class AddCardToDeckActivity extends AppCompatActivity implements CardQuantityDialogFragment.CardQuantityDialogListener, CardQuantityDialogFragment.QuantityPass {
    private ActivityAddCardToDeckBinding binding;
    private ToolbarBinding toolbarBinding;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private AddCardToDeckAdapter adapter;
    private AddCardToDeckViewModel viewModel;
    private SearchView searchView;
    private List<CardEntity> cards = new ArrayList<>();
    private List<CardInDeckEntity> cardInDeckEntities = new ArrayList<>();
    private int deckId, cardId, quantity;
    private boolean inDeck = true, isCommanderDeck, selectedCardBasic;
    private String cardName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCardToDeckBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //setup searchview
        searchView = binding.cardSearchView;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //do nothing
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        toolbarBinding = binding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        getSupportActionBar().setTitle("Add Card to Deck");

        initRecyclerView();
        initViewModel();


        fab = binding.floatingActionButton;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCardToDeckActivity.this, CardAddActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = binding.recyclerView;
        adapter = new AddCardToDeckAdapter(cards, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }

    private void initViewModel() {
        final Observer<List<CardInDeckEntity>> cardInDeckObserver = newCardInDecks ->{
            cardInDeckEntities.clear();
            cardInDeckEntities.addAll(newCardInDecks);
        };

        final Observer<List<CardEntity>> cardObserver = newCards ->{
            cards.clear();
            cards.addAll(newCards);
            if(adapter == null){
                adapter = new AddCardToDeckAdapter(cards, this);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
                adapter.updateCardsFull();
            }
        };

        viewModel = new ViewModelProvider(this).get(AddCardToDeckViewModel.class);
        Bundle extras = getIntent().getExtras();
        if(extras == null){
            System.out.println("something went wrong - should never be called - DeckDetailActivity.InitViewModel()");
        } else {
            deckId = extras.getInt(DECK_ID_KEY);
            isCommanderDeck = extras.getBoolean(IS_COMMANDER_KEY);
            viewModel.loadDeck(deckId);
        }
        viewModel.getAllCardEntity().observe(this, cardObserver);
        viewModel.getAllCardInDeckEntity().observe(this, cardInDeckObserver);
        adapter.setOnCardClickListener(card -> {
            this.cardId = card.getCardId();
            this.cardName = card.getName();
            this.quantity = 0;
            this.selectedCardBasic = card.isBasic();
            for(CardInDeckEntity cardInDeckEntity: cardInDeckEntities){
                if(cardInDeckEntity.getDeckId_FK()==deckId && cardInDeckEntity.getCardId_FK() == cardId){
                    quantity = cardInDeckEntity.getQuantity();
                }
            }
            // add the card to the deck
            // pop up for quantity to add
            showQuantityDialog();
       });
    }

    @Override
    public void quantityPass(int quantity) {
        this.quantity=this.quantity+quantity;
    }

    public void showQuantityDialog(){
        DialogFragment dialogFragment= new CardQuantityDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "CardQuantityDialogFragment");
    }

    public void onDialogPositiveClick(DialogFragment dialogFragment){
        // TODO sanity check?
        // validate number for edh
        if(isCommanderDeck && !selectedCardBasic && quantity > 1){
            //error
            AlertDialog.Builder builder = new AlertDialog.Builder(AddCardToDeckActivity.this);
            builder.setMessage("Commander deck can only have one of each nonbasic card.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                }
                    });

            builder.create().show();
        }else
        //save
            viewModel.save(new CardInDeckEntity(cardId,deckId, quantity, inDeck));
    }
    public void onDialogNegativeClick(DialogFragment dialogFragment){
        //do nothing
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
                Intent intent= new Intent(AddCardToDeckActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }
}
