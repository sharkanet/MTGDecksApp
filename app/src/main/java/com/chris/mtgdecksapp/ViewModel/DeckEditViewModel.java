package com.chris.mtgdecksapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.chris.mtgdecksapp.database.DeckEntity;
import com.chris.mtgdecksapp.database.MTGAppRepository;
import com.chris.mtgdecksapp.model.CardInDeck;

import java.util.List;

public class DeckEditViewModel extends AndroidViewModel {
    private MTGAppRepository repository;
    private LiveData<List<CardInDeck>> cardsInDeck;


    public DeckEditViewModel(@NonNull Application application) {
        super(application);
        repository = MTGAppRepository.getInstance(application.getApplicationContext());
    }

    public void save(int deckId, String name, boolean  isCommanderDeck){
        DeckEntity newDeckEntity = new DeckEntity(deckId, name, isCommanderDeck);
        repository.updateDeckEntity(newDeckEntity);
    }

    public void loadDeck(int deckId){
        cardsInDeck = repository.getCardsInDeckWithId(deckId);
    }

    public MTGAppRepository getRepository() {
        return repository;
    }

    public LiveData<List<CardInDeck>> getCardsInDeck() {
        return cardsInDeck;
    }
}
