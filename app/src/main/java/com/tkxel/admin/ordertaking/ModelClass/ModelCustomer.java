package com.tkxel.admin.ordertaking.ModelClass;

/**
 * Created by admin on 10/18/2017.
 */
public class ModelCustomer {

    int id_, SALES_PERSON_ID;

    public int getId() {
        return id_;
    }

    public void setId(int id_) {
        this.id_ = id_;
    }

    private double CUSTOMER_ID;
    private String CUSTOMER_NAME= "", ADDRESS = "", REGION= "", CUSTOMER_TYPE= "";

    public int get_SALES_PERSON_ID() {
        return SALES_PERSON_ID;
    }

    public void set_SALES_PERSON_ID(int SALES_PERSON_ID) {
        this.SALES_PERSON_ID = SALES_PERSON_ID;
    }

    public double get_CUSTOMER_ID() {
        return CUSTOMER_ID;
    }

    public void set_CUSTOMER_ID(double CUSTOMER_ID) {
        this.CUSTOMER_ID = CUSTOMER_ID;
    }

    public String get_CUSTOMER_TYPET() {
        return CUSTOMER_TYPE;
    }

    public void set_CUSTOMER_TYPE(String CUSTOMER_TYPE) {
        this.CUSTOMER_TYPE = CUSTOMER_TYPE;
    }

    double  CREDIT_LIMIT = 0.0 ;
    public double get_CREDIT_LIMIT() {
        return CREDIT_LIMIT;
    }

    public void set_CREDIT_LIMIT(double CREDIT_LIMIT) {
        this.CREDIT_LIMIT = CREDIT_LIMIT;
    }

    public String get_REGION() {
        return REGION;
    }

    public void set_REGION(String REGION) {
        this.REGION = REGION;
    }

    public String get_ADDRESS() {
        return ADDRESS;
    }

    public void set_ADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String get_CUSTOMER_NAME() {
        return CUSTOMER_NAME;
    }

    public void set_CUSTOMER_NAME(String CUSTOMER_NAME) {
        this.CUSTOMER_NAME = CUSTOMER_NAME;
    }


}