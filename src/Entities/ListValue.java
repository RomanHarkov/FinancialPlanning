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

    //Добавление значения в список
    public void addToList(String value){

        this.list.add(value);

    }

    //Добавление готового списка
    public void addList(ArrayList<String> list){

        this.list = list;

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
