package com.chris.mtgdecksapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CardTypeEntityDao {
    //insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCardTypeEntity(CardTypeEntity cardTypeEntity);

    //update
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCardTypeEntity(CardTypeEntity cardTypeEntity);

//    //select by id
//    @Query("SELECT * FROM cardType WHERE cardTypeId = :id")
//    CardTypeEntity getCardTypeEntityById(int id);

    //select for cardId_fk
    @Query("SELECT * FROM cardType WHERE cardId_FK = :id")
    LiveData<List<CardTypeEntity>> getCardTypeEntityForCardId(int id);

    //select all
    @Query("SELECT * FROM cardType ORDER BY cardId_FK")
    LiveData<List<CardTypeEntity>> getAllCardTypeEntity();

    //delete
    @Delete
    void deleteCardTypeEntity(CardTypeEntity cardTypeEntity);

    //delete for card id
    @Query("DELETE FROM cardType WHERE cardId_FK = :id")
    void deleteCardTypeEntityForCardId(int id);

    //delete all
    @Query("DELETE FROM cardType")
    void deleteAllCardTypeEntity();
}
