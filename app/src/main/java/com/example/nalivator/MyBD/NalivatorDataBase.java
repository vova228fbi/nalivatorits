package com.example.nalivator.MyBD;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {MyListNalivators.class, MyListAngle.class, MyListConnect.class}, version = 1)
public abstract class NalivatorDataBase extends RoomDatabase {

    public abstract NalivatorDao getNalivatorDao();

}
