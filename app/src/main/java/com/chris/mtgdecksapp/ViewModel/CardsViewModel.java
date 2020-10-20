package com.chris.mtgdecksapp.ViewModel;

import android.app.Application;
import android.mtp.MtpConstants;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.chris.mtgdecksapp.database.CardEntity;
import com.chris.mtgdecksapp.database.MTGAppRepository;

import java.util.List;

public class CardsViewModel extends AndroidViewModel {
    private MTGAppRepository repository;
    private LiveData<List<CardEntity>> allCardEntity;

    public CardsViewModel(@NonNull Application application){
        super(application);
        repository = MTGAppRepository.getInstance(application.getApplicationContext());
        allCardEntity = repository.getAllCardEntity();
    }

    public LiveData<List<CardEntity>> getAllCardEntity() {
        return allCardEntity;
    }
}
