package com.johnyhawkdesigns.a56_tailorapp.roomdatabase.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.Nullable;

@Entity(tableName = "person_table")
public class Person {

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


    public int getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(int mobileNo) {
        this.mobileNo = mobileNo;
    }

    @Nullable
    public String getPersonName() {
        return personName;
    }

    public void setPersonName(@Nullable String personName) {
        this.personName = personName;
    }

    @Nullable
    public int getMobileNoAlternate() {
        return mobileNoAlternate;
    }

    public void setMobileNoAlternate(@Nullable int mobileNoAlternate) {
        this.mobileNoAlternate = mobileNoAlternate;
    }

    @Nullable
    public String getPersonAddress() {
        return personAddress;
    }

    public void setPersonAddress(@Nullable String personAddress) {
        this.personAddress = personAddress;
    }

    @Nullable
    public String getPersonNote() {
        return personNote;
    }

    public void setPersonNote(@Nullable String personNote) {
        this.personNote = personNote;
    }
}
