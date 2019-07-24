package com.tkxel.admin.ordertaking.AllClass;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tkxel.admin.ordertaking.LoadingClass.PostTask_Deepferzer;
import com.tkxel.admin.ordertaking.ModelClass.ModelCustomer;
import com.tkxel.admin.ordertaking.R;
import com.tkxel.admin.ordertaking.Utilities.CityListDialog;
import com.tkxel.admin.ordertaking.Utilities.MyConstants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.tkxel.admin.ordertaking.Utilities.MyConstants.listCustomer;

/**
 * Created by admin on 2/27/2018.
 */

public class Add_DeepFrezer extends Activity implements View.OnClickListener {

    RadioButton radio_chash, radio_cheq, radio_yes, radio_no;
    Button btpic, btpic2;
    private Uri fileUri;

    String picturePath, picturePath2;
    Uri selectedImage;

    String base_1_64, base_2_64;

    public static String URL = "http://zapponomics.net/dev/trinityimage.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_deepferezer);
        fun_loadIds();
        fun_Date();
        setDialoguData();
    }

    int TAKE_PHOTO_CODE = 0;
    public static int count = 0;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    String dir;

    void fun_loadIds() {
        dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picFolder/";
        File newdir = new File(dir);
        if (!newdir.exists())
            newdir.mkdirs();


        radio_chash = (RadioButton) findViewById(R.id.radio_chash);
        radio_cheq = (RadioButton) findViewById(R.id.radio_cheq);
        radio_yes = (RadioButton) findViewById(R.id.radio_yes);
        radio_no = (RadioButton) findViewById(R.id.radio_no);

        radio_chash.setOnClickListener(this);
        radio_cheq.setOnClickListener(this);
        radio_yes.setOnClickListener(this);
        radio_no.setOnClickListener(this);


        findViewById(R.id.addphoto).setOnClickListener(this);
        findViewById(R.id.add_new_user).setOnClickListener(this);
        findViewById(R.id.imgnew_order).setOnClickListener(this);
        orer_date = (TextView) findViewById(R.id.orer_date);
        orer_date.setOnClickListener(this);
        tv_customer = (TextView) findViewById(R.id.tv_customer);
        tv_customer.setOnClickListener(this);


        btpic = (Button) findViewById(R.id.cpic);

        btpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 100);


            }
        });
        btpic2 = (Button) findViewById(R.id.cpic2);
        btpic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 200);
            }
        });

    }

    Bitmap photo_1 = null, photo_2 = null;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i("SArdarD", requestCode + "  ! " + resultCode);
        if (requestCode == 100 && resultCode == RESULT_OK) {

            photo_1 = (Bitmap) data.getExtras().get("data");
            ImageView imageView = (ImageView) findViewById(R.id.Imageprev);
            imageView.setImageBitmap(photo_1);


        } else if (requestCode == 200 && resultCode == RESULT_OK) {
            photo_2 = (Bitmap) data.getExtras().get("data");
            ImageView imageView = (ImageView) findViewById(R.id.Imageprev2);
            imageView.setImageBitmap(photo_2);

        }
    }

    private int year, month, day;
    String currentdat = "13-11-2017";
    private TextView orer_date;
    String str_date = "27-dec-2017";

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

    private List<ModelCustomer> listUsers;
    ArrayList<String> araList_warwhouse;
    String[] array;
    TextView tv_customer;
    CityListDialog cityListDialog;

    void setDialoguData() {
        araList_warwhouse = new ArrayList<String>();
        for (int i = 0; i < listCustomer.size(); i++) {
            araList_warwhouse.add(listCustomer.get(i).get_CUSTOMER_NAME());
        }
        array = araList_warwhouse.toArray(new String[araList_warwhouse.size()]);
        tv_customer.setText(array[0] + "");
        cityListDialog = new CityListDialog(Add_DeepFrezer.this, array, tv_customer, 200);
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


        }
    };


    final int DATE_DIALOG_ID = 999;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_new_user:
                finish();
                break;
            case R.id.orer_date:
                showDialog(DATE_DIALOG_ID);
                break;
            case R.id.tv_customer:
                cityListDialog.show();
                break;

            case R.id.radio_yes:
                isCorrectOrder = true;
                radio_yes.setChecked(true);
                radio_no.setChecked(false);
                break;
            case R.id.radio_no:
                isCorrectOrder = false;
                radio_yes.setChecked(false);
                radio_no.setChecked(true);
                break;
            case R.id.radio_cheq:
                isFIFo = false;
                radio_cheq.setChecked(true);
                radio_chash.setChecked(false);
                break;
            case R.id.radio_chash:
                isFIFo = true;
                radio_cheq.setChecked(false);
                radio_chash.setChecked(true);
                break;

            case R.id.imgnew_order:
                upload_1(photo_1);
                upload_2(photo_2);
                new PostTask_Deepferzer(Add_DeepFrezer.this, (int) listCustomer.get(MyConstants.customerpostionDepp).get_CUSTOMER_ID(),
                        tv_customer.getText() + "", isFIFo, isCorrectOrder).execute();

                break;

        }
    }

    boolean isFIFo = true, isCorrectOrder = true;


    private Bitmap bitmap;


    private void upload_1(Bitmap btm) {

        if (btm != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            btm.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            MyConstants.image1_64 = encoded;
        } else {
            Toast.makeText(getApplicationContext(), "Select Image 1", Toast.LENGTH_SHORT).show();
        }


    }

    private void upload_2(Bitmap btm) {
        if (btm != null) {

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            btm.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            MyConstants.image2_64 = encoded;
        } else {
            Toast.makeText(getApplicationContext(), "Select Image 2", Toast.LENGTH_SHORT).show();
        }


    }

    public static String getBase64Image(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }
}
