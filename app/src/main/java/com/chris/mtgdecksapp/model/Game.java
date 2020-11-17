package com.chris.mtgdecksapp.model;

import java.util.Date;

public class Game {
    private int gameId;
    private int deckId_FK;
    private String deckName;
    private String opponent;
    private String opponentDeckName;
    private String result;
    private Date gameTime;
    private boolean isCommanderGame;

    public Game(int gameId, int deckId_FK, String deckName, String opponent, String opponentDeckName, String result, Date gameTime, boolean isCommanderGame) {
        this.gameId = gameId;
        this.deckId_FK = deckId_FK;
        this.deckName = deckName;
        this.opponent = opponent;
        this.opponentDeckName = opponentDeckName;
        this.result = result;
        this.gameTime = gameTime;
        this.isCommanderGame = isCommanderGame;
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

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
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

    public Date getGameTime() {
        return gameTime;
    }

    public void setGameTime(Date gameTime) {
        this.gameTime = gameTime;
    }

    public boolean isCommanderGame() {
        return isCommanderGame;
    }

    public void setCommanderGame(boolean commanderGame) {
        isCommanderGame = commanderGame;
    }
}
