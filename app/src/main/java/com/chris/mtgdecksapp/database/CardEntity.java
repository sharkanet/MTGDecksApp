package com.chris.mtgdecksapp.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

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
    private boolean isBasic;

@Ignore
    public CardEntity(){
    }

@Ignore
    public CardEntity(String name, String manaCost, String text) {
        this(name, manaCost, text, "n/a", "n/a", "/na");
    }
@Ignore
    public CardEntity(String name, String manaCost, String text, String power, String toughness) {
        this(name, manaCost, text, power, toughness, "n/a");
    }

@Ignore
    public CardEntity(String name, String manaCost, String text, String loyalty) {
        this(name, manaCost, text, "n/a", "n/a", loyalty);
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

@Ignore
    public CardEntity(int cardId, String name, String manaCost, String text, String power, String toughness, String loyalty) {
        this.cardId = cardId;
        this.name = name;
        this.manaCost = manaCost;
        this.text = text;
        this.power = power;
        this.toughness = toughness;
        this.loyalty = loyalty;
    }

    public CardEntity(int cardId, String name, String manaCost, String text, String power, String toughness, String loyalty, boolean isBasic) {
        this.cardId = cardId;
        this.name = name;
        this.manaCost = manaCost;
        this.text = text;
        this.power = power;
        this.toughness = toughness;
        this.loyalty = loyalty;
        this.isBasic = isBasic;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardEntity that = (CardEntity) o;
        return getCardId() == that.getCardId() &&
                getName().equals(that.getName()) &&
                getManaCost().equals(that.getManaCost()) &&
                getText().equals(that.getText()) &&
                getPower().equals(that.getPower()) &&
                getToughness().equals(that.getToughness()) &&
                getLoyalty().equals(that.getLoyalty());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCardId(), getName(), getManaCost(), getText(), getPower(), getToughness(), getLoyalty());
    }
}
