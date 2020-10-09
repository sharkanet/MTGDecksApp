package com.chris.mtgdecksapp.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "cardType",
        foreignKeys = {@ForeignKey(entity = CardEntity.class, parentColumns = "cardId", childColumns = "cardId_FK", onDelete = CASCADE),
                        @ForeignKey(entity = TypeEntity.class, parentColumns = "typeId", childColumns = "typeId_FK", onDelete = CASCADE)
        }
)
public class CardTypeEntity {
    @PrimaryKey(autoGenerate = true)
    private int cardTypeId;

    private int cardId_FK;
    private int typeId_FK;

@Ignore
    public CardTypeEntity() {
    }

@Ignore
    public CardTypeEntity(int cardId_FK, int typeId_FK) {
        this.cardId_FK = cardId_FK;
        this.typeId_FK = typeId_FK;
    }

    public CardTypeEntity(int cardTypeId, int cardId_FK, int typeId_FK) {
        this.cardTypeId = cardTypeId;
        this.cardId_FK = cardId_FK;
        this.typeId_FK = typeId_FK;
    }

    public int getCardTypeId() {
        return cardTypeId;
    }

    public void setCardTypeId(int cardTypeId) {
        this.cardTypeId = cardTypeId;
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
}
