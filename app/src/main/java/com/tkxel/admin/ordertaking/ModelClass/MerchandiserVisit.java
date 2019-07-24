package com.tkxel.admin.ordertaking.ModelClass;

public class MerchandiserVisit
{
    private int MerchandiserID;
    private int CustomerID;
    private String Location;

    public MerchandiserVisit(int merchandiserID, int customerID, String location) {
        MerchandiserID = merchandiserID;
        CustomerID = customerID;
        Location = location;
    }

    public int getMerchandiserID() {
        return MerchandiserID;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public String getLocation() {
        return Location;
    }


}
