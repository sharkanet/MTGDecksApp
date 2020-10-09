package com.chris.mtgdecksapp.model;

import com.chris.mtgdecksapp.database.SupertypeEntity;
import com.chris.mtgdecksapp.database.TypeEntity;

import java.util.List;

public class Card_Creature extends Card {
    private String power;
    private String toughness;

    public Card_Creature() {
    }

    public Card_Creature(String power, String toughness) {
        this.power = power;
        this.toughness = toughness;
    }

    public Card_Creature(int cardId, String name, String manaCost, String text, String power, String toughness) {
        super(cardId, name, manaCost, text);
        this.power = power;
        this.toughness = toughness;
    }

    public Card_Creature(int cardId, String name, String manaCost, String text, List<SupertypeEntity> supertypes, List<TypeEntity> types, String power, String toughness) {
        super(cardId, name, manaCost, text, supertypes, types);
        this.power = power;
        this.toughness = toughness;
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
}
