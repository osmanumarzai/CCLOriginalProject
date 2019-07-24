package com.tkxel.admin.ordertaking.Loyalty;

/**
 * Created by admin on 10/21/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import com.google.zxing.Result;
import com.tkxel.admin.ordertaking.R;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends Activity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView scannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_scanner);
        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);

        scannerView = new ZXingScannerView(this);
        contentFrame.addView(scannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        //Call back data to main activity
        Intent intent = new Intent();
        intent.putExtra(SceenActivity_Class.FORMAT, rawResult.getBarcodeFormat().toString());
        intent.putExtra(SceenActivity_Class.CONTENT, rawResult.getText());

//        Toast.makeText(getApplicationContext(), "" + rawResult, Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplicationContext(), "" + rawResult.getText(), Toast.LENGTH_LONG).show();
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}