package com.tkxel.admin.ordertaking.ModelClass;

public class Merchandiser
{
        private int Merchandiser_Id ;
        private String MerchandiserName ;
        private String CNIC ;
        private String MobileNumber ;
        private String FatherName ;
        private String Address ;
        private String DOB ;
        private String City ;
        private int UserID;


    public Merchandiser(String merchandiserName, String cnic, String mobileNumber, String fatherName, String address, String dob, int userID,String city) {
        MerchandiserName = merchandiserName;
        CNIC = cnic;
        MobileNumber = mobileNumber;
        FatherName = fatherName;
        Address = address;
        DOB = dob;
        UserID = userID;
        City = city;
    }

    public int getMerchandiser_Id() {
        return Merchandiser_Id;
    }

    public String getMerchandiserName() {
        return MerchandiserName;
    }

    public String getCNIC() {
        return CNIC;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public String getFatherName() {
        return FatherName;
    }

    public String getAddress() {
        return Address;
    }

    public String getDOB() {
        return DOB;
    }

    public String getCity() {
        return City;
    }
}