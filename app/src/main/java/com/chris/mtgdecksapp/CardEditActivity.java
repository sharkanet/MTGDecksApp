package com.chris.mtgdecksapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.chris.mtgdecksapp.UI.SupertypeAdapter;
import com.chris.mtgdecksapp.UI.TypeAdapter;
import com.chris.mtgdecksapp.ViewModel.CardEditViewModel;
import com.chris.mtgdecksapp.database.CardSupertypeEntity;
import com.chris.mtgdecksapp.database.CardTypeEntity;
import com.chris.mtgdecksapp.database.SupertypeEntity;
import com.chris.mtgdecksapp.database.TypeEntity;
import com.chris.mtgdecksapp.databinding.ActivityCardEditBinding;
import com.chris.mtgdecksapp.databinding.ToolbarBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hootsuite.nachos.NachoTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.chris.mtgdecksapp.utility.Constants.CARD_ID_KEY;
import static com.chris.mtgdecksapp.utility.Constants.*;
import static com.hootsuite.nachos.terminator.ChipTerminatorHandler.BEHAVIOR_CHIPIFY_ALL;

public class CardEditActivity extends AppCompatActivity {
    private ActivityCardEditBinding binding;
    private EditText nameField, manaField, textField, powerField, toughnessField, loyaltyField;
    private CardEditViewModel viewModel;
    private ToolbarBinding toolbarBinding;
    private FloatingActionButton fab;
    private NachoTextView typeNachoField, supertypeNachoField;
    private TypeAdapter typeAdapter;
    private SupertypeAdapter supertypeAdapter;
    private List<TypeEntity> typeEntities = new ArrayList<>();
    private List<SupertypeEntity> supertypeEntities = new ArrayList<>();
    private int cardId;
    private String cardName, cardMana, cardText, cardPower, cardToughness, cardLoyalty;
    private Executor executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCardEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        nameField = binding.editTextName;
        manaField = binding.editTextManaCost;
        supertypeNachoField = binding.nachoTextViewSupertype;
        typeNachoField = binding.nachoTextViewType;
        textField = binding.editTextText;
        powerField = binding.editTextPower;
        toughnessField = binding.editTextToughness;
        loyaltyField = binding.editTextLoyalty;
        fab = binding.floatingActionButton;

        //setup nachoviews
        initNachoView();

        //setup view model
        initViewModel();


        //setuptoolbar
        toolbarBinding = binding.toolbar;
        setSupportActionBar(toolbarBinding.toolbar);
        getSupportActionBar().setTitle("Edit Card");

        //setup floating action button to save
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                fillNachoView();
                String cardName = nameField.getText().toString().trim();
                String cardMana = manaField.getText().toString().trim();
                String cardText = textField.getText().toString().trim();
                String cardPower = powerField.getText().toString().trim();
                String cardToughness = toughnessField.getText().toString().trim();
                String cardLoyalty = loyaltyField.getText().toString().trim();

                executor.execute(() -> {
                    viewModel.save(cardName, cardMana, cardText, cardPower, cardToughness, cardLoyalty);
                    supertypeNachoField.getAllChips().forEach(chip -> {
                        String supertypeName;
                        if (chip.getData() == null) {
                            supertypeName = chip.getText().toString();
                        } else supertypeName = (((SupertypeEntity) chip.getData()).getSupertype());
                        if (viewModel.getMapSupertypeToId().containsKey(supertypeName)) {
                            int chipId = viewModel.getMapSupertypeToId().get(supertypeName);
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
                        if (viewModel.getMapTypeToId().containsKey(typeName)) {
                            int chipId = viewModel.getMapTypeToId().get(typeName);
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


    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(CardEditViewModel.class);
// get extras and load viewmodel
        retrieveExtras();
        viewModel.loadCard(cardId);

// observe type entities
        final Observer<List<TypeEntity>> typeEntityObserver = newTypes ->{
            typeEntities.clear();
            typeEntities.addAll(newTypes);
            viewModel.getMapTypeToId().clear();
            viewModel.getMapIdToType().clear();
            newTypes.forEach( typeEntity -> {
                viewModel.getMapTypeToId().put(typeEntity.getType(),typeEntity.getTypeId());
                viewModel.getMapIdToType().put(typeEntity.getTypeId(), typeEntity.getType());
            });
            if(typeAdapter == null){
                typeAdapter = new TypeAdapter(this, R.layout.type_list_item, typeEntities);
            } else {
                typeAdapter.notifyDataSetChanged();
            }
        };
        viewModel.getTypeEntities().observe(this, typeEntityObserver);

// observe supertype entities
        final Observer<List<SupertypeEntity>> supertypeEntityObserver = newSupertypes ->{
            supertypeEntities.clear();
            supertypeEntities.addAll(newSupertypes);
            viewModel.getMapSupertypeToId().clear();
            viewModel.getMapIdToSupertype().clear();
            newSupertypes.forEach( supertypeEntity -> {
                viewModel.getMapSupertypeToId().put(supertypeEntity.getSupertype(),supertypeEntity.getSupertypeId());
                viewModel.getMapIdToSupertype().put(supertypeEntity.getSupertypeId(), supertypeEntity.getSupertype());
            });
            if(supertypeAdapter == null){
                supertypeAdapter = new SupertypeAdapter(this, R.layout.supertype_list_item, supertypeEntities);
            } else {
                supertypeAdapter.notifyDataSetChanged();
            }
        };
        viewModel.getSupertypeEntities().observe(this, supertypeEntityObserver);


//observe cardType entities and make list
        final Observer<List<CardTypeEntity>> cardTypeEntityObserver = newCardType ->{
            viewModel.getCardTypeEntitiesList().clear();
            viewModel.getCardTypeEntitiesList().addAll(newCardType);
            viewModel.getCardTypeEntitiesList().forEach( cardTypeEntity -> {
                System.out.println(cardTypeEntity.getTypeId_FK() + " " + cardTypeEntity.getCardId_FK());
            });
        };
        viewModel.getCardTypeEntities().observe(this, cardTypeEntityObserver);

//observe cardSupertype entities and make list
        final Observer<List<CardSupertypeEntity>> cardSupertypeEntityObserver = newCardSupertype ->{
            viewModel.getCardSupertypeEntitiesList().clear();
            viewModel.getCardSupertypeEntitiesList().addAll(newCardSupertype);
        };
        viewModel.getCardSupertypeEntities().observe(this, cardSupertypeEntityObserver);

       setFields();
    }

    private void retrieveExtras() {
        Bundle extras = getIntent().getExtras();
        if(extras == null){
            System.out.println("something went wrong - should never be called - DeckDetailActivity.InitViewModel()");
        } else {
            cardId = extras.getInt(CARD_ID_KEY);
            cardName = extras.getString(CARD_NAME_KEY);
            cardMana = extras.getString(CARD_MANA_KEY);
            cardText= extras.getString(CARD_TEXT_KEY);
            cardPower = extras.getString(CARD_POWER_KEY);
            cardToughness = extras.getString(CARD_TOUGHNESS_KEY);
            cardLoyalty= extras.getString(CARD_LOYALTY_KEY);

        }
    }

    private void setFields() {
        nameField.setText(cardName);
        manaField.setText(cardMana);
        textField.setText(cardText);
        powerField.setText(cardPower);
        toughnessField.setText(cardToughness);
        loyaltyField.setText(cardLoyalty);
        fillNachoView();

    }

    private void fillNachoView() {
        // find a way to make these happen during load
        viewModel.getCardTypeEntitiesList().forEach(cardTypeEntity -> {
            System.out.println((cardTypeEntity.getTypeId_FK()) );
            typeNachoField.append(viewModel.getMapIdToType().get(cardTypeEntity.getTypeId_FK()));
            typeNachoField.chipifyAllUnterminatedTokens();
            System.out.println("Append" + viewModel.getMapIdToType().get(cardTypeEntity.getTypeId_FK()) );
        });

        viewModel.getCardSupertypeEntitiesList().forEach(cardSupertypeEntity -> {
            System.out.println(cardSupertypeEntity.getSupertypeId_FK());
            supertypeNachoField.append(viewModel.getMapIdToSupertype().get(cardSupertypeEntity.getSupertypeId_FK()));
            supertypeNachoField.chipifyAllUnterminatedTokens();
        });
    }


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

        binding.textViewType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillNachoView();
                binding.textViewType.setText("Card Types:");
                binding.textViewSupertype.setText("Card Supertypes:");
                binding.textViewSupertype.setOnClickListener(null);
                binding.textViewType.setOnClickListener(null);

            }
        });
        binding.textViewSupertype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillNachoView();
                binding.textViewType.setText("Card Types:");
                binding.textViewSupertype.setText("Card Supertypes:");
                binding.textViewSupertype.setOnClickListener(null);
                binding.textViewType.setOnClickListener(null);
            }
        });
    }
}
