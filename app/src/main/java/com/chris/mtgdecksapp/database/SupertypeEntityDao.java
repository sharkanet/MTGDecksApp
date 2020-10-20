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
public interface SupertypeEntityDao {
    //insert
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    long insertSupertypeEntity(SupertypeEntity supertypeEntity);

    //update
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateSupertypeEntity(SupertypeEntity supertypeEntity);

    //select by id

    //select all
    @Query("SELECT * FROM supertype")
    LiveData<List<SupertypeEntity>> getAllSupertypeEntity();

    //delete
    @Delete
    void deleteSupertypeEntity(SupertypeEntity supertypeEntity);

    //delete all
    @Query("DELETE FROM supertype")
    void deleteAllSupertypeEntity();
}
