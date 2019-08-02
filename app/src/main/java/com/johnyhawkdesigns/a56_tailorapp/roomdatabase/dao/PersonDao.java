package com.johnyhawkdesigns.a56_tailorapp.roomdatabase.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.model.Person;

import java.util.List;

public interface PersonDao {


    @Query("Select * FROM person_table ORDER BY personName DESC")
    //Person[] loadAll(); // We don't use this because we want to retain observability
    LiveData<List<Person>> getAllPersons();


    @Query("SELECT * FROM person_table WHERE mobileNo = :mobileNo")
    Person getPersonWithMobileNo(int mobileNo);

    //Find Person by name
    @Query("SELECT * FROM person_table WHERE personName LIKE '%' || :personName || '%'")
    LiveData<List<Person>> findChildByName(String personName);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Person person); // we don't want to return "long" id because we are using MobileNo as PrimaryKey

    @Update
    void update(Person person);

    @Delete
    void delete(Person person);

    @Query("DELETE FROM person_table WHERE mobileNo = :mobileNo")
    void deleteChildWithID(int mobileNo);

    @Query("DELETE FROM person_table")
    void deleteAll();
}
