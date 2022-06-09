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

public class bedsAvailability extends AppCompatActivity {
    private TextView NoOfIcuBeds,NoOfGenWardBeds;
    DatabaseReference database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beds_availability);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        database = FirebaseDatabase.getInstance().getReference("beds");
        NoOfGenWardBeds = (TextView) findViewById(R.id.NoOfGenWardBeds);
        NoOfIcuBeds = (TextView) findViewById(R.id.NoOfIcuBeds);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String a = snapshot.child("NoOfIcuBeds").getValue(String.class);
                String b = snapshot.child("NoOfGenBeds").getValue(String.class);
                NoOfGenWardBeds.setText(b);
                NoOfIcuBeds.setText(a);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }


}