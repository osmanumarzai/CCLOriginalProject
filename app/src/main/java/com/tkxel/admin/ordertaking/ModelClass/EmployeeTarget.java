package com.tkxel.admin.ordertaking.ModelClass;

/**
 * Created by admin on 10/18/2017.
 */
public class EmployeeTarget {
    String EmployeeName, Month;

    int RegionId, SaleTarget=0, TargetAchieved=0;

    public int get_RegionId() {
        return RegionId;
    }

    public void set_RegionId(int RegionId) {
        this.RegionId = RegionId;
    }


    public int get_SaleTarget() {
        return SaleTarget;
    }

    public void set_SaleTarget(int SaleTarget) {
        this.SaleTarget = SaleTarget;
    }

    public int get_TargetAchieved() {
        return TargetAchieved;
    }

    public void set_TargetAchieved(int TargetAchieved) {
        this.TargetAchieved = TargetAchieved;
    }

    public String get_Month() {
        return Month;
    }

    public void set_Month(String Month) {
        this.Month = Month;
    }

    public String get_EmployeeName() {
        return EmployeeName;
    }

    public void set_EmployeeName(String EmployeeName) {
        this.EmployeeName = EmployeeName;
    }


}