package com.example.hospital_management_system;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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

public class nurseRegistrationActivity extends AppCompatActivity {
    private TextView backToLogin;
    private TextInputEditText registrationFullName, registrationIdNumber, registrationPhoneNumber, pateintRegMailId, pateintRegPassword;
    private CircleImageView profileImage;
    private Button regButton;
    public Uri resultUri;
    private FirebaseAuth mAuth;
    private FirebaseStorage Storage;
    private StorageReference storageReference;
    private DatabaseReference userDatabaseRef;
    private ProgressDialog loader;
    public String downloadUrlNurse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_registration);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Storage = FirebaseStorage.getInstance();
        storageReference = Storage.getReference();

        backToLogin = findViewById(R.id.backToLogin);
        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(nurseRegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        registrationFullName = findViewById(R.id.registrationFullName);
        registrationPhoneNumber = findViewById(R.id.registrationPhoneNumber);
        registrationIdNumber = findViewById(R.id.registrationIdNumber);
        pateintRegMailId = findViewById(R.id.pateintRegMailId);
        pateintRegPassword = findViewById(R.id.pateintRegPassword);
        regButton = findViewById(R.id.regButton);
        profileImage = findViewById(R.id.profileImage);

        loader = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });


        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = pateintRegMailId.getText().toString().trim();
                final String password = pateintRegPassword.getText().toString().trim();
                final String fullName = registrationFullName.getText().toString().trim();
                final String idNumber = registrationIdNumber.getText().toString().trim();
                final String phoneNumber = registrationPhoneNumber.getText().toString().trim();
                final String imgUri = resultUri.toString();

                if (TextUtils.isEmpty(email)) {
                    pateintRegMailId.setError("Email is Required!");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    pateintRegPassword.setError("Password is Required!");
                    return;
                }
                if (TextUtils.isEmpty(fullName)) {
                    registrationFullName.setError("Name is Required!");
                    return;
                }
                if (TextUtils.isEmpty(idNumber)) {
                    registrationIdNumber.setError("IdNumber is Required!");
                    return;
                }
                if (TextUtils.isEmpty(phoneNumber)) {
                    registrationPhoneNumber.setError("PhoneNumber is Required!");
                    return;
                }
                if(resultUri==null){
                    Toast.makeText(nurseRegistrationActivity.this, "profile is required", Toast.LENGTH_SHORT).show();
                }
                else{
                    loader.setMessage("Registration in progress...");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();
                    Log.d("yoyoyoyoyoyoyo", "reached to mauth ");
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                String currentUserId = mAuth.getCurrentUser().getUid();
                                userDatabaseRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);
                                HashMap userInfo = new HashMap();
                                userInfo.put("name",fullName);
                                userInfo.put("email",email);
                                userInfo.put("idnumber",idNumber);
                                userInfo.put("phonenumber",phoneNumber);
                                userInfo.put("type","nurse");
                                userInfo.put("ImgUri",downloadUrlNurse);



                                userDatabaseRef.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(nurseRegistrationActivity.this, "Details set successfully", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText(nurseRegistrationActivity.this,task.getException().toString() , Toast.LENGTH_SHORT).show();
                                        }
                                        finish();
                                        loader.dismiss();
                                    }
                                });


                                Intent intent = new Intent(nurseRegistrationActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                                loader.dismiss();
                            }
                            else{

                                String error = task.getException().toString();
                                Toast.makeText(nurseRegistrationActivity.this, "Error Occurred: "+error, Toast.LENGTH_SHORT).show();}
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

                    profileImage.setImageURI(resultUri);
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
                Toast.makeText(nurseRegistrationActivity.this, "Failed to Upload Image", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override

            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mountainsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        downloadUrlNurse = uri.toString();
                    }
                });

                Toast.makeText(nurseRegistrationActivity.this, "Successfully Uploaded Image", Toast.LENGTH_SHORT).show();
            }
        });
    }
}