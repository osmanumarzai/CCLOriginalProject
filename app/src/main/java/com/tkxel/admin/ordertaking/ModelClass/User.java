package com.tkxel.admin.ordertaking.ModelClass;

/**
 * Created by admin on 10/18/2017.
 */
public class User {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    private String coments;

    private String bussiness;
    private String first_name;
    private String last_name;
    private String phone;
    private String name;
    private String email;
    private String cnic;
    private String ntn;
    private String city;
    private String state;
    private String country;
    private String address;


    public String get_coments() {
        return coments;
    }

    public void set_coments(String coments) {
        this.coments = coments;
    }


    public String get_phone() {
        return phone;
    }

    public void set_phone(String phone) {
        this.phone = phone;
    }


    public String get_last_name() {
        return last_name;
    }

    public void set_last_name(String last_name) {
        this.last_name = last_name;
    }


    public String get_first_name() {
        return first_name;
    }

    public void set_first_name(String first_name) {
        this.first_name = first_name;
    }

    public String get_bussiness() {
        return bussiness;
    }

    public void set_bussiness(String bussiness) {
        this.bussiness = bussiness;
    }


    public String get_address() {
        return address;
    }

    public void set_address(String address) {
        this.address = address;
    }

    public String get_ntn() {
        return ntn;
    }

    public void set_ntn(String ntn) {
        this.ntn = ntn;
    }


    public String getcountry() {
        return country;
    }

    public void setcountry(String country) {
        this.country = country;
    }

    public String getstate() {
        return state;
    }

    public void setstate(String state) {
        this.state = state;
    }


    public String getcity() {
        return city;
    }

    public void setcity(String city) {
        this.city = city;
    }


    public String getcnic() {
        return cnic;
    }

    public void setcnic(String cnic) {
        this.cnic = cnic;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}