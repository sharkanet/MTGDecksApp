package com.chris.mtgdecksapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.chris.mtgdecksapp.model.CardType;

import java.util.List;

@Dao
public interface CardTypeDao {
    //select types for card with id
    @Query("SELECT cardType.*, type.type  FROM cardType INNER JOIN type ON cardType.typeId_FK = type.typeId WHERE cardType.cardId_FK = :id")
    LiveData<List<CardType>> getCardTypes(int id);

    @Query("SELECT cardType.*, type.type  FROM cardType INNER JOIN type ON cardType.typeId_FK = type.typeId WHERE cardType.cardId_FK = :id")
    List<CardType> getCardTypesList(int id);
}
