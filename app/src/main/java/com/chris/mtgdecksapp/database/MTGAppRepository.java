package com.chris.mtgdecksapp.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.chris.mtgdecksapp.model.CardInDeck;
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

    private LiveData<List<CardInDeck>> cardsInDeck;

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
        //TODO

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
    //retrieve list of card entity for a deck id
//    public void getCardsInDeckWithId(int id){
//        cardsInDeck = mtgAppDatabase.CardInDeckDao().getCardsInDeck(id);
//    }
    public LiveData<List<CardInDeck>> getCardsInDeckWithId(int id){
        return mtgAppDatabase.CardInDeckDao().getCardsInDeck(id);
    }

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

  //delete all from database
    public void clearDB(){
        //deleteAllCardEntity();
       // deleteAllDeckEntity();
        deleteAllCardInDeckEntity();
    }
    public void deleteAllDeckEntity(){
        executor.execute(()->{
            mtgAppDatabase.DeckEntityDao().deleteAllDeckEntity();
        });
    }
    public void deleteAllCardEntity(){
        executor.execute(()->{
            mtgAppDatabase.CardEntityDao().deleteAllCardEntity();
        });
    }
    public void deleteAllCardInDeckEntity(){
        executor.execute(()->{
            mtgAppDatabase.CardInDeckEntityDao().deleteAllCardInDeckEntity();
        });
    }
    public void deleteAllSupertypeEntity(){
        executor.execute(()->{
            mtgAppDatabase.SupertypeEntityDao().deleteAllSupertypeEntity();
        });
    }
    public void deleteAllTypeEntity(){
        executor.execute(()->{
            mtgAppDatabase.TypeEntityDao().deleteAllTypeEntity();
        });
    }
    public void deleteAllCardSupertypeEntity(){
        executor.execute(()->{
            mtgAppDatabase.CardSupertypeEntityDao().deleteAllCardSupertypeEntity();
        });
    }
    public void deleteAllCardTypeEntity(){
        executor.execute(()->{
            mtgAppDatabase.CardTypeEntityDao().deleteAllCardTypeEntity();
        });
    }
    public void deleteAllGameEntity(){
        executor.execute(()->{
            mtgAppDatabase.GameEntityDao().deleteAllGameEntity();
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

    public LiveData<List<CardInDeck>> getCardsInDeck() {
        return cardsInDeck;
    }

    //fake data
    public void putFakeData(){
        System.out.println("1");
      //  clearDB();
        insertDeckEntity(new DeckEntity(1,"placeholder 1"));
        insertDeckEntity(new DeckEntity(2,"placeholder 2"));
        insertDeckEntity(new DeckEntity(3,"placeholder 3"));
        insertCardEntity(new CardEntity(1, "placeholder 1", "{R}{R}{R}{3}","placeholder text 1", "n/a", "n/a", "n/a"));
        insertCardEntity(new CardEntity(2, "placeholder 2", "{R}{G}{B}{7}","placeholder text 2 \n more text 2", "n/a", "n/a", "n/a"));
        insertCardEntity(new CardEntity(3, "placeholder 3", "{W}{W}{W}{1}","placeholder text 3 IS VERY LONG!! \nanother \npotato", "n/a", "n/a", "n/a"));
        insertCardInDeckEntity(new CardInDeckEntity(1, 1, 1, 1,true));
        insertCardInDeckEntity(new CardInDeckEntity(2, 2, 1, 2,true));
        insertCardInDeckEntity(new CardInDeckEntity(3, 2, 2, 2,true));
        insertCardInDeckEntity(new CardInDeckEntity(4, 3, 2, 1,true));
        insertCardInDeckEntity(new CardInDeckEntity(5, 1, 3, 1,true));
        insertCardInDeckEntity(new CardInDeckEntity(6, 3, 3, 99,true));
        System.out.println("put fake data");

    }
}
