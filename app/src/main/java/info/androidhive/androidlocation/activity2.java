package info.androidhive.androidlocation;

import android.view.View;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;


public class activity2 extends AppCompatActivity {
    int rssi = MainActivity.rssi;
    double lon = MainActivity.lon;
    double lat = MainActivity.lat;
    private static final String TAG = activity2.class.getSimpleName();
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseReference = mDatabase.getReference("clusterred_OP");
    public ArrayList<op> list = new ArrayList<>();
    public static double val1;
    public static double val2;
    Button button;


    TextView rssiResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity2);
        button = (Button) findViewById(R.id.get_heatmap);
        rssiResult = findViewById(R.id.text);
        Log.v(TAG, "RSSI: " + rssi + "Lat: " + lat + " Long: " + lon);
        if (rssi > -50) {
            rssiResult.setText("You are in green zone!!!");
            rssiResult.setTextColor(Color.rgb(102, 255, 0));

        } else if (rssi < -75) {
            rssiResult.setText("You are in red zone!!!");
            rssiResult.setTextColor(Color.rgb(255, 0, 0));
        } else {
            rssiResult.setText("You are in yellow zone!!!");
            rssiResult.setTextColor(Color.rgb(255, 255, 0));
        }

        retrieve(new UserListCallback() {
            @Override
            public void onCallback(ArrayList<op> val) {
                // rssiResult.setText("Loaded "+val.size()+" values");

                for (int i = 0; i < val.size(); i++) {
                    val.get(i).dist = distance(lat, lon, val.get(i).latitude, val.get(i).longitude);
                    //Log.v(TAG,"" + val.get(i).latitude + " , "+val.get(i).longitude + " , "+val.get(i).tag+ " , "+val.get(i).dist);
                }
                double min = val.get(0).dist;
                int minIndex = 0;
                for (int i = 1; i < val.size(); i++) {
                    if (val.get(i).dist < min) {
                        min = val.get(i).dist;
                        minIndex = i;
                    }
                }
                Log.v(TAG, "nearest green zone at Lat: " + val.get(minIndex).latitude + " Lon: " + val.get(minIndex).longitude + "Distance: " + val.get(minIndex).dist);
                rssiResult.setText("Nearest green zone at Lat: " + val.get(minIndex).latitude + " Lon: " + val.get(minIndex).longitude + "Distance: " + val.get(minIndex).dist);
                 val1 = val.get(minIndex).latitude;
                 val2 = val.get(minIndex).longitude;


            }
        });

//       Log.v(TAG, "SIZE: "+newlist.size());
//        for(int i=0; i < list.size(); i++){
//            rssiResult.setText(rssiResult.getText() + " " + newlist.get(i).latitude + " , "+newlist.get(i).longitude + " , "+newlist.get(i).tag);
//            Log.v(TAG,"hello " + newlist.get(i).latitude + " , "+newlist.get(i).longitude + " , "+newlist.get(i).tag);
//        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openmapactivity();
            }
        });


    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public interface MyUserListener {
        void onUpdate(op val);

        void onCancel();
    }

    public interface UserListCallback {
        void onCallback(ArrayList<op> value);
    }

    public void retrieve(final UserListCallback myCallback) {

        mDatabaseReference.orderByChild("tag").equalTo(2).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                //Log.v(TAG,"onChildAdded");
                op val = dataSnapshot.getValue(op.class);
                assert val != null;
                list.add(val);
                myCallback.onCallback(list);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
                Log.v(TAG, "onChildChanged");
                op val = dataSnapshot.getValue(op.class);
                list.add(val);

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v(TAG, "FAiled to read value");
            }
        });
    }

    public void openmapactivity() {
        Intent intent = new Intent(this, mapactivity.class);
        startActivity(intent);
    }


}