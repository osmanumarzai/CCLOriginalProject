package com.tkxel.admin.ordertaking.ModelClass;

public class Customer {


    private int Customer_Id ;
    private String ShopName;
    private String ShopType ;
    private String ShopkeeperName ;
    private String ContactNum ;
    private String Address ;
    private String City ;
    private String Area ;
    private int UserID ;

    public Customer(String shopName, String shopType, String shopkeeperName, String contactNum, String address,int userID,String city,String area) {
        ShopName = shopName;
        ShopType = shopType;
        ShopkeeperName = shopkeeperName;
        ContactNum = contactNum;
        Address = address;
        UserID = userID;
        City = city;
        Area = area;
    }

    public int getCustomer_Id() {
        return Customer_Id;
    }

    public String getShopName() {
        return ShopName;
    }

    public String getShopType() {
        return ShopType;
    }

    public String getShopkeeperName() {
        return ShopkeeperName;
    }

    public String getContactNum() {
        return ContactNum;
    }

    public String getAddress() {
        return Address;
    }

    public String getCity() {
        return City;
    }

    public String getArea() {
        return Area;
    }

    public int getUserID() {
        return UserID;
    }
}
