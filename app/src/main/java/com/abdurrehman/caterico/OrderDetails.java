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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrderDetails extends AppCompatActivity{
    Button sign_out,back;
    Button delete;
    Button edit;
    Button hr;
    Button inventory;
    AlertDialog.Builder builder;
    AlertDialog.Builder builder2;
    TextView Id,Title,Date,Time,Desc,Status,Budget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        sign_out = findViewById(R.id.signout);
        back = findViewById(R.id.back);
        delete = findViewById(R.id.delete);
        builder = new AlertDialog.Builder(this);
        builder2 = new AlertDialog.Builder(this);
        edit = findViewById(R.id.edit);
        hr = findViewById(R.id.view_hr);
        inventory = findViewById(R.id.view_inv);

        Id = findViewById(R.id.order_id);
        Title = findViewById(R.id.order_title);
        Date = findViewById(R.id.due_date);
        Time = findViewById(R.id.due_time);
        Desc = findViewById(R.id.order_desc);
        Status = findViewById(R.id.status);
        Budget = findViewById(R.id.budget);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Order = new Intent(OrderDetails.this,Orders.class);
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
                                Intent intent = new Intent(OrderDetails.this,MainActivity.class);
                                startActivity(intent);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setMessage(R.string.delete_content) .setTitle(R.string.delete_title)
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
//                                Intent intent = new Intent(OrderDetails.this,MainActivity.class);
//                                startActivity(intent);

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

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderDetails.this,EditOrder.class);
                intent.putExtra("Type",getIntent().getExtras().getString("Type"));
                intent.putExtra("Username", getIntent().getExtras().getString("Username"));
                intent.putExtra("Order_Id",getIntent().getExtras().getString("Order_Id"));
                startActivity(intent);
            }
        });

        hr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderDetails.this,AssignedHR.class);
                intent.putExtra("Type",getIntent().getExtras().getString("Type"));
                intent.putExtra("Username", getIntent().getExtras().getString("Username"));
                intent.putExtra("Order_Id",getIntent().getExtras().getString("Order_Id"));
                startActivity(intent);
            }
        });

        inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderDetails.this,AssignedInventory.class);
                intent.putExtra("Type",getIntent().getExtras().getString("Type"));
                intent.putExtra("Username", getIntent().getExtras().getString("Username"));
                intent.putExtra("Order_Id",getIntent().getExtras().getString("Order_Id"));
                startActivity(intent);
            }
        });

        FirebaseDatabase.getInstance().getReference("Orders").child(getIntent().getExtras().getString("Order_Id")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Order Temp = snapshot.getValue(Order.class);
                Id.setText(Temp.getOrder_Id());
                Title.setText(Temp.getOrder_Title());
                Date.setText(Temp.getOrder_Date());
                Time.setText(Temp.getOrder_Time());
                Desc.setText(Temp.getOrder_Description());
                Status.setText(Temp.getStatus());
                Budget.setText(Temp.getBudget());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



}
