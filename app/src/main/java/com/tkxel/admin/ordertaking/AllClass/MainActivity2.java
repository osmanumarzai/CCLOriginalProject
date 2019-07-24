package com.tkxel.admin.ordertaking.AllClass;

/**
 * Created by admin on 10/16/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.tkxel.admin.ordertaking.AdopterClass.OfflineCheckAdop;
import com.tkxel.admin.ordertaking.DatabaseClass.DatabaseHelper;
import com.tkxel.admin.ordertaking.ModelClass.ModelCustomer;
import com.tkxel.admin.ordertaking.ModelClass.Product_model;
import com.tkxel.admin.ordertaking.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends Activity {
    private OfflineCheckAdop adapter;
    List<Product_model> arrayList = new ArrayList<>();
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        databaseHelper = new DatabaseHelper(MainActivity2.this);

        List<ModelCustomer> getAllUser = databaseHelper.getAllUser();
        List<Product_model> getAllProducts = databaseHelper.getAllProducts();

        for (int i = 0; i < getAllProducts.size(); i++) {
            Product_model atomPayment = new Product_model();

            atomPayment.set_ITEM_DESC(getAllProducts.get(i).get_ITEM_DESC());
            atomPayment.set_PRICERATE(getAllProducts.get(i).get_PRICERATE());

            arrayList.add(atomPayment);
        }

        adapter = new OfflineCheckAdop(MainActivity2.this, arrayList);

        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginDetails_save(false);
            }
        });
    }

    SharedPreferences sharedPreferences_form1;

    void loginDetails_save(boolean b) {
        sharedPreferences_form1 = getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences_form1.edit();
        editor.putBoolean("is_save", b);
        editor.commit();
    }

    public void itemDeleteListner(View v) {
//        Product_model itemToRemove = (Bean)v.getTag();
//        adapter.remove(itemToRemove);
    }

}