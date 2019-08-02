package com.johnyhawkdesigns.a56_tailorapp.roomdatabase.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.Nullable;

import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.typeConverters.DateTypeConverter;

import java.util.Date;

@Entity(tableName = "sizes_table")
public class Size {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sizeID")
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


    public int getSizeID() {
        return sizeID;
    }

    public void setSizeID(int sizeID) {
        this.sizeID = sizeID;
    }

    public int getForeignMobileNo() {
        return foreignMobileNo;
    }

    public void setForeignMobileNo(int foreignMobileNo) {
        this.foreignMobileNo = foreignMobileNo;
    }

    public int getNeck() {
        return neck;
    }

    public void setNeck(int neck) {
        this.neck = neck;
    }

    public int getWaist() {
        return waist;
    }

    public void setWaist(int waist) {
        this.waist = waist;
    }

    public int getHip() {
        return hip;
    }

    public void setHip(int hip) {
        this.hip = hip;
    }

    public int getChest() {
        return chest;
    }

    public void setChest(int chest) {
        this.chest = chest;
    }

    public int getArm() {
        return arm;
    }

    public void setArm(int arm) {
        this.arm = arm;
    }

    public int getHandcuff() {
        return handcuff;
    }

    public void setHandcuff(int handcuff) {
        this.handcuff = handcuff;
    }

    public int getShirtLength() {
        return shirtLength;
    }

    public void setShirtLength(int shirtLength) {
        this.shirtLength = shirtLength;
    }

    public int getLegOpening() {
        return legOpening;
    }

    public void setLegOpening(int legOpening) {
        this.legOpening = legOpening;
    }

    @Nullable
    public Date getSizeUpdateDate() {
        return sizeUpdateDate;
    }

    public void setSizeUpdateDate(@Nullable Date sizeUpdateDate) {
        this.sizeUpdateDate = sizeUpdateDate;
    }
}
