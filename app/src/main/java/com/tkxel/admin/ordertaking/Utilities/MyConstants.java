package com.tkxel.admin.ordertaking.Utilities;

import com.tkxel.admin.ordertaking.ModelClass.EmployeeTarget;
import com.tkxel.admin.ordertaking.ModelClass.LoginDetails;
import com.tkxel.admin.ordertaking.ModelClass.ModelCustomer;
import com.tkxel.admin.ordertaking.ModelClass.Order;
import com.tkxel.admin.ordertaking.ModelClass.Order_array;
import com.tkxel.admin.ordertaking.ModelClass.PaymentPostmodel;
import com.tkxel.admin.ordertaking.ModelClass.Product_model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 11/7/2017.
 * m micro soft iot
 * power biimran
 */

public class MyConstants {

    //  public static String local_IP = "http://10.0.1.164:8080"; waheed
    //  public static String Live_IP = "http://110.36.230.78:33811";

    //  Client testing  Ip      http://42.201.208.109:8080
    //  Client IP Live          http://42.201.208.109:8081

    // new ip http://202.154.226.70:8081/

//    public static String used_IP = "http://202.154.226.70:8081";
//    public static String used_IP_Live = "http://202.154.226.70:8081";


    //public static String used_IP = "http://202.154.226.70:8081";
    //public static String used_IP_Live = "http://202.154.226.70:8081";



//    public static String used_IP = "http://45.76.247.39/";
//    public static String used_IP_Live = "http://45.76.247.39/";

    //192.168.8.129

    public static String used_IP = "http://192.168.18.129:8082";
    public static String used_IP_Live = "http://192.168.18.129:8082";





    public static String image1_64 = "sdfasfasdfasfasdfasdfa";
    public static String image2_64 = "asdfasfasfasdfasdfasdfasdf";
    public static int customerpostionDepp = 0;


    //

    public static String url_AllProducts = used_IP + "/api/GetProducts";
    public static String url_EmployeeTarget = used_IP + "/api/GetEmployeeTarget/";

    public static List<EmployeeTarget> listEmployeeTarget = new ArrayList<>();
    public static ArrayList<InfoRowdata> list_InfoRowdata = new ArrayList<>();
    public static List<Product_model> mArrayProducts = new ArrayList<>();
    public static List<PaymentPostmodel> mListPayment = new ArrayList<>();

    public static List<PaymentPostmodel> mListCustomer = new ArrayList<>();


    public static List<Product_model> mArrayProducts_selected = new ArrayList<>();
    // public static List<ModelTargets> mArrayTargets = new ArrayList<>();
    // public static List<Order_Details> mArrayOrderPDetails = new ArrayList<>();

    public static List<ModelCustomer> listCustomer = new ArrayList<>();
    public static ArrayList<Order> listOrder = new ArrayList<>();
    public static ArrayList<Order> listOrderOffline = new ArrayList<>();


    public static List<LoginDetails> listLoginDetails = new ArrayList<>();

    public static ArrayList<Order_array> mOrderListByCustomer = new ArrayList<>();


    //  public static ArrayList<GetOrdersByCustmer> mOrderListByCustomer = new ArrayList<>();


//    public static String url_AllCustomer = "http://10.0.1.164:8080/api/GetCustomers";
//    public static String url_AllProducts = "http://110.36.230.78:33811/api/GetProducts";
//      public static String url_EmployeeTarget = "http://110.36.230.78:33811/api/GetEmployeeTarget/";

    //  http://110.36.230.78:33811api/GetOrderBySalesPersonId/8


    // http://110.36.230.78:33811/api/GetCustomerByRegion/15/1000010

    //   http://110.36.230.78:33811/api/GetOrderBySalesPersonId/8


    //   http://10.0.1.164:8080/api/GetOrderBySalesPersonId/8

    //http://hasmukhbhadani.blogspot.com/2014/12/edittext-in-listview-focusable-edittext.html


}

