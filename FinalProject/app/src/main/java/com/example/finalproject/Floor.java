package com.example.finalproject;

import java.io.Serializable;
import java.util.ArrayList;

public class Floor implements Serializable {
    private String name;
    private int total;



    public Floor(String name, int total){
        this.name = name;
        this.total = total;
    }

    public String getName(){
        return this.name;
    }
    public int getCapacity(){
        return this.total;
    }



}
