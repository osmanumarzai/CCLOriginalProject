package com.tkxel.admin.ordertaking.ModelClass;

public class ProductAvailability
{

    private int ProductAvailabilityID ;
    private int ShopID ;
    private int MerchandiserID ;
    private int ProductId ;
    private int Available ;

    public ProductAvailability(int shopID, int merchandiserID, int productId, int available) {
        ShopID = shopID;
        MerchandiserID = merchandiserID;
        ProductId = productId;
        Available = available;
    }


    public int getShopID() {
        return ShopID;
    }

    public int getMerchandiserID() {
        return MerchandiserID;
    }

    public int getProductId() {
        return ProductId;
    }

    public int getAvailable() {
        return Available;
    }
}
