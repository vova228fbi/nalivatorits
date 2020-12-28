package com.example.nalivator.MyBD;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.RoomDatabase;

@Entity
public
class MyListAngle {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int myId;

    @ColumnInfo(name = "idNal")
    private int idNal;

    @ColumnInfo(name = "numberR")
    private int myNumberR;

    @ColumnInfo(name = "angleR")
    private int myAngleR;

    public MyListAngle() {
    }

    public MyListAngle(int myId, int idNal, int myNumberR, int myAngleR) {
        this.myId = myId;
        this.idNal = idNal;
        this.myNumberR = myNumberR;
        this.myAngleR = myAngleR;
    }

    public int getIdNal() {
        return idNal;
    }

    public void setIdNal(int idNal) {
        this.idNal = idNal;
    }

    public int getMyId() {
        return myId;
    }

    public void setMyId(int myId) {
        this.myId = myId;
    }

    public int getMyNumberR() {
        return myNumberR;
    }

    public void setMyNumberR(int myNumberR) {
        this.myNumberR = myNumberR;
    }

    public int getMyAngleR() {
        return myAngleR;
    }

    public void setMyAngleR(int myAngleR) {
        this.myAngleR = myAngleR;
    }
}
