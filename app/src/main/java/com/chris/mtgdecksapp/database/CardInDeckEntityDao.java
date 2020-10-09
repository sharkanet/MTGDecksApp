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
public interface CardInDeckEntityDao {
    //insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCardInDeckEntity(CardInDeckEntity cardInDeckEntity);

    //update
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCardInDeckEntity(CardInDeckEntity cardInDeckEntity);

    //select by id
    @Query("SELECT * FROM cardInDeck WHERE cardInDeckId = :id")
    CardInDeckEntity getCardInDeckEntityById(int id);

    //select all
    @Query("SELECT * FROM cardInDeck ORDER BY deckId_FK")
    LiveData<List<CardInDeckEntity>> getAllCardInDeckEntity();

    //delete
    @Delete
    void deleteCardInDeckEntity(CardInDeckEntity cardInDeckEntity);

    //delete for card
    @Query("DELETE FROM cardInDeck WHERE deckId_FK = :id")
    void deleteCardInDeckEntityForId(int id);

    //delete all
    @Query("DELETE FROM cardInDeck")
    void deleteAllCardInDeckEntity();
}
