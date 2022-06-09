package com.example.hospital_management_system;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder>{





        Context context;

        ArrayList<User2> list;

public MyAdapter2(Context context, ArrayList<User2> list) {
        this.context = context;
        this.list = list;
        }
@NonNull
@Override
public MyAdapter2.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item2,parent,false);
        return  new MyAdapter2.MyViewHolder(v);
        }

@Override
public void onBindViewHolder(@NonNull MyAdapter2.MyViewHolder holder, int position) {
        User2 user = list.get(position);
    holder.name.setText(user.getName());
    holder.PhoneNo.setText(user.getPhoneNo());
    holder.email.setText(user.getEmail());
    holder.scheduledDate.setText(user.getScheduledDate());
    holder.scheduledTime.setText(user.getScheduledTime());

        }

@Override
public int getItemCount() {
        return list.size();
        }

public class MyViewHolder extends RecyclerView.ViewHolder{
    TextView name,PhoneNo,email,scheduledDate ,scheduledTime;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.pName);
        PhoneNo = itemView.findViewById(R.id.pPhoneNo);
        email = itemView.findViewById(R.id.pMail);
        scheduledDate  = itemView.findViewById(R.id.pSDate);
        scheduledTime = itemView.findViewById(R.id.pSTime);


    }
}
}

