package com.example.doan4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class anhnen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anhnen);
        final Handler hd= new Handler();
        hd.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(anhnen.this, MainActivity.class);
                startActivity(intent);
                finish();
                hd.removeCallbacks(this);
            }
        },3000);
    }
}