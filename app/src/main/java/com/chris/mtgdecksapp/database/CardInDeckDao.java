package com.chris.mtgdecksapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.chris.mtgdecksapp.model.CardInDeck;

import java.util.List;

@Dao
public interface CardInDeckDao {
    //select all cards from a deck
    @Query("SELECT card.*, cardInDeck.quantity, cardInDeck.currentlyInDeck FROM card INNER JOIN cardInDeck ON card.cardId = cardInDeck.cardId_FK WHERE deckId_FK =:id ")
    LiveData<List<CardInDeck>> getCardsInDeck(int id);

}
