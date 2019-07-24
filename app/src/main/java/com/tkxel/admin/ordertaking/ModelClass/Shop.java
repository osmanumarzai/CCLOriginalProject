package com.tkxel.admin.ordertaking.ModelClass;

public class Shop
{

    private  int Id;
    private String Name;

    public Shop(String name) {
        Name = name;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    @Override
    public String toString() {
        return Name;
    }
}
