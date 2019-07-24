package com.tkxel.admin.ordertaking.ModelClass;

public class SellingPattern
{

    private int CustomerID;
    private  int ProductID;
    private  int OTC;
    private  int RX;
    private  int UserID;


    public SellingPattern(int customerID, int productID, int OTC, int RX,int userID) {
        CustomerID = customerID;
        ProductID = productID;
        this.OTC = OTC;
        this.RX = RX;
        this.UserID = userID;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public int getProductID() {
        return ProductID;
    }

    public int getOTC() {
        return OTC;
    }

    public int getRX() {
        return RX;
    }

    public int getUserID() {
        return UserID;
    }
}
