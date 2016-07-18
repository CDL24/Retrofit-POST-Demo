package com.example.climbachiya.retrofitdemo_2;

import android.app.Activity;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import interfaces.ResponseApi;
import modals.Person;
import modals.RegistrationResponse;
import modals.ResponseData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by C.limbachiya on 7/14/2016.
 */
public class RegistrationActivity extends AppCompatActivity {

    EditText editTextName,editTextEmail;
    TextView txtResponse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_view);

        initUIControls();
    }

    private void initUIControls() {
        // get reference to the views
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        txtResponse = (TextView) findViewById(R.id.text_view_result);

        // check if you are connected or not
        if(isConnected()){
            Toast.makeText(RegistrationActivity.this, "You are connected!", Toast.LENGTH_SHORT).show();
        }

    }

    public void onPostData(View view){

        if(!validate()){
            Toast.makeText(RegistrationActivity.this, "Some data missing!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(RegistrationActivity.this, "Sending...", Toast.LENGTH_SHORT).show();


            String name = editTextName.getText().toString();
            String email = editTextEmail.getText().toString();

            Gson gson = new GsonBuilder()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://182.75.142.125:901")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();


            ResponseApi responseApi = retrofit.create(ResponseApi.class);
            Call<RegistrationResponse> call = responseApi.sendData(name, email);
            call.enqueue(new Callback<RegistrationResponse>() {
                @Override
                public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                    Toast.makeText(RegistrationActivity.this, "Success :: "+response.code(),Toast.LENGTH_LONG).show();

                    if(response.code() == 200){
                        RegistrationResponse rr = response.body();
                        Log.v("Status", rr.getStatus());
                        txtResponse.setVisibility(View.VISIBLE);
                        txtResponse.setText(" :: You are registered As :: \n"+
                                                "Name : "+rr.getData().name+" \n "+
                                                "Email : "+rr.getData().email
                                            );

                    }
                }
                @Override
                public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                    Toast.makeText(RegistrationActivity.this, t.getMessage(),Toast.LENGTH_LONG).show();
                    txtResponse.setVisibility(View.VISIBLE);
                    txtResponse.setText(" :: Error :: \n"+
                                        t.getMessage()
                                        );
                }
            });

        }
    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
    private boolean validate(){
        if(editTextName.getText().toString().trim().equals(""))
            return false;
        else if(editTextEmail.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }
}