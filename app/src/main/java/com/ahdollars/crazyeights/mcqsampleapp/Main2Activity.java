package com.ahdollars.crazyeights.mcqsampleapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        startActivity(new Intent(Main2Activity.this,MainActivity.class));
        finish();
    }
}
