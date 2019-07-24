package com.tkxel.admin.ordertaking.AllClass;

/**
 * Created by admin on 10/16/2017.
 */

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.frosquivel.magicaltakephoto.MagicalTakePhoto;
import com.tkxel.admin.ordertaking.AdopterClass.Adapter_All_Payment;
import com.tkxel.admin.ordertaking.DatabaseClass.DatabaseHelper;
import com.tkxel.admin.ordertaking.LoadingClass.Payment_PostTask;
import com.tkxel.admin.ordertaking.ModelClass.ModelCustomer;
import com.tkxel.admin.ordertaking.ModelClass.PaymentPostmodel;
import com.tkxel.admin.ordertaking.R;
import com.tkxel.admin.ordertaking.Utilities.CityListDialog;
import com.tkxel.admin.ordertaking.Utilities.MyConstants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.tkxel.admin.ordertaking.Utilities.MyConstants.listCustomer;


public class Add_Payment_Class extends Activity implements View.OnClickListener {

    DatabaseHelper databaseHelper;
    ModelCustomer user;
    String paymentmoth = "Cash";

    ImageView img;
    EditText et_bussiness;
    boolean isOnline = false;
    TextView tvoffline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_customer_activity);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isOnline = bundle.getBoolean("isOnline", true);
        }

        tvoffline = (TextView) findViewById(R.id.tvoffline);

        if (isOnline) {
            tvoffline.setVisibility(View.GONE);
        } else {
            tvoffline.setVisibility(View.VISIBLE);
        }
        initObjects();
        LoadIDs();
        Get_All_customr();
        findViewById(R.id.add_new_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    ArrayList<String> araList_warwhouse;
    String[] array;

    CityListDialog cityListDialog;
    TextView tv_customer;
    private TextView orer_date;
    String str_date = "27-dec-2017";

    private void LoadIDs() {

        orer_date = (TextView) findViewById(R.id.orer_date);
        orer_date.setOnClickListener(this);

        et_bussiness = (EditText) findViewById(R.id.et_bussiness);
        radio_chash = (RadioButton) findViewById(R.id.radio_chash);
        radio_chash.setOnClickListener(this);
        radio_cheq = (RadioButton) findViewById(R.id.radio_cheq);
        radio_cheq.setOnClickListener(this);
        radio_other = (RadioButton) findViewById(R.id.radio_other);
        radio_other.setOnClickListener(this);
        et_chaquno = (EditText) findViewById(R.id.et_chaquno);
        tv_chaquno = (TextView) findViewById(R.id.tv_chaquno);
        layoutno = (LinearLayout) findViewById(R.id.layoutno);
        img = (ImageView) findViewById(R.id.img);
        tv_customer = (TextView) findViewById(R.id.tv_customer);
        tv_customer.setOnClickListener(this);

        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.addphoto).setOnClickListener(this);
        // defaults Values

        radio_chash.setChecked(true);
        radio_chash.setText("Cheque");

        radio_cheq.setChecked(false);
        radio_chash.setText("Cash");

        radio_other.setChecked(false);
        radio_other.setText("Others");
        layoutno.setVisibility(View.GONE);
        fun_Date();
    }

    MagicalTakePhoto magicalTakePhoto;

    private void initObjects() {
        databaseHelper = new DatabaseHelper(Add_Payment_Class.this);
        magicalTakePhoto = new MagicalTakePhoto(this, 200);


        listUsers = new ArrayList<>();
        user = new ModelCustomer();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        magicalTakePhoto.resultPhoto(requestCode, resultCode, data);
        //example to get photo
        img.setImageBitmap(magicalTakePhoto.getMyPhoto());
    }

    EditText et_chaquno;
    TextView tv_chaquno;
    LinearLayout layoutno;
    RadioButton radio_chash, radio_cheq, radio_other;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.orer_date:
                showDialog(DATE_DIALOG_ID);

                break;
            case R.id.radio_chash:
                paymentmoth = "Cash";
                radio_chash.setChecked(true);
                radio_cheq.setChecked(false);
                radio_other.setChecked(false);
                layoutno.setVisibility(View.GONE);
                break;
            case R.id.radio_cheq:
                paymentmoth = "Cheque";
                radio_chash.setChecked(false);
                radio_cheq.setChecked(true);
                radio_other.setChecked(false);
                tv_chaquno.setText("Cheque Number");
                et_chaquno.setHint("Enter Cheque Number");
                layoutno.setVisibility(View.VISIBLE);
                break;
            case R.id.radio_other:
                paymentmoth = "Others";
                radio_chash.setChecked(false);
                radio_cheq.setChecked(false);
                radio_other.setChecked(true);
                tv_chaquno.setText("Other Details");
                et_chaquno.setHint("Enter Details");
                layoutno.setVisibility(View.VISIBLE);
                break;

            case R.id.tv_customer:
                cityListDialog.show();
                break;
            case R.id.addphoto:
                magicalTakePhoto.takePhoto("my_photo_name");
                break;

            case R.id.btn_login:
                postpayment();

                break;
        }
    }

    // RadioButton radio_chash, radio_cheq, radio_other;
    void postpayment() {
        PaymentPostmodel paymentPostmodel = new PaymentPostmodel();

        if (radio_chash.isChecked()) {

            paymentPostmodel.set_ChequeNo("---");
            paymentPostmodel.set_CustomerId((int) MyConstants.listCustomer.get(cityListDialog.curent_userID).get_CUSTOMER_ID());
            paymentPostmodel.set_CustomerName(tv_customer.getText() + "");
            paymentPostmodel.set_Detail(et_chaquno.getText() + "");
            paymentPostmodel.set_PaymentAmount(Double.parseDouble(et_bussiness.getText() + ""));
            paymentPostmodel.set_PaymentDate(str_date + "");
            paymentPostmodel.set_PaymentMethod(paymentmoth);
            paymentPostmodel.set_SalesPersonId(MyConstants.listLoginDetails.get(0).get_EmployeeId());


        } else if (radio_cheq.isChecked()) {
            paymentPostmodel.set_ChequeNo(et_chaquno.getText() + "");
            paymentPostmodel.set_CustomerId((int) MyConstants.listCustomer.get(cityListDialog.curent_userID).get_CUSTOMER_ID());
            paymentPostmodel.set_CustomerName(tv_customer.getText() + "");
            paymentPostmodel.set_Detail(et_chaquno.getText() + "");
            paymentPostmodel.set_PaymentAmount(Double.parseDouble(et_bussiness.getText() + ""));
            paymentPostmodel.set_PaymentDate(str_date + "");
            paymentPostmodel.set_PaymentMethod(paymentmoth);
            paymentPostmodel.set_SalesPersonId(MyConstants.listLoginDetails.get(0).get_EmployeeId());

        } else if (radio_other.isChecked()) {

            paymentPostmodel.set_ChequeNo("---");
            paymentPostmodel.set_CustomerId((int) MyConstants.listCustomer.get(cityListDialog.curent_userID).get_CUSTOMER_ID());
            paymentPostmodel.set_CustomerName(tv_customer.getText() + "");
            paymentPostmodel.set_Detail(et_chaquno.getText() + "");
            paymentPostmodel.set_PaymentAmount(Double.parseDouble(et_bussiness.getText() + ""));
            paymentPostmodel.set_PaymentDate(str_date + "");
            paymentPostmodel.set_PaymentMethod(paymentmoth);
            paymentPostmodel.set_SalesPersonId(MyConstants.listLoginDetails.get(0).get_EmployeeId());
        }


        if (isOnline) {
            new Payment_PostTask(Add_Payment_Class.this, paymentPostmodel).execute();
        } else {
            databaseHelper.add_Payment(paymentPostmodel);
            MyConstants.mListPayment.clear();
            MyConstants.mListPayment = databaseHelper.getAllPayments();
            All_PaymentList_Offline.main_lisrview.setAdapter(new Adapter_All_Payment(Add_Payment_Class.this, MyConstants.mListPayment, 1));
            finish();
        }


    }

    private List<ModelCustomer> listUsers;

    void setDialoguData() {
        araList_warwhouse = new ArrayList<String>();
        for (int i = 0; i < listCustomer.size(); i++) {
            araList_warwhouse.add(listCustomer.get(i).get_CUSTOMER_NAME());
        }
        array = araList_warwhouse.toArray(new String[araList_warwhouse.size()]);

        tv_customer.setText(array[0] + "");
        cityListDialog = new CityListDialog(Add_Payment_Class.this, array, tv_customer, 200);
    }

    private int year, month, day;
    String currentdat = "13-11-2017";

    void fun_Date() {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        currentdat = new StringBuilder()
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year).append(" ") + "";

        orer_date.setText("Date : " + new StringBuilder()
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year).append(" "));

        str_date = new StringBuilder()
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year).append(" ") + "";

    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            currentdat = new StringBuilder()
                    // Month is 0 based, just add 1
                    .append(month + 1).append("-").append(day).append("-")
                    .append(year).append("") + "";

            // set selected date into textview
            orer_date.setText("Date : " + new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(""));

            str_date = new StringBuilder()
                    // Month is 0 based, just add 1
                    .append(month + 1).append("-").append(day).append("-")
                    .append(year).append(" ") + "";


            // set selected date into datepicker also

        }
    };
    static final int DATE_DIALOG_ID = 999;

    private void Get_All_customr() {
        setDialoguData();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
                        year, month, day);
        }
        return null;
    }
}