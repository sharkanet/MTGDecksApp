package com.chris.mtgdecksapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
import android.widget.Toast;

import com.chris.mtgdecksapp.UI.CardsAdapter;
import com.chris.mtgdecksapp.ViewModel.CardsViewModel;
import com.chris.mtgdecksapp.database.CardEntity;
import com.chris.mtgdecksapp.databinding.ActivityCardsBinding;
import com.chris.mtgdecksapp.databinding.ToolbarBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.chris.mtgdecksapp.utility.Constants.*;

import java.util.ArrayList;
import java.util.List;


public class CardsActivity extends AppCompatActivity {
    private ActivityCardsBinding binding;
    private ToolbarBinding toolbarBinding;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private List<CardEntity> cards = new ArrayList<>();
    private CardsAdapter adapter;
    private CardsViewModel viewModel;
    private SearchView searchView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCardsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        recyclerView = binding.recyclerView;

        //setup toolbar
        toolbarBinding = binding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        getSupportActionBar().setTitle("Cards");


        initSearchView();
        //setup recyclerview
        initRecyclerView();
        //setup viewmodel
        initViewModel();

        //setup floating action button
        fab = binding.floatingActionButton;
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(CardsActivity.this, CardAddActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initSearchView() {
        //setup searchview
        searchView = binding.cardSearchView;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter("");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = binding.recyclerView;
        adapter = new CardsAdapter(cards, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }

    private void initViewModel() {
        final Observer<List<CardEntity>> cardObserver = newCards ->{
            cards.clear();
            cards.addAll(newCards);
            if(adapter == null){
                adapter = new CardsAdapter(cards, this);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
                adapter.updateCardsFull();
            }
        };
        viewModel = new ViewModelProvider(this).get(CardsViewModel.class);
        viewModel.getAllCardEntity().observe(this, cardObserver);
        adapter.setOnCardClickListener(card -> {
            //TODO
            Intent intent = new Intent(CardsActivity.this, CardEditActivity.class);
            intent.putExtra(CARD_ID_KEY, card.getCardId());
            intent.putExtra(CARD_NAME_KEY, card.getName());
            intent.putExtra(CARD_MANA_KEY, card.getManaCost());
            intent.putExtra(CARD_TEXT_KEY, card.getText());
            intent.putExtra(CARD_POWER_KEY, card.getPower());
            intent.putExtra(CARD_TOUGHNESS_KEY, card.getToughness());
            intent.putExtra(CARD_LOYALTY_KEY, card.getLoyalty());
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
                Intent intent= new Intent(CardsActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }

}
