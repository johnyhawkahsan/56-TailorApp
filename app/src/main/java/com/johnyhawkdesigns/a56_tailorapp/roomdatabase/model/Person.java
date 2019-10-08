package com.johnyhawkdesigns.a56_tailorapp.roomdatabase.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import androidx.annotation.Nullable;

import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.typeConverters.DateTypeConverter;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "person_table")
public class Person implements Serializable{

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "personID")
    private int personID;

    @ColumnInfo(name = "mobileNo")
    private String mobileNo;

    @Nullable
    @ColumnInfo(name = "personName")
    private String personName;

    @Nullable
    @ColumnInfo(name = "mobileNoAlternate")
    private String mobileNoAlternate;

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

    @Nullable
    @ColumnInfo(name = "person_pantSeat")
    private int person_pantSeat;

    @Nullable
    @ColumnInfo(name = "person_ShoulderWidth")
    private int person_ShoulderWidth;

    @Nullable
    public int getPerson_pantSeat() {
        return person_pantSeat;
    }

    public void setPerson_pantSeat(@Nullable int person_pantSeat) {
        this.person_pantSeat = person_pantSeat;
    }

    @Nullable
    public int getPerson_ShoulderWidth() {
        return person_ShoulderWidth;
    }

    public void setPerson_ShoulderWidth(@Nullable int person_ShoulderWidth) {
        this.person_ShoulderWidth = person_ShoulderWidth;
    }

    @Nullable
    public int getPerson_CoatSleeve() {
        return person_CoatSleeve;
    }

    public void setPerson_CoatSleeve(@Nullable int person_CoatSleeve) {
        this.person_CoatSleeve = person_CoatSleeve;
    }

    @Nullable
    @ColumnInfo(name = "person_CoatSleeve")
    private int person_CoatSleeve;


    @ColumnInfo(name = "lastProfileUpdateDate")
    @TypeConverters({DateTypeConverter.class})
    private Date lastProfileUpdateDate;






    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
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
    public String getMobileNoAlternate() {
        return mobileNoAlternate;
    }

    public void setMobileNoAlternate(@Nullable String mobileNoAlternate) {
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
