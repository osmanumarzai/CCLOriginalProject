package com.tkxel.admin.ordertaking.AllClass;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.tkxel.admin.ordertaking.R;

/**
 * Created by admin on 1/2/2018.
 */

public class TestClass extends Activity {
    TextView grid_text;
    String str = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testingclass);
        StringBuilder sb = new StringBuilder();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            str = bundle.getString("Waheed");
        }
        grid_text = (TextView) findViewById(R.id.grid_text);


//        for (int i = 0; i < MyConstants.mArraydetails_offline2.size(); i++) {
//
//            for (int jj = 0; jj < MyConstants.mArraydetails_offline2.get(i).getDetails().size(); jj++) {
//                sb.append(MyConstants.mArraydetails_offline2.get(i).getDetails().get(jj).get_itemName()+"\n");
//            }
//            sb.append("\n------------------------\n");
//
//        }
//        String sel_cat = sb + "";
//
//        grid_text.setText(sel_cat + "");
    }
}
