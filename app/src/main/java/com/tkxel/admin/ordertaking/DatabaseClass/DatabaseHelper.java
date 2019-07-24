package com.tkxel.admin.ordertaking.DatabaseClass;

/**
 * Created by admin on 10/18/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.tkxel.admin.ordertaking.ModelClass.ModelCustomer;
import com.tkxel.admin.ordertaking.ModelClass.Order;
import com.tkxel.admin.ordertaking.ModelClass.Order_Details;
import com.tkxel.admin.ordertaking.ModelClass.PaymentPostmodel;
import com.tkxel.admin.ordertaking.ModelClass.Product_model;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    //https://stackoverflow.com/questions/19194576/how-do-i-view-the-sqlite-database-on-an-android-device
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "CustomerManager.db";
    // ModelCustomer table name
    private final String TABLE_USER = "customer";
    private final String TABLE_order = "tableorder";
    private final String TABLE_order_Deatails = "tableorderdetails";
    private final String TABLE_Product = "tableProduct";

    private final String TABLE_Payment = "Payment";

    // ModelCustomer Table Columns names
//    CUSTOMER_ID: 5053,
//    CUSTOMER_NAME: "FORK N KNIVES KOHINOOR FAISALABAD",
//    ADDRESS: null,
//    REGION: "15",
//    CREDIT_LIMIT: null,
//    CUSTOMER_TYPE: "plant",
//    SALES_PERSON_ID: 1000013

    private static final String COLUMN_PAYMENT_ID = "payment_id";

    private static final String COLUMN_PAYMENT_Customer = "payment_Customer";
    private static final String COLUMN_PAYMENT_method = "payment_method";
    private static final String COLUMN_PAYMENT_date = "payment_date";
    private static final String COLUMN_PAYMENT_amount = "payment_amount";
    private static final String COLUMN_PAYMENT_Detail = "payment_Detail";
    private static final String COLUMN_PAYMENT_others1 = "payment_others1";
    private static final String COLUMN_PAYMENT_others2 = "payment_others2";
    private static final String COLUMN_PAYMENT_Region = "payment_Region";
    private static final String COLUMN_PAYMENT_CustomerId = "payment_CustomerId";
    private static final String COLUMN_PAYMENT_SalesPersonId = "payment_SalesPersonId";
    private static final String COLUMN_PAYMENT_ChequeNo = "payment_ChequeNo";

    /*  Creaete Table for payment*/

    private String CREATE_Payment_TABLE = "CREATE TABLE " + TABLE_Payment + "("
            + COLUMN_PAYMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_PAYMENT_Customer + " TEXT,"
            + COLUMN_PAYMENT_method + " TEXT,"
            + COLUMN_PAYMENT_amount + " INTEGER,"
            + COLUMN_PAYMENT_Detail + " TEXT,"
            + COLUMN_PAYMENT_others1 + " TEXT,"
            + COLUMN_PAYMENT_others2 + " TEXT,"
            + COLUMN_PAYMENT_Region + " TEXT,"
            + COLUMN_PAYMENT_ChequeNo + " TEXT,"
            + COLUMN_PAYMENT_CustomerId + " INTEGER,"
            + COLUMN_PAYMENT_SalesPersonId + " INTEGER,"
            + COLUMN_PAYMENT_date + " TEXT" + ")";

    public void add_Payment(PaymentPostmodel user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PAYMENT_Customer, user.get_CustomerName());
        values.put(COLUMN_PAYMENT_method, user.get_PaymentMethod());
        values.put(COLUMN_PAYMENT_amount, user.get_PaymentAmount());
        values.put(COLUMN_PAYMENT_Detail, user.get_Detail());
        values.put(COLUMN_PAYMENT_others1, "");
        values.put(COLUMN_PAYMENT_others2, "");
        values.put(COLUMN_PAYMENT_Region, user.get_Region());
        values.put(COLUMN_PAYMENT_ChequeNo, user.get_ChequeNo());
        values.put(COLUMN_PAYMENT_CustomerId, user.get_CustomerId());
        values.put(COLUMN_PAYMENT_SalesPersonId, user.get_SalesPersonId());
        values.put(COLUMN_PAYMENT_date, user.get_PaymentDate());
        db.insert(TABLE_Payment, null, values);
        db.close();
    }

    public List<PaymentPostmodel> getAllPayments() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_PAYMENT_ID,
                COLUMN_PAYMENT_Customer,
                COLUMN_PAYMENT_method,
                COLUMN_PAYMENT_amount,
                COLUMN_PAYMENT_Detail,
                COLUMN_PAYMENT_others1,
                COLUMN_PAYMENT_others2,
                COLUMN_PAYMENT_Region,
                COLUMN_PAYMENT_ChequeNo,
                COLUMN_PAYMENT_CustomerId,
                COLUMN_PAYMENT_SalesPersonId,
                COLUMN_PAYMENT_date
        };

        String sortOrder =
                COLUMN_PAYMENT_ID + " ASC";
        List<PaymentPostmodel> userList = new ArrayList<PaymentPostmodel>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_Payment, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order

        if (cursor.moveToFirst()) {
            do {
                PaymentPostmodel user = new PaymentPostmodel();

                user.set_Id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_PAYMENT_ID))));
                user.set_CustomerName(cursor.getString(cursor.getColumnIndex(COLUMN_PAYMENT_Customer)));
                user.set_PaymentMethod(cursor.getString(cursor.getColumnIndex(COLUMN_PAYMENT_method)));
                user.set_PaymentAmount(cursor.getInt(cursor.getColumnIndex(COLUMN_PAYMENT_amount)));
                user.set_Detail(cursor.getString(cursor.getColumnIndex(COLUMN_PAYMENT_Detail)));
                //  user.set_ITEM_ID(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_ID))));
                user.set_Region(cursor.getString(cursor.getColumnIndex(COLUMN_PAYMENT_Region)));
                user.set_ChequeNo(cursor.getString(cursor.getColumnIndex(COLUMN_PAYMENT_ChequeNo)));
                user.set_CustomerId(cursor.getInt(cursor.getColumnIndex(COLUMN_PAYMENT_CustomerId)));
                user.set_SalesPersonId(cursor.getInt(cursor.getColumnIndex(COLUMN_PAYMENT_SalesPersonId)));
                user.set_PaymentDate(cursor.getString(cursor.getColumnIndex(COLUMN_PAYMENT_date)));


                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;
    }

    public void deletePayment(PaymentPostmodel user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_Payment, COLUMN_PAYMENT_ID + " = ?",
                new String[]{String.valueOf(user.get_Id())});
        db.close();
    }
    /* End Creaete Table for payment*/

    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_CUSTOMER_ID = "customer_id";
    private static final String COLUMN_USER_TYPE = "user_type";
    private static final String COLUMN_USER_NAME = "user_name";


    private static final String COLUMN_USER_ADDRESS = "user_address";
    private static final String COLUMN_USER_REGION = "user_region";
    private static final String COLUMN_USER_LIMIT = "user_limit";
    private static final String COLUMN_USER_SALES_PERSON_ID = "user_salp_id";


    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_TYPE + " TEXT," + COLUMN_USER_REGION + " TEXT,"
            + COLUMN_USER_CUSTOMER_ID + " INTEGER,"
            + COLUMN_USER_LIMIT + " TEXT,"
            + COLUMN_USER_SALES_PERSON_ID + " TEXT," + COLUMN_USER_ADDRESS + " TEXT" + ")";

    // drop table sql query


    String SalesPersonName, strLat, strLog, Region;

    private static final String COLUMN_order_ID = "order_id";
    private static final String COLUMN_order_Name = "order_Name";

    private static final String COLUMN_SalesPersonName = "SalesPersonName";
    private static final String COLUMN_strLat = "strLat";
    private static final String COLUMN_strLog = "strLog";
    private static final String COLUMN_Region = "Region";


    private static final String COLUMN_order_location = "order_location";
    private static final String COLUMN_order_PRICE = "order_PRICE";
    private static final String COLUMN_order_QtyOrder = "order_QtyOrder";
    private static final String COLUMN_order_Status = "order_Status";
    private static final String COLUMN_order_date = "order_date";
    private static final String COLUMN_order_customer = "order_customer";

    private static final String COLUMN_order_customer_crd = "order_customer_crd";
    private static final String COLUMN_itemTotleAmount = "order_TotleAmount";

    private static final String COLUMN_SalesPersonId = "order_SalesPersonId";


    private static final String COLUMN_itemQty = "order_itemQty";
    private static final String COLUMN_itemPrice = "order_itemPrice";


    // COLUMN_itemQty, COLUMN_itemPrice
    private String CREATE_order_TABLE = "CREATE TABLE " + TABLE_order + "("
            + COLUMN_order_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_ID + " INTEGER,"
            + COLUMN_order_PRICE + " TEXT,"
            + COLUMN_order_QtyOrder + " TEXT,"
            + COLUMN_order_Name + " TEXT,"

            + COLUMN_SalesPersonName + " TEXT,"
            + COLUMN_strLat + " TEXT,"
            + COLUMN_strLog + " TEXT,"
            + COLUMN_Region + " TEXT,"


            + COLUMN_itemQty + " TEXT,"
            + COLUMN_itemPrice + " TEXT,"
            + COLUMN_order_location + " TEXT,"
            + COLUMN_order_Status + " TEXT,"
            + COLUMN_order_date + " TEXT,"
            + COLUMN_order_customer_crd + " TEXT,"
            + COLUMN_itemTotleAmount + " TEXT,"
            + COLUMN_SalesPersonId + " TEXT,"


            + COLUMN_order_customer + " TEXT" + ")";

    // drop table sql query
    private static final String COLUMN_order_Deatails_ID = "order_detail_id";
    private static final String COLUMN_order_Deatails_itemName = "order_detail_Name";
    private static final String COLUMN_order_Deatails_itemPRICE = "order_detail_PRICE";
    private static final String COLUMN_order_Deatails_itemQty = "order_detail_QtyOrder";

    private String CREATE_orderdeatals_TABLE = "CREATE TABLE " + TABLE_order_Deatails + "("
            + COLUMN_order_Deatails_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_Product_ID + " INTEGER,"
            + COLUMN_USER_ID + " INTEGER,"
            + COLUMN_order_ID + " INTEGER,"
            + COLUMN_order_Deatails_itemName + " TEXT,"
            + COLUMN_order_Deatails_itemPRICE + " TEXT,"
            + COLUMN_order_Deatails_itemQty + " TEXT" + ")";


    public void add_OrderDetails(Order_Details ordr_details) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //get_PRODUCT_ID
        values.put(COLUMN_Product_ID, ordr_details.get_PRODUCT_ID());
        values.put(COLUMN_USER_ID, ordr_details.get_USER_ID());
        values.put(COLUMN_order_ID, ordr_details.get_Order_ID());
        values.put(COLUMN_order_Deatails_itemName, ordr_details.get_itemName());
        values.put(COLUMN_order_Deatails_itemPRICE, ordr_details.get_itemPRICE());
        values.put(COLUMN_order_Deatails_itemQty, ordr_details.get_itemQty());
        db.insert(TABLE_order_Deatails, null, values);
        db.close();

    }

    private String CREATE_Product_TABLE = "CREATE TABLE " + TABLE_Product + "("
            + COLUMN_Product_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_ITEM_CODE + " INTEGER,"
            + COLUMN_ITEM_ID + " INTEGER,"
            + COLUMN_Product_Name + " TEXT,"
            + COLUMN_PART_NO + " TEXT,"
            + COLUMN_IUOM + " TEXT,"
            + COLUMN_WEIGHT_IN_KG + " TEXT,"
            + COLUMN_PRICERATE + " TEXT,"
            + COLUMN_PRICEREGION + " TEXT" + ")";


    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    private String DROP_order_TABLE = "DROP TABLE IF EXISTS " + TABLE_order;
    private String DROP_order_Deatails = "DROP TABLE IF EXISTS " + TABLE_order_Deatails;
    private String DROP_Prduct_TABLE = "DROP TABLE IF EXISTS " + TABLE_Product;

    private String DROP_Payment_TABLE = "DROP TABLE IF EXISTS " + TABLE_Payment;

    /**
     * Constructor
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_order_TABLE);
        db.execSQL(CREATE_orderdeatals_TABLE);
        db.execSQL(CREATE_Product_TABLE);
        db.execSQL(CREATE_Payment_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop ModelCustomer Table if exist
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_order_TABLE);
        db.execSQL(DROP_order_Deatails);
        db.execSQL(DROP_Prduct_TABLE);
        db.execSQL(DROP_Payment_TABLE);

        // Create tables again
        onCreate(db);

    }

    private static final String COLUMN_Product_ID = "Product_id";

    private static final String COLUMN_ITEM_CODE = "ITEM_CODE";
    private static final String COLUMN_PART_NO = "PART_NO";
    private static final String COLUMN_IUOM = "UOM";
    private static final String COLUMN_WEIGHT_IN_KG = "WEIGHT_IN_KG";
    private static final String COLUMN_ITEM_ID = "ITEM_ID";

    private static final String COLUMN_Product_Name = "Product_Name";
    private static final String COLUMN_PRICERATE = "Product_PRICE";
    private static final String COLUMN_PRICEREGION = "Product_PRICEREGION";


    public void add_Products(Product_model user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_Product_Name, user.get_ITEM_DESC());
        values.put(COLUMN_ITEM_CODE, user.get_ITEM_CODE());
        values.put(COLUMN_PART_NO, user.get_PART_NO());
        values.put(COLUMN_IUOM, user.get_UOM());
        values.put(COLUMN_WEIGHT_IN_KG, user.get_WEIGHT_IN_KG());
        values.put(COLUMN_ITEM_ID, user.get_ITEM_ID());
        values.put(COLUMN_PRICERATE, user.get_PRICERATE());
        values.put(COLUMN_PRICEREGION, user.get_PRICEREGION());
        db.insert(TABLE_Product, null, values);
        db.close();

        Log.i("list_Products", " end nsert to database");
    }

    public List<Product_model> getAllProducts() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_Product_ID,
                COLUMN_ITEM_CODE,
                COLUMN_PART_NO,
                COLUMN_IUOM,
                COLUMN_WEIGHT_IN_KG,
                COLUMN_ITEM_ID,
                COLUMN_Product_Name,
                COLUMN_PRICERATE,
                COLUMN_PRICEREGION
        };

        String sortOrder =
                COLUMN_Product_ID + " ASC";
        List<Product_model> userList = new ArrayList<Product_model>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_Product, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order

        if (cursor.moveToFirst()) {
            do {
                Product_model user = new Product_model();

                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_Product_ID))));

                user.set_ITEM_CODE(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_CODE)));
                user.set_PART_NO(cursor.getString(cursor.getColumnIndex(COLUMN_PART_NO)));
                user.set_UOM(cursor.getString(cursor.getColumnIndex(COLUMN_IUOM)));
                user.set_WEIGHT_IN_KG(cursor.getString(cursor.getColumnIndex(COLUMN_WEIGHT_IN_KG)));
                user.set_ITEM_ID(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_ID))));
                user.set_ITEM_DESC(cursor.getString(cursor.getColumnIndex(COLUMN_Product_Name)));
                user.set_PRICEREGION(cursor.getString(cursor.getColumnIndex(COLUMN_PRICEREGION)));
                user.set_PRICERATE(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_PRICERATE))));
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;
    }

    public void addUser(ModelCustomer user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.get_CUSTOMER_NAME());
        values.put(COLUMN_USER_TYPE, user.get_CUSTOMER_TYPET());
        values.put(COLUMN_USER_REGION, user.get_REGION());
        values.put(COLUMN_USER_CUSTOMER_ID, user.get_CUSTOMER_ID());
        values.put(COLUMN_USER_LIMIT, user.get_CREDIT_LIMIT());
        values.put(COLUMN_USER_SALES_PERSON_ID, user.get_SALES_PERSON_ID());
        values.put(COLUMN_USER_ADDRESS, user.get_ADDRESS());
        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }


    //////////////////////////order////////////

    long latID = 00;

    public long addorder(Order user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(COLUMN_USER_ID, user.get_USER_ID());
        values.put(COLUMN_order_Name, user.get_Name());

        values.put(COLUMN_Region, user.get_Region());
        values.put(COLUMN_strLat, user.get_strLat());
        values.put(COLUMN_strLog, user.get_strLog());
        values.put(COLUMN_SalesPersonName, user.get_SalesPersonName());


        values.put(COLUMN_order_location, user.get_Location());
        values.put(COLUMN_order_PRICE, user.get_PRICE());
        values.put(COLUMN_order_QtyOrder, user.get_QtyOrder());
        values.put(COLUMN_order_Status, user.get_Status());

        values.put(COLUMN_order_customer, user.get_customer());
        values.put(COLUMN_order_customer_crd, user.get_CrdLimit());
        values.put(COLUMN_itemTotleAmount, user.get_TotleAmount());


        values.put(COLUMN_SalesPersonId, user.get_SalesPersonId());

        values.put(COLUMN_itemQty, user.get_itemQty());
        values.put(COLUMN_itemPrice, user.get_itemPrice());
        values.put(COLUMN_order_date, user.get_Date());
        latID = db.insert(TABLE_order, null, values);
        db.close();
        return latID;

    }


    public ArrayList<Order> getAllorderNoCustomer() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_order_ID,
                COLUMN_USER_ID,
                COLUMN_order_Name,

                COLUMN_Region,
                COLUMN_strLat,
                COLUMN_strLog,
                COLUMN_SalesPersonName,

                COLUMN_order_location,
                COLUMN_order_PRICE,
                COLUMN_order_QtyOrder,
                COLUMN_order_Status,
                COLUMN_order_customer,

                COLUMN_order_customer_crd,
                COLUMN_itemTotleAmount,

                COLUMN_SalesPersonId,

                COLUMN_itemQty,
                COLUMN_itemPrice,
                COLUMN_order_date
        };


        // sorting orders
        String sortOrder =
                COLUMN_order_ID + " DESC";
        ArrayList<Order> userList = new ArrayList<Order>();
        SQLiteDatabase db = this.getReadableDatabase();
        // query the user table
        //  String selection = COLUMN_USER_ID + " = ?";
        //   String[] selectionArgs = {userid};
        Cursor cursor = db.query(TABLE_order, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        if (cursor.moveToFirst()) {
            do {
                Order user = new Order();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_order_ID))));
                user.set_USER_ID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.set_Name(cursor.getString(cursor.getColumnIndex(COLUMN_order_Name)));


                user.set_Region(cursor.getString(cursor.getColumnIndex(COLUMN_Region)));
                user.set_strLat(cursor.getString(cursor.getColumnIndex(COLUMN_strLat)));
                user.set_strLog(cursor.getString(cursor.getColumnIndex(COLUMN_strLog)));
                user.set_SalesPersonName(cursor.getString(cursor.getColumnIndex(COLUMN_SalesPersonName)));


                user.set_Location(cursor.getString(cursor.getColumnIndex(COLUMN_order_location)));
                user.set_PRICE(cursor.getString(cursor.getColumnIndex(COLUMN_order_PRICE)));
                user.set_QtyOrder(cursor.getString(cursor.getColumnIndex(COLUMN_order_QtyOrder)));
                user.set_Status(cursor.getString(cursor.getColumnIndex(COLUMN_order_Status)));
                user.set_customer(cursor.getString(cursor.getColumnIndex(COLUMN_order_customer)));

                user.set_CrdLimit(cursor.getString(cursor.getColumnIndex(COLUMN_order_customer_crd)));
                user.set_TotleAmount(cursor.getString(cursor.getColumnIndex(COLUMN_itemTotleAmount)));

                user.set_SalesPersonId(cursor.getInt(cursor.getColumnIndex(COLUMN_SalesPersonId)));


                user.set_itemQty(cursor.getString(cursor.getColumnIndex(COLUMN_itemQty)));
                user.set_itemPrice(cursor.getString(cursor.getColumnIndex(COLUMN_itemPrice)));

                user.set_Date(cursor.getString(cursor.getColumnIndex(COLUMN_order_date)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return userList;
    }

    public List<Order_Details> getAllOrderDetails_2(int order_id) {

        List<Order_Details> userList = new ArrayList<Order_Details>();

        SQLiteDatabase db = this.getReadableDatabase();

//        String sss = "SELECT  * FROM tableProduct ord INNER JOIN tableorderdetails dtl ON ord.Product_id=dtl.Product_id" +
//                "   left join tableorder yu on yu.order_id = dtl.order_id" +
//                "   WHERE yu.order_id = 1";

        String sss = "SELECT  dtl.* FROM tableorderdetails dtl   INNER JOIN tableorder ord on ord.order_id = dtl.order_id " +
                "   WHERE dtl.order_id =" + order_id;


        Cursor cursor = db.rawQuery(sss, null);

        if (cursor.moveToFirst()) {
            do {
                Order_Details user = new Order_Details();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_order_Deatails_ID))));

                user.set_PRODUCT_ID(cursor.getDouble(cursor.getColumnIndex(COLUMN_Product_ID)));
                user.set_itemName(cursor.getString(cursor.getColumnIndex(COLUMN_order_Deatails_itemName)));
                user.set_itemPRICE(cursor.getString(cursor.getColumnIndex(COLUMN_order_Deatails_itemPRICE)));
                user.set_itemQty(cursor.getString(cursor.getColumnIndex(COLUMN_order_Deatails_itemQty)));
                userList.add(user);
            } while (cursor.moveToNext());
        }
        Log.i("Waheed", "Exit");
        cursor.close();
        db.close();
        return userList;

    }


    public List<Product_model> getAllOrderDetails(int order_id) {

        List<Product_model> userList = new ArrayList<Product_model>();

        SQLiteDatabase db = this.getReadableDatabase();

        String sss = "SELECT  dtl.* FROM tableorderdetails dtl   INNER JOIN tableorder ord on ord.order_id = dtl.order_id " +
                "   WHERE dtl.order_id =" + order_id;

        // String sss = "SELECT * FROM tableorderdetails dtl   ";


// String SELECT_QUERY = SELECT * FROM Table1 t1 INNER JOIN Table2 t2 ON t1.id = t2.id GROUP BY t1.data1;

        Cursor cursor = db.rawQuery(sss, null);

        Log.i("Waheed", "Enter");
        if (cursor.moveToFirst()) {
            do {
                Product_model user = new Product_model();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_Product_ID))));
                user.set_ITEM_DESC(cursor.getString(cursor.getColumnIndex(COLUMN_Product_Name)));
                user.set_PRICERATE(cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICERATE)));

                userList.add(user);
            } while (cursor.moveToNext());
        }
        Log.i("Waheed", "Exit");
        cursor.close();
        db.close();
        return userList;

    }

    public ArrayList<Order> getAllorder(String userid) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_order_ID,
                COLUMN_USER_ID,
                COLUMN_order_Name,

                COLUMN_Region,
                COLUMN_strLat,
                COLUMN_strLog,
                COLUMN_SalesPersonName,


                COLUMN_order_location,
                COLUMN_order_PRICE,
                COLUMN_order_QtyOrder,
                COLUMN_order_Status,
                COLUMN_order_customer,

                COLUMN_order_customer_crd,
                COLUMN_itemTotleAmount,
                COLUMN_SalesPersonId,


                COLUMN_itemQty,
                COLUMN_itemPrice,
                COLUMN_order_date
        };


        // sorting orders
        String sortOrder =
                COLUMN_order_ID + " DESC";
        ArrayList<Order> userList = new ArrayList<Order>();
        SQLiteDatabase db = this.getReadableDatabase();
        // query the user table
        String selection = COLUMN_USER_ID + " = ?";
        String[] selectionArgs = {userid};
        Cursor cursor = db.query(TABLE_order, //Table to query
                columns,    //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        if (cursor.moveToFirst()) {
            do {
                Order user = new Order();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_order_ID))));
                user.set_USER_ID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.set_Name(cursor.getString(cursor.getColumnIndex(COLUMN_order_Name)));

                user.set_Region(cursor.getString(cursor.getColumnIndex(COLUMN_Region)));
                user.set_strLat(cursor.getString(cursor.getColumnIndex(COLUMN_strLat)));
                user.set_strLog(cursor.getString(cursor.getColumnIndex(COLUMN_strLog)));
                user.set_SalesPersonName(cursor.getString(cursor.getColumnIndex(COLUMN_SalesPersonName)));


                user.set_Location(cursor.getString(cursor.getColumnIndex(COLUMN_order_location)));
                user.set_PRICE(cursor.getString(cursor.getColumnIndex(COLUMN_order_PRICE)));
                user.set_QtyOrder(cursor.getString(cursor.getColumnIndex(COLUMN_order_QtyOrder)));
                user.set_Status(cursor.getString(cursor.getColumnIndex(COLUMN_order_Status)));
                user.set_customer(cursor.getString(cursor.getColumnIndex(COLUMN_order_customer)));
                user.set_CrdLimit(cursor.getString(cursor.getColumnIndex(COLUMN_order_customer_crd)));
                user.set_TotleAmount(cursor.getString(cursor.getColumnIndex(COLUMN_itemTotleAmount)));

                user.set_SalesPersonId(cursor.getInt(cursor.getColumnIndex(COLUMN_SalesPersonId)));


                user.set_itemQty(cursor.getString(cursor.getColumnIndex(COLUMN_itemQty)));
                user.set_itemPrice(cursor.getString(cursor.getColumnIndex(COLUMN_itemPrice)));

                user.set_Date(cursor.getString(cursor.getColumnIndex(COLUMN_order_date)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return userList;
    }


    public List<ModelCustomer> getAllUser() {
        // array of columns to fetch

        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_CUSTOMER_ID,
                COLUMN_USER_TYPE,
                COLUMN_USER_NAME,
                COLUMN_USER_ADDRESS,
                COLUMN_USER_REGION,
                COLUMN_USER_LIMIT,
                COLUMN_USER_SALES_PERSON_ID
        };
        // sorting orders
        String sortOrder =
                COLUMN_USER_ID + " ASC";
        List<ModelCustomer> userList = new ArrayList<ModelCustomer>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order

        if (cursor.moveToFirst()) {
            do {

                ModelCustomer user = new ModelCustomer();

                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.set_CUSTOMER_NAME(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.set_CUSTOMER_ID(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_CUSTOMER_ID)));
                user.set_CUSTOMER_TYPE(cursor.getString(cursor.getColumnIndex(COLUMN_USER_TYPE)));
                user.set_ADDRESS(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ADDRESS)));
                user.set_CREDIT_LIMIT(cursor.getDouble(cursor.getColumnIndex(COLUMN_USER_LIMIT)));
                user.set_SALES_PERSON_ID(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_SALES_PERSON_ID)));
                userList.add(user);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

    public void deleteUser(ModelCustomer user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public void deleteProduct(Product_model user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_Product, COLUMN_Product_ID + " = ?",
                new String[]{String.valueOf(user.get_Id())});
        db.close();
    }

    public void deleteOrder(Order user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_order, COLUMN_order_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public void deleteOrderDetail(Order_Details user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_order_Deatails, COLUMN_order_Deatails_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }
}