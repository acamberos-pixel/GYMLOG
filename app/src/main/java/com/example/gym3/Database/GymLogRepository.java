package com.example.gym3.Database;

import android.app.Application;
import android.util.Log;

import com.example.gym3.Database.entities.GymLog;
import com.example.gym3.MainActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class GymLogRepository {
    private GymLogDAO gymLogDAO;
    private ArrayList<GymLog> allLogs;

    public GymLogRepository(Application application)
    {
        GymLogDatabase db = GymLogDatabase.getDatabase(application);
        this.gymLogDAO = db.gymLogDAO();
        this.allLogs =  this.gymLogDAO.getAllRecords();
    }

    public ArrayList<GymLog> getAllLogs() {
        Future<ArrayList<GymLog>> future = GymLogDatabase.databaseWriterExecutor.submit(
                new Callable<ArrayList<GymLog>>() {
                    @Override
                    public ArrayList<GymLog> call() throws Exception {
                        return gymLogDAO.getAllRecords();
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {

            Log.i(MainActivity.TAG, "problem when getting all gymlog in repo")
        }
        return null;
    }
    public void  insertGymLog(GymLog gymLog){
        GymLogDatabase.databaseWriterExecutor.execute(()->
        {
            gymLogDAO.insert(gymLog);
        });
    }


}
