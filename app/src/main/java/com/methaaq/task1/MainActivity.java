package com.methaaq.task1;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity  {


    String token;
    private static final String BASE_URL = "http://icanstudioz.com/taxiapp/user/login/format/json/" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);



    }
    private Api getInterfaceService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final Api mInterfaceService = retrofit.create(Api.class);
        return mInterfaceService;
    }

    public void map(View v){
        Intent i = new Intent(MainActivity.this,MapsActivity.class);
        startActivity(i);
    }
    public void register (View v){
       token = FirebaseInstanceId.getInstance().getToken();
        Toast.makeText(MainActivity.this,token,Toast.LENGTH_LONG).show();
        registrationProcessWithRetrofit( "123456" ,"customer","0",token);

    }

    private void registrationProcessWithRetrofit(final String password , String  email ,String utype,String token){
        Api mApiService = this.getInterfaceService();
        Call<Login> mService = mApiService.registration(email,password,utype,token);
        mService.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Login mLoginObject = response.body();
                String returnedResponse = mLoginObject.getStatus();
                if(returnedResponse.trim().equals("success")){

                    Toast.makeText(MainActivity.this,"success",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(MainActivity.this,MapsActivity.class);
                    startActivity(i);
                }else {
                    Toast.makeText(MainActivity.this,"fail",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                call.cancel();
                Log.e("error",t.getMessage());
                Toast.makeText(MainActivity.this, "Please check your network connection and internet permission"+ t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

