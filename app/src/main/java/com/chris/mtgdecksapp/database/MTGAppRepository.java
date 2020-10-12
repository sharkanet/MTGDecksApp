package com.chris.mtgdecksapp.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.chris.mtgdecksapp.model.Deck;

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
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    private MTGAppDatabase mtgAppDatabase;
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
////insert
    public void insertCardEntity(CardEntity cardEntity){
        executor.execute(()->{
            mtgAppDatabase.CardEntityDao().insertCardEntity(cardEntity);
        });
    }
    public void insertCardInDeckEntity(CardInDeckEntity cardInDeckEntity){
        executor.execute(()->{
            mtgAppDatabase.CardInDeckEntityDao().insertCardInDeckEntity(cardInDeckEntity);
        });
    }
    public void insertCardSupertypeEntity(CardSupertypeEntity cardSupertypeEntity){
        executor.execute(()->{
            mtgAppDatabase.CardSupertypeEntityDao().insertCardSupertypeEntity(cardSupertypeEntity);
        });
    }
    public void insertCardTypeEntity(CardTypeEntity cardTypeEntity){
        executor.execute(()->{
            mtgAppDatabase.CardTypeEntityDao().insertCardTypeEntity(cardTypeEntity);
        });
    }
    public void insertDeckEntity(DeckEntity deckEntity){
        executor.execute(()->{
            mtgAppDatabase.DeckEntityDao().insertDeckEntity(deckEntity);
        });
    }
    public void insertGameEntity(GameEntity gameEntity){
        executor.execute(()->{
            mtgAppDatabase.GameEntityDao().insertGameEntity(gameEntity);
        });
    }
    public void insertSupertypeEntity(SupertypeEntity supertypeEntity){
        executor.execute(()->{
            mtgAppDatabase.SupertypeEntityDao().insertSupertypeEntity(supertypeEntity);
        });
    }
    public void insertTypeEntity(TypeEntity typeEntity){
        executor.execute(()->{
            mtgAppDatabase.TypeEntityDao().insertTypeEntity(typeEntity);
        });
    }

////update
    public void updateCardEntity(CardEntity cardEntity){
        executor.execute(()->{
            mtgAppDatabase.CardEntityDao().updateCardEntity(cardEntity);
        });
    }
    public void updateCardInDeckEntity(CardInDeckEntity cardInDeckEntity){
        executor.execute(()->{
            mtgAppDatabase.CardInDeckEntityDao().updateCardInDeckEntity(cardInDeckEntity);
        });
    }
    public void updateCardSupertypeEntity(CardSupertypeEntity cardSupertypeEntity){
        executor.execute(()->{
            mtgAppDatabase.CardSupertypeEntityDao().updateCardSupertypeEntity(cardSupertypeEntity);
        });
    }
    public void updateCardTypeEntity(CardTypeEntity cardTypeEntity){
        executor.execute(()->{
            mtgAppDatabase.CardTypeEntityDao().updateCardTypeEntity(cardTypeEntity);
        });
    }
    public void updateDeckEntity(DeckEntity deckEntity){
        executor.execute(()->{
            mtgAppDatabase.DeckEntityDao().updateDeckEntity(deckEntity);
        });
    }
    public void updateGameEntity(GameEntity gameEntity){
        executor.execute(()->{
            mtgAppDatabase.GameEntityDao().updateGameEntity(gameEntity);
        });
    }
    public void updateSupertypeEntity(SupertypeEntity supertypeEntity){
        executor.execute(()->{
            mtgAppDatabase.SupertypeEntityDao().updateSupertypeEntity(supertypeEntity);
        });
    }
    public void updateTypeEntity(TypeEntity typeEntity){
        executor.execute(()->{
            mtgAppDatabase.TypeEntityDao().updateTypeEntity(typeEntity);
        });
    }

////delete by object
public void deleteCardEntity(CardEntity cardEntity){
    executor.execute(()->{
        mtgAppDatabase.CardEntityDao().deleteCardEntity(cardEntity);
    });
}
    public void deleteCardInDeckEntity(CardInDeckEntity cardInDeckEntity){
        executor.execute(()->{
            mtgAppDatabase.CardInDeckEntityDao().deleteCardInDeckEntity(cardInDeckEntity);
        });
    }
    public void deleteCardSupertypeEntity(CardSupertypeEntity cardSupertypeEntity){
        executor.execute(()->{
            mtgAppDatabase.CardSupertypeEntityDao().deleteCardSupertypeEntity(cardSupertypeEntity);
        });
    }
    public void deleteCardTypeEntity(CardTypeEntity cardTypeEntity){
        executor.execute(()->{
            mtgAppDatabase.CardTypeEntityDao().deleteCardTypeEntity(cardTypeEntity);
        });
    }
    public void deleteDeckEntity(DeckEntity deckEntity){
        executor.execute(()->{
            mtgAppDatabase.DeckEntityDao().deleteDeckEntity(deckEntity);
        });
    }
    public void deleteGameEntity(GameEntity gameEntity){
        executor.execute(()->{
            mtgAppDatabase.GameEntityDao().deleteGameEntity(gameEntity);
        });
    }
    public void deleteSupertypeEntity(SupertypeEntity supertypeEntity){
        executor.execute(()->{
            mtgAppDatabase.SupertypeEntityDao().deleteSupertypeEntity(supertypeEntity);
        });
    }
    public void deleteTypeEntity(TypeEntity typeEntity){
        executor.execute(()->{
            mtgAppDatabase.TypeEntityDao().deleteTypeEntity(typeEntity);
        });
    }


//getters
    public LiveData<List<CardEntity>> getAllCardEntity() {
        return allCardEntity;
    }

    public LiveData<List<CardInDeckEntity>> getAllCardInDeckEntity() {
        return allCardInDeckEntity;
    }

    public LiveData<List<CardSupertypeEntity>> getAllCardSupertypeEntity() {  return allCardSupertypeEntity; }

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
