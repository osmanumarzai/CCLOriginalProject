package com.tkxel.admin.ordertaking.ModelClass;

/**
 * Created by admin on 10/18/2017.
 */
public class PaymentPostmodel {


    private int CustomerId, SalesPersonId, PaymentId, setId;


    public int get_Id() {

        return setId;
    }

    public void set_Id(int setId) {

        this.setId = setId;
    }


    double PaymentAmount;
    private String CustomerName, PaymentDate, PaymentMethod, ChequeNo, Detail, Image, Region, ImageUrl;


    public int get_PaymentId() {
        return PaymentId;
    }

    public void set_PaymentId(int PaymentId) {
        this.PaymentId = PaymentId;
    }


    public double get_PaymentAmount() {
        return PaymentAmount;
    }

    public void set_PaymentAmount(double PaymentAmount) {
        this.PaymentAmount = PaymentAmount;
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


    public String get_ImageUrl() {
        return ImageUrl;
    }

    public void set_ImageUrl(String ImageUrl) {
        this.ImageUrl = ImageUrl;
    }


    public String get_Region() {
        return Region;
    }

    public void set_Region(String Region) {
        this.Region = Region;
    }

    public String get_Image() {
        return Image;
    }

    public void set_Image(String Image) {
        this.Image = Image;
    }


    public String get_Detail() {
        return Detail;
    }

    public void set_Detail(String Detail) {
        this.Detail = Detail;
    }


    public String get_ChequeNo() {
        return ChequeNo;
    }

    public void set_ChequeNo(String ChequeNo) {
        this.ChequeNo = ChequeNo;
    }


    public String get_PaymentMethod() {
        return PaymentMethod;
    }

    public void set_PaymentMethod(String PaymentMethod) {
        this.PaymentMethod = PaymentMethod;
    }


    public String get_PaymentDate() {
        return PaymentDate;
    }

    public void set_PaymentDate(String PaymentDate) {
        this.PaymentDate = PaymentDate;
    }

    public String get_CustomerName() {
        return CustomerName;
    }

    public void set_CustomerName(String CustomerName) {
        this.CustomerName = CustomerName;
    }


}