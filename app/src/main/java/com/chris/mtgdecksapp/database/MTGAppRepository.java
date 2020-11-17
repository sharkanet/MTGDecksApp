package com.chris.mtgdecksapp.database;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Query;

import com.chris.mtgdecksapp.model.CardInDeck;
import com.chris.mtgdecksapp.model.CardSupertype;
import com.chris.mtgdecksapp.model.CardType;
import com.chris.mtgdecksapp.model.Game;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
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
    private LiveData<List<CardSupertype>> cardSupertypes;
    private LiveData<List<CardType>> cardTypes;

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

    public LiveData<List<Game>> getAllGamesOrderedByDate(){
        return mtgAppDatabase.GameDao().getAllGamesOrderedByDate();
    }
    public LiveData<List<Game>> getAllGamesOrderedByWin() {
        return mtgAppDatabase.GameDao().getAllGamesOrderedByWin();
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
    public LiveData<List<CardType>> getCardTypesForCardId(int id){
//        cardTypes = mtgAppDatabase.CardTypeDao().getCardTypes(id);
//        return cardTypes;
        return mtgAppDatabase.CardTypeDao().getCardTypes(id);
    }
    public LiveData<List<CardSupertype>> getCardSupertypesForCardId(int id){
//        cardSupertypes = mtgAppDatabase.CardSupertypeDao().getCardSupertypes(id);
//        return cardSupertypes;
        return mtgAppDatabase.CardSupertypeDao().getCardSupertypes(id);
    }
    public List<CardType> getCardTypesListForCardId(int id){
        return mtgAppDatabase.CardTypeDao().getCardTypesList(id);
    }
    public List<CardSupertype> getCardSupertypeListForCardId(int id){
        return mtgAppDatabase.CardSupertypeDao().getCardSupertypesList(id);
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
    public void deleteGameEntityById(int gameId) {
        executor.execute(()->{
            mtgAppDatabase.GameEntityDao().deleteGameEntityById(gameId);
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
    public void putTestData(){
       // clearDB();
        insertDeckEntity(new DeckEntity(1,"Generic Deck", false));
        insertDeckEntity(new DeckEntity(2,"Commander Deck", true));
        insertDeckEntity(new DeckEntity(3,"placeholder 3", false));
        insertDeckEntity(new DeckEntity(4, "commander deck", true));
        insertCardEntity(new CardEntity(1, "Forest", "0","Forest", "n/a", "n/a", "n/a", true));
        insertCardEntity(new CardEntity(2, "Jace", "{U}{U}{U}{3}","placeholder text 2 \n more text 2", "n/a", "n/a", "5", false));
        insertCardEntity(new CardEntity(3, "Some Card", "{W}{B}{1}","placeholder text 3 IS VERY LONG!! \nanother \nline", "n/a", "n/a", "n/a", false));
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
        insertTypeEntity(new TypeEntity(1, "Forest"));
        insertTypeEntity(new TypeEntity(2, "Plains"));
        insertTypeEntity(new TypeEntity(3, "Island"));
        insertTypeEntity(new TypeEntity(4, "Swamp"));
        insertTypeEntity(new TypeEntity(5, "Mountain"));
        insertTypeEntity(new TypeEntity(6, "Human"));
        insertTypeEntity(new TypeEntity(7, "Jace"));
        insertTypeEntity(new TypeEntity(8, "Equipment"));
        insertCardSupertypeEntity(new CardSupertypeEntity(1,1));
        insertCardSupertypeEntity(new CardSupertypeEntity(1,2));
        insertCardSupertypeEntity(new CardSupertypeEntity(2,10));
        insertCardSupertypeEntity(new CardSupertypeEntity(2,6));
        insertCardSupertypeEntity(new CardSupertypeEntity(3,10));
        insertCardSupertypeEntity(new CardSupertypeEntity(3,4));
        insertCardTypeEntity(new CardTypeEntity(1,1));
        insertCardTypeEntity(new CardTypeEntity(2,8));
        insertCardTypeEntity(new CardTypeEntity(3,9));
        insertGameEntity(new GameEntity(1,1,"opponent", "opponent","Win"));
        insertGameEntity(new GameEntity(2,1,"opponent", "opponent","Win"));
        insertGameEntity(new GameEntity(3,2,"opponent", "opponent","Lose"));

    }

    public void initializeTestData(){
        executor.execute(()->{
            mtgAppDatabase.SupertypeEntityDao().deleteAllSupertypeEntity();
            mtgAppDatabase.TypeEntityDao().deleteAllTypeEntity();
            mtgAppDatabase.DeckEntityDao().deleteAllDeckEntity();
            mtgAppDatabase.CardEntityDao().deleteAllCardEntity();
            mtgAppDatabase.SupertypeEntityDao().insertSupertypeEntity(new SupertypeEntity(1, "Basic"));
            mtgAppDatabase.SupertypeEntityDao().insertSupertypeEntity(new SupertypeEntity(2, "Land"));
            mtgAppDatabase.SupertypeEntityDao().insertSupertypeEntity(new SupertypeEntity(3,"Creature"));
            mtgAppDatabase.SupertypeEntityDao().insertSupertypeEntity(new SupertypeEntity(4,"Artifact"));
            mtgAppDatabase.SupertypeEntityDao().insertSupertypeEntity(new SupertypeEntity(5,"Enchantment"));
            mtgAppDatabase.SupertypeEntityDao().insertSupertypeEntity(new SupertypeEntity(6, "Planeswalker"));
            mtgAppDatabase.SupertypeEntityDao().insertSupertypeEntity(new SupertypeEntity(7, "Instant"));
            mtgAppDatabase.SupertypeEntityDao().insertSupertypeEntity(new SupertypeEntity(8, "Sorcery"));
            mtgAppDatabase.SupertypeEntityDao().insertSupertypeEntity(new SupertypeEntity(9, "Tribal"));
            mtgAppDatabase.SupertypeEntityDao().insertSupertypeEntity(new SupertypeEntity(10, "Legendary"));
            mtgAppDatabase.TypeEntityDao().insertTypeEntity(new TypeEntity(1, "Forest"));
            mtgAppDatabase.TypeEntityDao().insertTypeEntity(new TypeEntity(2, "Plains"));
            mtgAppDatabase.TypeEntityDao().insertTypeEntity(new TypeEntity(3, "Island"));
            mtgAppDatabase.TypeEntityDao().insertTypeEntity(new TypeEntity(4, "Swamp"));
            mtgAppDatabase.TypeEntityDao().insertTypeEntity(new TypeEntity(5, "Mountain"));
            mtgAppDatabase.TypeEntityDao().insertTypeEntity(new TypeEntity(6, "Human"));
            mtgAppDatabase.TypeEntityDao().insertTypeEntity(new TypeEntity(7, "Jace"));
            mtgAppDatabase.TypeEntityDao().insertTypeEntity(new TypeEntity(8, "Equipment"));
            mtgAppDatabase.DeckEntityDao().insertDeckEntity(new DeckEntity(1,"Generic Deck", false));
            mtgAppDatabase.DeckEntityDao().insertDeckEntity(new DeckEntity(2,"Commander Deck", true));
            mtgAppDatabase.CardEntityDao().insertCardEntity(new CardEntity(1, "Forest", "0","{T}: Add {G} to mana pool", "n/a", "n/a", "n/a", true));
            mtgAppDatabase.CardEntityDao().insertCardEntity(new CardEntity(2, "Jace", "{U}{U}{U}{3}","placeholder text \n more text", "n/a", "n/a", "5", false));
            mtgAppDatabase.CardEntityDao().insertCardEntity(new CardEntity(3, "Some Card", "{W}{B}{1}","placeholder text 3\nanother \nline", "n/a", "n/a", "n/a", false));
            mtgAppDatabase.CardSupertypeEntityDao().insertCardSupertypeEntity(new CardSupertypeEntity(1,1));
            mtgAppDatabase.CardSupertypeEntityDao().insertCardSupertypeEntity(new CardSupertypeEntity(1,2));
            mtgAppDatabase.CardSupertypeEntityDao().insertCardSupertypeEntity(new CardSupertypeEntity(2,10));
            mtgAppDatabase.CardSupertypeEntityDao().insertCardSupertypeEntity(new CardSupertypeEntity(2,6));
            mtgAppDatabase.CardSupertypeEntityDao().insertCardSupertypeEntity(new CardSupertypeEntity(3,10));
            mtgAppDatabase.CardSupertypeEntityDao().insertCardSupertypeEntity(new CardSupertypeEntity(3,4));
            mtgAppDatabase.CardTypeEntityDao().insertCardTypeEntity(new CardTypeEntity(1,1));
            mtgAppDatabase.CardTypeEntityDao().insertCardTypeEntity(new CardTypeEntity(2,7));
            mtgAppDatabase.CardTypeEntityDao().insertCardTypeEntity(new CardTypeEntity(3,8));
            Calendar calendar = Calendar.getInstance();
            mtgAppDatabase.GameEntityDao().insertGameEntity(new GameEntity(1,1,"Opponent", "Opponent Deck","Win", calendar.getTime()));
            calendar.add(Calendar.MINUTE,10);
            mtgAppDatabase.GameEntityDao().insertGameEntity(new GameEntity(2,1,"Opponent", "Opponent Deck B","Lose", calendar.getTime()));
            calendar.add(Calendar.MINUTE,10);
            mtgAppDatabase.GameEntityDao().insertGameEntity(new GameEntity(3,2,"Opponent", "Opponent Deck C","Lose", calendar.getTime()));
            calendar.add(Calendar.MINUTE,10);
            mtgAppDatabase.GameEntityDao().insertGameEntity(new GameEntity(4,2,"Guy", "Some Deck","Win", calendar.getTime()));
        });
    }



}
