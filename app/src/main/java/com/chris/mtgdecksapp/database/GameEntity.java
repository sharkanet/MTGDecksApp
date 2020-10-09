package com.chris.mtgdecksapp.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "game",
        foreignKeys = @ForeignKey(entity = DeckEntity.class, parentColumns = "deckId", childColumns = "deckId_FK", onDelete = CASCADE)
)
public class GameEntity {
    @PrimaryKey(autoGenerate = true)
    private int gameId;

    private int deckId_FK;
    private String opponent;
    private String opponentDeckName;
    private String result;

    public GameEntity(int gameId, int deckId_FK, String opponent, String opponentDeckName, String result) {
        this.gameId = gameId;
        this.deckId_FK = deckId_FK;
        this.opponent = opponent;
        this.opponentDeckName = opponentDeckName;
        this.result = result;
    }

@Ignore
    public GameEntity(int deckId_FK, String opponent, String opponentDeckName, String result) {
        this.deckId_FK = deckId_FK;
        this.opponent = opponent;
        this.opponentDeckName = opponentDeckName;
        this.result = result;
    }
@Ignore
    public GameEntity() {
    }
    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getDeckId_FK() {
        return deckId_FK;
    }

    public void setDeckId_FK(int deckId_FK) {
        this.deckId_FK = deckId_FK;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public String getOpponentDeckName() {
        return opponentDeckName;
    }

    public void setOpponentDeckName(String opponentDeckName) {
        this.opponentDeckName = opponentDeckName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
