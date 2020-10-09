package com.chris.mtgdecksapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DeckEntityDao {
    //insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDeckEntity(DeckEntity deckEntity);

    //update
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateDeckEntity(DeckEntity deckEntity);

    //select by id

    //select all
    @Query("SELECT * FROM deck ORDER BY deckId")
    List<DeckEntity> getAllDeckEntity();

    //delete
    @Delete
    void deleteDeckEntity(DeckEntity deckEntity);
    //delete all
    @Query("DELETE FROM deck")
    void deleteAllDeckEntity();
}
