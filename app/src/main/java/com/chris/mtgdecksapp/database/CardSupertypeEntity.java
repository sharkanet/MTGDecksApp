package com.chris.mtgdecksapp.database;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "cardSupertype",
        primaryKeys = {"cardId_FK", "supertypeId_FK"},
        indices = {@Index("cardId_FK"),@Index("supertypeId_FK")},
        foreignKeys = {@ForeignKey(entity = CardEntity.class, parentColumns = "cardId", childColumns = "cardId_FK", onDelete = CASCADE, deferred = true),
                        @ForeignKey(entity = SupertypeEntity.class, parentColumns = "supertypeId", childColumns = "supertypeId_FK", onDelete = CASCADE,deferred = true)
        }
)
public class CardSupertypeEntity {
//@PrimaryKey(autoGenerate = true)
//    private int cardSupertypesId;

    private int cardId_FK;
    private int supertypeId_FK;

@Ignore
    public CardSupertypeEntity() {
    }

    public CardSupertypeEntity(int cardId_FK, int supertypeId_FK) {
        this.cardId_FK = cardId_FK;
        this.supertypeId_FK = supertypeId_FK;
    }

//    public CardSupertypeEntity(int cardSupertypesId, int cardId_FK, int supertypeId_FK) {
//        this.cardSupertypesId = cardSupertypesId;
//        this.cardId_FK = cardId_FK;
//        this.supertypeId_FK = supertypeId_FK;
//    }

//    public int getCardSupertypesId() {
//        return cardSupertypesId;
//    }
//
//    public void setCardSupertypesId(int cardSupertypesId) {
//        this.cardSupertypesId = cardSupertypesId;
//    }

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


}
