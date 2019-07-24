package com.tkxel.admin.ordertaking.ModelClass;

public class Product {



    private  int ProductID;
    private  String Name1;
    private  String Name3;

    public Product(String name1, String name3) {
        Name1 = name1;
        Name3 = name3;
    }

    public String getName1() {
        return Name1;
    }

    public String getName3() {
        return Name3;
    }

    public int getProductID() {
        return ProductID;
    }



}
