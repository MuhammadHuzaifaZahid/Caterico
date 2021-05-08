package com.abdurrehman.caterico;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewHr extends AppCompatActivity{
    Button sign_out;
    Button Back,Save;
    AlertDialog.Builder builder;
    TextView dis_expertise;
    EditText Name,Id,Phone,Address;

    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hr);

        Back = findViewById(R.id.back);
        Name = findViewById(R.id.hr_name);
        Id = findViewById(R.id.hr_id);
        Phone = findViewById(R.id.hr_contact);
        Address = findViewById(R.id.hr_address);
        Name = findViewById(R.id.hr_name);
        Save = findViewById(R.id.save);

        Save.setVisibility(View.INVISIBLE);
        Name.setEnabled(false);
        Id.setEnabled(false);
        Phone.setEnabled(false);
        Address.setEnabled(false);

        sign_out = findViewById(R.id.signout);
        dis_expertise = findViewById(R.id.expertise);
        builder = new AlertDialog.Builder(this);
        Spinner spin1 = findViewById(R.id.spinner1);
        spin1.setVisibility(View.INVISIBLE);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Admin_Page = new Intent(ViewHr.this,AdminPage.class);
                Admin_Page.putExtra("Type",getIntent().getExtras().getString("Type"));
                Admin_Page.putExtra("Username", getIntent().getExtras().getString("Username"));
                startActivity(Admin_Page);
            }
        });

        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setMessage(R.string.alert_content) .setTitle(R.string.alert_title)
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                                Intent intent = new Intent(ViewHr.this,MainActivity.class);
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

        dbRef = FirebaseDatabase.getInstance().getReference("HumanResources").child(getIntent().getExtras().getString("Id"));

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Name.setText(dataSnapshot.child("Name").getValue(String.class));
                Id.setText(dataSnapshot.child("Id").getValue(String.class));
                Phone.setText(dataSnapshot.child("Phone").getValue(String.class));
                Address.setText(dataSnapshot.child("Address").getValue(String.class));
                dis_expertise.setText(dataSnapshot.child("Expertise").getValue(String.class));

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(ViewHr.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
}