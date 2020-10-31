package com.chris.mtgdecksapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.chris.mtgdecksapp.model.Deck;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {CardEntity.class, CardInDeckEntity.class, CardSupertypeEntity.class, CardTypeEntity.class, DeckEntity.class, GameEntity.class,
                        SupertypeEntity.class, TypeEntity.class}, exportSchema = false, version = 7)
public abstract class MTGAppDatabase extends RoomDatabase {
    public abstract CardEntityDao CardEntityDao();
    public abstract CardInDeckEntityDao CardInDeckEntityDao();
    public abstract CardSupertypeEntityDao CardSupertypeEntityDao();
    public abstract CardTypeEntityDao CardTypeEntityDao();
    public abstract DeckEntityDao DeckEntityDao();
    public abstract GameEntityDao GameEntityDao();
    public abstract SupertypeEntityDao SupertypeEntityDao();
    public abstract TypeEntityDao TypeEntityDao();
    public abstract CardInDeckDao CardInDeckDao();

    private static final String DATABASE_NAME = "MTGAppDB.db";
    private static final int NUMBER_OF_THREADS = 1;

    private static volatile MTGAppDatabase instance;
    private static final Object LOCK = new Object();
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static MTGAppDatabase getDatabase(final Context context){
        if(instance == null){
            synchronized (LOCK){
                if (instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(), MTGAppDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
                }
            }
        }
        return instance;
    }
}
