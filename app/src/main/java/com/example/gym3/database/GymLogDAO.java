package com.example.gym3.database;

import  androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.gym3.database.entities.GymLog;


import java.util.List;


@Dao
public interface GymLogDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GymLog gymLog);

    @Query("SELECT * FROM " + GymLogDatabase.GYM_LOG_TABLE + " ORDER BY date DESC")
    List<GymLog> getAllRecords();


//    @Query("SELECT * FROM " + GymLogDatabase.GYM_LOG_TABLE + " WHERE userId = :userId ORDER BY date DESC")
//    LiveData<List<GymLog>> getAllLogsByUserId(int userId);


    @Query("SELECT * FROM " + GymLogDatabase.GYM_LOG_TABLE + " WHERE userId = :userId ORDER BY date DESC")
    List<GymLog> getRecordableUserId(int userId);

}

