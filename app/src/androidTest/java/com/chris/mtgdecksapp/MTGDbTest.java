package com.chris.mtgdecksapp;

import android.app.Activity;
import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.chris.mtgdecksapp.database.CardEntity;
import com.chris.mtgdecksapp.database.CardEntityDao;
import com.chris.mtgdecksapp.database.CardSupertypeDao;
import com.chris.mtgdecksapp.database.CardSupertypeEntityDao;
import com.chris.mtgdecksapp.database.CardTypeEntityDao;
import com.chris.mtgdecksapp.database.DeckEntity;
import com.chris.mtgdecksapp.database.DeckEntityDao;
import com.chris.mtgdecksapp.database.MTGAppDatabase;
import com.chris.mtgdecksapp.database.MTGAppRepository;
import com.chris.mtgdecksapp.database.SupertypeEntity;
import com.chris.mtgdecksapp.database.SupertypeEntityDao;
import com.chris.mtgdecksapp.database.TypeEntity;
import com.chris.mtgdecksapp.database.TypeEntityDao;
import com.chris.mtgdecksapp.model.Deck;
import com.chris.mtgdecksapp.utility.LiveDataTestUtil;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOError;
import java.io.IOException;
import java.util.List;

import kotlin.jvm.JvmField;

import static org.junit.Assert.assertEquals;
import static com.chris.mtgdecksapp.utility.LiveDataTestUtil.getOrAwaitValue;

@RunWith(AndroidJUnit4.class)
public class MTGDbTest {
    @Rule
    @JvmField
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    private MTGAppDatabase mtgAppDatabase;
    private SupertypeEntityDao supertypeEntityDao;
    private TypeEntityDao typeEntityDao;
    private CardEntityDao cardEntityDao;
    private DeckEntityDao deckEntityDao;
    private CardTypeEntityDao cardTypeEntityDao;
    private CardSupertypeEntityDao cardSupertypeEntityDao;

    @Before
    public void initDb(){
        mtgAppDatabase = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),MTGAppDatabase.class).build();
        supertypeEntityDao = mtgAppDatabase.SupertypeEntityDao();
        typeEntityDao =mtgAppDatabase.TypeEntityDao();
        cardEntityDao = mtgAppDatabase.CardEntityDao();
        deckEntityDao = mtgAppDatabase.DeckEntityDao();
        cardTypeEntityDao =mtgAppDatabase.CardTypeEntityDao();
        cardSupertypeEntityDao = mtgAppDatabase.CardSupertypeEntityDao();

    }
    @After
    public void closeDb() throws IOException {
        mtgAppDatabase.close();
    }
    @Test
    public void insertAndGetSupertypeTest() throws Exception{
        SupertypeEntity supertypeEntity = new SupertypeEntity(1, "Basic");
        supertypeEntityDao.insertSupertypeEntity(supertypeEntity);
        LiveData<List<SupertypeEntity>> supertypeEntities =supertypeEntityDao.getAllSupertypeEntity();
        assertEquals(getOrAwaitValue(supertypeEntities).get(0),supertypeEntity);
    }

    @Test
    public void insertAndGetTypeTest() throws Exception{
        TypeEntity typeEntity = new TypeEntity(1,"Forest");
        typeEntityDao.insertTypeEntity(typeEntity);
        LiveData<List<TypeEntity>> typeEntities = typeEntityDao.getAllTypeEntity();
        assertEquals(getOrAwaitValue(typeEntities).get(0), typeEntity);
    }

    @Test
    public void insertAndGetCardTest() throws Exception{
        CardEntity cardEntity = new CardEntity(1,"Forest", "0", "Forest", "n/a","n/a","n/a");
        cardEntityDao.insertCardEntity(cardEntity);
        LiveData<List<CardEntity>> cardEntities = cardEntityDao.getAllCardEntity();
        assertEquals(getOrAwaitValue(cardEntities).get(0),cardEntity);
    }

    @Test
    public void insertAndGetDeckTest() throws Exception{
        DeckEntity deckEntity = new DeckEntity(1,"Deck", false);
        deckEntityDao.insertDeckEntity(deckEntity);
        LiveData<List<DeckEntity>> deckEntities = deckEntityDao.getAllDeckEntity();
        assertEquals(getOrAwaitValue(deckEntities).get(0), deckEntity);
    }
}
