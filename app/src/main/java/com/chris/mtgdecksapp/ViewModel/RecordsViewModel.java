package com.chris.mtgdecksapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.chris.mtgdecksapp.R;

import com.chris.mtgdecksapp.database.GameEntity;
import com.chris.mtgdecksapp.database.MTGAppRepository;

import java.util.List;

public class RecordsViewModel extends AndroidViewModel {
    private MTGAppRepository repository;
    private LiveData<List<GameEntity>> games;

    public RecordsViewModel(@NonNull Application application){
        super(application);
        repository = MTGAppRepository.getInstance(application.getApplicationContext());

    }
    public void loadGames(int id){
        games = repository.getGamesForDeckId(id);
    }

    public LiveData<List<GameEntity>> getGames() {
        return games;
    }
}
