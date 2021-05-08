package com.abdurrehman.caterico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView Register;
    Button SignIn_Customer,SignIn_Admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Register = findViewById(R.id.register);
        SignIn_Admin = findViewById(R.id.admin);
        SignIn_Customer = findViewById(R.id.customer);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Registration_Intent = new Intent(MainActivity.this,Signup.class);
                startActivity(Registration_Intent);
            }
        });

        SignIn_Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SignIn_Admin_Intent = new Intent(MainActivity.this,Signin.class);
                SignIn_Admin_Intent.putExtra("Type","Admin");
                startActivity(SignIn_Admin_Intent);
            }
        });

        SignIn_Customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SignIn_Customer_Intent = new Intent(MainActivity.this,Signin.class);
                SignIn_Customer_Intent.putExtra("Type","Customer");
                startActivity(SignIn_Customer_Intent);
            }
        });

    }
}