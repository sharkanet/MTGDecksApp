package com.chris.mtgdecksapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chris.mtgdecksapp.database.CardEntity;
import com.chris.mtgdecksapp.database.CardSupertypeEntity;
import com.chris.mtgdecksapp.database.CardTypeEntity;
import com.chris.mtgdecksapp.database.MTGAppRepository;
import com.chris.mtgdecksapp.database.SupertypeEntity;
import com.chris.mtgdecksapp.database.TypeEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CardAddViewModel extends AndroidViewModel {
    private MTGAppRepository repository;
    private MutableLiveData<CardEntity> cardEntity = new MutableLiveData<>();
    private LiveData<List<TypeEntity>> typeEntities;
    private LiveData<List<SupertypeEntity>> supertypeEntities;
    private LiveData<List<CardEntity>> cardEntities, cardEntitiesPostAdd;
    private Executor executor = Executors.newSingleThreadExecutor();

    public CardAddViewModel(@NonNull Application application){
        super(application);
        repository = MTGAppRepository.getInstance(application.getApplicationContext());
        typeEntities = repository.getAllTypeEntity();
        supertypeEntities = repository.getAllSuperTypeEntity();
        cardEntities = repository.getAllCardEntity();
//        repository.getAllSuperTypeEntity().getValue().forEach( supertypeEntity -> {
//            mapSupertypeToId.put(supertypeEntity.getSupertype().trim(), supertypeEntity.getSupertypeId());
//        });
//        repository.getAllTypeEntity().getValue().forEach( typeEntity -> {
//            mapTypeToId.put(typeEntity.getType().trim(),typeEntity.getTypeId());
//        });
//        repository.getAllCardEntity().getValue().forEach( cardEntity1 -> {
//            mapCardToId.put(cardEntity1.getName().trim(), cardEntity1.getCardId());
//        });
    }

    public long save(String name, String manaCost, String text, String power, String toughness, String loyalty){
        CardEntity newCardEntity = new CardEntity(name, manaCost, text, power, toughness, loyalty);
        return repository.insertCardEntityWithReturn(newCardEntity);
    }

    public void save(int id, String name, String manaCost, String text, String power, String toughness, String loyalty, boolean isBasic){
        CardEntity newCardEntity = new CardEntity(id, name, manaCost, text, power, toughness, loyalty, isBasic);
        repository.updateCardEntity(newCardEntity);
    }
/*
    public void insertCardSupertype(String cardName, String supertype){
//        if(!repository.getMapSupertypeToId().containsKey(supertype)){
//            repository.insertSupertypeEntity(new SupertypeEntity(supertype));
//        }
//        Integer supertypeId = repository.getMapSupertypeToId().get(supertype);
//        CardSupertypeEntity cardSupertypeEntity = new CardSupertypeEntity(repository.getMapCardNameToId().get(cardName), supertypeId);
//        repository.insertCardSupertypeEntity(cardSupertypeEntity);
        if(mapSupertypeToId.containsKey(supertype)){
//            Integer supertypeId = mapSupertypeToId.get(supertype);
            CardSupertypeEntity cardSupertypeEntity = new CardSupertypeEntity(mapCardToId.get(cardName), mapSupertypeToId.get(supertype));
            repository.insertCardSupertypeEntity(cardSupertypeEntity);
        } else {
            repository.insertSupertypeEntity(new SupertypeEntity(supertype));
            repository.getAllSuperTypeEntity().getValue().forEach( supertypeEntity -> {
                mapSupertypeToId.put(supertypeEntity.getSupertype().trim(), supertypeEntity.getSupertypeId());
            });
//            Integer supertypeId = mapSupertypeToId.get(supertype);
            CardSupertypeEntity cardSupertypeEntity = new CardSupertypeEntity(mapCardToId.get(cardName), mapSupertypeToId.get(supertype));
            repository.insertCardSupertypeEntity(cardSupertypeEntity);
        }

    }

 */
    public void insertCardSupertype(int cardId, int supertypeId){
        repository.insertCardSupertypeEntity(new CardSupertypeEntity(cardId, supertypeId));
    }
/*
    public void insertCardType(String cardName, String type){
//        if(!repository.getMapTypeToId().containsKey(type)){
//            repository.insertTypeEntity(new TypeEntity(type));
//        }
//        Integer typeId = repository.getMapTypeToId().get(type);
//        CardTypeEntity cardTypeEntity = new CardTypeEntity(repository.getMapCardNameToId().get(cardName), typeId);
//        repository.insertCardTypeEntity(cardTypeEntity);
        if(mapTypeToId.containsKey(type)){
 //           Integer typeId = mapTypeToId.get(type);
            CardTypeEntity cardTypeEntity = new CardTypeEntity(mapCardToId.get(cardName), mapTypeToId.get(type));
            repository.insertCardTypeEntity(cardTypeEntity);
        } else {
            repository.insertTypeEntity(new TypeEntity(type));
            repository.getAllTypeEntity().getValue().forEach( typeEntity -> {
                mapTypeToId.put(typeEntity.getType().trim(), typeEntity.getTypeId());
            });
 //           Integer typeId = mapTypeToId.get(type);
            CardTypeEntity cardTypeEntity = new CardTypeEntity(mapCardToId.get(cardName), mapSupertypeToId.get(type));
            repository.insertCardTypeEntity(cardTypeEntity);
        }

    }

 */
    public void insertCardType(int cardId, int typeId){
        repository.insertCardTypeEntity(new CardTypeEntity(cardId,typeId));
    }

    public void deleteCardSupertypeForCard(int cardId){
        repository.deleteCardSupertypeEntitiesForCardId(cardId);
    }
    public void deleteCardTypeForCard(int cardId){
        repository.deleteCardTypeEntitiesForCardId(cardId);
    }


    public LiveData<List<TypeEntity>> getTypeEntities() {
        return typeEntities;
    }

    public LiveData<List<SupertypeEntity>> getSupertypeEntities() {
        return supertypeEntities;
    }

    public MTGAppRepository getRepository(){
        return repository;
    }

    public void loadCard(String cardName){
        cardEntity = repository.getCardEntityByName(cardName);
    }

    public MutableLiveData<CardEntity> getCardEntity() {
        return cardEntity;
    }

    public CardEntity getCardEntityByName(String cardName){executor.execute(()->{});
        return repository.getCardEntityByName1(cardName);
    }

    public LiveData<List<CardEntity>> getCardEntities() {
        return cardEntities;
    }

    public LiveData<List<CardEntity>> getCardEntitiesPostAdd(){
        cardEntitiesPostAdd =repository.retrieveAllCardEntity();
        return cardEntitiesPostAdd;
    }

    public long insertSupertypeEntityWithReturn(SupertypeEntity supertypeEntity){
        return repository.insertSupertypeEntityWithReturn(supertypeEntity);
    }

    public long insertTypeEntityWithReturn(TypeEntity typeEntity){
        return repository.insertTypeEntityWithReturn(typeEntity);
    }
}
