package com.abdurrehman.caterico;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddHr extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Button sign_out;
    Button Back,Save;
    AlertDialog.Builder builder;
    TextView dis_expertise;
    EditText Name,Id,Phone,Address;

    String [] expertise = {"-Select-","Cooking","Decor","Logistics","Management"};

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

        sign_out = findViewById(R.id.signout);
        dis_expertise = findViewById(R.id.expertise);
        builder = new AlertDialog.Builder(this);
        Spinner spin1 = findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, expertise);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(adapter);
        spin1.setOnItemSelectedListener(this);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Admin_Page = new Intent(AddHr.this,AdminPage.class);
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
                                Intent intent = new Intent(AddHr.this,MainActivity.class);
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

        dbRef = FirebaseDatabase.getInstance().getReference("HumanResources");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Id.setText("HR_"+(dataSnapshot.getChildrenCount()+1));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(AddHr.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Name.getText().toString().length()==0)
                {
                    Toast.makeText(AddHr.this,"Kindly enter name.",Toast.LENGTH_SHORT).show();
                }
                else if (Phone.getText().toString().length()==0)
                {
                    Toast.makeText(AddHr.this,"Kindly enter phone number.",Toast.LENGTH_SHORT).show();
                }
                else if (Phone.getText().toString().length()<10)
                {
                    Toast.makeText(AddHr.this,"Kindly enter valid phone number. Length can not be less than 10.",Toast.LENGTH_SHORT).show();
                }
                else if (Address.getText().toString().length()==0)
                {
                    Toast.makeText(AddHr.this,"Kindly enter address.",Toast.LENGTH_SHORT).show();
                }
                else if (dis_expertise.getText().toString().equals("-Select-"))
                {
                    Toast.makeText(AddHr.this,"Kindly select expertise.",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    DatabaseReference save_db = dbRef.child(Id.getText().toString());
                    save_db.child("Name").setValue(Name.getText().toString());
                    save_db.child("Id").setValue(Id.getText().toString());
                    save_db.child("Phone").setValue(Phone.getText().toString());
                    save_db.child("Address").setValue(Address.getText().toString());
                    save_db.child("Expertise").setValue(dis_expertise.getText().toString());

                    Toast.makeText(AddHr.this,"Human Resource added successfully.",Toast.LENGTH_SHORT).show();

                    //Intent View = new Intent(AddHr.this,AdminPage.class);
                    Intent View = new Intent(AddHr.this,ViewHr.class); // Comment 1
                    View.putExtra("Type",getIntent().getExtras().getString("Type"));
                    View.putExtra("Username", getIntent().getExtras().getString("Username"));
                    View.putExtra("Id",Id.getText().toString()); // Comment 2
                    startActivity(View);
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        dis_expertise.setText(expertise[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}