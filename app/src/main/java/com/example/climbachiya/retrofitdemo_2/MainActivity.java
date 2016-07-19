package com.example.climbachiya.retrofitdemo_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import adapters.MyRecyclerAdapter;
import interfaces.ResponseApi;
import modals.Contact;
import modals.ResponseData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ProgressBar mProgressBar;
    public static final String ROOT_URL = "YOUR BASE URL HERE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUIControls();
        initClassObjects();
    }

    private void initUIControls() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    private void initClassObjects() {

    }

    public void onRedirect(View view){
        startActivity(new Intent(this, RegistrationActivity.class));
    }
    public void loadData(View view) {

        mProgressBar.setVisibility(View.VISIBLE);

        Gson gson = new GsonBuilder()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ResponseApi responseApi = retrofit.create(ResponseApi.class);
        Call<ResponseData> call = responseApi.getResponse();
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                Toast.makeText(MainActivity.this, "onResponse :: "+response.code(), Toast.LENGTH_SHORT).show();

                ResponseData responseData = response.body();

                List<Contact> list = responseData.getContacts();
                if (null != list && list.size() > 0) {

                    MyRecyclerAdapter myRecyclerAdapter = new MyRecyclerAdapter(MainActivity.this, list);
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setAdapter(myRecyclerAdapter);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());

                } else {
                    Toast.makeText(MainActivity.this, "No records found!", Toast.LENGTH_SHORT).show();
                }
                mProgressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(MainActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });

    }
}