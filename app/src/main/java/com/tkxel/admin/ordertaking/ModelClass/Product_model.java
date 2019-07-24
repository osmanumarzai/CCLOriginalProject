package com.tkxel.admin.ordertaking.ModelClass;

/**
 * Created by admin on 10/18/2017.
 */
public class Product_model {

    boolean isSelected = false, isBox = false;

    double Count = 0;
    double tottle_Price = 0.0;

    public boolean getisSelected() {
        return isSelected;
    }

    public void setisSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean get_isBox() {
        return isBox;
    }

    public void set_isBox(boolean isBox) {
        this.isBox = isBox;
    }


    public double get_tottle_Price() {
        return tottle_Price;
    }

    public void set_tottle_Price(double tottle_Price) {
        this.tottle_Price = tottle_Price;
    }

    public double getCount() {
        return Count;
    }

    public void setCount(double Count) {
        this.Count = Count;
    }

    int id;
    double ITEM_ID, PRICERATE = 0.0;
    String ITEM_CODE, ITEM_DESC, PART_NO, UOM, WEIGHT_IN_KG, PRICEREGION;


    public double get_PRICERATE() {
        return PRICERATE;
    }

    public void set_PRICERATE(double PRICERATE) {
        this.PRICERATE = PRICERATE;
    }

    public String get_PRICEREGION() {
        return PRICEREGION;
    }

    public void set_PRICEREGION(String PRICEREGION) {
        this.PRICEREGION = PRICEREGION;
    }


    public String get_WEIGHT_IN_KG() {
        return WEIGHT_IN_KG;
    }

    public void set_WEIGHT_IN_KG(String WEIGHT_IN_KG) {
        this.WEIGHT_IN_KG = WEIGHT_IN_KG;
    }

    public String get_UOM() {
        return UOM;
    }

    public void set_UOM(String UOM) {
        this.UOM = UOM;
    }


    public String get_PART_NO() {
        return PART_NO;
    }

    public void set_PART_NO(String PART_NO) {
        this.PART_NO = PART_NO;
    }


    public String get_ITEM_DESC() {
        return ITEM_DESC;
    }

    public void set_ITEM_DESC(String ITEM_DESC) {
        this.ITEM_DESC = ITEM_DESC;
    }


    public String get_ITEM_CODE() {
        return ITEM_CODE;
    }

    public void set_ITEM_CODE(String ITEM_ID) {
        this.ITEM_CODE = ITEM_CODE;
    }


    public int get_Id() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double get_ITEM_ID() {
        return ITEM_ID;
    }

    public void set_ITEM_ID(double ITEM_ID) {
        this.ITEM_ID = ITEM_ID;
    }

}