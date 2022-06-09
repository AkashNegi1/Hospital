package com.example.hospital_management_system.ui.slideshow;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hospital_management_system.LoginActivity;
import com.example.hospital_management_system.PatientRegistrationActivity;
import com.example.hospital_management_system.R;
import com.example.hospital_management_system.databinding.FragmentSlideshowBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class SlideshowFragment extends Fragment  {

    private FragmentSlideshowBinding binding;
    private StorageReference storage;
    private View mView;
    public ImageView profile_image;
    private TextView fullname_field;
    private TextInputLayout full_name_profile,phone_no_profile,Email_id_profile;
    private Button Update_button;
    private DatabaseReference reference;
    public static String a,b,c,UID;
    public static String img;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {




        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mView = inflater.inflate(R.layout.fragment_slideshow, container, false);

        binding.fullnameField.setText(a);
        binding.fullNameProfile.getEditText().setText(a);
        binding.EmailIdProfile.getEditText().setText(c);
        binding.phoneNoProfile.getEditText().setText(b);


        storage = FirebaseStorage.getInstance().getReference();

//        StorageReference photoReference= storage.child(img);

        Picasso.with(getContext()).load(img).centerCrop().fit().into(binding.profileImage);

        reference = FirebaseDatabase.getInstance().getReference("users").child(UID);

        binding.UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = binding.EmailIdProfile.getEditText().getText().toString().trim();
                final String fullName = binding.fullNameProfile.getEditText().getText().toString().trim();
                final String phoneNumber = binding.phoneNoProfile.getEditText().getText().toString().trim();
                HashMap userInfo = new HashMap();
                userInfo.put("name",fullName);
                userInfo.put("email",email);
                userInfo.put("phonenumber",phoneNumber);
                reference.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(), "Details set successfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getContext(),task.getException().toString() , Toast.LENGTH_SHORT).show();
                        }


                    }
                });

                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });


//        final TextView textView = binding.textSlideshow;
//        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }




}