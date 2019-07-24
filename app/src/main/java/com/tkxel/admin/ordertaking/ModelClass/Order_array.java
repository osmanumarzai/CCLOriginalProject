package com.tkxel.admin.ordertaking.ModelClass;

import java.util.List;

/**
 * Created by admin on 11/21/2017.
 */

public class Order_array {


    /*  -----------------------------------------------*/
    double DELIVERY_ORDER_ID;
    String DELIVERY_ORDER_DATE, STATUS, REGION, CUSTOMER_NAME, SALES_PERSON_NAME;


    public double get_DELIVERY_ORDER_ID() {
        return DELIVERY_ORDER_ID;
    }

    public void set_DELIVERY_ORDER_ID(double DELIVERY_ORDER_ID) {
        this.DELIVERY_ORDER_ID = DELIVERY_ORDER_ID;
    }


    public String get_SALES_PERSON_NAME() {
        return SALES_PERSON_NAME;
    }

    public void set_SALES_PERSON_NAME(String SALES_PERSON_NAME) {
        this.SALES_PERSON_NAME = SALES_PERSON_NAME;
    }


    public String get_REGION() {
        return REGION;
    }

    public void set_REGION(String REGION) {
        this.REGION = REGION;
    }


    public String get_CUSTOMER_NAME() {
        return CUSTOMER_NAME;
    }

    public void set_CUSTOMER_NAME(String CUSTOMER_NAME) {
        this.CUSTOMER_NAME = CUSTOMER_NAME;
    }


    public String get_STATUS() {
        return STATUS;
    }

    public void set_STATUS(String STATUS) {
        this.STATUS = STATUS;
    }


    public String get_DELIVERY_ORDER_DATE() {
        return DELIVERY_ORDER_DATE;
    }

    public void set_DELIVERY_ORDER_DATE(String DELIVERY_ORDER_DATE) {
        this.DELIVERY_ORDER_DATE = DELIVERY_ORDER_DATE;
    }





   /* -------------------------------------------*/








    GetOrdersByCustmer obj_customer;


    public GetOrdersByCustmer get_obj_customer() {
        return obj_customer;
    }

    public void set_obj_customer(GetOrdersByCustmer obj_customer) {
        this.obj_customer = obj_customer;
    }

    List<Product_array> Order_array;
    public List<Product_array> get_mProduct_array() {
        return Order_array;
    }

    public void set_mProduct_array(List<Product_array> Order_array) {
        this.Order_array = Order_array;
    }



    int TotalPrice, TotalItemQuantity, Id;
    String Status, OrderLocation, CreatedDate, ShipmentDate, Name;


    public int get_TotalItemQuantity() {
        return TotalItemQuantity;
    }
    public void set_TotalItemQuantity(int TotalItemQuantity) {
        this.TotalItemQuantity = TotalItemQuantity;
    }

    public int get_TotalPrice() {
        return TotalPrice;
    }
    public void set_TotalPrice(int TotalPrice) {
        this.TotalPrice = TotalPrice;
    }


    public int get_Id() {
        return Id;
    }

    public void set_Id(int Id) {
        this.Id = Id;
    }


    public String get_Name() {
        return Name;
    }

    public void set_Name(String Name) {
        this.Name = Name;
    }

    public String get_ShipmentDate() {
        return ShipmentDate;
    }

    public void set_ShipmentDate(String ShipmentDate) {
        this.ShipmentDate = ShipmentDate;
    }

    public String get_CreatedDate() {
        return CreatedDate;
    }

    public void set_CreatedDate(String CreatedDate) {
        this.CreatedDate = CreatedDate;
    }

    public String get_Status() {
        return Status;
    }

    public void set_Status(String Status) {
        this.Status = Status;
    }

    public String get_OrderLocation() {
        return OrderLocation;
    }

    public void set_OrderLocation(String OrderLocation) {
        this.OrderLocation = OrderLocation;
    }


}
