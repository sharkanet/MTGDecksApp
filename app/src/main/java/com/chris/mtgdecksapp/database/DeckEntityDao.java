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
public interface DeckEntityDao {
    //insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDeckEntity(DeckEntity deckEntity);

    //update
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateDeckEntity(DeckEntity deckEntity);

    //delete by id
    @Query("DELETE FROM deck WHERE deckId =:id")
    void deleteDeckEntity(int id);

    //select all
    @Query("SELECT * FROM deck ORDER BY deckId")
    LiveData<List<DeckEntity>> getAllDeckEntity();

    //delete
    @Delete
    void deleteDeckEntity(DeckEntity deckEntity);
    //delete all
    @Query("DELETE FROM deck")
    void deleteAllDeckEntity();
}
