package com.chris.mtgdecksapp.model;

import com.chris.mtgdecksapp.database.SupertypeEntity;
import com.chris.mtgdecksapp.database.TypeEntity;

import java.util.List;

public class Card_Planeswalker extends Card {
    private String loyalty;

    public Card_Planeswalker() {
    }

    public Card_Planeswalker(String loyalty) {
        this.loyalty = loyalty;
    }

    public Card_Planeswalker(int cardId, String name, String manaCost, String text, String loyalty) {
        super(cardId, name, manaCost, text);
        this.loyalty = loyalty;
    }

    public Card_Planeswalker(int cardId, String name, String manaCost, String text, List<SupertypeEntity> supertypes, List<TypeEntity> types, String loyalty) {
        super(cardId, name, manaCost, text, supertypes, types);
        this.loyalty = loyalty;
    }

    public String getLoyalty() {
        return loyalty;
    }

    public void setLoyalty(String loyalty) {
        this.loyalty = loyalty;
    }
}
