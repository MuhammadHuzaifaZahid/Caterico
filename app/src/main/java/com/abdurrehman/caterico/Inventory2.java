package com.abdurrehman.caterico;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Inventory2 extends AppCompatActivity {
    Button sign_out,Back,SAVE;
    AlertDialog.Builder builder;
    TextView QP,QS,QF,QG,QT,NQP,NQS,NQF,NQG,NQT;
    Button SubP,AddP,SubS,AddS,SubF,AddF,SubG,AddG,SubT,AddT;

    int maxP,maxS,maxF,maxG,maxT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory2);

        sign_out = findViewById(R.id.signout);
        SAVE = findViewById(R.id.save);
        Back = findViewById(R.id.back);
        QP = findViewById(R.id.current_plates);
        QS = findViewById(R.id.current_spoons);
        QF = findViewById(R.id.current_forks);
        QG = findViewById(R.id.current_glasses);
        QT = findViewById(R.id.current_tables);
        NQP = findViewById(R.id.new_plates);
        NQS = findViewById(R.id.new_spoons);
        NQF = findViewById(R.id.new_forks);
        NQG = findViewById(R.id.new_glasses);
        NQT = findViewById(R.id.new_tables);

        SubP = findViewById(R.id.subP);
        SubS = findViewById(R.id.subS);
        SubF = findViewById(R.id.subF);
        SubG = findViewById(R.id.subG);
        SubT = findViewById(R.id.subT);

        AddP = findViewById(R.id.addP);
        AddS = findViewById(R.id.addS);
        AddF = findViewById(R.id.addF);
        AddG = findViewById(R.id.addG);
        AddT = findViewById(R.id.addT);

        SubP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(NQP.getText().toString());
                if (n>0)
                {
                    NQP.setText((n-1)+"");
                }
            }
        });

        SubS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(NQS.getText().toString());
                if (n>0)
                {
                    NQS.setText((n-1)+"");
                }
            }
        });

        SubF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(NQF.getText().toString());
                if (n>0)
                {
                    NQF.setText((n-1)+"");
                }
            }
        });

        SubG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(NQG.getText().toString());
                if (n>0)
                {
                    NQG.setText((n-1)+"");
                }
            }
        });

        SubT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(NQT.getText().toString());
                if (n>0)
                {
                    NQT.setText((n-1)+"");
                }
            }
        });

        AddP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(NQP.getText().toString());
                if (n<maxP)
                {
                    NQP.setText((n+1)+"");
                }
            }
        });

        AddS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(NQS.getText().toString());
                if (n<maxS)
                {
                    NQS.setText((n+1)+"");
                }
            }
        });

        AddF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(NQF.getText().toString());
                if (n<maxF)
                {
                    NQF.setText((n+1)+"");
                }
            }
        });

        AddG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(NQG.getText().toString());
                if (n<maxG)
                {
                    NQG.setText((n+1)+"");
                }
            }
        });

        AddT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(NQT.getText().toString());
                if (n<maxT)
                {
                    NQT.setText((n+1)+"");
                }
            }
        });

        builder = new AlertDialog.Builder(this);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Order = new Intent(Inventory2.this,OrderDetails.class);
                Order.putExtra("Order_Id",getIntent().getExtras().getString("Order_Id"));
                Order.putExtra("Type",getIntent().getExtras().getString("Type"));
                Order.putExtra("Username", getIntent().getExtras().getString("Username"));
                startActivity(Order);
            }
        });

        SAVE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference snapshot = FirebaseDatabase.getInstance()
                        .getReference("Assigned_Inventory")
                        .child(getIntent().getExtras().getString("Order_Id"));
                snapshot.child("Plates").setValue(NQP.getText().toString());
                snapshot.child("Spoons").setValue(NQS.getText().toString());
                snapshot.child("Forks").setValue(NQF.getText().toString());
                snapshot.child("Glasses").setValue(NQG.getText().toString());
                snapshot.child("Tables").setValue(NQT.getText().toString());

                snapshot = FirebaseDatabase.getInstance().getReference("Inventory");
                snapshot.child("Plates").setValue(maxP-Integer.parseInt(NQP.getText().toString())+-Integer.parseInt(QP.getText().toString()));
                snapshot.child("Spoons").setValue(maxS-Integer.parseInt(NQS.getText().toString())+-Integer.parseInt(QS.getText().toString()));
                snapshot.child("Forks").setValue(maxF-Integer.parseInt(NQF.getText().toString())+-Integer.parseInt(QF.getText().toString()));
                snapshot.child("Glasses").setValue(maxG-Integer.parseInt(NQG.getText().toString())+-Integer.parseInt(QG.getText().toString()));
                snapshot.child("Tables").setValue(maxT-Integer.parseInt(NQT.getText().toString())+-Integer.parseInt(QT.getText().toString()));

                Intent Order = new Intent(Inventory2.this,OrderDetails.class);
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
                                Intent intent = new Intent(Inventory2.this,MainActivity.class);
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

        FirebaseDatabase.getInstance().getReference("Assigned_Inventory")
                .child(getIntent().getExtras().getString("Order_Id"))
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    QP.setText(snapshot.child("Plates").getValue().toString());
                    QS.setText(snapshot.child("Spoons").getValue().toString());
                    QF.setText(snapshot.child("Forks").getValue().toString());
                    QG.setText(snapshot.child("Glasses").getValue().toString());
                    QT.setText(snapshot.child("Tables").getValue().toString());

                    NQP.setText(snapshot.child("Plates").getValue().toString());
                    NQS.setText(snapshot.child("Spoons").getValue().toString());
                    NQF.setText(snapshot.child("Forks").getValue().toString());
                    NQG.setText(snapshot.child("Glasses").getValue().toString());
                    NQT.setText(snapshot.child("Tables").getValue().toString());

                }
                else
                {
                    QP.setText("0");
                    QS.setText("0");
                    QF.setText("0");
                    QG.setText("0");
                    QT.setText("0");

                    NQP.setText("0");
                    NQS.setText("0");
                    NQF.setText("0");
                    NQG.setText("0");
                    NQT.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference("Inventory").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                maxP = Integer.parseInt(snapshot.child("Plates").getValue().toString());
                maxS = Integer.parseInt(snapshot.child("Spoons").getValue().toString());
                maxF = Integer.parseInt(snapshot.child("Forks").getValue().toString());
                maxG = Integer.parseInt(snapshot.child("Glasses").getValue().toString());
                maxT = Integer.parseInt(snapshot.child("Tables").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}