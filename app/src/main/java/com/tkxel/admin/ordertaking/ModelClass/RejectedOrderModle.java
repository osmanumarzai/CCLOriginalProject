package com.tkxel.admin.ordertaking.ModelClass;

/**
 * Created by admin on 12/21/2017.
 */

import java.io.Serializable;
import java.util.List;

public class RejectedOrderModle implements Serializable {

    private int CustomerId = 0, SalesPersonId = 0;
    double  TotalAmount = 0.0, CREDIT_LIMIT = 0.0;

    public List<RejectedItemsDetails> listRejected;
    public List<RejectedItemsDetails> get_LIST()
    {
        return listRejected;
    }
     public void set_LIST(List<RejectedItemsDetails> listRejected) {
                this.listRejected = listRejected;
    }




    public double get_CREDIT_LIMIT() {
        return CREDIT_LIMIT;
    }
    public void set_CREDIT_LIMIT(double CREDIT_LIMIT) {
        this.CREDIT_LIMIT = CREDIT_LIMIT;
    }
    public double get_TotalAmount() {
        return TotalAmount;
    }
    public void set_TotalAmount(double TotalAmount) {
        this.TotalAmount = TotalAmount;
    }

    public int get_SalesPersonId() {
        return SalesPersonId;
    }
    public void set_SalesPersonId(int SalesPersonId) {
        this.SalesPersonId = SalesPersonId;
    }



    public int get_CustomerId() {
        return CustomerId;
    }
    public void set_CustomerId(int CustomerId) {
        this.CustomerId = CustomerId;
    }

}