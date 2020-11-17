package com.chris.mtgdecksapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.chris.mtgdecksapp.model.Game;

import java.util.List;

@Dao
public interface GameDao {
    @Query("SELECT game.*, deck.name AS deckName, deck.isCommanderDeck AS isCommanderGame  FROM game INNER JOIN deck ON game.deckId_FK = deck.deckId ORDER BY game.gameTime ASC")
    public LiveData<List<Game>> getAllGamesOrderedByDate();

    @Query("SELECT game.*, deck.name AS deckName, deck.isCommanderDeck AS isCommanderGame  FROM game INNER JOIN deck ON game.deckId_FK = deck.deckId ORDER BY result = 'Win' DESC, result ='Draw' DESC, result = 'Lose' DESC")
    LiveData<List<Game>> getAllGamesOrderedByWin();
}
