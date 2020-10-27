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
public interface GameEntityDao {
    //insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGameEntity(GameEntity gameEntity);

    //update
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateGameEntity(GameEntity gameEntity);

    //select by id
    //select all
    @Query("SELECT * FROM game ORDER BY deckId_FK")
    LiveData<List<GameEntity>> getAllGameEntity();
    //get games for deck
    @Query("SELECT * FROM game  WHERE deckId_FK = :id")
    LiveData<List<GameEntity>> getAllGameEntityForDeck(int id);
    //get wins for deck
    @Query("SELECT COUNT(*) FROM game WHERE result ='Win' AND deckId_FK = :id")
    int getWinCountForDeck(int id);
    //get losses for deck
    @Query("SELECT COUNT(*) FROM game WHERE result ='Lose' AND deckId_FK = :id")
    int getLoseCountForDeck(int id);

    //delete
    @Delete
    void deleteGameEntity(GameEntity gameEntity);

    //delete for deck
    @Query("DELETE FROM game WHERE deckId_FK = :id")
    void deleteGameEntityForDeckId(int id);

    //delete all
    @Query("DELETE FROM game")
    void deleteAllGameEntity();



}
