package com.example.hospital_management_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class SelectRegistrationActivity extends AppCompatActivity {
        private TextView back;
        private Button patientReg,doctorReg,nurseReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_registration);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectRegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        patientReg = findViewById(R.id.patientReg);
        patientReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectRegistrationActivity.this, PatientRegistrationActivity.class);
                startActivity(intent);
            }
        });
        doctorReg = findViewById(R.id.doctorReg);
        doctorReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectRegistrationActivity.this, DoctorRegistrationActivity.class);
                startActivity(intent);
            }
        });

        nurseReg = findViewById(R.id.nurseReg);
        nurseReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectRegistrationActivity.this, nurseRegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}