package com.example.hospital_management_system;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospital_management_system.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    int day, month, year, hour, minute;
    int myday, myMonth, myYear, myHour, myMinute;
    public static String name,phNo,email;
    Context context;
    private MyViewHolder.OnNoteListener mOnNoteListener;
    public static String f;
    public static String A="0";
    DatabaseReference refr;
    DatabaseReference ok;

    ArrayList<User> list;

    public MyAdapter(Context context, ArrayList<User> list, MyViewHolder.OnNoteListener onNoteListener) {
        this.context = context;
        this.list = list;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return  new MyViewHolder(v,mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user = list.get(position);
        holder.Name.setText(user.getName());
        holder.Availability.setText(user.getAvailability());
        holder.dUid.setText(user.getdUid());

        holder.Specialization.setText(user.getSpecialization());
//        String uid = list.get(position).getUid();
        holder.getAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(),MyAdapter.this,year, month,day);
                datePickerDialog.show();
                f =list.get(position).getdUid();

//                ok = FirebaseDatabase.getInstance().getReference().child("count");
//                ok.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                        A=snapshot.getValue(String.class);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
                int l = Integer.parseInt(A);
                l++;
                A=String.valueOf(l);
//                ok.setValue(A);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        myYear = year;
        myday = day;
        myMonth = month;
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(datePicker.getContext(), MyAdapter.this, hour, minute, DateFormat.is24HourFormat(datePicker.getContext()));
        timePickerDialog.show();

    }

    @Override
    public void onTimeSet(TimePicker timePicker,int hourOfDay, int minute) {
        myHour = hourOfDay;
        myMinute = minute;

//        textView.setText("Year: " + myYear + "\n" +
//                "Month: " + myMonth + "\n" +
//                "Day: " + myday + "\n" +
//                "Hour: " + myHour + "\n" +
//                "Minute: " + myMinute);
//       List<String> person=new ArrayList<>();
//       person.add("1")
        refr = FirebaseDatabase.getInstance().getReference("users").child(f).child("upcomingApp").child(A);
        HashMap patInfo = new HashMap();
        patInfo.put("name",name);
        patInfo.put("PhoneNo",phNo);
        patInfo.put("email",email);
        patInfo.put("scheduledDate",myday+"/"+myMonth+"/"+myYear);
        patInfo.put("scheduledTime",myHour+":"+myMinute);
        refr.updateChildren(patInfo).addOnCompleteListener(new OnCompleteListener() {

            @Override
            public void onComplete(@NonNull Task task) {

                if(task.isSuccessful()){
                    Toast.makeText(context.getApplicationContext(), "You Got The Appointment", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context.getApplicationContext(),task.getException().toString() , Toast.LENGTH_SHORT).show();
                }



            }
        });

    }



    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Name, Availability, Specialization, dUid;
        Button getAppointment;
        OnNoteListener mOnNoteListener;

        public MyViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);

            Name = itemView.findViewById(R.id.tvName);
            Availability = itemView.findViewById(R.id.tvAvailability);
            Specialization = itemView.findViewById(R.id.tvSpecialization);
            getAppointment = itemView.findViewById(R.id.tvAppButton);
            dUid = itemView.findViewById(R.id.tvUid);
            mOnNoteListener = onNoteListener;

            getAppointment.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d("yooyoyy", "onClick: " + getAdapterPosition());
            mOnNoteListener.onNoteClick(getAdapterPosition());

        }



        public interface OnNoteListener{
            void onNoteClick(int position);
        }
    }
}
