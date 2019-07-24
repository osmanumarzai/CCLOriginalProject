package com.tkxel.admin.ordertaking.ModelClass;

/**
 * Created by admin on 10/18/2017.
 */
public class LoginDetails {
    //http://10.0.1.164:8080/api/IsUserValid/khuram@gmail.com/khuram123
    //{"EmployeeId":1000013,"Name":"Khuram javeed","RegionId":"15","isValid":true,"Email":"khuram@gmail.com"}

    String Name,Email ,RegionId;
    int EmployeeId;

    public String get_RegionId() {
        return RegionId;
    }
    public void set_RegionId(String RegionId) {
        this.RegionId = RegionId;
    }



    public String get_Email() {
        return Email;
    }
    public void set_Email(String Email) {
        this.Email = Email;
    }

    public String get_Name() {
        return Name;
    }
    public void set_Name(String Name) {
        this.Name = Name;
    }

    public int get_EmployeeId() {
        return EmployeeId;
    }
    public void set_EmployeeId(int EmployeeId) {
        this.EmployeeId = EmployeeId;
    }


}