package com.example.hospital_management_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class medicsAvailability extends AppCompatActivity {
    private TextView xray,ct,ultrasound,mri,ov;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medics_availability);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        database = FirebaseDatabase.getInstance().getReference("medics");
        xray = (TextView) findViewById(R.id.xray);
        ct = (TextView) findViewById(R.id.ct);
        ultrasound = (TextView) findViewById(R.id.ultrasound);
        mri = (TextView) findViewById(R.id.mri);
        ov = (TextView) findViewById(R.id.ov);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String a = snapshot.child("xray").getValue(String.class);
                String b = snapshot.child("ct").getValue(String.class);
                String c = snapshot.child("ultrasound").getValue(String.class);
                String d = snapshot.child("mri").getValue(String.class);
                String e = snapshot.child("ov").getValue(String.class);
                xray.setText(a);
                ct.setText(b);
                ultrasound.setText(c);
                mri.setText(d);
                ov.setText(e);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}