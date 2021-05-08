package com.abdurrehman.caterico;

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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateOrder extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Button sign_out;
    Button back;
    Button hr;
    Button inventory;
    AlertDialog.Builder builder;
    TextView new_status;
    String [] available_status = {"-Status-","New","In Progress","Completed"};
    EditText Order_Id,Order_Title,Order_Date,Order_Time,Order_Description,Budget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        Order_Id = findViewById(R.id.order_id);
        Order_Title = findViewById(R.id.order_title);
        Order_Date = findViewById(R.id.due_date);
        Order_Time = findViewById(R.id.due_time);
        Order_Description = findViewById(R.id.order_desc);
        Budget = findViewById(R.id.budget);
        back = findViewById(R.id.back);
        sign_out = findViewById(R.id.signout);
        hr = findViewById(R.id.assign_hr);
        new_status = findViewById(R.id.new_status);
        inventory = findViewById(R.id.assign_inventory);
        Spinner spin1 = findViewById(R.id.spinner1);
        builder = new AlertDialog.Builder(this);
        Order_Id.setEnabled(false);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Order = new Intent(CreateOrder.this,Orders.class);
                Order.putExtra("Type",getIntent().getExtras().getString("Type"));
                Order.putExtra("Username", getIntent().getExtras().getString("Username"));
                startActivity(Order);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, available_status);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(adapter);
        spin1.setOnItemSelectedListener(this);

        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setMessage(R.string.alert_content) .setTitle(R.string.alert_title)
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                                Intent intent = new Intent(CreateOrder.this,MainActivity.class);
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

        hr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Order_Title.getText().toString().length()==0)
                {
                    Toast.makeText(CreateOrder.this, "Kindly enter order title.", Toast.LENGTH_SHORT).show();
                }
                else if (Order_Date.getText().toString().length()==0)
                {
                    Toast.makeText(CreateOrder.this, "Kindly enter due date.", Toast.LENGTH_SHORT).show();
                }
                else if (Order_Time.getText().toString().length()==0)
                {
                    Toast.makeText(CreateOrder.this, "Kindly enter due time.", Toast.LENGTH_SHORT).show();
                }
                else if (Order_Description.getText().toString().length()==0)
                {
                    Toast.makeText(CreateOrder.this, "Kindly enter order description.", Toast.LENGTH_SHORT).show();
                }
                else if (Budget.getText().toString().length()==0)
                {
                    Toast.makeText(CreateOrder.this, "Kindly enter order budget.", Toast.LENGTH_SHORT).show();
                }
                else if (new_status.getText().toString().equals("-Status-"))
                {
                    Toast.makeText(CreateOrder.this, "Kindly select order status.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("Orders").child(Order_Id.getText().toString());
                    Order Temp = new Order();

                    Temp.setOrder_Id(Order_Id.getText().toString());
                    Temp.setOrder_Title(Order_Title.getText().toString());
                    Temp.setOrder_Date(Order_Date.getText().toString());
                    Temp.setOrder_Time(Order_Time.getText().toString());
                    Temp.setOrder_Description(Order_Description.getText().toString());
                    Temp.setBudget(Budget.getText().toString());
                    Temp.setStatus(new_status.getText().toString());
                    Temp.setAdded_By(getIntent().getExtras().getString("Username"));

                    db.setValue(Temp);

                    Intent intent = new Intent(CreateOrder.this,AssignHr.class);
                    intent.putExtra("Type",getIntent().getExtras().getString("Type"));
                    intent.putExtra("Username", getIntent().getExtras().getString("Username"));
                    intent.putExtra("Order_Id",Order_Id.getText().toString());
                    startActivity(intent);
                }
            }
        });
        inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Order_Title.getText().toString().length()==0)
                {
                    Toast.makeText(CreateOrder.this, "Kindly enter order title.", Toast.LENGTH_SHORT).show();
                }
                else if (Order_Date.getText().toString().length()==0)
                {
                    Toast.makeText(CreateOrder.this, "Kindly enter due date.", Toast.LENGTH_SHORT).show();
                }
                else if (Order_Time.getText().toString().length()==0)
                {
                    Toast.makeText(CreateOrder.this, "Kindly enter due time.", Toast.LENGTH_SHORT).show();
                }
                else if (Order_Description.getText().toString().length()==0)
                {
                    Toast.makeText(CreateOrder.this, "Kindly enter order description.", Toast.LENGTH_SHORT).show();
                }
                else if (Budget.getText().toString().length()==0)
                {
                    Toast.makeText(CreateOrder.this, "Kindly enter order budget.", Toast.LENGTH_SHORT).show();
                }
                else if (new_status.getText().toString().equals("-Status-"))
                {
                    Toast.makeText(CreateOrder.this, "Kindly select order status.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("Orders").child(Order_Id.getText().toString());
                    Order Temp = new Order();

                    Temp.setOrder_Id(Order_Id.getText().toString());
                    Temp.setOrder_Title(Order_Title.getText().toString());
                    Temp.setOrder_Date(Order_Date.getText().toString());
                    Temp.setOrder_Time(Order_Time.getText().toString());
                    Temp.setOrder_Description(Order_Description.getText().toString());
                    Temp.setBudget(Budget.getText().toString());
                    Temp.setStatus(new_status.getText().toString());
                    Temp.setAdded_By(getIntent().getExtras().getString("Username"));

                    db.setValue(Temp);

                    Intent intent = new Intent(CreateOrder.this,Inventory2.class);
                    intent.putExtra("Type",getIntent().getExtras().getString("Type"));
                    intent.putExtra("Username", getIntent().getExtras().getString("Username"));
                    intent.putExtra("Order_Id",Order_Id.getText().toString());
                    startActivity(intent);
                }

            }
        });

        FirebaseDatabase.getInstance().getReference("Orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Order_Id.setText("O_"+(dataSnapshot.getChildrenCount()+1));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(CreateOrder.this,error.getMessage(),Toast.LENGTH_SHORT).show();
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