package com.tkxel.admin.ordertaking.ModelClass;

public class ProductAdvertisement
{

    private int ShopID ;
    private int MerchandiserID;
    private int ProductId ;
    private int Poster ;
    private int Sticker ;
    private int Flyer ;
    private int Buntings ;
    private int FlagSign ;
    private int ShelfStrip ;
    private int MobileHanger ;
    private int CCD ;

    public ProductAdvertisement(int shopID, int merchandiserID, int productId, int poster, int sticker, int flyer, int buntings, int flagSign, int shelfStrip, int mobileHanger,int ccd) {
        ShopID = shopID;
        MerchandiserID = merchandiserID;
        ProductId = productId;
        Poster = poster;
        Sticker = sticker;
        Flyer = flyer;
        Buntings = buntings;
        FlagSign = flagSign;
        ShelfStrip = shelfStrip;
        MobileHanger = mobileHanger;
        CCD = ccd;
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

    public int getPoster() {
        return Poster;
    }

    public int getSticker() {
        return Sticker;
    }

    public int getFlyer() {
        return Flyer;
    }

    public int getBuntings() {
        return Buntings;
    }

    public int getFlagSign() {
        return FlagSign;
    }

    public int getShelfStrip() {
        return ShelfStrip;
    }

    public int getMobileHanger() {
        return MobileHanger;
    }
}
