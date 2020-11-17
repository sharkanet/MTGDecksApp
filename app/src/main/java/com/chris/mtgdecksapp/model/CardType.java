package com.chris.mtgdecksapp.model;

import androidx.annotation.NonNull;

public class CardType {
    private int cardId_FK;
    private int typeId_FK;
    private String type;

    public CardType(int cardId_FK, int typeId_FK, String type) {
        this.cardId_FK = cardId_FK;
        this.typeId_FK = typeId_FK;
        this.type = type;
    }

    public int getCardId_FK() {
        return cardId_FK;
    }

    public void setCardId_FK(int cardId_FK) {
        this.cardId_FK = cardId_FK;
    }

    public int getTypeId_FK() {
        return typeId_FK;
    }

    public void setTypeId_FK(int typeId_FK) {
        this.typeId_FK = typeId_FK;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @NonNull
    @Override
    public String toString() {
        return type;
    }
}
