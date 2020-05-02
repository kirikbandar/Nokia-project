package com.example.uitest;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import android.widget.AdapterView.OnItemSelectedListener;

import java.lang.reflect.Array;
import java.util.Collections;

public class spinactivity extends Activity {
    String[] carrier = {"All", "Jio", "Airtel"};
    String carriervalue;
    Button GOTO;
    Intent intent;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinactivity);
        spinner = (Spinner) findViewById(R.id.spinner1);
        GOTO = (Button)findViewById(R.id.button1);

        ArrayAdapter<String>adapter=new ArrayAdapter<String>(spinactivity.this, android.R.layout.simple_list_item_1, carrier);
       // spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                carriervalue = (String)spinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        GOTO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                switch(carriervalue){

                    case "All":
                        //addheatmapall();
                        intent = new Intent(spinactivity.this, mapactivity.class);
                        startActivity(intent);
                        break;

                    case "Jio":
                        //addheatmapjio();
                      intent = new Intent(spinactivity.this, mapactivity.class);
                        startActivity(intent);
                        break;

                    //case "Airtel":
                      //  intent = new Intent(spinactivity.this, airtelactivity.class);
                        //startActivity(intent);
                        //break;


                }
            }
        });

    }
}






