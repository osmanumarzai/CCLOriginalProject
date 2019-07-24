package com.tkxel.admin.ordertaking.ModelClass;

/**
 * Created by admin on 12/21/2017.
 */
import java.io.Serializable;

public class Bean implements Serializable
{
    private static final long serialVersionUID = -5435670920302756945L;
    private int val1 =0;
    private int val2 =0;
    private double Total = 0;

    public int getVal1() {
        return val1;
    }

    public void setVal1(int val1) {
        this.val1 = val1;
    }

    public int getVal2() {
        return val2;
    }

    public void setVal2(int val2) {
        this.val2 = val2;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(Double total) {
        Total = total;
    }

}