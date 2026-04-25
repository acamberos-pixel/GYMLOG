package com.example.gym3.database.entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.gym3.database.GymLogDatabase;

import java.time.LocalDateTime;
import java.util.Objects;





// will replace tablename with diff var later according to professor
@Entity(tableName = GymLogDatabase.GYM_LOG_TABLE)
public class GymLog {
    @PrimaryKey(autoGenerate = true)

    private int id;

    private String exercise;

    private double weight;

    private int reps;

    private LocalDateTime date;

    private int userId ;

    //constructor
    public GymLog(String exercise, double weight, int reps, int userId) {
        this.exercise = exercise;
        this.weight = weight;
        this.reps = reps;
        this.userId = userId;
        date = LocalDateTime.now();
    }

    // to string


    @Override
    public String toString() {
        return

             exercise + '\n' +
                "weight:" + weight + '\n'+
                ", reps:" + reps + '\n'+
                ", date:" + date.toString() + '\n' +
                "=-=-=-=-=-=-=-=- \n";
    }

    //equals and hashcodes

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GymLog gymLog = (GymLog) o;
        return getId() == gymLog.getId() && Double.compare(getWeight(), gymLog.getWeight()) == 0 && getReps() == gymLog.getReps() && getUserId() == gymLog.getUserId() && Objects.equals(getExercise(), gymLog.getExercise()) && Objects.equals(getDate(), gymLog.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getExercise(), getWeight(), getReps(), getDate(), getUserId());
    }


    //getters and setters


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
