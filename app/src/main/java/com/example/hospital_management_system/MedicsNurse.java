package com.example.hospital_management_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MedicsNurse extends AppCompatActivity {
    private Button setXray,setCt,setUltrasound,setMri,setOv;
    private Spinner xray,ct,ultrasound,mri,ov;
    private DatabaseReference medicsDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medics_nurse);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setXray = findViewById(R.id.setXray);
        setCt = findViewById(R.id.setCt);
        setUltrasound = findViewById(R.id.setUltrasound);
        setMri = findViewById(R.id.setMri);
        setOv = findViewById(R.id.setOv);

        xray = findViewById(R.id.xray);
        ct = findViewById(R.id.ct);
        ultrasound = findViewById(R.id.ultrasound);
        mri = findViewById(R.id.mri);
        ov = findViewById(R.id.ov);

        setXray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = xray.getSelectedItem().toString();
                medicsDatabaseRef = FirebaseDatabase.getInstance().getReference().child("medics");
                HashMap userInfo = new HashMap();

                userInfo.put("xray",a);
                medicsDatabaseRef.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MedicsNurse.this, "Details set successfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MedicsNurse.this,task.getException().toString() , Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

        setCt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String b = ct.getSelectedItem().toString();
                medicsDatabaseRef = FirebaseDatabase.getInstance().getReference().child("medics");
                HashMap userInfo = new HashMap();

                userInfo.put("ct",b);
                medicsDatabaseRef.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MedicsNurse.this, "Details set successfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MedicsNurse.this,task.getException().toString() , Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

        setUltrasound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String c = ultrasound.getSelectedItem().toString();
                medicsDatabaseRef = FirebaseDatabase.getInstance().getReference().child("medics");
                HashMap userInfo = new HashMap();

                userInfo.put("ultrasound",c);
                medicsDatabaseRef.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MedicsNurse.this, "Details set successfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MedicsNurse.this,task.getException().toString() , Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

        setOv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String d = ov.getSelectedItem().toString();
                medicsDatabaseRef = FirebaseDatabase.getInstance().getReference().child("medics");
                HashMap userInfo = new HashMap();

                userInfo.put("ov",d);
                medicsDatabaseRef.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MedicsNurse.this, "Details set successfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MedicsNurse.this,task.getException().toString() , Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

        setMri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e = mri.getSelectedItem().toString();
                medicsDatabaseRef = FirebaseDatabase.getInstance().getReference().child("medics");
                HashMap userInfo = new HashMap();

                userInfo.put("mri",e);
                medicsDatabaseRef.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MedicsNurse.this, "Details set successfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MedicsNurse.this,task.getException().toString() , Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }
}