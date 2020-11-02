package com.chris.mtgdecksapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.chris.mtgdecksapp.database.DeckEntity;
import com.chris.mtgdecksapp.database.MTGAppRepository;

public class DeckAddViewModel extends AndroidViewModel {
    private MTGAppRepository repository;
    private MutableLiveData<DeckEntity> deckEntity = new MutableLiveData<>();

    public DeckAddViewModel(@NonNull Application application){
        super(application);
        repository = MTGAppRepository.getInstance(application.getApplicationContext());
    }

    public void save(String name, boolean isCommanderDeck){
        DeckEntity newDeckEntity = new DeckEntity(name, isCommanderDeck);
        repository.insertDeckEntity(newDeckEntity);
    }

}
