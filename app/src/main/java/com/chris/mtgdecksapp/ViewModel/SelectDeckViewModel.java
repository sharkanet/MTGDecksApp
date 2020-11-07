package com.chris.mtgdecksapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.chris.mtgdecksapp.database.DeckEntity;
import com.chris.mtgdecksapp.database.MTGAppRepository;

import java.util.List;

public class SelectDeckViewModel extends AndroidViewModel {
    private MTGAppRepository repository;


    private LiveData<List<DeckEntity>> allDecks;

    public SelectDeckViewModel(@NonNull Application application){
        super(application);
        repository = MTGAppRepository.getInstance(application.getApplicationContext());
        allDecks = repository.getAllDeckEntity();

    }

    public LiveData<List<DeckEntity>> getAllDecks() {
        return allDecks;
    }

}
