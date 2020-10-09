package com.chris.mtgdecksapp.model;

import com.chris.mtgdecksapp.database.GameEntity;

import java.util.List;

public class Deck {
   private int deckId;
   private List<Card> cards;
   private List<GameEntity> gameRecord;
   private String name;

   public Deck() {
   }

   public Deck(int deckId, String name) {
      this.deckId = deckId;
      this.name = name;
   }

   public Deck(int deckId, List<Card> cards, List<GameEntity> gameRecord, String name) {
      this.deckId = deckId;
      this.cards = cards;
      this.gameRecord = gameRecord;
      this.name = name;
   }

   public int getDeckId() {
      return deckId;
   }

   public void setDeckId(int deckId) {
      this.deckId = deckId;
   }

   public List<Card> getCards() {
      return cards;
   }

   public void setCards(List<Card> cards) {
      this.cards = cards;
   }

   public List<GameEntity> getGameRecord() {
      return gameRecord;
   }

   public void setGameRecord(List<GameEntity> gameRecord) {
      this.gameRecord = gameRecord;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }
}
