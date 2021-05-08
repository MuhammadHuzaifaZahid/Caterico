package com.abdurrehman.caterico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Inventory extends AppCompatActivity {
    Button sign_out,Back,SAVE;
    AlertDialog.Builder builder;
    TextView QP,QS,QF,QG,QT,NQP,NQS,NQF,NQG,NQT;
    Button SubP,AddP,SubS,AddS,SubF,AddF,SubG,AddG,SubT,AddT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

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
                if (n<10000)
                {
                    NQP.setText((n+1)+"");
                }
            }
        });

        AddS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(NQS.getText().toString());
                if (n<10000)
                {
                    NQS.setText((n+1)+"");
                }
            }
        });

        AddF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(NQF.getText().toString());
                if (n<10000)
                {
                    NQF.setText((n+1)+"");
                }
            }
        });

        AddG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(NQG.getText().toString());
                if (n<10000)
                {
                    NQG.setText((n+1)+"");
                }
            }
        });

        AddT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(NQT.getText().toString());
                if (n<10000)
                {
                    NQT.setText((n+1)+"");
                }
            }
        });

        builder = new AlertDialog.Builder(this);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Admin_Page = new Intent(Inventory.this,AdminPage.class);
                Admin_Page.putExtra("Type",getIntent().getExtras().getString("Type"));
                Admin_Page.putExtra("Username", getIntent().getExtras().getString("Username"));
                startActivity(Admin_Page);
            }
        });

        SAVE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference snapshot = FirebaseDatabase.getInstance().getReference("Inventory");
                snapshot.child("Plates").setValue(NQP.getText().toString());
                snapshot.child("Spoons").setValue(NQS.getText().toString());
                snapshot.child("Forks").setValue(NQF.getText().toString());
                snapshot.child("Glasses").setValue(NQG.getText().toString());
                snapshot.child("Tables").setValue(NQT.getText().toString());

                Intent Admin_Page = new Intent(Inventory.this,AdminPage.class);
                Admin_Page.putExtra("Type",getIntent().getExtras().getString("Type"));
                Admin_Page.putExtra("Username", getIntent().getExtras().getString("Username"));
                startActivity(Admin_Page);
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
                                Intent intent = new Intent(Inventory.this,MainActivity.class);
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

        FirebaseDatabase.getInstance().getReference("Inventory").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}