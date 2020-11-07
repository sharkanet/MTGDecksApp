package com.chris.mtgdecksapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.chris.mtgdecksapp.UI.CardsAdapter;
import com.chris.mtgdecksapp.UI.SupertypeAdapter;
import com.chris.mtgdecksapp.UI.TypeAdapter;
import com.chris.mtgdecksapp.ViewModel.CardAddViewModel;
import com.chris.mtgdecksapp.database.CardEntity;
import com.chris.mtgdecksapp.database.CardTypeEntity;
import com.chris.mtgdecksapp.database.SupertypeEntity;
import com.chris.mtgdecksapp.database.TypeEntity;
import com.chris.mtgdecksapp.databinding.ActivityCardAddBinding;
import com.chris.mtgdecksapp.databinding.ToolbarBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hootsuite.nachos.NachoTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


import static com.hootsuite.nachos.terminator.ChipTerminatorHandler.BEHAVIOR_CHIPIFY_ALL;

public class CardAddActivity extends AppCompatActivity {
    private ActivityCardAddBinding  binding;
    private EditText nameField, manaField, supertypeField, typeField, textField, powerField, toughnessField, loyaltyField;
    private CardAddViewModel viewModel;
    private ToolbarBinding toolbarBinding;
    private FloatingActionButton fab;
    private NachoTextView typeNachoField, supertypeNachoField;
    private TypeAdapter typeAdapter;
    private SupertypeAdapter supertypeAdapter;
    private CardsAdapter cardAdapter;
    private List<TypeEntity> typeEntities = new ArrayList<>();
    private List<SupertypeEntity> supertypeEntities = new ArrayList<>();
    private List<CardEntity> cardEntities = new ArrayList<>();
    private Map<String, Integer> mapSupertypeToId = new HashMap<>();
    private Map<String, Integer> mapTypeToId = new HashMap<>();
    private Map<String, Integer> mapCardToId = new HashMap<>();
    private int cardId;
    private Executor executor = Executors.newSingleThreadExecutor();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCardAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        nameField = binding.editTextName;
        manaField = binding.editTextManaCost;
        // supertypeField = binding.editTextSupertype;
        // typeField = binding.editTextType;
        supertypeNachoField = binding.nachoTextViewSupertype;
        typeNachoField = binding.nachoTextViewType;
        textField = binding.editTextText;
        powerField = binding.editTextPower;
        toughnessField = binding.editTextToughness;
        loyaltyField = binding.editTextLoyalty;


        //setup viewmodel
        initViewModel();

        //setup supertype nachoview
        //setup type nachoview
        initNachoView();


        //setup toolbar
        toolbarBinding = binding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        getSupportActionBar().setTitle("Add Card");

        //setup floating action button
        fab = binding.floatingActionButton;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
                String cardName = nameField.getText().toString().trim();
                String cardMana = manaField.getText().toString().trim();
                String cardText = textField.getText().toString().trim();
                String cardPower = powerField.getText().toString().trim();
                String cardToughness = toughnessField.getText().toString().trim();
                String cardLoyalty = loyaltyField.getText().toString().trim();

                // handle types
                // handle non existent
                // handle validation
                executor.execute(() -> {
                    long cardId = viewModel.save(cardName, cardMana, cardText, cardPower, cardToughness, cardLoyalty);
                    supertypeNachoField.getAllChips().forEach(chip -> {
                        String supertypeName;
                        if (chip.getData() == null) {
                            supertypeName = chip.getText().toString();
                        } else supertypeName = (((SupertypeEntity) chip.getData()).getSupertype());
                        if (supertypeName.equalsIgnoreCase("Basic")) {
                            viewModel.save((int) cardId, cardName, cardMana, cardText, cardPower, cardToughness, cardLoyalty, true);
                        }
                        if (mapSupertypeToId.containsKey(supertypeName)) {
                            int chipId = mapSupertypeToId.get(supertypeName);
                            System.out.println("cardId is: " + cardId + "\n");
                            System.out.println(chipId);
                            viewModel.insertCardSupertype((int) cardId, chipId);
                        } else {
                            long chipId = viewModel.insertSupertypeEntityWithReturn(new SupertypeEntity(supertypeName));
                            viewModel.insertCardSupertype((int) cardId, (int) chipId);
                        }
                    });
                    typeNachoField.getAllChips().forEach(chip -> {
                        String typeName;
                        if (chip.getData() == null) {
                            typeName = chip.getText().toString();
                        } else typeName = (((TypeEntity) chip.getData()).getType());
                        if (mapTypeToId.containsKey(typeName)) {
                            int chipId = mapTypeToId.get(typeName);
                            viewModel.insertCardType((int) cardId, chipId);
                        } else {
                            long chipId = viewModel.insertTypeEntityWithReturn(new TypeEntity(typeName));
                            viewModel.insertCardType((int) cardId, (int) chipId);
                        }
                    });
                });
                finish();
            }
        });
    }

/**
//                viewModel.loadCard(cardName);
//                int cardId = viewModel.getCardEntity().getValue().getCardId();
//                int cardId = mapCardToId.get(cardName);
/* WHAT AM I DOING????
                viewModel.getCardEntity().observe(CardAddActivity.this, cardEntity -> {
                    if(cardEntity!=null){
                        cardId = cardEntity.getCardId();            }
                });
                final Observer<List<CardEntity>> cardObserver = cards ->{
                    cardEntities.clear();
                    cardEntities.addAll(cards);
                    mapCardToId.clear();
                    cardEntities.forEach( cardEntity -> {
                        mapCardToId.put(cardEntity.getName(),cardEntity.getCardId());
                    });
                    if(cardAdapter == null){
                        cardAdapter = new CardsAdapter(cards, CardAddActivity.this);
                    } else {
                        cardAdapter.notifyDataSetChanged();
                    }
                };
                viewModel.getCardEntitiesPostAdd().observeForever(cardObserver);
                cardEntities.forEach(cardEntity -> {
                    if(cardEntity.getName().equalsIgnoreCase(cardName)){
                        cardId = cardEntity.getCardId();
                        System.out.println("found card id: " + cardId);
                    } else{
                        System.out.println("not found");
                    }
                });
/*
                supertypeNachoField.getAllChips().forEach( chip -> {
                        System.out.println("cardId is: "+cardId+"\n");
                        System.out.println(mapSupertypeToId.get(((SupertypeEntity)chip.getData()).getSupertype()));
                      //  viewModel.insertCardSupertype(cardId, mapSupertypeToId.get(((SupertypeEntity)chip.getData()).getSupertype()));
                });
//                typeNachoField.getAllChips().forEach(chip -> {
//                    viewModel.insertCardType(cardName, ((TypeEntity)chip.getData()).getType());
//                });
//???????????                viewModel.getCardEntitiesPostAdd().removeObserver(cardObserver);

//        powerField.setOnClickListener( v->{
//            Intent intent = new Intent(CardAddActivity.this, AddCardSupertypeActivity.class);
//            startActivity(intent);
//        });
*/


    private void initNachoView() {
        typeNachoField = binding.nachoTextViewType;
        supertypeNachoField = binding.nachoTextViewSupertype;
        if(typeAdapter == null)
            typeAdapter = new TypeAdapter(this, R.layout.type_list_item, typeEntities);
        typeNachoField.setAdapter(typeAdapter);
        typeNachoField.addChipTerminator('\n', BEHAVIOR_CHIPIFY_ALL);

        if(supertypeAdapter == null){
            supertypeAdapter= new SupertypeAdapter(this, R.layout.supertype_list_item, supertypeEntities);
        }
        supertypeNachoField.setAdapter(supertypeAdapter);
        supertypeNachoField.addChipTerminator('\n', BEHAVIOR_CHIPIFY_ALL);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(CardAddViewModel.class);
        final Observer<List<TypeEntity>> typeEntityObserver = newTypes ->{
            typeEntities.clear();
            typeEntities.addAll(newTypes);
            mapTypeToId.clear();
            newTypes.forEach( typeEntity -> {
                mapTypeToId.put(typeEntity.getType(),typeEntity.getTypeId());
            });
            if(typeAdapter == null){
                typeAdapter = new TypeAdapter(this, R.layout.type_list_item, typeEntities);
            } else {
                typeAdapter.notifyDataSetChanged();
            }
        };
        viewModel.getTypeEntities().observe(this, typeEntityObserver);

        final Observer<List<SupertypeEntity>> supertypeEntityObserver = newSupertypes ->{
            supertypeEntities.clear();
            supertypeEntities.addAll(newSupertypes);
            mapSupertypeToId.clear();
            newSupertypes.forEach( supertypeEntity -> {
                mapSupertypeToId.put(supertypeEntity.getSupertype(),supertypeEntity.getSupertypeId());
            });
            if(supertypeAdapter == null){
                supertypeAdapter = new SupertypeAdapter(this, R.layout.supertype_list_item, supertypeEntities);
            } else {
                supertypeAdapter.notifyDataSetChanged();
            }
        };
        viewModel.getSupertypeEntities().observe(this, supertypeEntityObserver);

        final Observer<List<CardEntity>> cardObserver = cards ->{
            cardEntities.clear();
            cardEntities.addAll(cards);
            mapCardToId.clear();
            cardEntities.forEach( cardEntity -> {
                mapCardToId.put(cardEntity.getName(),cardEntity.getCardId());
            });
            if(cardAdapter == null){
                cardAdapter = new CardsAdapter(cards, this);
            } else {
                cardAdapter.notifyDataSetChanged();
            }
        };
        viewModel.getCardEntities().observe(this, cardObserver);
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
            //todo
            // manage types/supertypes?
            case R.id.about:
                Intent intent= new Intent(CardAddActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }

}
