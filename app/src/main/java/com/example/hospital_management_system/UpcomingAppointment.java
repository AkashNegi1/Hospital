package com.example.hospital_management_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UpcomingAppointment extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference database2;
    MyAdapter2 myAdapter2;
    ArrayList<User2> list;
    //    private ActivityMakeAppointmentBinding binding;
    private Button makeAppButton;
    public static String did;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_appointment);



//        binding = ActivityMakeAppointmentBinding.inflate(getLayoutInflater());
//        View view = binding.getRoot();
//        setContentView(view);
//


        recyclerView = findViewById(R.id.patientList);
        database2 = FirebaseDatabase.getInstance().getReference().child("users").child(did).child("upcomingApp");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter2 = new MyAdapter2(this,list);
        recyclerView.setAdapter(myAdapter2);

        database2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                for (DataSnapshot dataSnapshot : snapshot.getChildren()){


                        User2 user = dataSnapshot.getValue(User2.class);
                        list.add(user);


                }
                myAdapter2.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







    }
    }
