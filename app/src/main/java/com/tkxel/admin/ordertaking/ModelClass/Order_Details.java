package com.tkxel.admin.ordertaking.ModelClass;

/**
 * Created by admin on 10/18/2017.
 */
public class Order_Details {
    private int id, _USER_ID, _Order_ID;
    double _PRODUCT_ID;

    private String itemPRICE="0.0", itemQty="0.0",itemName;

    public int get_Order_ID() {
        return _Order_ID;
    }

    public void set_Order_ID(int _Order_ID) {
        this._Order_ID = _Order_ID;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double get_PRODUCT_ID() {
        return _PRODUCT_ID;
    }

    public void set_PRODUCT_ID(double _PRODUCT_ID) {
        this._PRODUCT_ID = _PRODUCT_ID;
    }

    public int get_USER_ID() {
        return _USER_ID;
    }

    public void set_USER_ID(int _USER_ID) {
        this._USER_ID = _USER_ID;
    }


    public String get_itemQty() {
        return itemQty;
    }

    public void set_itemQty(String itemQty) {
        this.itemQty = itemQty;
    }

    public String get_itemPRICE() {
        return itemPRICE;
    }

    public void set_itemPRICE(String itemPRICE) {
        this.itemPRICE = itemPRICE;
    }
    public String get_itemName() {
        return itemName;
    }

    public void set_itemName(String itemName) {
        this.itemName = itemName;
    }



}
