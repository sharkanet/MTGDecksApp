package com.chris.mtgdecksapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.chris.mtgdecksapp.database.CardInDeckEntity;
import com.chris.mtgdecksapp.database.MTGAppRepository;

public class EditCardInDeckViewModel extends AndroidViewModel {
    private MTGAppRepository repository;

    public EditCardInDeckViewModel (@NonNull Application application){
        super(application);
        repository = MTGAppRepository.getInstance(application.getApplicationContext());

    }

    public void save(int cardId, int deckId, int quantity, boolean inDeck){
        repository.updateCardInDeckEntity(new CardInDeckEntity(cardId,deckId,quantity,inDeck));
    }

    public void delete(int cardId, int deckId){
        repository.deleteCardInDeckById(cardId,deckId);
    }

}
