package com.chris.mtgdecksapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.chris.mtgdecksapp.database.GameEntity;
import com.chris.mtgdecksapp.database.MTGAppRepository;
import com.chris.mtgdecksapp.model.Game;

import java.util.List;

public class ReportsViewModel extends AndroidViewModel {
    private MTGAppRepository repository;
    private LiveData<List<Game>> games;

    public ReportsViewModel(@NonNull Application application){
        super(application);
        repository = MTGAppRepository.getInstance(application.getApplicationContext());
        games = repository.getAllGamesOrderedByDate();
    }

    public void getAllGamesOrderedByWin(){
        games = repository.getAllGamesOrderedByWin();
    }
    public void getAllGamesOrderedByDate(){
        games = repository.getAllGamesOrderedByDate();
    }

    public LiveData<List<Game>> getGames() {
        return games;
    }

}
