package com.johnyhawkdesigns.a56_tailorapp.roomdatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.dao.personDao;
import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.dao.sizeDao;
import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.model.person;
import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.model.size;

@Database(entities = {person.class, size.class}, version = 1)
public abstract class TailorRoomDatabase extends RoomDatabase {

    private static final String TAG = TailorRoomDatabase.class.getSimpleName();
    private static String DB_NAME = "tailor-db";

    // get DAO objects
    public abstract personDao personDao();
    public abstract sizeDao sizeDao();

    //We only need one instance of this class, so we make it singleton
    private static TailorRoomDatabase DBINSTANCE;

    //Singleton pattern method
    public static TailorRoomDatabase getDBINSTANCE(Context context){
        if (DBINSTANCE == null){
            synchronized (TailorRoomDatabase.class){
                if (DBINSTANCE == null){
                    Log.d(TAG, "getDatabase: INSTANCE == null, Build new Database INSTANCE");
                    DBINSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TailorRoomDatabase.class,
                            DB_NAME)
                            //.allowMainThreadQueries() // Allow queries in Main thread instead of AsyncTask - This is not recommended.
                            .build();
                }
            }
        }
        return DBINSTANCE;
    }




}
