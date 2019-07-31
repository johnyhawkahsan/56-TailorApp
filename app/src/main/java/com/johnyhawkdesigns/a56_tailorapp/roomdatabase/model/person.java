package com.johnyhawkdesigns.a56_tailorapp.roomdatabase.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.Nullable;

@Entity(tableName = "person_table")
public class person {

    @PrimaryKey
    @ColumnInfo(name = "mobileNo")
    private int mobileNo;


    @Nullable
    @ColumnInfo(name = "personName")
    private String personName;


    @Nullable
    @ColumnInfo(name = "mobileNoAlternate")
    private int mobileNoAlternate;

    @Nullable
    @ColumnInfo(name = "personAddress")
    private String personAddress;

    @Nullable
    @ColumnInfo(name = "personNote")
    private String personNote;


}
