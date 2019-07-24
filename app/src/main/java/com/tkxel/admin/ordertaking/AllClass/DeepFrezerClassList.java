package com.tkxel.admin.ordertaking.AllClass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tkxel.admin.ordertaking.R;

/**
 * Created by admin on 2/27/2018.
 */

public class DeepFrezerClassList extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deepfrezerclass);
        fun_loadIds();
    }

    void fun_loadIds() {
        findViewById(R.id.add_new_user).setOnClickListener(this);
        findViewById(R.id.imgnew_order).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_new_user:
                //  back arrow
                finish();
                break;
            case R.id.imgnew_order:
                // add new Deepfrezeer item
                startActivity(new Intent(getApplicationContext(), Add_DeepFrezer.class));

                break;
        }
    }
}
