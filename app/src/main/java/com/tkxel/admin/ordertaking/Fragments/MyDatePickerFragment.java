package com.tkxel.admin.ordertaking.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.Toast;

import com.tkxel.admin.ordertaking.AllClass.MerchandiserAddActivity;

import java.util.Calendar;

public class MyDatePickerFragment extends DialogFragment {
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
    }

    private DatePickerDialog.OnDateSetListener dateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int month, int day) {

                    String date =    view.getDayOfMonth() + " / " + (view.getMonth()+1) + " / " + view.getYear();

                    //setting data in public field
//                    ((MerchandiserAddActivity)getActivity()).inputDOB.setText(date);


                    Toast.makeText(getActivity(), "selected date is " + date  , Toast.LENGTH_SHORT).show();
                }
            };


//    private void sendData(String date)
//    {
//        //INTENT OBJ
//        Intent i = new Intent(getActivity().getBaseContext(),
//                CustomerAddActivity.class);
//
//        //PACK DATA
//        i.putExtra("SENDER_KEY", "MyDatePickerFragment");
//        i.putExtra("Date", date);
//
//    }
}
