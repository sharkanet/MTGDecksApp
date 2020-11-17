package com.chris.mtgdecksapp.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "type")

public class TypeEntity {
    @PrimaryKey(autoGenerate = true)
    private int typeId;
    private String type;

    public TypeEntity(int typeId, String type) {
        this.typeId = typeId;
        this.type = type;
    }

@Ignore
    public TypeEntity(String type) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @NonNull
    @Override
    public String toString() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeEntity that = (TypeEntity) o;
        return getTypeId() == that.getTypeId() &&
                Objects.equals(getType(), that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTypeId(), getType());
    }
}
