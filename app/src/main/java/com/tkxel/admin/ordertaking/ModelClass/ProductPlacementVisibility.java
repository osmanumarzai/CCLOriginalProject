package com.tkxel.admin.ordertaking.ModelClass;

public class ProductPlacementVisibility {


    private int CustomerID ;
    private int UserID;
    private int ProductId ;
    private int PrimaryDisplay ;
    private int OCDDisplay ;
    private int VinylBranding ;
    private int FlagSign ;
    private int CCD ;

    public ProductPlacementVisibility(int customerID, int userID, int productId, int primaryDisplay, int ocddisplay, int vinylBranding, int flagSign, int ccd) {
        CustomerID = customerID;
        UserID = userID;
        ProductId = productId;
        PrimaryDisplay = primaryDisplay;
        OCDDisplay = ocddisplay;
        VinylBranding = vinylBranding;
        FlagSign = flagSign;
        CCD = ccd;
    }

    public int getShopID() {
        return CustomerID;
    }

    public int getUserID() {
        return UserID;
    }

    public int getProductId() {
        return ProductId;
    }

    public int getPrimaryDisplay() {
        return PrimaryDisplay;
    }

    public int getOCDDisplay() {
        return OCDDisplay;
    }

    public int getVinylBranding() {
        return VinylBranding;
    }

    public int getFlagSign() {
        return FlagSign;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public int getCCD() {
        return CCD;
    }
}
