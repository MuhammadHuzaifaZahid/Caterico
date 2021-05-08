package com.abdurrehman.caterico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;

public class Starting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

        // Enabling Firebase Persistence
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        Intent Main = new Intent(Starting.this,MainActivity.class);
        startActivity(Main);
        finish();
    }
}