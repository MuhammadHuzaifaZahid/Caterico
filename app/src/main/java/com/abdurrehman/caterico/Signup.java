package com.abdurrehman.caterico;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Signup extends AppCompatActivity {

    Button Back,Register;
    ImageView Profile_Pic;
    String encodedPhoto = null;
    EditText Name,Email,Phone,User_Name,Password;
    RadioButton Admin,Customer;
    String Account_Type = null;
    boolean Image_Selection_Intent = false;

    List<String> User_Names_List = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Back = findViewById(R.id.back);

        Profile_Pic = findViewById(R.id.pic);

        Name =findViewById(R.id.name);

        Email = findViewById(R.id.email);

        Phone = findViewById(R.id.phone);

        User_Name = findViewById(R.id.uname);

        Password = findViewById(R.id.password);

        Admin = findViewById(R.id.admin_account);

        Customer = findViewById(R.id.customer_account);

        Register = findViewById(R.id.register);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Back = new Intent(Signup.this,MainActivity.class);
                startActivity(Back);
            }
        });

        Profile_Pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Image_Selection_Intent = true;
                Intent Select_Photo = new Intent(Intent.ACTION_PICK);
                Select_Photo.setType("image/*");
                startActivityForResult(Select_Photo, 0);
            }
        });

        Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Admin.setChecked(true);
                Customer.setChecked(false);
                Account_Type = "Admin";
            }
        });

        Customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Customer.setChecked(true);
                Admin.setChecked(false);
                Account_Type = "Customer";
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (encodedPhoto ==null)
                {
                    Toast.makeText(Signup.this,"Kindly select a profile photo.",Toast.LENGTH_SHORT).show();
                }
                else if (Name.getText().toString().length()==0)
                {
                    Toast.makeText(Signup.this,"Kindly enter profile name.",Toast.LENGTH_SHORT).show();
                }
                else if (Email.getText().toString().length()==0)
                {
                    Toast.makeText(Signup.this,"Kindly enter profile email.",Toast.LENGTH_SHORT).show();
                }
                else if (Phone.getText().toString().length()==0)
                {
                    Toast.makeText(Signup.this,"Kindly enter profile phone number.",Toast.LENGTH_SHORT).show();
                }
                else if (User_Name.getText().toString().length()==0)
                {
                    Toast.makeText(Signup.this,"Kindly enter profile username.",Toast.LENGTH_SHORT).show();
                }
                else if (Password.getText().toString().length()==0)
                {
                    Toast.makeText(Signup.this,"Kindly enter profile password.",Toast.LENGTH_SHORT).show();
                }
                else if (Account_Type==null)
                {
                    Toast.makeText(Signup.this,"Kindly select account type.",Toast.LENGTH_SHORT).show();
                }
                else if (!Email.getText().toString().contains("@") || !Email.getText().toString().contains(".com"))
                {
                    Toast.makeText(Signup.this,"Kindly enter valid email. Email must contain @ and .com.",Toast.LENGTH_SHORT).show();
                }
                else if (Phone.getText().toString().length()<10)
                {
                    Toast.makeText(Signup.this,"Kindly enter valid phone number. Length can not be less than 10.",Toast.LENGTH_SHORT).show();
                }
                else if (User_Name.getText().toString().length()<6)
                {
                    Toast.makeText(Signup.this,"Kindly enter valid username. Length can not be less than 6.",Toast.LENGTH_SHORT).show();
                }
                else if (Password.getText().toString().length()<6)
                {
                    Toast.makeText(Signup.this,"Kindly enter valid password. Length can not be less than 6.",Toast.LENGTH_SHORT).show();
                }
                else if (User_Names_List.contains(User_Name.getText().toString()))
                {
                    Toast.makeText(Signup.this,"Kindly enter another username. Username already used.",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Registered").child(User_Name.getText().toString());
                    dbRef.child("Picture").setValue(encodedPhoto);
                    dbRef.child("Name").setValue(Name.getText().toString());
                    dbRef.child("Email").setValue(Email.getText().toString());
                    dbRef.child("Phone").setValue(Phone.getText().toString());
                    dbRef.child("Username").setValue(User_Name.getText().toString());
                    dbRef.child("Password").setValue(Password.getText().toString());
                    dbRef.child("Account_Type").setValue(Account_Type);

                    Toast.makeText(Signup.this,"Registration successful.",Toast.LENGTH_SHORT).show();

                    Intent Main = new Intent(Signup.this,MainActivity.class);
                    startActivity(Main);
                }
            }
        });

        FirebaseDatabase.getInstance().getReference("Registered").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {
                User_Names_List.add(dataSnapshot.child("Username").getValue(String.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
        {
            final Uri ImageUri = data.getData();
            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), ImageUri);
                ByteArrayOutputStream Byte_Stream = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, Byte_Stream);
                byte[] bytes = Byte_Stream.toByteArray();
                encodedPhoto = Base64.encodeToString(bytes, Base64.DEFAULT);
                Profile_Pic.setImageURI(ImageUri);
                Image_Selection_Intent = false;
            }
            catch (IOException e) {
                Toast.makeText(Signup.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }

}