package com.btcsc.project;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class ListPlanning {
    String name;
    int id;

    public ListPlanning(String name) {
        this.name = name;
    }

    public ListPlanning(String name, int id) {
        this.name = name;
        this.id = id;
    }

   static ArrayList<ListPlanning> getData()
   {
       ArrayList<ListPlanning> temp = new ArrayList<>();
       temp.add(new ListPlanning("Mặc định"));
       temp.add(new ListPlanning("Ưa thích"));
       temp.add(new ListPlanning("Cá nhân"));
       temp.add(new ListPlanning("Công việc"));
       temp.add(new ListPlanning("Gia đình"));

       return  temp;
   }

    @Override
    public String toString() {
        return name;
    }
}
