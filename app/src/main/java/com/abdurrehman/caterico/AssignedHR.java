package com.abdurrehman.caterico;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AssignedHR extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Button sign_out,back,save;
    AlertDialog.Builder builder;
    TextView em_employee;
    TextView emp_decor;
    TextView emp_logistics;
    TextView emp_cook;
    List<String> hr_persons = new ArrayList<>();

    Spinner spin1;
    Spinner spin2;
    Spinner spin3;
    Spinner spin4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigned_h_r);
        sign_out = findViewById(R.id.signout);
        back = findViewById(R.id.back);
        save = findViewById(R.id.save);

        hr_persons.add("-Select-");

        builder = new AlertDialog.Builder(this);

        em_employee = findViewById(R.id.em_employee);
        emp_decor = findViewById(R.id.emp_decor);
        emp_cook = findViewById(R.id.emp_cook);
        emp_logistics = findViewById(R.id.emp_logistics);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, hr_persons);
        ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, hr_persons);
        ArrayAdapter<String> adapter3= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, hr_persons);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, hr_persons);

        spin1 = findViewById(R.id.spinner1);
        spin2 = findViewById(R.id.spinner2);
        spin3 = findViewById(R.id.spinner3);
        spin4 = findViewById(R.id.spinner4);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(adapter);
        spin1.setOnItemSelectedListener(this);
        spin2.setAdapter(adapter2);
        spin2.setOnItemSelectedListener(this);
        spin3.setAdapter(adapter3);
        spin3.setOnItemSelectedListener(this);
        spin4.setAdapter(adapter4);
        spin4.setOnItemSelectedListener(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Order = new Intent(AssignedHR.this,OrderDetails.class);
                Order.putExtra("Order_Id",getIntent().getExtras().getString("Order_Id"));
                Order.putExtra("Type",getIntent().getExtras().getString("Type"));
                Order.putExtra("Username", getIntent().getExtras().getString("Username"));
                startActivity(Order);
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (em_employee.getText().toString().equals("-Select-"))
                {
                    Toast.makeText(AssignedHR.this, "Kindly select event manager.", Toast.LENGTH_SHORT).show();
                }
                else if (emp_cook.getText().toString().equals("-Select-"))
                {
                    Toast.makeText(AssignedHR.this, "Kindly select event head cook.", Toast.LENGTH_SHORT).show();
                }
                else if (emp_decor.getText().toString().equals("-Select-"))
                {
                    Toast.makeText(AssignedHR.this, "Kindly select event head decor.", Toast.LENGTH_SHORT).show();
                }
                else if (emp_logistics.getText().toString().equals("-Select-"))
                {
                    Toast.makeText(AssignedHR.this, "Kindly select event head logistics.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    DatabaseReference db = FirebaseDatabase.getInstance()
                            .getReference("Assigned_Hr")
                            .child(getIntent().getExtras().getString("Order_Id"));
                    db.child("EM").setValue(em_employee.getText().toString());
                    db.child("HC").setValue(emp_cook.getText().toString());
                    db.child("HD").setValue(emp_decor.getText().toString());
                    db.child("HL").setValue(emp_logistics.getText().toString());

                    Intent Order = new Intent(AssignedHR.this,OrderDetails.class);
                    Order.putExtra("Order_Id",getIntent().getExtras().getString("Order_Id"));
                    Order.putExtra("Type",getIntent().getExtras().getString("Type"));
                    Order.putExtra("Username", getIntent().getExtras().getString("Username"));
                    startActivity(Order);
                    finish();
                }
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
                                Intent intent = new Intent(AssignedHR.this,MainActivity.class);
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

        FirebaseDatabase.getInstance()
                .getReference("Assigned_Hr")
                .child(getIntent().getExtras().getString("Order_Id"))
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("EM")) {
                    spin1.setSelection(hr_persons.indexOf(snapshot.child("EM").getValue()));
                    spin2.setSelection(hr_persons.indexOf(snapshot.child("HC").getValue()));
                    spin3.setSelection(hr_persons.indexOf(snapshot.child("HD").getValue()));
                    spin4.setSelection(hr_persons.indexOf(snapshot.child("HL").getValue()));

                    em_employee.setText(snapshot.child("EM").getValue().toString());

                    emp_cook.setText(snapshot.child("HC").getValue().toString());

                    emp_decor.setText(snapshot.child("HD").getValue().toString());

                    emp_logistics.setText(snapshot.child("HL").getValue().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference("HumanResources").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                hr_persons.add(snapshot.child("Name").getValue()+" ("+snapshot.child("Id").getValue()+" )");
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        em_employee.setText(spin1.getSelectedItem().toString());

        emp_cook.setText(spin2.getSelectedItem().toString());

        emp_decor.setText(spin3.getSelectedItem().toString());

        emp_logistics.setText(spin4.getSelectedItem().toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}