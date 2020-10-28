package com.chris.mtgdecksapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.chris.mtgdecksapp.database.MTGAppRepository;
import com.chris.mtgdecksapp.model.CardInDeck;

import java.util.ArrayList;
import java.util.List;

public class PlayGameViewModel extends AndroidViewModel {
    private MTGAppRepository repository;
    private LiveData<List<CardInDeck>> cards;
    public List<CardInDeck> fullDeck = new ArrayList<>();
    public List<String> cardsUnseen = new ArrayList<>(), cardsSeen = new ArrayList<>();

    public PlayGameViewModel(@NonNull Application application){
        super(application);
        repository = MTGAppRepository.getInstance(application.getApplicationContext());
    }

    public void loadDeck(int id){
        cards = repository.getCardsInDeckWithId(id);
    }

    public LiveData<List<CardInDeck>> getCards() {
        return cards;
    }


}
