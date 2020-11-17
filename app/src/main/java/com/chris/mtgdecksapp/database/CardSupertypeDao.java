package com.chris.mtgdecksapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.chris.mtgdecksapp.model.CardSupertype;

import java.util.List;

@Dao
public interface CardSupertypeDao {
    //select supertype for card with id
    @Query("SELECT cardSupertype.*, supertype FROM cardSupertype INNER JOIN supertype ON cardSupertype.supertypeId_FK = supertype.supertypeId WHERE cardSupertype.cardId_FK = :id")
    LiveData<List<CardSupertype>> getCardSupertypes(int id);

    @Query("SELECT cardSupertype.*, supertype FROM cardSupertype INNER JOIN supertype ON cardSupertype.supertypeId_FK = supertype.supertypeId WHERE cardSupertype.cardId_FK = :id")
    List<CardSupertype> getCardSupertypesList(int id);
}
