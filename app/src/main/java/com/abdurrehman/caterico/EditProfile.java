package com.abdurrehman.caterico;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditProfile extends AppCompatActivity {

    Button Back, Save;
    ImageView Profile_Pic;
    String encodedPhoto = null;
    EditText Name,Email,Phone,User_Name,Password;
    RadioButton Admin,Customer;
    String Account_Type = null;
    boolean Image_Selection_Intent = false;

    List<String> User_Names_List = new ArrayList<String>();
    Button sign_out;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        sign_out = findViewById(R.id.signout);
        builder = new AlertDialog.Builder(this);

        Back = findViewById(R.id.back);
        Profile_Pic = findViewById(R.id.pic);
        Name =findViewById(R.id.name);
        Email = findViewById(R.id.email);
        Phone = findViewById(R.id.phone);
        User_Name = findViewById(R.id.uname);
        Password = findViewById(R.id.pwd);
        Admin = findViewById(R.id.btn_admin);
        Customer = findViewById(R.id.btn_customer);
        Save = findViewById(R.id.save);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Back = new Intent(EditProfile.this,UserProfile.class);
                Back.putExtra("Username",getIntent().getExtras().getString("Username"));
                Back.putExtra("Type",getIntent().getExtras().getString("Type"));
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

        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setMessage(R.string.alert_content) .setTitle(R.string.alert_title)
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                                Intent intent = new Intent(EditProfile.this,MainActivity.class);
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

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (encodedPhoto ==null)
                {
                    Toast.makeText(EditProfile.this,"Kindly select a profile photo.",Toast.LENGTH_SHORT).show();
                }
                else if (Name.getText().toString().length()==0)
                {
                    Toast.makeText(EditProfile.this,"Kindly enter profile name.",Toast.LENGTH_SHORT).show();
                }
                else if (Email.getText().toString().length()==0)
                {
                    Toast.makeText(EditProfile.this,"Kindly enter profile email.",Toast.LENGTH_SHORT).show();
                }
                else if (Phone.getText().toString().length()==0)
                {
                    Toast.makeText(EditProfile.this,"Kindly enter profile phone number.",Toast.LENGTH_SHORT).show();
                }
                else if (Password.getText().toString().length()==0)
                {
                    Toast.makeText(EditProfile.this,"Kindly enter profile password.",Toast.LENGTH_SHORT).show();
                }
                else if (!Email.getText().toString().contains("@") || !Email.getText().toString().contains(".com"))
                {
                    Toast.makeText(EditProfile.this,"Kindly enter valid email. Email must contain @ and .com.",Toast.LENGTH_SHORT).show();
                }
                else if (Phone.getText().toString().length()<10)
                {
                    Toast.makeText(EditProfile.this,"Kindly enter valid phone number. Length can not be less than 10.",Toast.LENGTH_SHORT).show();
                }
                else if (Password.getText().toString().length()<6)
                {
                    Toast.makeText(EditProfile.this,"Kindly enter valid password. Length can not be less than 6.",Toast.LENGTH_SHORT).show();
                }
                else if (User_Names_List.contains(User_Name.getText().toString()))
                {
                    Toast.makeText(EditProfile.this,"Kindly enter another username. Username already used.",Toast.LENGTH_SHORT).show();
                }
                else
                {

                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Registered").child(User_Name.getText().toString());
                    dbRef.child("Picture").setValue(encodedPhoto);
                    dbRef.child("Name").setValue(Name.getText().toString());
                    dbRef.child("Email").setValue(Email.getText().toString());
                    dbRef.child("Phone").setValue(Phone.getText().toString());
                    dbRef.child("Password").setValue(Password.getText().toString());

                    Intent Profile = new Intent(EditProfile.this,UserProfile.class);
                    Profile.putExtra("Type",getIntent().getExtras().getString("Type"));
                    Profile.putExtra("Username",User_Name.getText().toString());
                    startActivity(Profile);
                }
            }
        });

        FirebaseDatabase.getInstance().getReference("Registered").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {
                if (!getIntent().getExtras().getString("Username").contentEquals(dataSnapshot.child("Username").getValue(String.class))) {
                    User_Names_List.add(dataSnapshot.child("Username").getValue(String.class));
                }
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

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Registered").child(getIntent().getExtras().getString("Username"));

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Name.setText(dataSnapshot.child("Name").getValue(String.class));

                Email.setText(dataSnapshot.child("Email").getValue(String.class));

                Phone.setText(dataSnapshot.child("Phone").getValue(String.class));

                User_Name.setText(dataSnapshot.child("Username").getValue(String.class));

                User_Name.setEnabled(false);

                Password.setText(dataSnapshot.child("Password").getValue(String.class));

                if (dataSnapshot.child("Account_Type").getValue(String.class).length()==5)
                {
                    Admin.setChecked(true);
                    Customer.setChecked(false);
                    Admin.setEnabled(false);
                    Customer.setEnabled(false);
                    Account_Type = "Admin";
                }
                else
                {
                    Customer.setChecked(true);
                    Admin.setChecked(false);
                    Account_Type = "Customer";
                    Admin.setEnabled(false);
                    Customer.setEnabled(false);
                }

                encodedPhoto =dataSnapshot.child("Picture").getValue(String.class);

                byte[] decodedString = Base64.decode(encodedPhoto, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                Profile_Pic.setImageBitmap(decodedByte);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(EditProfile.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!Image_Selection_Intent) {
            finish();
        }
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
                Toast.makeText(EditProfile.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }

}