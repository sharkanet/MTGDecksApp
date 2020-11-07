package com.chris.mtgdecksapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.chris.mtgdecksapp.database.CardEntity;
import com.chris.mtgdecksapp.database.CardSupertypeEntity;
import com.chris.mtgdecksapp.database.CardTypeEntity;
import com.chris.mtgdecksapp.database.MTGAppRepository;
import com.chris.mtgdecksapp.database.SupertypeEntity;
import com.chris.mtgdecksapp.database.TypeEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardEditViewModel extends AndroidViewModel {
    private MTGAppRepository repository;
    private LiveData<List<TypeEntity>> typeEntities;
    private LiveData<List<SupertypeEntity>> supertypeEntities;
    private LiveData<List<CardTypeEntity>> cardTypeEntities;
    private LiveData<List<CardSupertypeEntity>> cardSupertypeEntities;
    private List<CardTypeEntity> cardTypeEntitiesList = new ArrayList<>();
    private List<CardSupertypeEntity> cardSupertypeEntitiesList = new ArrayList<>();
    private Map<String, Integer> mapSupertypeToId = new HashMap<>();
    private Map<String, Integer> mapTypeToId = new HashMap<>();
    private Map<Integer, String> mapIdToSupertype = new HashMap<>();
    private Map<Integer, String> mapIdToType = new HashMap<>();
//    private Executor executor = Executors.newSingleThreadExecutor();

    public CardEditViewModel(@NonNull Application application){
        super(application);
        repository = MTGAppRepository.getInstance(application.getApplicationContext());
        typeEntities = repository.getAllTypeEntity();
        supertypeEntities = repository.getAllSuperTypeEntity();
        cardTypeEntities = repository.getAllCardTypeEntity();
        cardSupertypeEntities = repository.getAllCardSupertypeEntity();

    }

    public void loadCard(int id){
        cardSupertypeEntities = repository.getCardSupertypeForCardId(id);
        cardTypeEntities = repository.getCardTypeForCardId(id);
    }

    public void save(int id, String name, String manaCost, String text, String power, String toughness, String loyalty){
        CardEntity newCardEntity = new CardEntity(id, name, manaCost, text, power, toughness, loyalty);
        repository.updateCardEntity(newCardEntity);
    }

    public void save(int id, String name, String manaCost, String text, String power, String toughness, String loyalty, boolean isBasic){
        CardEntity newCardEntity = new CardEntity(id, name, manaCost, text, power, toughness, loyalty, isBasic);
        repository.updateCardEntity(newCardEntity);
    }

    public LiveData<List<TypeEntity>> getTypeEntities() {
        return typeEntities;
    }

    public LiveData<List<SupertypeEntity>> getSupertypeEntities() {
        return supertypeEntities;
    }

    public LiveData<List<CardTypeEntity>> getCardTypeEntities() {
        return cardTypeEntities;
    }

    public LiveData<List<CardSupertypeEntity>> getCardSupertypeEntities() {
        return cardSupertypeEntities;
    }


    public List<CardTypeEntity> getCardTypeEntitiesList() {
        return cardTypeEntitiesList;
    }

    public void setCardTypeEntitiesList(List<CardTypeEntity> cardTypeEntitiesList) {
        this.cardTypeEntitiesList = cardTypeEntitiesList;
    }

    public List<CardSupertypeEntity> getCardSupertypeEntitiesList() {
        return cardSupertypeEntitiesList;
    }

    public void setCardSupertypeEntitiesList(List<CardSupertypeEntity> cardSupertypeEntitiesList) {
        this.cardSupertypeEntitiesList = cardSupertypeEntitiesList;
    }

    public List<CardTypeEntity> getListCardTypeForCardId(int id){
        return repository.getListCardTypeForCardId(id);
    }
    public List<CardSupertypeEntity> getListCardSupertypeForCardId(int id){
        return repository.getListCardSupertypeForCardId(id);
    }

    public Map<String, Integer> getMapSupertypeToId() {
        return mapSupertypeToId;
    }

    public Map<String, Integer> getMapTypeToId() {
        return mapTypeToId;
    }

    public Map<Integer, String> getMapIdToSupertype() {
        return mapIdToSupertype;
    }

    public Map<Integer, String> getMapIdToType() {
        return mapIdToType;
    }

    public void insertCardSupertype(int cardId, int supertypeId){
        repository.insertCardSupertypeEntity(new CardSupertypeEntity(cardId, supertypeId));
    }
    public void insertCardType(int cardId, int typeId){
        repository.insertCardTypeEntity(new CardTypeEntity(cardId,typeId));
    }

    public long insertSupertypeEntityWithReturn(SupertypeEntity supertypeEntity){
        return repository.insertSupertypeEntityWithReturn(supertypeEntity);
    }

    public long insertTypeEntityWithReturn(TypeEntity typeEntity){
        return repository.insertTypeEntityWithReturn(typeEntity);
    }

    public void deleteCardTypeAndSupertypes(int id){
        repository.deleteCardTypeEntitiesForCardId(id);
        repository.deleteCardSupertypeEntitiesForCardId(id);
    }

    public void delete(int cardId){
        repository.deleteCardEntityById(cardId);
    }
}
