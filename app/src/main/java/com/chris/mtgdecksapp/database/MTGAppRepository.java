package com.chris.mtgdecksapp.database;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Query;

import com.chris.mtgdecksapp.model.CardInDeck;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class MTGAppRepository {
//    private CardEntityDao cardEntityDao;
//    private CardInDeckEntityDao cardInDeckEntityDao;
//    private CardSupertypeEntityDao cardSupertypeEntityDao;
//    private CardTypeEntityDao cardTypeEntityDao;
//    private DeckEntityDao deckEntityDao;
//    private GameEntityDao gameEntityDao;
//    private SupertypeEntityDao supertypeEntityDao;
//    private TypeEntityDao typeEntityDao;
    private static final int NUMBER_OF_THREADS = 1;
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

//    private Map<String, Integer> mapCardNameToId = new HashMap<>();
//    private Map<String, Integer> mapTypeToId = new HashMap<>();
//    private Map<String, Integer> mapSupertypeToId = new HashMap<>();

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

        //setup maps
//        setupMaps();
    }
/*
    private void setupMaps() {
        final Observer<List<CardEntity>> cardObserver = newCards ->{
            mapCardNameToId.clear();
            newCards.forEach( cardEntity -> {
                mapCardNameToId.put(cardEntity.getName(), cardEntity.getCardId());
            });
        };
        allCardEntity.observeForever(cardObserver);


        final Observer<List<TypeEntity>> typeObserver = newTypes ->{
            mapTypeToId.clear();
            newTypes.forEach( typeEntity -> {
                mapCardNameToId.put(typeEntity.getType(), typeEntity.getTypeId());
            });
        };
        allTypeEntity.observeForever(typeObserver);

        final Observer<List<SupertypeEntity>> superTypeObserver = newSupertypes ->{
            mapSupertypeToId.clear();
            newSupertypes.forEach( supertypeEntity -> {
                mapCardNameToId.put(supertypeEntity.getSupertype(), supertypeEntity.getSupertypeId());
            });
        };
        allSuperTypeEntity.observeForever(superTypeObserver);
    }

 */

    //TODO
    //retrieve list of card entity for a deck id
//    public void getCardsInDeckWithId(int id){
//        cardsInDeck = mtgAppDatabase.CardInDeckDao().getCardsInDeck(id);
//    }
    public LiveData<List<CardInDeck>> getCardsInDeckWithId(int id){
        return mtgAppDatabase.CardInDeckDao().getCardsInDeck(id);
    }
    //get games for deck id
    public LiveData<List<GameEntity>> getGamesForDeckId(int id){
        return mtgAppDatabase.GameEntityDao().getAllGameEntityForDeck(id);
    }
    //get wins and losses for deck
    public int getWinCountForDeck(int id){
        return mtgAppDatabase.GameEntityDao().getWinCountForDeck(id);
    }
    public int getLoseCountForDeck(int id){
        return mtgAppDatabase.GameEntityDao().getLoseCountForDeck(id);
    }

    //retrieve lists of type and supertype for card id
    public LiveData<List<CardTypeEntity>> getCardTypeForCardId(int id){
        return mtgAppDatabase.CardTypeEntityDao().getCardTypeEntityForCardId(id);
    }
    public LiveData<List<CardSupertypeEntity>> getCardSupertypeForCardId(int id){
        return mtgAppDatabase.CardSupertypeEntityDao().getCardSupertypeEntityForCardId(id);
    }
    public List<CardTypeEntity> getListCardTypeForCardId(int id){
        return mtgAppDatabase.CardTypeEntityDao().getListCardTypeEntityForCardId(id);
    }
    public List<CardSupertypeEntity> getListCardSupertypeForCardId(int id){
        return mtgAppDatabase.CardSupertypeEntityDao().getListCardSupertypeEntityForCardId(id);
    }



    //retrieve full lists of entities
    public LiveData<List<CardEntity>> retrieveAllCardEntity() {
        return mtgAppDatabase.CardEntityDao().getAllCardEntity();
    }
    public LiveData<List<CardInDeckEntity>> retrieveAllCardInDeckEntity() {
       return allCardInDeckEntity = mtgAppDatabase.CardInDeckEntityDao().getAllCardInDeckEntity();
    }
    public LiveData<List<CardSupertypeEntity>> retrieveAllCardSupertypeEntity() {
        return allCardSupertypeEntity = mtgAppDatabase.CardSupertypeEntityDao().getAllCardSupertypeEntity();
    }
    public LiveData<List<CardTypeEntity>> retrieveAllCardTypeEntity() {
        return mtgAppDatabase.CardTypeEntityDao().getAllCardTypeEntity();
    }
    public LiveData<List<DeckEntity>> retrieveAllDeckEntity(){
            return mtgAppDatabase.DeckEntityDao().getAllDeckEntity();
    }
//    return mtgAppDatabase.GameEntityDao().getAllGameEntity();
//    return mtgAppDatabase.SupertypeEntityDao().getAllSupertypeEntity();
//    return mtgAppDatabase.TypeEntityDao().getAllTypeEntity();

////insert
    public long insertCardEntityWithReturn(CardEntity cardEntity){
        return mtgAppDatabase.CardEntityDao().insertCardEntity(cardEntity);
    }

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
    public long insertSupertypeEntityWithReturn(SupertypeEntity supertypeEntity){
         return  mtgAppDatabase.SupertypeEntityDao().insertSupertypeEntity(supertypeEntity);
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
    public long insertTypeEntityWithReturn(TypeEntity typeEntity){
        return  mtgAppDatabase.TypeEntityDao().insertTypeEntity(typeEntity);
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

  //delete by id
    public void deleteDeckById(int id){
         executor.execute(()->{
             mtgAppDatabase.DeckEntityDao().deleteDeckEntity(id);
         });
    }
    public void deleteCardInDeckById(int cardId, int deckId){
        executor.execute(()->{
            mtgAppDatabase.CardInDeckEntityDao().deleteCardInDeckEntityById(cardId, deckId);
        });
    }
    public void deleteCardEntityById(int cardId) {
        executor.execute(()->{
            mtgAppDatabase.CardEntityDao().deleteCardEntityById(cardId);
        });
    }

  //delete all from database
    public void clearDB(){
        deleteAllTypeEntity();
        deleteAllSupertypeEntity();
        deleteAllCardEntity();
        deleteAllDeckEntity();
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

    //
    public void deleteCardSupertypeEntitiesForCardId(int cardId){
        executor.execute(()->{
            mtgAppDatabase.CardSupertypeEntityDao().deleteCardSupertypeEntityForCardId(cardId);
        });
    }
    public void deleteCardTypeEntitiesForCardId(int cardId){
        executor.execute(()->{
            mtgAppDatabase.CardTypeEntityDao().deleteCardTypeEntityForCardId(cardId);
        });
    }

    public MutableLiveData<CardEntity> getCardEntityByName(String cardName){
        MutableLiveData<CardEntity> cardEntityMutableLiveData = new MutableLiveData<>();
        executor.execute(()->{
            cardEntityMutableLiveData.postValue(mtgAppDatabase.CardEntityDao().getCardEntityByName(cardName));
        });
        return cardEntityMutableLiveData;
    }

    public CardEntity getCardEntityByName1(String cardName) {
       return mtgAppDatabase.CardEntityDao().getCardEntityByName(cardName);
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
       // clearDB();
        insertDeckEntity(new DeckEntity(1,"placeholder 1"));
        insertDeckEntity(new DeckEntity(2,"placeholder 2"));
        insertDeckEntity(new DeckEntity(3,"placeholder 3"));
        insertCardEntity(new CardEntity(1, "placeholder 1", "{R}{R}{R}{3}","placeholder text 1", "n/a", "n/a", "n/a"));
        insertCardEntity(new CardEntity(2, "placeholder 2", "{R}{G}{B}{7}","placeholder text 2 \n more text 2", "n/a", "n/a", "n/a"));
        insertCardEntity(new CardEntity(3, "placeholder 3", "{W}{W}{W}{1}","placeholder text 3 IS VERY LONG!! \nanother \npotato", "n/a", "n/a", "n/a"));
        insertCardInDeckEntity(new CardInDeckEntity(1, 1, 1,true));
        insertCardInDeckEntity(new CardInDeckEntity(2, 1, 2,true));
        insertCardInDeckEntity(new CardInDeckEntity(2, 2, 2,true));
        insertCardInDeckEntity(new CardInDeckEntity(3, 2, 1,true));
        insertCardInDeckEntity(new CardInDeckEntity(1, 3, 1,true));
        insertCardInDeckEntity(new CardInDeckEntity(3, 3, 99,true));
        insertSupertypeEntity(new SupertypeEntity(1, "Basic"));
        insertSupertypeEntity(new SupertypeEntity(2, "Land"));
        insertSupertypeEntity(new SupertypeEntity(3,"Creature"));
        insertSupertypeEntity(new SupertypeEntity(4,"Artifact"));
        insertSupertypeEntity(new SupertypeEntity(5,"Enchantment"));
        insertSupertypeEntity(new SupertypeEntity(6, "Planeswalker"));
        insertSupertypeEntity(new SupertypeEntity(7, "Instant"));
        insertSupertypeEntity(new SupertypeEntity(8, "Sorcery"));
        insertSupertypeEntity(new SupertypeEntity(9, "Tribal"));
        insertSupertypeEntity(new SupertypeEntity(10, "Legendary"));
        insertTypeEntity(new TypeEntity(1, "Human"));
        insertTypeEntity(new TypeEntity(2, "Jace"));
        insertTypeEntity(new TypeEntity(3, "Forest"));
        insertTypeEntity(new TypeEntity(4, "Equipment"));
        insertGameEntity(new GameEntity(1,1,"opponent", "opponent","Win"));
        insertGameEntity(new GameEntity(2,1,"opponent", "opponent","Win"));
        insertGameEntity(new GameEntity(3,2,"opponent", "opponent","Lose"));


        System.out.println("put fake data");

    }



}
