package com.example.uitest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class actionbar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actionbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.Contact)
        {
            String recepientEmail = ""; // either set to destination email or leave empty
            Intent email = new Intent(Intent.ACTION_SENDTO);
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{"some@email.address"});
            email.putExtra(Intent.EXTRA_SUBJECT, "subject");
            email.putExtra(Intent.EXTRA_TEXT, "mailbody");
            email.setType("message/rfc822");
            email.setData(Uri.parse("mailto:"+recepientEmail));
            startActivity(Intent.createChooser(email, "Choose an Email client :"));


        }
            return super.onOptionsItemSelected(item);


    }
}

