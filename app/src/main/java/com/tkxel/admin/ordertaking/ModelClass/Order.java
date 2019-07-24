package com.tkxel.admin.ordertaking.ModelClass;

/**
 * Created by admin on 10/18/2017.
 */
public class Order {

    String SalesPersonName,  strLat, strLog, Region;

    public String get_SalesPersonName() {
        return SalesPersonName;
    }

    public void set_SalesPersonName(String SalesPersonName) {
        this.SalesPersonName = SalesPersonName;
    }

    public String get_Region() {
        return Region;
    }

    public void set_Region(String Region) {
        this.Region = Region;
    }





    public String get_strLog() {
        return strLog;
    }
    public void set_strLog(String strLog) {
        this.strLog = strLog;
    }

    public String get_strLat() {
        return strLat;
    }
    public void set_strLat(String strLat) {
        this.strLat = strLat;
    }


    public int get_PRODUCT_ID() {
        return _PRODUCT_ID;
    }

    public void set_PRODUCT_ID(int _PRODUCT_ID) {
        this._PRODUCT_ID = _PRODUCT_ID;
    }


    private int ord_id, _USER_ID, _PRODUCT_ID, SalesPersonId;

    public int get_SalesPersonId() {
        return SalesPersonId;
    }

    public void set_SalesPersonId(int SalesPersonId) {
        this.SalesPersonId = SalesPersonId;
    }


    private String Name;

    private String Location;
    private String PRICE;
    private String Date;
    private String QtyOrder, itemQty, itemPrice;
    private String Status;
    private String customer, TotleAmount, CrdLimit;


    public String get_TotleAmount() {
        return TotleAmount;
    }

    public void set_TotleAmount(String TotleAmount) {
        this.TotleAmount = TotleAmount;
    }

    public String get_CrdLimit() {
        return CrdLimit;
    }

    public void set_CrdLimit(String CrdLimit) {
        this.CrdLimit = CrdLimit;
    }


    public String get_itemQty() {
        return itemQty;
    }

    public void set_itemQty(String itemQty) {
        this.itemQty = itemQty;
    }

    public String get_itemPrice() {
        return itemPrice;
    }

    public void set_itemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }


    public int get_USER_ID() {
        return _USER_ID;
    }

    public void set_USER_ID(int _USER_ID) {
        this._USER_ID = _USER_ID;
    }


    public int getId() {
        return ord_id;
    }

    public void setId(int id) {
        this.ord_id = id;
    }


    public String get_customer() {
        return customer;
    }

    public void set_customer(String customer) {
        this.customer = customer;
    }

    public String get_Status() {
        return Status;
    }

    public void set_Status(String Status) {
        this.Status = Status;
    }

    public String get_QtyOrder() {
        return QtyOrder;
    }

    public void set_QtyOrder(String QtyOrder) {
        this.QtyOrder = QtyOrder;
    }

    public String get_Date() {
        return Date;
    }

    public void set_Date(String Date) {
        this.Date = Date;
    }

    public String get_PRICE() {
        return PRICE;
    }

    public void set_PRICE(String PRICE) {
        this.PRICE = PRICE;
    }

    public String get_Location() {
        return Location;
    }

    public void set_Location(String Location) {
        this.Location = Location;
    }

    public String get_Name() {
        return Name;
    }

    public void set_Name(String Name) {
        this.Name = Name;
    }

}
