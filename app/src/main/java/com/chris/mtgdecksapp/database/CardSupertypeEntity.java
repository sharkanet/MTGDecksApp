package com.chris.mtgdecksapp.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "cardSupertype",
        foreignKeys = {@ForeignKey(entity = CardEntity.class, parentColumns = "cardId", childColumns = "cardId_FK", onDelete = CASCADE),
                        @ForeignKey(entity = SupertypeEntity.class, parentColumns = "supertypeId", childColumns = "supertypeId_FK", onDelete = CASCADE)
        }
)
public class CardSupertypeEntity {
@PrimaryKey(autoGenerate = true)
    private int cardSupertypesId;

    private int cardId_FK;
    private int supertypeId_FK;

@Ignore
    public CardSupertypeEntity() {
    }
@Ignore
    public CardSupertypeEntity(int cardId_FK, int supertypeId_FK) {
        this.cardId_FK = cardId_FK;
        this.supertypeId_FK = supertypeId_FK;
    }

    public CardSupertypeEntity(int cardSupertypesId, int cardId_FK, int supertypeId_FK) {
        this.cardSupertypesId = cardSupertypesId;
        this.cardId_FK = cardId_FK;
        this.supertypeId_FK = supertypeId_FK;
    }
}
