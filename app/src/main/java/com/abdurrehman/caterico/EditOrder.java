package com.abdurrehman.caterico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.AdapterView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditOrder extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    AlertDialog.Builder builder;
    Button sign_out,back,save;
    TextView new_status;
    String [] available_status = {"New","In Progress","Completed"};

    EditText Id,Title,Date,Time,Desc;

    Order Temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order);

        sign_out = findViewById(R.id.signout);
        new_status = findViewById(R.id.new_status);
        back = findViewById(R.id.back);
        save = findViewById(R.id.save);

        Id = findViewById(R.id.order_id);
        Title = findViewById(R.id.order_title);
        Date = findViewById(R.id.due_date);
        Time = findViewById(R.id.due_time);
        Desc = findViewById(R.id.order_desc);

        Id.setEnabled(false);

        builder = new AlertDialog.Builder(this);
        Spinner spin1 = findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, available_status);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(adapter);
        spin1.setOnItemSelectedListener(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Order = new Intent(EditOrder.this,OrderDetails.class);
                Order.putExtra("Order_Id",getIntent().getExtras().getString("Order_Id"));
                Order.putExtra("Type",getIntent().getExtras().getString("Type"));
                Order.putExtra("Username", getIntent().getExtras().getString("Username"));
                startActivity(Order);
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
                                Intent intent = new Intent(EditOrder.this,MainActivity.class);
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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference db = FirebaseDatabase.getInstance().getReference("Orders").child(Id.getText().toString());

                Temp.setOrder_Id(Id.getText().toString());
                Temp.setOrder_Title(Title.getText().toString());
                Temp.setOrder_Date(Date.getText().toString());
                Temp.setOrder_Time(Time.getText().toString());
                Temp.setOrder_Description(Desc.getText().toString());
                Temp.setStatus(new_status.getText().toString());

                db.setValue(Temp);

                Intent Order = new Intent(EditOrder.this,OrderDetails.class);
                Order.putExtra("Order_Id",getIntent().getExtras().getString("Order_Id"));
                Order.putExtra("Type",getIntent().getExtras().getString("Type"));
                Order.putExtra("Username", getIntent().getExtras().getString("Username"));
                startActivity(Order);
            }
        });

        FirebaseDatabase.getInstance().getReference("Orders").child(getIntent().getExtras().getString("Order_Id")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Temp = snapshot.getValue(Order.class);
                Id.setText(Temp.getOrder_Id());
                Title.setText(Temp.getOrder_Title());
                Date.setText(Temp.getOrder_Date());
                Time.setText(Temp.getOrder_Time());
                Desc.setText(Temp.getOrder_Description());
                new_status.setText(Temp.getStatus());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        new_status.setText(available_status[position]);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}