package com.johnyhawkdesigns.a56_tailorapp;

import android.app.Application;
import androidx.room.Room;
import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.TailorRoomDatabase;
import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.dao.PersonDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseDAOTest {

    private static final String TAG = DatabaseDAOTest.class.getSimpleName();
    Context appContext;
    Application application;
    private TailorRoomDatabase tailorRoomDatabase;
    private PersonDao personDao;
    //private PersonViewModel personViewModel;


    @Before
    public void setupDatabase(){

        appContext = InstrumentationRegistry.getTargetContext();

        try {
            // Using an in-memory database because the information stored here disappears when the process is killed
            tailorRoomDatabase = Room.inMemoryDatabaseBuilder(appContext, TailorRoomDatabase.class).allowMainThreadQueries().build();

            personDao = tailorRoomDatabase.personDao();
            //personViewModel = new PersonViewModel(appContext);


        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @After
    public void closeDatabase(){
        tailorRoomDatabase.close();
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.johnyhawkdesigns.a56_tailorapp", appContext.getPackageName());
    }




}
