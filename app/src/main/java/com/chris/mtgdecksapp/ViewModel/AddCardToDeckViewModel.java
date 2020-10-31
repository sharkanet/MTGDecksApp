package com.chris.mtgdecksapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.chris.mtgdecksapp.database.CardEntity;
import com.chris.mtgdecksapp.database.CardInDeckEntity;
import com.chris.mtgdecksapp.database.MTGAppRepository;

import java.util.List;

public class AddCardToDeckViewModel extends AndroidViewModel {
    private MTGAppRepository repository;
    private LiveData<List<CardEntity>> allCardEntity;
    private LiveData<List<CardInDeckEntity>> allCardInDeckEntity;
    private int deckId;


    public AddCardToDeckViewModel(@NonNull Application application){
        super(application);
        repository = MTGAppRepository.getInstance(application.getApplicationContext());
        allCardEntity = repository.getAllCardEntity();
        allCardInDeckEntity = repository.getAllCardInDeckEntity();
    }

    public void loadDeck(int id){
       this.deckId = id;
    }

    public void save(CardInDeckEntity cardInDeckEntity){
        repository.insertCardInDeckEntity(cardInDeckEntity);
    }

    public LiveData<List<CardEntity>> getAllCardEntity() {
        return allCardEntity;
    }

    public LiveData<List<CardInDeckEntity>> getAllCardInDeckEntity() {
        return allCardInDeckEntity;
    }
}
