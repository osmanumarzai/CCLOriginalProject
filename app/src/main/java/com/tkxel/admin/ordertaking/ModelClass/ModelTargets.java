package com.tkxel.admin.ordertaking.ModelClass;

/**
 * Created by admin on 10/18/2017.
 */
public class ModelTargets {

//  "EmployeeId": 1,
//          "Month": "january",
//          "RegionId": 10,
//          "SaleTarget": 12500,
//          "SaleTargetQuantity": 100,
//          "Id": 0,
//          "Name": null

    String Month, RegionId, SaleTarget, SaleTargetQuantity;

    public String get_SaleTargetQuantity() {
        return SaleTargetQuantity;
    }

    public void set_SaleTargetQuantity(String SaleTargetQuantity) {
        this.SaleTargetQuantity = SaleTargetQuantity;
    }


    public String get_SaleTarget() {
        return SaleTarget;
    }

    public void set_SaleTarget(String SaleTarget) {
        this.SaleTarget = SaleTarget;
    }


    public String get_Month() {
        return Month;
    }

    public void set_Month(String Month) {
        this.Month = Month;
    }

    public String get_RegionId() {
        return RegionId;
    }

    public void set_RegionId(String RegionId) {
        this.RegionId = RegionId;
    }


}