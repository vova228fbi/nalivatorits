package com.example.nalivator.MyBD;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
/*
@Entity(foreignKeys = {
        @ForeignKey(entity = MyListNalivators.class, parentColumns = "id", childColumns = "idNalivator"),
        @ForeignKey(entity = MyListAngle.class, parentColumns = "id", childColumns = "idAngle")
})
*/
@Entity
public class MyListConnect {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int myId;

    @ColumnInfo(name = "idNalivator")
    private int myIdNalivator;

    @ColumnInfo(name = "idAngle")
    private int myIdAngle;

    public MyListConnect() {
    }

    public MyListConnect(int myId, int myIdNalivator, int myIdAngle) {
        this.myId = myId;
        this.myIdNalivator = myIdNalivator;
        this.myIdAngle = myIdAngle;
    }

    public int getMyId() {
        return myId;
    }

    public void setMyId(int myId) {
        this.myId = myId;
    }

    public int getMyIdNalivator() {
        return myIdNalivator;
    }

    public void setMyIdNalivator(int myIdNalivator) {
        this.myIdNalivator = myIdNalivator;
    }

    public int getMyIdAngle() {
        return myIdAngle;
    }

    public void setMyIdAngle(int myIdAngle) {
        this.myIdAngle = myIdAngle;
    }
}
