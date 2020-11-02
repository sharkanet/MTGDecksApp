package com.chris.mtgdecksapp.model;

import androidx.annotation.NonNull;

public class CardInDeck {
    private int cardId;
    private String name;
    private String manaCost;
    private String text;
    private String power;
    private String toughness;
    private String loyalty;
    private int quantity;
    private boolean currentlyInDeck;
    private boolean isBasic;



    public CardInDeck(int cardId, String name, String manaCost, String text, String power, String toughness, String loyalty, int quantity, boolean currentlyInDeck) {
        this.cardId = cardId;
        this.name = name;
        this.manaCost = manaCost;
        this.text = text;
        this.power = power;
        this.toughness = toughness;
        this.loyalty = loyalty;
        this.quantity = quantity;
        this.currentlyInDeck = currentlyInDeck;
    }

    public boolean isCurrentlyInDeck() {
        return currentlyInDeck;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManaCost() {
        return manaCost;
    }

    public void setManaCost(String manaCost) {
        this.manaCost = manaCost;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getToughness() {
        return toughness;
    }

    public void setToughness(String toughness) {
        this.toughness = toughness;
    }

    public String getLoyalty() {
        return loyalty;
    }

    public void setLoyalty(String loyalty) {
        this.loyalty = loyalty;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCurrentlyInDeck(boolean currentlyInDeck) {
        this.currentlyInDeck = currentlyInDeck;
    }

    public boolean isBasic() {
        return isBasic;
    }

    public void setBasic(boolean basic) {
        isBasic = basic;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
