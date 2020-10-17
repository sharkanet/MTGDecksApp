package com.chris.mtgdecksapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chris.mtgdecksapp.database.MTGAppRepository;
import com.chris.mtgdecksapp.model.CardInDeck;

import java.util.List;

public class DeckDetailViewModel extends AndroidViewModel {
    private MTGAppRepository repository;
    private LiveData<List<CardInDeck>> cardsInDeck;


    public DeckDetailViewModel(@NonNull Application application){
        super(application);
        repository = MTGAppRepository.getInstance(application.getApplicationContext());
    }

    public void loadDeck(int id){
//        repository.getCardsInDeckWithId(id);
//        cardsInDeck = repository.getCardsInDeck();
        cardsInDeck = repository.getCardsInDeckWithId(id);
    }

    public LiveData<List<CardInDeck>> getCardsInDeck() {
        return cardsInDeck;
    }

    public void setCardsInDeck(LiveData<List<CardInDeck>> cardsInDeck) {
        this.cardsInDeck = cardsInDeck;
    }
}
