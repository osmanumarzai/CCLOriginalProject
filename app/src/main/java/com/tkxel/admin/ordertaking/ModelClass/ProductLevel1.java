package com.tkxel.admin.ordertaking.ModelClass;

public class ProductLevel1 {

    private  int Id;

    private String Name;

    public ProductLevel1(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public int getId() {
        return Id;
    }
}
