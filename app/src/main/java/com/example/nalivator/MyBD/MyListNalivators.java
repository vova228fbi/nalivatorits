package com.example.nalivator.MyBD;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.RoomDatabase;


@Entity
public
class MyListNalivators {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int myId;

    @ColumnInfo(name = "nameNalivator")
    private String myNameNalivator;

    @ColumnInfo(name = "kolvoR")
    private int myKolvoR;

    @ColumnInfo(name = "timeNaliv")
    private double myTimeNaliv;

    public MyListNalivators() {
    }

    public MyListNalivators(int myId, String myNameNalivator, int myKolvoR, double myTimeNaliv) {
        this.myId = myId;
        this.myNameNalivator = myNameNalivator;
        this.myKolvoR = myKolvoR;
        this.myTimeNaliv = myTimeNaliv;
    }

    public int getMyId() {
        return myId;
    }

    public void setMyId(int myId) {
        this.myId = myId;
    }

    public String getMyNameNalivator() {
        return myNameNalivator;
    }

    public void setMyNameNalivator(String myNameNalivator) {
        this.myNameNalivator = myNameNalivator;
    }

    public int getMyKolvoR() {
        return myKolvoR;
    }

    public void setMyKolvoR(int myKolvoR) {
        this.myKolvoR = myKolvoR;
    }

    public double getMyTimeNaliv() {
        return myTimeNaliv;
    }

    public void setMyTimeNaliv(double myTimeNaliv) {
        this.myTimeNaliv = myTimeNaliv;
    }

    @Override
    public String toString() {
        return myId + ". " + myNameNalivator  + ".";
    }
}
