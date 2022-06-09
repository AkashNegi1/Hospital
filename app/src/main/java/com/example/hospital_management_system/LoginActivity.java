package com.example.hospital_management_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hospital_management_system.ui.home.HomeFragment;
import com.example.hospital_management_system.ui.slideshow.SlideshowFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
        private TextView loginPageQuestion;
        private Button loginButton;
        private TextInputEditText loginEmail,loginPassword;
        private FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);



        loginPageQuestion = findViewById(R.id.loginPageQuestion);
        loginPageQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, SelectRegistrationActivity.class);
                startActivity(intent);
            }
        });
        fAuth = FirebaseAuth.getInstance();

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                final String email = loginEmail.getText().toString().trim();
                final String password = loginPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    loginEmail.setError("Email is Required!");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    loginPassword.setError("Password is Required!");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){


                            String uid = fAuth.getCurrentUser().getUid();

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
                ref.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        DataSnapshot snapshot = task.getResult();
                        String type = snapshot.child("type").getValue(String.class);
                        String imgUri = snapshot.child("ImgUri").getValue(String.class);

                        String nameFromDB = snapshot.child("name").getValue(String.class);
                        String phoneNoFromDB = snapshot.child("phonenumber").getValue(String.class);
                        String emailFromDB = snapshot.child("email").getValue(String.class);
                        SlideshowFragment x = new SlideshowFragment();
                        x.a=nameFromDB;
                        x.b=phoneNoFromDB ;
                        x.c= emailFromDB;
                        x.img=imgUri;
                        x.UID=uid;

                        MyAdapter.name=nameFromDB;
                        MyAdapter.phNo=phoneNoFromDB ;
                        MyAdapter.email= emailFromDB;

                        UpcomingAppointment.did=uid;
//                        Intent dntent = new Intent(getApplicationContext(), SlideshowFragment.class);
//                        dntent.putExtra("name", nameFromDB);
//                        dntent.putExtra("email", emailFromDB);
//                        dntent.putExtra("phonenumber", phoneNoFromDB);
//                        startActivity(dntent);

                            HomeFragment set = new HomeFragment();
                            set.setDash(type);
                            Intent intent = new Intent(LoginActivity.this,DashboardPateint.class);
                            intent.putExtra(imgUri,1);
                            startActivity(intent);

                            Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();


                    }
                });



  //                         else if(type.equals("admin")){}
                        }
                        else
                            Toast.makeText(LoginActivity.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}