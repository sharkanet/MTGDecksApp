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
public interface CardEntityDao {
    //insert card
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCardEntity(CardEntity cardEntity);

    //update card
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCardEntity(CardEntity cardEntity);

    //get a card by id
    @Query("SELECT * FROM card WHERE cardId = :id")
    CardEntity getCardEntityById(int id);

    //get a card by name
    @Query("SELECT * FROM card WHERE name = :name")
    CardEntity getCardEntityById(String name);

    //get cards with text
    @Query("SELECT * FROM card WHERE text LIKE '%' || :text || '%'")
    LiveData<List<CardEntity>> getCardsWithText(String text);

    //get all cards
    @Query("SELECT * FROM card")
    LiveData<List<CardEntity>> getAllCardEntity();

    //delete card
    @Delete
    void deleteCardEntity(CardEntity cardEntity);

    //delete all cards
    @Query("DELETE FROM card")
    void deleteAllCardEntity();
}
