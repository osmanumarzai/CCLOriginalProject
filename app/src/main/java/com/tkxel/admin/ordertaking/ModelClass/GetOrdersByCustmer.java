package com.tkxel.admin.ordertaking.ModelClass;

import java.util.List;

/**
 * Created by admin on 11/21/2017.
 */

public class GetOrdersByCustmer {

//    {
//            "DELIVERY_ORDER_ID": 1014324.0,
//            "DELIVERY_ORDER_DATE": "2017-12-15T14:42:27",
//            "STATUS": "PREPARED",
//            "REGION": "15",
//            "CUSTOMER_NAME": "FORK N KNIVES KOHINOOR FAISALABAD",
//            "SALES_PERSON_NAME": "Khuram javeed"
//    }


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


    List<Order_array> Order_array;


    public List<Order_array> get_mOrder_list() {
        return Order_array;
    }

    public void set_mOrder_list(List<Order_array> Order_array) {
        this.Order_array = Order_array;
    }


    int NtnNo, Id;
    String Phone, BusinessName, Address1, Address2, City, State, Country, CnicNo, Email, Name;


    public String get_CnicNo() {
        return CnicNo;
    }

    public void set_CnicNo(String CnicNo) {
        this.CnicNo = CnicNo;
    }


    public String get_Name() {
        return Name;
    }

    public void set_Name(String Name) {
        this.Name = Name;
    }

    public String get_Email() {
        return Email;
    }

    public void set_Email(String Email) {
        this.Email = Email;
    }


    public String get_BusinessName() {
        return BusinessName;
    }

    public void set_BusinessName(String BusinessName) {
        this.BusinessName = BusinessName;
    }


    public String get_Country() {
        return Country;
    }

    public void set_Country(String Country) {
        this.Country = Country;
    }


    public String get_Address1e() {
        return Address1;
    }

    public void set_Address1(String Address1) {
        this.Address1 = Address1;
    }


    public String get_Address2() {
        return Address2;
    }

    public void set_Address2(String Address2) {
        this.Address2 = Address2;
    }


    public String get_Phone() {
        return Phone;
    }

    public void set_Phone(String Phone) {
        this.Phone = Phone;
    }

    public String get_City() {
        return City;
    }

    public void set_City(String City) {
        this.City = City;
    }

    public String get_State() {
        return State;
    }

    public void set_State(String State) {
        this.State = State;
    }


    public int get_NtnNo() {
        return NtnNo;
    }

    public void set_NtnNo(int NtnNo) {
        this.NtnNo = NtnNo;
    }

    public int get_Id() {
        return Id;
    }

    public void set_Id(int Id) {
        this.Id = Id;
    }

}
