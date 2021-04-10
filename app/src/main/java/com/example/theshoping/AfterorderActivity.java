package com.example.theshoping;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AfterorderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afterorder);


        this.setTitle("Final Order");
        //set the back arrow button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


}
