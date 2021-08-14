package Entities;

import java.util.ArrayList;

/**
 * Created by Роман on 23.03.2021.
 */
public class ListValue {

    private String name;
    private ArrayList<String> list;


    public ListValue() {

    }

    public void addList(String value){

        this.list.add(value);

    }

    public ArrayList getList(){

       return this.list;

    }

    public String getName(String name){

        return this.name;
    }

    public void setName(String name){

        this.name = name;
    }
}
