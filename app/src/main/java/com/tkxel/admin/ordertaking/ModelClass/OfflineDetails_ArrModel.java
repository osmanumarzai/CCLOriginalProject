package com.tkxel.admin.ordertaking.ModelClass;

/**
 * Created by admin on 12/21/2017.
 */

import java.io.Serializable;
import java.util.List;

public class OfflineDetails_ArrModel implements Serializable {


    String SalesPersonName,  strLat, strLog, Region ,Location;




    public String get_Location() {
        return Location;
    }

    public void set_Location(String Location) {
        this.Location = Location;
    }





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



    List<Order_Details> mArraydetails_offline2;


    int customerId = 0;
    String TottleAmount = "0", CrdtLimit = "0";


    public String get_TottleAmount() {
        return TottleAmount;
    }

    public void set_TottleAmount(String TottleAmount) {
        this.TottleAmount = TottleAmount;
    }
    public String get_CrdtLimit() {
        return CrdtLimit;
    }
    public void set_CrdtLimit(String CrdtLimit) {
        this.CrdtLimit = CrdtLimit;
    }



    public int get_customerId() {
        return customerId;
    }


    public void set_customerId(int customerId) {

        this.customerId = customerId;
    }


    public List<Order_Details> getDetailsList() {
        return mArraydetails_offline2;
    }

    public void setDetailsList(List<Order_Details> mArraydetails_offline2) {

        this.mArraydetails_offline2 = mArraydetails_offline2;
    }


}