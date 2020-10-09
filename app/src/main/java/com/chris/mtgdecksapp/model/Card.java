package com.chris.mtgdecksapp.model;

import com.chris.mtgdecksapp.database.SupertypeEntity;
import com.chris.mtgdecksapp.database.TypeEntity;

import java.util.List;

public class Card {
    private int cardId;
    private String name;
    private String manaCost;
    private String text;
    private List<SupertypeEntity> supertypes;
    private List<TypeEntity> types;

    public Card() {
    }

    public Card(int cardId, String name, String manaCost, String text) {
        this.cardId = cardId;
        this.name = name;
        this.manaCost = manaCost;
        this.text = text;
    }

    public Card(int cardId, String name, String manaCost, String text, List<SupertypeEntity> supertypes, List<TypeEntity> types) {
        this.cardId = cardId;
        this.name = name;
        this.manaCost = manaCost;
        this.text = text;
        this.supertypes = supertypes;
        this.types = types;
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

    public List<SupertypeEntity> getSupertypes() {
        return supertypes;
    }

    public void setSupertypes(List<SupertypeEntity> supertypes) {
        this.supertypes = supertypes;
    }

    public List<TypeEntity> getTypes() {
        return types;
    }

    public void setTypes(List<TypeEntity> types) {
        this.types = types;
    }
}
