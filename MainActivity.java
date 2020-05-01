package com.example.nokiafinal1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    DatabaseReference databaseNokias;

    ListView listViewNokias;
    List<Nokia> nokiaList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseNokias = FirebaseDatabase.getInstance().getReference("users").child("users");
        listViewNokias = (ListView) findViewById(R.id.listViewNokias);
        nokiaList = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseNokias.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nokiaList.clear();

                for (DataSnapshot nokiaSnapshot : dataSnapshot.getChildren()) {
                    Nokia nokia = nokiaSnapshot.getValue(Nokia.class);
                    nokiaList.add(nokia);
                }

                NokiaList adapter = new NokiaList(MainActivity.this, nokiaList);
                listViewNokias.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}



