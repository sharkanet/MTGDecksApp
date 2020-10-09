package com.chris.mtgdecksapp.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "type")

public class TypeEntity {
    @PrimaryKey(autoGenerate = true)
    private int typeId;
    private int type;

    public TypeEntity(int typeId, int type) {
        this.typeId = typeId;
        this.type = type;
    }

@Ignore
    public TypeEntity(int type) {
        this.type = type;
    }
@Ignore
    public TypeEntity() {
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
