package com.johnyhawkdesigns.a56_tailorapp.roomdatabase.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.model.Size;

import java.util.List;

public interface SizeDao {

    @Query("Select * FROM sizes_table WHERE foreignMobileNo == :foreignMobileNo ORDER BY sizeUpdateDate DESC")
    LiveData<List<Size>> loadAllSizesOfPerson(int foreignMobileNo);

    @Query("Select * FROM sizes_table WHERE sizeID == :sizeID AND foreignMobileNo == :foreignMobileNo")
    Size getSizeOfPersonWithMobileNo(int sizeID, int foreignMobileNo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSize(Size... sizes);

    @Update
    void updateSize(Size... sizes);

    @Delete
    void deleteSize(Size... sizes);

    @Query("DELETE FROM sizes_table WHERE foreignMobileNo = :foreignMobileNo AND sizeID = :sizeID")
    void deleteSizeWithID(int foreignMobileNo, int sizeID);

    @Query("DELETE FROM sizes_table WHERE foreignMobileNo = :foreignMobileNo")
    void deleteAllSizesOFPerson(int foreignMobileNo);

}
