package com.abdurrehman.caterico;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {

    ImageView Photo;
    TextView Name,Email,Phone,Username,Account_Type;
    Button edit;
    Button signout;
    Button Back;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        edit =findViewById(R.id.edit);
        signout = findViewById(R.id.signout);
        builder = new AlertDialog.Builder(this);
        Photo = findViewById(R.id.pic);
        Name = findViewById(R.id.name);
        Email = findViewById(R.id.email);
        Phone = findViewById(R.id.phone);
        Username = findViewById(R.id.uname);
        Account_Type = findViewById(R.id.account_type);
        Back = findViewById(R.id.back);

        if (!(getIntent().getExtras().getString("Type").equals("Admin")))
        {
            Back.setVisibility(View.INVISIBLE);
        }

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Registered").child(getIntent().getExtras().getString("Username"));

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Name.setText(dataSnapshot.child("Name").getValue(String.class));

                Email.setText(dataSnapshot.child("Email").getValue(String.class));

                Phone.setText(dataSnapshot.child("Phone").getValue(String.class));

                Username.setText(dataSnapshot.child("Username").getValue(String.class));

                Account_Type.setText(dataSnapshot.child("Account_Type").getValue(String.class));

                String encodedImage =dataSnapshot.child("Picture").getValue(String.class);

                byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                Photo.setImageBitmap(decodedByte);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(UserProfile.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent Admin_Page = new Intent(UserProfile.this,AdminPage.class);
                    Admin_Page.putExtra("Type",getIntent().getExtras().getString("Type"));
                    Admin_Page.putExtra("Username", getIntent().getExtras().getString("Username"));
                    startActivity(Admin_Page);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Edit = new Intent(UserProfile.this,EditProfile.class);
                Edit.putExtra("Username",getIntent().getExtras().getString("Username"));
                Edit.putExtra("Type",getIntent().getExtras().getString("Type"));
                startActivity(Edit);
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setMessage(R.string.alert_content) .setTitle(R.string.alert_title)
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                                Intent intent = new Intent(UserProfile.this,MainActivity.class);
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
    }
}