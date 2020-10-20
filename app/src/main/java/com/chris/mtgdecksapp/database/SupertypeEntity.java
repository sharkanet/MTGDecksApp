package com.chris.mtgdecksapp.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "supertype")
public class SupertypeEntity {
    @PrimaryKey(autoGenerate = true)
    private int supertypeId;
    private String supertype;

@Ignore
    public SupertypeEntity() {
    }

@Ignore
    public SupertypeEntity(String supertype) {
        this.supertype = supertype;
    }

    public SupertypeEntity(int supertypeId, String supertype) {
        this.supertypeId = supertypeId;
        this.supertype = supertype;
    }

    public int getSupertypeId() {
        return supertypeId;
    }

    public void setSupertypeId(int supertypeId) {
        this.supertypeId = supertypeId;
    }

    public String getSupertype() {
        return supertype;
    }

    public void setSupertype(String supertype) {
        this.supertype = supertype;
    }

    @NonNull
    @Override
    public String toString() {
        return supertype;
    }
}
