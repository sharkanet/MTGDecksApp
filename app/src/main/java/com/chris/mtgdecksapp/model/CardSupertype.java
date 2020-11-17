package com.chris.mtgdecksapp.model;

public class CardSupertype {
    private int cardId_FK;
    private int supertypeId_FK;
    private String supertype;

    public CardSupertype(int cardId_FK, int supertypeId_FK, String supertype) {
        this.cardId_FK = cardId_FK;
        this.supertypeId_FK = supertypeId_FK;
        this.supertype = supertype;
    }

    public int getCardId_FK() {
        return cardId_FK;
    }

    public void setCardId_FK(int cardId_FK) {
        this.cardId_FK = cardId_FK;
    }

    public int getSupertypeId_FK() {
        return supertypeId_FK;
    }

    public void setSupertypeId_FK(int supertypeId_FK) {
        this.supertypeId_FK = supertypeId_FK;
    }

    public String getSupertype() {
        return supertype;
    }

    public void setSupertype(String supertype) {
        this.supertype = supertype;
    }

    @Override
    public String toString() {
        return supertype;
    }
}
