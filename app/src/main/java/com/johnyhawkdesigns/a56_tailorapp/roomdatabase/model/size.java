package com.johnyhawkdesigns.a56_tailorapp.roomdatabase.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.Nullable;

import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.typeConverters.DateTypeConverter;

import java.util.Date;

@Entity(tableName = "sizes_table")
public class size {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "medID")
    private int sizeID;

    @ColumnInfo(name = "foreignMobileNo")
    private int foreignMobileNo;

    @ColumnInfo(name = "neck")
    private int neck;

    @ColumnInfo(name = "waist")
    private int waist;

    @ColumnInfo(name = "hip")
    private int hip;

    @ColumnInfo(name = "chest")
    private int chest;

    @ColumnInfo(name = "arm")
    private int arm;

    @ColumnInfo(name = "handcuff")
    private int handcuff;

    @ColumnInfo(name = "shirtLength")
    private int shirtLength;

    @ColumnInfo(name = "legOpening")
    private int legOpening;

    @Nullable
    @ColumnInfo(name = "sizeUpdateDate")
    @TypeConverters({DateTypeConverter.class})
    private Date sizeUpdateDate;





}
