package com.example.hospital_management_system.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hospital_management_system.BedsNurse;
import com.example.hospital_management_system.MedicsNurse;
import com.example.hospital_management_system.R;
import com.example.hospital_management_system.UpcomingAppointment;
import com.example.hospital_management_system.bedsAvailability;
import com.example.hospital_management_system.databinding.FragmentHomeBinding;
import com.example.hospital_management_system.doctorsAvailability;
import com.example.hospital_management_system.makeAppointment;
import com.example.hospital_management_system.medicsAvailability;


public class HomeFragment extends Fragment {


    private View mView;
    private FragmentHomeBinding binding;
    private CardView makeAppointment, doctorsAvailability, bedsAvailability, medicsAvailability,upcomingApp,bedsNurse,medicNurse;
public static String type;
    public void setDash(String t){

         type = t;

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mView = inflater.inflate(R.layout.fragment_home, container, false);


        if(type.equals("patient")){
            binding.makeAppointment.setVisibility(View.VISIBLE);
            binding.doctorsAvailability.setVisibility(View.VISIBLE);
            binding.bedsAvailability.setVisibility(View.VISIBLE);
            binding.medicsAvailability.setVisibility(View.VISIBLE);
            binding.upcomingApp.setVisibility(View.GONE);
            binding.medicNurse.setVisibility(View.GONE);
            binding.bedsNurse.setVisibility(View.GONE);
        }
        else if(type.equals("doctor")){
            binding.makeAppointment.setVisibility(View.GONE);
            binding.doctorsAvailability.setVisibility(View.GONE);
            binding.bedsAvailability.setVisibility(View.VISIBLE);
            binding.medicsAvailability.setVisibility(View.VISIBLE);
            binding.upcomingApp.setVisibility(View.VISIBLE);
            binding.medicNurse.setVisibility(View.GONE);
            binding.bedsNurse.setVisibility(View.GONE);
        }
        else if(type.equals("nurse")){
            binding.makeAppointment.setVisibility(View.GONE);
            binding.doctorsAvailability.setVisibility(View.VISIBLE);
            binding.bedsAvailability.setVisibility(View.VISIBLE);
            binding.medicsAvailability.setVisibility(View.VISIBLE);
            binding.upcomingApp.setVisibility(View.GONE);
            binding.medicNurse.setVisibility(View.VISIBLE);
            binding.bedsNurse.setVisibility(View.VISIBLE);
        }
        else if(type.equals("admin")){
            binding.makeAppointment.setVisibility(View.VISIBLE);
            binding.doctorsAvailability.setVisibility(View.VISIBLE);
            binding.bedsAvailability.setVisibility(View.VISIBLE);
            binding.medicsAvailability.setVisibility(View.VISIBLE);
            binding.upcomingApp.setVisibility(View.VISIBLE);
            binding.medicNurse.setVisibility(View.VISIBLE);
            binding.bedsNurse.setVisibility(View.VISIBLE);
        }



        binding.makeAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), makeAppointment.class);
                startActivity(i);
            }
        });
        binding.doctorsAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), doctorsAvailability.class);
                startActivity(i);
            }
        });
        binding.bedsAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), bedsAvailability.class);
                startActivity(i);
            }
        });
        binding.medicsAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), medicsAvailability.class);
                startActivity(i);
            }
        });
        binding.bedsNurse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), BedsNurse.class);
                startActivity(i);
            }
        });
        binding.medicNurse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), MedicsNurse.class);
                startActivity(i);
            }
        });

        binding.upcomingApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), UpcomingAppointment.class);
                startActivity(i);
            }
        });


//        final TextView textView = binding.;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}