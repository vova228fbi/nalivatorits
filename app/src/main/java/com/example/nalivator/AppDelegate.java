package com.example.nalivator;

import android.app.Application;

import androidx.room.Room;

import com.example.nalivator.MyBD.NalivatorDataBase;

public class AppDelegate extends Application {

    private NalivatorDataBase mNalivatorDataBase;

    @Override
    public void onCreate() {
        super.onCreate();

        mNalivatorDataBase = Room.databaseBuilder(getApplicationContext(), NalivatorDataBase.class, "nalivator_database")
                .allowMainThreadQueries()
                .build();

    }

    public NalivatorDataBase getNalivatorDataBase() {
        return mNalivatorDataBase;
    }
}
