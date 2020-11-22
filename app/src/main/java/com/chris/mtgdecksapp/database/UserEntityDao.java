package com.chris.mtgdecksapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserEntityDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insertUserEntity(UserEntity userEntity);

    @Update (onConflict = OnConflictStrategy.REPLACE)
    void updateUserEntity(UserEntity userEntity);

    @Query("SELECT * FROM user")
    LiveData<List<UserEntity>> getAllUserEntity();

    @Query("DELETE FROM user")
    void deleteAllUserEntity();
}
