package com.example.hospital_management_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hospital_management_system.databinding.ActivityMakeAppointmentBinding;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hospital_management_system.databinding.ActivityDashboardPateintBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class makeAppointment extends AppCompatActivity implements MyAdapter.MyViewHolder.OnNoteListener {
    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList<User> list;
//    private ActivityMakeAppointmentBinding binding;
    private Button makeAppButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_appointment);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);



//        binding = ActivityMakeAppointmentBinding.inflate(getLayoutInflater());
//        View view = binding.getRoot();
//        setContentView(view);
//


        recyclerView = findViewById(R.id.doctorList);
        database = FirebaseDatabase.getInstance().getReference("users");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new MyAdapter(this,list,this);
        recyclerView.setAdapter(myAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String type = dataSnapshot.child("type").getValue(String.class);
                    if(type.equals("doctor")) {
                        User user = dataSnapshot.getValue(User.class);
                        list.add(user);
                    }

                }
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







    }


    @Override
    public void onNoteClick(int position) {

        list.get(position);
    }
}