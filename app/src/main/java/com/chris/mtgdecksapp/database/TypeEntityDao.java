package com.chris.mtgdecksapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TypeEntityDao {
    //insert
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    long insertTypeEntity(TypeEntity typeEntity);

    //update
    @Update (onConflict = OnConflictStrategy.REPLACE)
    void updateTypeEntity(TypeEntity typeEntity);

    //select by id
    //select all
    @Query("SELECT * FROM type")
    LiveData<List<TypeEntity>> getAllTypeEntity();

    //delete
    @Delete
    void deleteTypeEntity(TypeEntity typeEntity);

    //delete all
    @Query("DELETE FROM type")
    void deleteAllTypeEntity();
}
