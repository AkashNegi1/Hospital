package com.example.hospital_management_system;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorRegistrationActivity extends AppCompatActivity {
    private TextView backToLogin;
    private TextInputEditText dregistrationFullName, dregistrationIdNumber, dregistrationPhoneNumber, doctorRegMailId, doctorRegPassword;
    private CircleImageView dprofileImage;
    private Spinner availabilitySpinner,departmentSpinner,specializationSpinner;
    private Button regButton;
    public Uri resultUri;
    private FirebaseAuth mAuth;
    private FirebaseStorage Storage;
    private StorageReference storageReference;
    private DatabaseReference userDatabaseRef;
    public String downloadUrlDoctor;

    private ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_registration);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        backToLogin = findViewById(R.id.backToLogin);
        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorRegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        dregistrationFullName = findViewById(R.id.dregistrationFullName);
        dregistrationPhoneNumber = findViewById(R.id.dregistrationPhoneNumber);
        dregistrationIdNumber = findViewById(R.id.dregistrationIdNumber);
        doctorRegMailId = findViewById(R.id.doctorRegMailId);
        doctorRegPassword = findViewById(R.id.doctorRegPassword);
        regButton = findViewById(R.id.regButton);
        dprofileImage = findViewById(R.id.dprofileImage);
        departmentSpinner = (Spinner)findViewById(R.id.departmentSpinner);
        specializationSpinner =(Spinner)findViewById(R.id.specializationSpinner);
        availabilitySpinner =(Spinner)findViewById(R.id.availabilitySpinner);

        loader = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        dprofileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });


        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = doctorRegMailId.getText().toString().trim();
                final String password = doctorRegPassword.getText().toString().trim();
                final String fullName = dregistrationFullName.getText().toString().trim();
                final String idNumber = dregistrationIdNumber.getText().toString().trim();
                final String phoneNumber = dregistrationPhoneNumber.getText().toString().trim();

                final String department = departmentSpinner.getSelectedItem().toString().trim();
                final String specialization = specializationSpinner.getSelectedItem().toString().trim();
                final String availability = availabilitySpinner.getSelectedItem().toString().trim();
                final String imgUri = resultUri.toString();


                if (TextUtils.isEmpty(email)) {
                    doctorRegMailId.setError("Email is Required!");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    doctorRegPassword.setError("Password is Required!");
                    return;
                }
                if (TextUtils.isEmpty(fullName)) {
                    dregistrationFullName.setError("Name is Required!");
                    return;
                }
                if (TextUtils.isEmpty(idNumber)) {
                    dregistrationIdNumber.setError("IdNumber is Required!");
                    return;
                }
                if (TextUtils.isEmpty(phoneNumber)) {
                    dregistrationPhoneNumber.setError("PhoneNumber is Required!");
                    return;
                }

                if(resultUri==null){
                    Toast.makeText(DoctorRegistrationActivity.this, "profile is required", Toast.LENGTH_SHORT).show();
                }
                else{

                    loader.setMessage("Registration in progress...");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                String currentUserId = mAuth.getCurrentUser().getUid();
                                userDatabaseRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);
//                                userDatabaseRef.setValue("name",fullName);
//                                userDatabaseRef.setValue("email",email);
//                                userDatabaseRef.setValue("idnumber",idNumber);
//                                userDatabaseRef.setValue("phonenumber",phoneNumber);
//                                userDatabaseRef.setValue("department",department);
//                                userDatabaseRef.setValue("specialization",specialization);
//                                userDatabaseRef.setValue("availability",availability);
                                HashMap doctorInfo = new HashMap();

                                doctorInfo.put("name",fullName);
                                doctorInfo.put("email",email);
                                doctorInfo.put("idnumber",idNumber);
                                doctorInfo.put("phonenumber",phoneNumber);

                                doctorInfo.put("department",department);
                                doctorInfo.put("specialization",specialization);

                                doctorInfo.put("availability",availability);
                                doctorInfo.put("type","doctor");
                                doctorInfo.put("ImgUri",downloadUrlDoctor);
                                doctorInfo.put("dUid",currentUserId);


                                userDatabaseRef.updateChildren(doctorInfo).addOnCompleteListener(new OnCompleteListener() {

                                    @Override
                                    public void onComplete(@NonNull Task task) {

                                        if(task.isSuccessful()){
                                            Toast.makeText(DoctorRegistrationActivity.this, "Details set successfully", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText(DoctorRegistrationActivity.this,task.getException().toString() , Toast.LENGTH_SHORT).show();
                                        }
                                        finish();
                                        loader.dismiss();
                                    }
                                });


                                Intent intent = new Intent(DoctorRegistrationActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                                loader.dismiss();
                            }
                            else{

                                String error = task.getException().toString();
                                Toast.makeText(DoctorRegistrationActivity.this, "Error Occurred: "+error, Toast.LENGTH_SHORT).show();}
                        }

                    });
                }
            }
        });


    }



    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startForResult.launch(intent);
    }
    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result!=null&&result.getResultCode()==RESULT_OK){
                if(result.getData()!=null ){
                    Intent data = result.getData();
                    resultUri = data.getData();
                    dprofileImage.setImageURI(resultUri);
                    uploadImage();

                }
            }
        }
    });

    public void uploadImage(){
        final String randomKey = UUID.randomUUID().toString();
// Create a reference to "mountains.jpg"
        FirebaseStorage storage = FirebaseStorage.getInstance(); // add this
        storageReference = storage.getReference(); // and this
        StorageReference mountainsRef = storageReference.child("image/"+randomKey);



        mountainsRef.putFile(resultUri).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(DoctorRegistrationActivity.this, "Failed to Upload Image", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override

            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mountainsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        downloadUrlDoctor = uri.toString();
                    }
                });

                Toast.makeText(DoctorRegistrationActivity.this, "Successfully Uploaded Image", Toast.LENGTH_SHORT).show();
            }
        });
    }
}




