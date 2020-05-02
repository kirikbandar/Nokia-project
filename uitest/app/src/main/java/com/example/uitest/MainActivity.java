package com.example.uitest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.graphics.Color;

import android.widget.RelativeLayout;


public class MainActivity extends AppCompatActivity
{
        private static int SPLASH_TIME_OUT = 4000;
        RelativeLayout relativeLayout;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            relativeLayout = findViewById(R.id.relativeLayout);
            relativeLayout.setBackgroundColor(Color.WHITE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, mapactivity.class);
                    startActivity(intent);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
}



