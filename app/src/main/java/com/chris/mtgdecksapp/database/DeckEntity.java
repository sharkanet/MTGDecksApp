package com.chris.mtgdecksapp.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "deck")

public class DeckEntity {
    @PrimaryKey(autoGenerate = true)
    private int deckId;
    private String name;
    private boolean isCommanderDeck;

    public DeckEntity(int deckId, String name, boolean isCommanderDeck) {
        this.deckId = deckId;
        this.name = name;
        this.isCommanderDeck = isCommanderDeck;
    }

@Ignore
    public DeckEntity(String name, boolean isCommanderDeck) {
        this.name = name;
        this.isCommanderDeck = isCommanderDeck;
    }

@Ignore
    public DeckEntity(){}

    public int getDeckId() {
        return deckId;
    }

    public void setDeckId(int deckId) {
        this.deckId = deckId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCommanderDeck() {
        return isCommanderDeck;
    }

    public void setCommanderDeck(boolean commanderDeck) {
        isCommanderDeck = commanderDeck;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
