package com.example.hospital_management_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class BedsNurse extends AppCompatActivity {
    private Button setGen,setIcu;
    private EditText NoOfIcuBeds,NoOfGenWardBeds;
    private DatabaseReference bedsDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beds_nurse);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setGen = findViewById(R.id.setGen);
        setIcu = findViewById(R.id.setIcu);
        NoOfGenWardBeds = findViewById(R.id.NoOfGenWardBeds);
        NoOfIcuBeds = findViewById(R.id.NoOfIcuBeds);

        setGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String b = NoOfGenWardBeds.getText().toString();
                bedsDatabaseRef = FirebaseDatabase.getInstance().getReference().child("beds");
                HashMap userInfo = new HashMap();

                userInfo.put("NoOfGenBeds",b);
                bedsDatabaseRef.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Toast.makeText(BedsNurse.this, "Details set successfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(BedsNurse.this,task.getException().toString() , Toast.LENGTH_SHORT).show();
                        }

            }
        });
            }
        });

        setIcu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = NoOfIcuBeds.getText().toString();
                bedsDatabaseRef = FirebaseDatabase.getInstance().getReference().child("beds");
                HashMap userInfo = new HashMap();
                userInfo.put("NoOfIcuBeds",a);
                bedsDatabaseRef.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Toast.makeText(BedsNurse.this, "Details set successfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(BedsNurse.this,task.getException().toString() , Toast.LENGTH_SHORT).show();
                        }

            }
        });

    }
});
    }
}