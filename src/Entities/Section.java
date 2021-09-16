package Entities;

import Entities.ListValue;

import java.util.ArrayList;

/**
 * Created by Роман on 23.03.2021.
 */
public class Section {

    private String name;
    private ArrayList<ListValue> listValue;

    public Section() {

    }

    public void addListValue(ListValue list){

        listValue.add(list);
    }

    public ArrayList getListValue(){

        return this.listValue;

    }

    public String getName(){

        return this.name;
    }

    public void setName(String name){

        this.name = name;

    }
}
