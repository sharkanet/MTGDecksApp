package com.chris.mtgdecksapp.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MTGAppRepository {
//    private CardEntityDao cardEntityDao;
//    private CardInDeckEntityDao cardInDeckEntityDao;
//    private CardSupertypeEntityDao cardSupertypeEntityDao;
//    private CardTypeEntityDao cardTypeEntityDao;
//    private DeckEntityDao deckEntityDao;
//    private GameEntityDao gameEntityDao;
//    private SupertypeEntityDao supertypeEntityDao;
//    private TypeEntityDao typeEntityDao;
    private MTGAppDatabase mtgAppDatabase;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
// the all lists
    private LiveData<List<CardEntity>> allCardEntity;
    private LiveData<List<CardInDeckEntity>> allCardInDeckEntity;
    private LiveData<List<CardSupertypeEntity>> allCardSupertypeEntity;
    private LiveData<List<CardTypeEntity>> allCardTypeEntity;
    private LiveData<List<DeckEntity>> allDeckEntity;
    private LiveData<List<GameEntity>> allGameEntity;
    private LiveData<List<SupertypeEntity>> allSuperTypeEntity;
    private LiveData<List<TypeEntity>> allTypeEntity;

//singleton
    private static MTGAppRepository instance;
    private static final Object LOCK = new Object();
    public static MTGAppRepository getInstance(Context context){
        if(instance == null){
            synchronized (LOCK){
                if(instance== null){
                    instance = new MTGAppRepository(context);
                }
            }
        }
        return instance;
    }

//private constructor
    private  MTGAppRepository (Context context){
        mtgAppDatabase = MTGAppDatabase.getDatabase(context);
        //retrieve all lists from database
        allCardEntity = mtgAppDatabase.CardEntityDao().getAllCardEntity();
        allCardInDeckEntity = mtgAppDatabase.CardInDeckEntityDao().getAllCardInDeckEntity();
        allCardSupertypeEntity = mtgAppDatabase.CardSupertypeEntityDao().getAllCardSupertypeEntity();
        allCardTypeEntity = mtgAppDatabase.CardTypeEntityDao().getAllCardTypeEntity();
        allDeckEntity = mtgAppDatabase.DeckEntityDao().getAllDeckEntity();
        allGameEntity = mtgAppDatabase.GameEntityDao().getAllGameEntity();
        allSuperTypeEntity = mtgAppDatabase.SupertypeEntityDao().getAllSupertypeEntity();
        allTypeEntity =mtgAppDatabase.TypeEntityDao().getAllTypeEntity();
    }
//TODO
    //insert
    //update
    //delete


//getters
    public LiveData<List<CardEntity>> getAllCardEntity() {
        return allCardEntity;
    }

    public LiveData<List<CardInDeckEntity>> getAllCardInDeckEntity() {
        return allCardInDeckEntity;
    }

    public LiveData<List<CardSupertypeEntity>> getAllCardSupertypeEntity() {
        return allCardSupertypeEntity;
    }

    public LiveData<List<CardTypeEntity>> getAllCardTypeEntity() {
        return allCardTypeEntity;
    }

    public LiveData<List<DeckEntity>> getAllDeckEntity() {
        return allDeckEntity;
    }

    public LiveData<List<GameEntity>> getAllGameEntity() {
        return allGameEntity;
    }

    public LiveData<List<SupertypeEntity>> getAllSuperTypeEntity() {
        return allSuperTypeEntity;
    }

    public LiveData<List<TypeEntity>> getAllTypeEntity() {
        return allTypeEntity;
    }

}
