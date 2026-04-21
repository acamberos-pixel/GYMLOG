package com.example.gym3.Database;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.*;
import java.util.Objects;
import java.time.LocalDate;

// gym log entity

// will replace tablename with diff var later according to professor
@Entity(tableName = "gymLog")
public class GymLog {
    @PrimaryKey(autoGenerate = true)

    private int id;

    private String exercise;

    private double weight;

    private int reps;

    private LocalDate date;


    //constructor
    public GymLog(String exercise, double weight, int reps) {
        this.exercise = exercise;
        this.weight = weight;
        this.reps = reps;
        date = LocalDate.now();
    }

    //equals and hashcodes
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GymLog gymLog = (GymLog) o;
        return getId() == gymLog.getId() && Double.compare(getWeight(), gymLog.getWeight()) == 0 && getReps() == gymLog.getReps() && Objects.equals(getExercise(), gymLog.getExercise()) && Objects.equals(getDate(), gymLog.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getExercise(), getWeight(), getReps(), getDate());
    }

    //getters and setters
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
