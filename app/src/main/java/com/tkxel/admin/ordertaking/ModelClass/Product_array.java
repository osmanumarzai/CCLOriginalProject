package com.tkxel.admin.ordertaking.ModelClass;

/**
 * Created by admin on 11/21/2017.
 */

public class Product_array {

    int Price, Quantity, ProductId;
    String Name;

    public int get_Quantity() {
        return Quantity;
    }
    public void set_Quantity(int TotalItemQuantity) {
        this.Quantity = TotalItemQuantity;
    }


    public int get_Price() {
        return Price;
    }

    public void set_Price(int TotalPrice) {
        this.Price = TotalPrice;
    }


    public int get_ProductId() {
        return ProductId;
    }
    public void set_ProductId(int Id) {
        this.ProductId = Id;
    }


    public String get_Name() {
        return Name;
    }

    public void set_Name(String Name) {
        this.Name = Name;
    }


    public String get_Status() {
        return Name;
    }
    public void set_Status(String Status) {
        this.Name = Status;
    }


}
