package com.johnyhawkdesigns.a56_tailorapp.roomdatabase;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.dao.PersonDao;
import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.model.Person;

@Database(entities = {Person.class}, version = 1, exportSchema = false)
public abstract class TailorRoomDatabase extends RoomDatabase {

    private static final String TAG = TailorRoomDatabase.class.getSimpleName();
    private static String DB_NAME = "tailor-db";

    // get DAO objects
    public abstract PersonDao personDao();

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
