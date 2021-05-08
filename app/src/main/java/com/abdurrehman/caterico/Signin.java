package com.abdurrehman.caterico;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.TintTypedArray;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Signin extends AppCompatActivity {

    Button Back, Sign_in;
    EditText User_Name,Password;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        Back = findViewById(R.id.back);
        User_Name = findViewById(R.id.uname);
        Password = findViewById(R.id.password);
        Sign_in = findViewById(R.id.sign_in);
        loadingBar=new ProgressDialog(this);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Back = new Intent(Signin.this,MainActivity.class);
                startActivity(Back);
            }
        });

        Sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingBar.setMessage("Please Wait.....");
                loadingBar.setCanceledOnTouchOutside(true);
                loadingBar.setProgress(90);
                loadingBar.show();

                if (User_Name.getText().length()==0)
                {
                    Toast.makeText(Signin.this, "Kindly enter username", Toast.LENGTH_SHORT).show();
                }
                else if (Password.getText().length()==0)
                {
                    Toast.makeText(Signin.this, "Kindly enter password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Registered").child(User_Name.getText().toString());

                    dbRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (!dataSnapshot.hasChild("Name") || !dataSnapshot.child("Account_Type").getValue(String.class).contentEquals(getIntent().getExtras().getString("Type")))
                            {
                                Toast.makeText(Signin.this, "Username not registered or incorrect account type.", Toast.LENGTH_SHORT).show();
                            }
                            else if (!dataSnapshot.child("Password").getValue(String.class).contentEquals(Password.getText().toString()))
                            {
                                Toast.makeText(Signin.this, "Kindly enter correct password.", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                if (getIntent().getExtras().getString("Type").equals("Admin"))
                                {
                                    Intent Admin_Page = new Intent(Signin.this,AdminPage.class);
                                    Admin_Page.putExtra("Type",getIntent().getExtras().getString("Type"));
                                    Admin_Page.putExtra("Username", User_Name.getText().toString());
                                    startActivity(Admin_Page);
                                }
                                else {
                                    Intent Profile = new Intent(Signin.this, UserProfile.class);
                                    Profile.putExtra("Type",getIntent().getExtras().getString("Type"));
                                    Profile.putExtra("Username", User_Name.getText().toString());
                                    startActivity(Profile);
                                }
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            Toast.makeText(Signin.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}