package com.tkxel.admin.ordertaking.AllClass;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tkxel.admin.ordertaking.AdopterClass.AdapterAllCustomer;
import com.tkxel.admin.ordertaking.AdopterClass.AdapterAllMerchandiser;
import com.tkxel.admin.ordertaking.ModelClass.Merchandiser;
import com.tkxel.admin.ordertaking.R;
import com.tkxel.admin.ordertaking.Utilities.Api;
import com.tkxel.admin.ordertaking.Utilities.ProgressBarHandler;
import com.tkxel.admin.ordertaking.Utilities.RetrofitClient;
import com.tkxel.admin.ordertaking.Utilities.SharedPreferencesSingletonStringKey;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MerchandiserListActivity extends Activity implements View.OnClickListener {

    private RecyclerView merchandiser_recycler_view;
    private AdapterAllMerchandiser adapterAllMerchandiser;
    ArrayList<Merchandiser> mListMerchandiser = new ArrayList<>();

    ProgressBarHandler progressBarHandler;
    ProgressDialog mProgressDialog;

    int UserID  = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchandiser_list);

        setListener();

        merchandiser_recycler_view  =  (RecyclerView) findViewById(R.id.merchandiser_recycler_view);


        UserID = SharedPreferencesSingletonStringKey.getInstance(MerchandiserListActivity.this).getInt(SharedPreferencesSingletonStringKey.Key.UserID);


        GetMerchandisersByUserID(UserID );
    }

    private void GetMerchandisersByUserID(int userid) {
        Api api = RetrofitClient.createService(Api.class);

        Call<Object> call = api.GetMerchandisersByUserID(userid);

        mProgressDialog = new ProgressDialog(MerchandiserListActivity.this);
        progressBarHandler = new ProgressBarHandler(mProgressDialog,"Merchandiser");
        progressBarHandler.show(mProgressDialog);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                if(response.isSuccessful()) {


                    mListMerchandiser= GetMerchandiserList(response);

                    adapterAllMerchandiser= new AdapterAllMerchandiser(mListMerchandiser);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MerchandiserListActivity.this);
                    merchandiser_recycler_view.setLayoutManager(layoutManager);
                    merchandiser_recycler_view.setAdapter(adapterAllMerchandiser);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
                }

                progressBarHandler.hide(mProgressDialog);

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                // close it after response
                progressBarHandler.hide(mProgressDialog);

            }
        });

    }

    private ArrayList<Merchandiser> GetMerchandiserList(Response<Object> response) {

        ArrayList<Merchandiser> mArrayList = new ArrayList<Merchandiser>();

        List<Merchandiser> mList = new ArrayList<>();
        Gson gson = new Gson();
        String json = gson.toJson(response.body());
        JSONArray jArray = null;
        try {
            jArray = new JSONArray(json);

            mList = Arrays.asList(gson.fromJson(jArray.toString(), Merchandiser[].class));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i=0 ; i<mList.size() ; i++)
        {
            mArrayList.add(mList.get(i));
        }

        return mArrayList;
    }


    private void setListener() {

        findViewById(R.id.imgnew_merchandiser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MerchandiserListActivity.this, MerchandiserAddActivity.class);
                intent.putExtra("isOnline", true);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
         //   case R.id.btn_sync:



        }

    }
}
