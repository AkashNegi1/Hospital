package com.example.hospital_management_system;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter1 extends RecyclerView.Adapter<MyAdapter1.MyViewHolder>{


    Context context;

    ArrayList<User> list;

    public MyAdapter1(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public MyAdapter1.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item1,parent,false);
        return  new MyAdapter1.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter1.MyViewHolder holder, int position) {
        User user = list.get(position);
        holder.Name.setText(user.getName());
        holder.Availability.setText(user.getAvailability());
        holder.Specialization.setText(user.getSpecialization());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Name, Availability, Specialization;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.tvName);
            Availability = itemView.findViewById(R.id.tvAvailability);
            Specialization = itemView.findViewById(R.id.tvSpecialization);


        }
    }
}
