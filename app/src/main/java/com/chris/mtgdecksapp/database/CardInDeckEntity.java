package com.chris.mtgdecksapp.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "cardInDeck",
        primaryKeys = {"cardId_FK", "deckId_FK"},
        indices = {@Index("cardId_FK"),@Index("deckId_FK")},
        foreignKeys = {@ForeignKey(entity = CardEntity.class,
                             parentColumns = "cardId", childColumns = "cardId_FK", onDelete = CASCADE),
                        @ForeignKey(entity = DeckEntity.class,
                            parentColumns = "deckId", childColumns = "deckId_FK", onDelete = CASCADE)
        }
)
public class CardInDeckEntity {
    private int cardId_FK;
    private int deckId_FK;
    private int quantity;
    private boolean currentlyInDeck;


//    public CardInDeckEntity(int cardInDeckId, int cardId_FK, int deckId_FK, int quantity, boolean currentlyInDeck) {
//        this.cardInDeckId = cardInDeckId;
//        this.cardId_FK = cardId_FK;
//        this.deckId_FK = deckId_FK;
//        this.quantity = quantity;
//        this.currentlyInDeck = currentlyInDeck;
//    }

    public CardInDeckEntity(int cardId_FK, int deckId_FK, int quantity, boolean currentlyInDeck) {
        this.cardId_FK = cardId_FK;
        this.deckId_FK = deckId_FK;
        this.quantity = quantity;
        this.currentlyInDeck = currentlyInDeck;
    }
@Ignore
    public CardInDeckEntity(){}


    public int getCardId_FK() {
        return cardId_FK;
    }

    public void setCardId_FK(int cardId_FK) {
        this.cardId_FK = cardId_FK;
    }

    public int getDeckId_FK() {
        return deckId_FK;
    }

    public void setDeckId_FK(int deckId_FK) {
        this.deckId_FK = deckId_FK;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isCurrentlyInDeck() { return currentlyInDeck; }

    public void setCurrentlyInDeck(boolean currentlyInDeck) { this.currentlyInDeck = currentlyInDeck; }
}
