package com.chris.mtgdecksapp.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(
        tableName="card"
)
public class CardEntity {
    @PrimaryKey(autoGenerate = true)
    private int cardId;

    private String name;
    private String manaCost;
    private String text;
    private String power;
    private String toughness;
    private String loyalty;

@Ignore
    public CardEntity(){
    }

@Ignore
    public CardEntity(String name, String manaCost, String text, String power, String toughness, String loyalty) {
        this.name = name;
        this.manaCost = manaCost;
        this.text = text;
        this.power = power;
        this.toughness = toughness;
        this.loyalty = loyalty;
    }

    public CardEntity(int cardId, String name, String manaCost, String text, String power, String toughness, String loyalty) {
        this.cardId = cardId;
        this.name = name;
        this.manaCost = manaCost;
        this.text = text;
        this.power = power;
        this.toughness = toughness;
        this.loyalty = loyalty;
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
}
