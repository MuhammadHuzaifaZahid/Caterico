package com.abdurrehman.caterico;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminPage extends AppCompatActivity {

    Button orders;
    Button add_hr;
    Button profile;
    Button sign_out;
    Button inventory;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        orders = findViewById(R.id.orders);
        add_hr = findViewById(R.id.add_hr);
        profile = findViewById(R.id.profile);
        sign_out = findViewById(R.id.signout);
        inventory = findViewById(R.id.inventory);
        builder = new AlertDialog.Builder(this);

        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setMessage(R.string.alert_content) .setTitle(R.string.alert_title)
                 .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                                Intent intent = new Intent(AdminPage.this,MainActivity.class);
                                startActivity(intent);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPage.this,UserProfile.class);
                intent.putExtra("Type",getIntent().getExtras().getString("Type"));
                intent.putExtra("Username",getIntent().getExtras().getString("Username"));
                startActivity(intent);
            }
        });

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPage.this,Orders.class);
                intent.putExtra("Type",getIntent().getExtras().getString("Type"));
                intent.putExtra("Username", getIntent().getExtras().getString("Username"));
                startActivity(intent);
            }
        });

        add_hr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPage.this,AddHr.class);
                intent.putExtra("Type",getIntent().getExtras().getString("Type"));
                intent.putExtra("Username",getIntent().getExtras().getString("Username"));
                startActivity(intent);
            }
        });

        inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPage.this,Inventory.class);
                intent.putExtra("Type",getIntent().getExtras().getString("Type"));
                intent.putExtra("Username", getIntent().getExtras().getString("Username"));
                startActivity(intent);
            }
        });

    }
}