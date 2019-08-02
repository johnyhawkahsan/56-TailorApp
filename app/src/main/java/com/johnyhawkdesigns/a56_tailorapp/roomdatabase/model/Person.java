package com.johnyhawkdesigns.a56_tailorapp.roomdatabase.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.Nullable;

import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.typeConverters.DateTypeConverter;

import java.util.Date;

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

    @Nullable
    @ColumnInfo(name = "person_neck")
    private int person_neck;

    @Nullable
    @ColumnInfo(name = "person_waist")
    private int person_waist;

    @Nullable
    @ColumnInfo(name = "person_hip")
    private int person_hip;

    @Nullable
    @ColumnInfo(name = "person_chest")
    private int person_chest;

    @Nullable
    @ColumnInfo(name = "person_arm")
    private int person_arm;

    @Nullable
    @ColumnInfo(name = "person_handcuff")
    private int person_handcuff;

    @Nullable
    @ColumnInfo(name = "person_shirtLength")
    private int person_shirtLength;

    @Nullable
    @ColumnInfo(name = "person_legOpening")
    private int person_legOpening;


    @ColumnInfo(name = "lastProfileUpdateDate")
    @TypeConverters({DateTypeConverter.class})
    private Date lastProfileUpdateDate;

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

    @Nullable
    public int getPerson_neck() {
        return person_neck;
    }

    public void setPerson_neck(@Nullable int person_neck) {
        this.person_neck = person_neck;
    }

    @Nullable
    public int getPerson_waist() {
        return person_waist;
    }

    public void setPerson_waist(@Nullable int person_waist) {
        this.person_waist = person_waist;
    }

    @Nullable
    public int getPerson_hip() {
        return person_hip;
    }

    public void setPerson_hip(@Nullable int person_hip) {
        this.person_hip = person_hip;
    }

    @Nullable
    public int getPerson_chest() {
        return person_chest;
    }

    public void setPerson_chest(@Nullable int person_chest) {
        this.person_chest = person_chest;
    }

    @Nullable
    public int getPerson_arm() {
        return person_arm;
    }

    public void setPerson_arm(@Nullable int person_arm) {
        this.person_arm = person_arm;
    }

    @Nullable
    public int getPerson_handcuff() {
        return person_handcuff;
    }

    public void setPerson_handcuff(@Nullable int person_handcuff) {
        this.person_handcuff = person_handcuff;
    }

    @Nullable
    public int getPerson_shirtLength() {
        return person_shirtLength;
    }

    public void setPerson_shirtLength(@Nullable int person_shirtLength) {
        this.person_shirtLength = person_shirtLength;
    }

    @Nullable
    public int getPerson_legOpening() {
        return person_legOpening;
    }

    public void setPerson_legOpening(@Nullable int person_legOpening) {
        this.person_legOpening = person_legOpening;
    }

    public Date getLastProfileUpdateDate() {
        return lastProfileUpdateDate;
    }

    public void setLastProfileUpdateDate(Date lastProfileUpdateDate) {
        this.lastProfileUpdateDate = lastProfileUpdateDate;
    }
}
