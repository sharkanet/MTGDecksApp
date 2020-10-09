package com.chris.mtgdecksapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CardSupertypeEntityDao {
    //insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCardSupertypeEntity(CardSupertypeEntity cardSupertypeEntity);

    //update
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCardSupertypeEntity(CardSupertypeEntity cardSupertypeEntity);

    //select by id
    //select all
    @Query("SELECT * FROM cardSupertype ORDER BY cardId_FK")
    List<CardSupertypeEntity> getAllCardSupertypeEntity();

    //delete
    @Delete
    void deleteCardSupertypeEntity(CardSupertypeEntity cardSupertypeEntity);

    //delete for card
    @Query("DELETE FROM cardSupertype WHERE cardId_FK = :id")
    void deleteCardSupertypeEntityForCardId(int id);

    //delete all
    @Query("DELETE FROM cardSupertype")
    void deleteAllCardSupertypeEntity();
}
