package com.example.nalivator.MyBD;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomDatabase;

import java.util.List;

@Dao
public interface NalivatorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNalivators(List<MyListNalivators> nalivators);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertListAngle(List<MyListAngle> angles);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertListConnect(List<MyListConnect> myListConnects);

    @Query("select * from mylistnalivators")
    List<MyListNalivators> getMyListNalivators();

    @Query("select * from mylistangle")
    List<MyListAngle> getMyListAngle();

    @Query("DELETE FROM mylistnalivators WHERE id = :userId")
    void deleteByUserId(int userId);

    @Query("DELETE FROM mylistangle WHERE idNal = :userId")
    void deleteByUserAndleId(int userId);

    @Query("select * from mylistangle inner join mylistconnect on idAngle = mylistconnect.idAngle where idNalivator = :ITSmyIdNalivator")
    List<MyListAngle> getMyAngleFromNalivator(int ITSmyIdNalivator);

    @Query("select * from mylistangle WHERE idNal = :idNal")
    List<MyListAngle> getidnal(int idNal);


    @Query("select * from mylistnalivators WHERE mylistnalivators.id == :idNal")
    List<MyListNalivators> getMyListNalivatorsNEW(int idNal);


/*
    @Query("select mylistnalivators.id  from mylistnalivators WHERE mylistnalivators.id = :idNal")
    List<Integer> getMyListNalivatorsID(int idNal);

    @Query("select mylistnalivators.nameNalivator  from mylistnalivators WHERE id = :idNal")
    List<String> getMyListNalivatorsID(int idNal);

    @Query("select mylistnalivators.kolvoR  from mylistnalivators WHERE id = :idNal")
    List<Integer> getMyListNalivatorsID(int idNal);


    @Query("select  mylistnalivators.timeNaliv  from mylistnalivators WHERE id = :idNal")
    List<Double> getMyListNalivatorsID(int idNal);


*/


/*
    @Query("select id from mylistconnect")
    List<MyListAngle> getMyAngleFromNalivatorAll();


    @Query("DELETE FROM mylistconnect WHERE != -1")
    void deleteByAllmylistconnect();
*/

}
