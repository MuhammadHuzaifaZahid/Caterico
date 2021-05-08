package com.abdurrehman.caterico;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Orders extends AppCompatActivity {
    Button sign_out,back;
    Button create;
    AlertDialog.Builder builder;

    RecyclerView Rv_Ongoing,Rv_Completed;
    List<String> Ongoing_List = new ArrayList<String>();
    List<String> Completed_List = new ArrayList<String>();
    private RecyclerViewAdapterOrders Adapter_Ongoing,Adapter_Completed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        sign_out = findViewById(R.id.signout);
        create = findViewById(R.id.create);
        builder = new AlertDialog.Builder(this);
        Rv_Ongoing = findViewById(R.id.recycler_view1);
        Rv_Completed = findViewById(R.id.recycler_view2);
        back = findViewById(R.id.back);

        LinearLayoutManager layoutManager_ongoing = new LinearLayoutManager(this);
        Rv_Ongoing.setHasFixedSize(true);
        Rv_Ongoing.setLayoutManager(layoutManager_ongoing);
        Adapter_Ongoing = new RecyclerViewAdapterOrders(Ongoing_List);
        Rv_Ongoing.setAdapter(Adapter_Ongoing);
        Adapter_Ongoing.notifyDataSetChanged();

        LinearLayoutManager layoutManager_completed = new LinearLayoutManager(this);
        Rv_Completed.setHasFixedSize(true);
        Rv_Completed.setLayoutManager(layoutManager_completed);
        Adapter_Completed = new RecyclerViewAdapterOrders(Completed_List);
        Rv_Completed.setAdapter(Adapter_Completed);
        Adapter_Completed.notifyDataSetChanged();

        FirebaseDatabase.getInstance().getReference("Orders").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {

                Order Temp = dataSnapshot.getValue(Order.class);
                if (Temp!=null) {
                    if (Temp.getStatus().equals("Completed")) {
                        Completed_List.add(Temp.getOrder_Id() + "-" + Temp.getOrder_Title()
                                + "-" + getIntent().getExtras().getString("Type")
                                + "-" + getIntent().getExtras().getString("Username"));
                        Adapter_Completed.notifyDataSetChanged();
                    }
                    else {
                        Ongoing_List.add(Temp.getOrder_Id() + "-" + Temp.getOrder_Title()
                                + "-" + getIntent().getExtras().getString("Type")
                                + "-" + getIntent().getExtras().getString("Username"));
                        Adapter_Ongoing.notifyDataSetChanged();
                    }
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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Admin_Page = new Intent(Orders.this,AdminPage.class);
                Admin_Page.putExtra("Type",getIntent().getExtras().getString("Type"));
                Admin_Page.putExtra("Username", getIntent().getExtras().getString("Username"));
                startActivity(Admin_Page);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Orders.this,CreateOrder.class);
                intent.putExtra("Type",getIntent().getExtras().getString("Type"));
                intent.putExtra("Username", getIntent().getExtras().getString("Username"));
                startActivity(intent);
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
                                Intent intent = new Intent(Orders.this,MainActivity.class);
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