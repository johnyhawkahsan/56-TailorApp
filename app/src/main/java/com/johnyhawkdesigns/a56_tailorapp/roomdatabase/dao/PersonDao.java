package com.johnyhawkdesigns.a56_tailorapp.roomdatabase.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.model.Person;

import java.util.List;

@Dao
public interface PersonDao {


    @Query("Select * FROM person_table ORDER BY personID DESC")
    //Person[] loadAll(); // We don't use this because we want to retain observability
    LiveData<List<Person>> getAllPersons();

    //Find Person by Person ID
    @Query("SELECT * FROM person_table WHERE personID = :personID")
    Person getPersonWithPersonID(int personID);

    //Find Person by mobileNO
    @Query("SELECT * FROM person_table WHERE mobileNO LIKE '%' || :mobileNO || '%'")
    LiveData<List<Person>> findPersonByMobileNo(String mobileNO);

    //Find Person by name
    @Query("SELECT * FROM person_table WHERE personName LIKE '%' || :personName || '%'")
    LiveData<List<Person>> findPersonByName(String personName);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Person person); // we don't want to return "long" id

    @Update
    void update(Person person);

    @Delete
    void delete(Person person);

    @Query("DELETE FROM person_table WHERE personID = :personID")
    void deletePersonWithID(int personID);

    @Query("DELETE FROM person_table")
    void deleteAll();
}
